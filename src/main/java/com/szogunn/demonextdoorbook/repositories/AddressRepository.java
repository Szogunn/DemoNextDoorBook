package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findAddressByCityAndAndStreetAndHouseNumberAndZipCode(String city, String street, String houseNumber, String zipCode);
}
