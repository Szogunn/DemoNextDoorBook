package com.szogunn.demonextdoorbook.payloads;

import java.time.LocalDate;

public record RentalRequest(
        Long id,
        LocalDate endRent
) {
}
