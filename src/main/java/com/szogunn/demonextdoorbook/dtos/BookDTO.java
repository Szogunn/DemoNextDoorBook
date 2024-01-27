package com.szogunn.demonextdoorbook.dtos;

import com.szogunn.demonextdoorbook.model.Author;

import java.time.LocalDate;
import java.util.Set;

public record BookDTO(
        String tittle,
        String ISBN,
        int numPages,
        String language,
        String publisher,
        LocalDate publishedYear,
        Set<Author> authors
) {
}
