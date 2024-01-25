package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.jwt.JwtUtils;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.JwtInfoResponse;
import com.szogunn.demonextdoorbook.payloads.LoginRequest;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import com.szogunn.demonextdoorbook.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signupRequest) {
        if (userRepository.existsUserByEmail(signupRequest.email())){
            return new ResponseEntity<>(new MessageResponse("Error: Email is already taken"), HttpStatus.BAD_REQUEST);
        }

        userRepository.save(new User(signupRequest.login()
                , encoder.encode(signupRequest.password())
                , signupRequest.email()
                , signupRequest.address()));
        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> logIn(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException ex){
            return new ResponseEntity<>(new MessageResponse("Incorrect password or username"), HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtils.generateToken(loginRequest.login());
        return new ResponseEntity<>(new JwtInfoResponse(loginRequest.login(), token), HttpStatus.OK);
    }

}
