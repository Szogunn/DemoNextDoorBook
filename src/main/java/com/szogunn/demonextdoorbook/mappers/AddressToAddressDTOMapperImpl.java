package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.AddressDTO;
import com.szogunn.demonextdoorbook.model.Address;

public class AddressToAddressDTOMapperImpl implements Mapper<Address, AddressDTO> {

    @Override
    public AddressDTO map(Address source) {
        return new AddressDTO(source.getCity(), source.getStreet(), source.getHouseNumber(), source.getZipCode());
    }
}
