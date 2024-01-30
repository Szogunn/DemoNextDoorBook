package com.szogunn.demonextdoorbook.payloads;

public record Response<T>(T object, String message) {
}
