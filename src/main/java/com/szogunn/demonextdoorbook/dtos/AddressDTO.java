package com.szogunn.demonextdoorbook.dtos;

public record AddressDTO(
        String city,
        String street,
        String houseNumber,
        String zipCode
) {
}
