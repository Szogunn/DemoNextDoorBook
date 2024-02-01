package com.szogunn.demonextdoorbook.controllers;

import com.szogunn.demonextdoorbook.dtos.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/book")
public interface BookController {

    @PostMapping(path = "/add")
    ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO, Authentication authentication);
    @GetMapping(path = "/show")
    ResponseEntity<List<BookDTO>> showAllBooks();
    @GetMapping(path = "/showNeighbours")
    ResponseEntity<?> showNeighboursBooks();
    @GetMapping(path = "/get/{bookId}")
    ResponseEntity<?> getBook(@PathVariable Long bookId);
}
