package com.szogunn.demonextdoorbook.dtos;

public record NotificationDTO(
        Long id,
        UserDTO receiver,
        String description
) {
}
