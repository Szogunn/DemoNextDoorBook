package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.dtos.AddressDTO;
import com.szogunn.demonextdoorbook.payloads.LoginRequest;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserController {

    @PostMapping(path = "/signup")
    ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest);

    @PostMapping(path = "/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

    @PostMapping(path = "/add-address")
    ResponseEntity<?> addAddress(@RequestBody AddressDTO addressDTO);

}
