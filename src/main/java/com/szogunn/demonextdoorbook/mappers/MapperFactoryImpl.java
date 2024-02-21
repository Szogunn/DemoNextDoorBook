package com.szogunn.demonextdoorbook.mappers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapperFactoryImpl implements MapperFactory {
    public static final String CLASS_CONNECTOR = "To";
    public static final String IMPL = "MapperImpl";
    private final Map<String, Mapper<?, ?>> mapperRegistry;

    public MapperFactoryImpl() {
        mapperRegistry = new HashMap<>();
        mapperRegistry.put("BookToBookDTOMapperImpl" , new BookToBookDTOMapperImpl());
        mapperRegistry.put("UserToUserDTOMapperImpl" , new UserToUserDTOMapperImpl());
        mapperRegistry.put("ExchangeToExchangeDTOMapperImpl" , new ExchangeToExchangeDTOMapperImpl());
        mapperRegistry.put("AddressToAddressDTOMapperImpl" , new AddressToAddressDTOMapperImpl());
        mapperRegistry.put("NotificationToNotificationDTOMapperImpl" , new NotificationToNotificationDTOMapperImpl());
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public <S,T> Mapper<S, T> getMapper(Class<S> sourceClass, Class<T> targetClass){
        Mapper<S, T> mapper = (Mapper<S, T>) mapperRegistry.get(sourceClass.getSimpleName() + CLASS_CONNECTOR + targetClass.getSimpleName() + IMPL);

        if (mapper == null){
            throw new IllegalArgumentException(String.format("No mapper registered for object type: %s", sourceClass.getSimpleName()));
        }

        return mapper;
    }

}
