package com.szogunn.demonextdoorbook.dtos;

import java.time.LocalDate;

public record ExchangeDTO(
        BookDTO bookDTO,
        LocalDate startRent,
        LocalDate endRent

) {
}
