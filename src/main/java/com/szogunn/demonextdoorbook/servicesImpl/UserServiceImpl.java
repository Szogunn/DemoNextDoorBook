package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.jwt.JwtUtils;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.LoginRequest;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import com.szogunn.demonextdoorbook.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    public ResponseEntity<String> signUp(SignupRequest signupRequest) {
        if (userRepository.existsUserByEmail(signupRequest.email())){
            return new ResponseEntity<>("Error: Email is already taken", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(new User(signupRequest.login()
                ,encoder.encode(signupRequest.password())
                ,signupRequest.email()
                ,signupRequest.address()));
        return new ResponseEntity<>("Successfully Registered", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> logIn(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateToken(loginRequest.username());
        return new ResponseEntity<>("{\"message\":\"token:\"}" + token, HttpStatus.OK);
    }

}
