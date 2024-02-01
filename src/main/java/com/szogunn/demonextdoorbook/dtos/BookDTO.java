package com.szogunn.demonextdoorbook.dtos;

import java.time.LocalDate;

public record BookDTO(
        Long id,
        String title,
        String ISBN,
        int numPages,
        String language,
        String publisher,
        LocalDate publishedYear,
        UserDTO userDTO
) {
}
