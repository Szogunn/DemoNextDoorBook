package com.szogunn.demonextdoorbook.mappers;

public interface Mapper<S, T> {
    T map(S source);
}
