package com.szogunn.demonextdoorbook.mappers;

public interface MapperFactory {
    <S,T> Mapper<S, T> getMapper(Class<S> sourceClass, Class<T> targetClass);
}
