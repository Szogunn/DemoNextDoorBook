package com.szogunn.demonextdoorbook.dtos;

import com.szogunn.demonextdoorbook.model.ExchangeStatus;

import java.time.LocalDate;

public record ExchangeDTO(
        Long id,
        BookDTO bookDTO,
        LocalDate startRent,
        LocalDate endRent,
        ExchangeStatus exchangeStatus,
        UserDTO renter
) {
}
