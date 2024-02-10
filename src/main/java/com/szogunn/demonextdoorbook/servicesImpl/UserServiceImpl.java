package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.dtos.AddressDTO;
import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.jwt.JwtUtils;
import com.szogunn.demonextdoorbook.jwt.UserDetailsImpl;
import com.szogunn.demonextdoorbook.mappers.Mapper;
import com.szogunn.demonextdoorbook.mappers.MapperFactory;
import com.szogunn.demonextdoorbook.model.Address;
import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.*;
import com.szogunn.demonextdoorbook.repositories.AddressRepository;
import com.szogunn.demonextdoorbook.repositories.ExchangeRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final AddressRepository addressRepository;
    private final ExchangeRepository exchangeRepository;
    private final Mapper<User, UserDTO> userDTOMapper;
    private final Mapper<Address, AddressDTO> addressDTOMapper;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder, AddressRepository addressRepository, ExchangeRepository exchangeRepository, MapperFactory mapperFactory) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.addressRepository = addressRepository;
        this.exchangeRepository = exchangeRepository;
        this.userDTOMapper = mapperFactory.getMapper(User.class, UserDTO.class);
        this.addressDTOMapper = mapperFactory.getMapper(Address.class, AddressDTO.class);
    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signupRequest) {
        if (userRepository.existsUserByEmailOrLogin(signupRequest.email(), signupRequest.login())){
            return new ResponseEntity<>(new MessageResponse("Error: Email or login is already taken"), HttpStatus.BAD_REQUEST);
        }

        userRepository.save(new User(signupRequest.login()
                , encoder.encode(signupRequest.password())
                , signupRequest.email()));
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

    @Override
    public ResponseEntity<?> addAddress(AddressDTO addressDTO) {
        User user = getAuthenticatedUser();
        Optional<Address> addressFromDb = addressRepository.findAddressByCityAndAndStreetAndHouseNumberAndZipCode(addressDTO.city(), addressDTO.street(), addressDTO.houseNumber(), addressDTO.zipCode());

        if (addressFromDb.isPresent()){
            user.setAddress(addressFromDb.get());
            userRepository.save(user);
            return new ResponseEntity<>(new Response<>(addressDTO,"Address successfully added to your account!"), HttpStatus.CREATED);
        }

        Address address = new Address();
        address.setCity(addressDTO.city());
        address.setStreet(addressDTO.street());
        address.setHouseNumber(addressDTO.houseNumber());
        address.setZipCode(addressDTO.zipCode());
        address.setUsers(Set.of(user));
        user.setAddress(address);

        addressRepository.save(address);
        return new ResponseEntity<>(new Response<>(addressDTO,"Address successfully added to your account!"), HttpStatus.CREATED);
    }

    @Override
    public User getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + userDetails.getUsername()));
    }

    @Override
    public ResponseEntity<?> getUserProfile(Long userId) {
        User authenticatedUser = getAuthenticatedUser();
        List<Exchange> proceed = exchangeRepository.findExchangesBetweenUsersInStatus(userId, authenticatedUser.getId(), ExchangeStatus.ACCEPTED);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Map<String, Object> result = new HashMap<>();
            UserDTO userDTO = userDTOMapper.map(user);
            result.put("userDTO", userDTO);

            List<Exchange> lentBooks = exchangeRepository.findExchangesByRenter_IdAndStatusIsIn(userId, new ExchangeStatus[]{ExchangeStatus.ACCEPTED});
            List<Exchange> borrowedBooks = exchangeRepository.findExchangesByBook_User_IdAndStatusIsIn(userId, new ExchangeStatus[]{ExchangeStatus.ACCEPTED});
            int bookAmount = user.getBooks().size();
            int booksBorrowedAmount = borrowedBooks.size();
            int booksLentAmount = lentBooks.size();
            result.put("bookAmount", bookAmount);
            result.put("booksBorrowedAmount", booksBorrowedAmount);
            result.put("booksLentAmount", booksLentAmount);

            if (!proceed.isEmpty() && user.getAddress() != null) {
                AddressDTO addressDTO = addressDTOMapper.map(user.getAddress());
                result.put("addressDTO", addressDTO);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("Smth went wrong"), HttpStatus.BAD_REQUEST);
    }

}
