package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/book")
public interface BookController {

    @PostMapping(path = "/add")
    ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO, Authentication authentication);
    @GetMapping(path = "/show")
    ResponseEntity<List<BookDTO>> showAllBooks();
}
