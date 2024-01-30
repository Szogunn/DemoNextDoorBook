package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.payloads.RentalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/exchange")
public interface ExchangeController {

    @PostMapping(path = "/get")
    ResponseEntity<?> exchange(@RequestBody RentalRequest rentalRequest);
}
