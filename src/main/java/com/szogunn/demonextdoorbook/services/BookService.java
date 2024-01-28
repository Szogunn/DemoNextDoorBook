package com.szogunn.demonextdoorbook.services;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import com.szogunn.demonextdoorbook.jwt.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<?> addBook(BookDTO bookDTO, String username);

    ResponseEntity<List<BookDTO>> showAllBooks(UserDetailsImpl userDetails);
}
