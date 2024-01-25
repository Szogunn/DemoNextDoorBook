package com.szogunn.demonextdoorbook.payloads;

public record JwtInfoResponse (
        String username,
        String token
){
}
