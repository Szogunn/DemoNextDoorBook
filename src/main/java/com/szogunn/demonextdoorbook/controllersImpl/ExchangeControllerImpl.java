package com.szogunn.demonextdoorbook.controllersImpl;

import com.szogunn.demonextdoorbook.controllers.ExchangeController;
import com.szogunn.demonextdoorbook.dtos.ExchangeDTO;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import com.szogunn.demonextdoorbook.services.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExchangeControllerImpl implements ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeControllerImpl(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
    @Override
    public ResponseEntity<?> exchange(Long id, LocalDate endRent) {
        return exchangeService.exchange(id, endRent);
    }

    @Override
    public ResponseEntity<List<ExchangeDTO>> getUserBooksRentalHistory(ExchangeStatus[] exchangeStatuses) {
        return exchangeService.getUserBooksRentalHistory(exchangeStatuses);
    }

    @Override
    public ResponseEntity<?> updateExchangeStatus(Long id, ExchangeStatus exchangeStatus) {
        return exchangeService.changeExchangeStatus(id, exchangeStatus);
    }
}
