package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import org.springframework.http.ResponseEntity;

public interface ExchangeService {

    ResponseEntity<?> exchange(BookDTO bookDTO);
}
