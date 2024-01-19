package com.szogunn.demonextdoorbook.payloads;

import com.szogunn.demonextdoorbook.model.Address;

public record SignupRequest(
        String login,
        String email,
        String password,
        Address address
) {
}
