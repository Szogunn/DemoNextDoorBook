package com.szogunn.demonextdoorbook.payloads;

public record LoginRequest(
        String login,
        String password
) {
}
