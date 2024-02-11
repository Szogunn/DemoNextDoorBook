package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {

    ResponseEntity<?> exchange(Long bookId, LocalDate endRent);
    ResponseEntity<?> changeExchangeStatus(Long exchangeId, ExchangeStatus newStatus);
    ResponseEntity<List<ExchangeDTO>> getUserBooksRentalHistory(ExchangeStatus [] statuses);
    ResponseEntity<?> rateExchange(double rate, Long exchangeId);
}
