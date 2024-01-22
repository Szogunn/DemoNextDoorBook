package com.szogunn.demonextdoorbook.payloads;

public record LoginRequest(
        String username,
        String password
) {
}
