package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserController {

    @PostMapping(path = "/signup")
    ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest);
}
