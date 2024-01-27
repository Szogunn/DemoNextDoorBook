package com.szogunn.demonextdoorbook.payloads;

public record SignupRequest(
        String login,
        String email,
        String password
) {
}
