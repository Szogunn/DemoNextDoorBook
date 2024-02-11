package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.model.User;
import org.springframework.stereotype.Component;

import static com.szogunn.demonextdoorbook.mappers.MappersUtil.getUserRate;

@Component
public class UserToUserDTOMapperImpl implements Mapper<User, UserDTO> {
    @Override
    public UserDTO map(User source) {
        return new UserDTO(source.getId(), source.getLogin(), getUserRate(source));
    }

}
