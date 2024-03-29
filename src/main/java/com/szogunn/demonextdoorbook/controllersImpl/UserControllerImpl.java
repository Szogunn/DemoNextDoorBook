package com.szogunn.demonextdoorbook.controllersImpl;

import com.szogunn.demonextdoorbook.controllers.UserController;
import com.szogunn.demonextdoorbook.dtos.AddressDTO;
import com.szogunn.demonextdoorbook.payloads.LoginRequest;
import com.szogunn.demonextdoorbook.payloads.MessageResponse;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import com.szogunn.demonextdoorbook.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signupRequest) {
        try {
            return userService.signUp(signupRequest);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            return userService.logIn(loginRequest);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new MessageResponse("Something went wrong!!!"), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<?> addAddress(AddressDTO addressDTO) {
        return userService.addAddress(addressDTO);
    }

    @Override
    public ResponseEntity<?> getUserProfile(Long userId) {
        return userService.getUserProfile(userId);
    }

}
