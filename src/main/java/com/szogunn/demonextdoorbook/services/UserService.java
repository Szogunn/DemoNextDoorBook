package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.payloads.LoginRequest;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> signUp(SignupRequest signupRequest);
    ResponseEntity<?> logIn(LoginRequest loginRequest);
}
