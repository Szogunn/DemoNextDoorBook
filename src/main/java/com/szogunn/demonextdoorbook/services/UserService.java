package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> signUp(SignupRequest signupRequest);
}
