package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<?> addBook(BookDTO bookDTO, String username);
}
