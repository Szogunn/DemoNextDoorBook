package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import com.szogunn.demonextdoorbook.payloads.RentalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/exchange")
public interface ExchangeController {

    @PostMapping(path = "/get")
    ResponseEntity<?> exchange(@RequestBody RentalRequest rentalRequest);
    @GetMapping(path = "/history")
    ResponseEntity<List<ExchangeDTO>> getUserBooksRentalHistory(@RequestParam("statuses") ExchangeStatus [] statuses);
    @PostMapping(path = "/update-status/{id}")
    ResponseEntity<?> updateExchangeStatus(@PathVariable Long id, @RequestParam ExchangeStatus exchangeStatus);
}
