package com.szogunn.demonextdoorbook.controllersImpl;

import com.szogunn.demonextdoorbook.controllers.UserController;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import com.szogunn.demonextdoorbook.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> signUp(SignupRequest signupRequest) {
        try {

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
