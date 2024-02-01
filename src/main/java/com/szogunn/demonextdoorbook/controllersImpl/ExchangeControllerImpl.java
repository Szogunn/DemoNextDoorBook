package com.szogunn.demonextdoorbook.controllersImpl;

import com.szogunn.demonextdoorbook.controllers.ExchangeController;
import com.szogunn.demonextdoorbook.payloads.RentalRequest;
import com.szogunn.demonextdoorbook.services.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeControllerImpl implements ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeControllerImpl(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
    @Override
    public ResponseEntity<?> exchange(RentalRequest rentalRequest) {
        return exchangeService.exchange(rentalRequest.id(), rentalRequest.endRent());
    }

    @Override
    public ResponseEntity<?> getUserBooksRentalHistory() {
        return exchangeService.getUserBooksRentalHistory();
    }
}
