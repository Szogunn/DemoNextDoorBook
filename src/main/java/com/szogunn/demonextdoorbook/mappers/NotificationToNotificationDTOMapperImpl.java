package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.dtos.NotificationDTO;
import com.szogunn.demonextdoorbook.dtos.UserDTO;
import com.szogunn.demonextdoorbook.model.Notification;
import com.szogunn.demonextdoorbook.model.User;

import static com.szogunn.demonextdoorbook.mappers.MappersUtil.getUserRate;

public class NotificationToNotificationDTOMapperImpl implements Mapper<Notification, NotificationDTO> {
    @Override
    public NotificationDTO map(Notification source) {
        User receiver = source.getReceiver();
        UserDTO userDTO = new UserDTO(receiver.getId(), receiver.getLogin(), getUserRate(receiver));
        return new NotificationDTO(source.getId(), userDTO, source.getDescription());
    }
}
