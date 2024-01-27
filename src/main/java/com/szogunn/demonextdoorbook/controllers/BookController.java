package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/book")
public interface BookController {

    @PostMapping(path = "/add")
    ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO, Authentication authentication);
}
