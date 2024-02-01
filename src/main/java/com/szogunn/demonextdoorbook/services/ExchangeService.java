package com.szogunn.demonextdoorbook.services;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface ExchangeService {

    ResponseEntity<?> exchange(Long bookId, LocalDate endRent);
    ResponseEntity<?> getUserBooksRentalHistory();
}
