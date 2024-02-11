package com.szogunn.demonextdoorbook.model;

public enum Rate {
    ZERO(0.0),
    HALF(0.5),
    ONE(1.0),
    ONE_AND_HALF(1.5),
    TWO(2.0),
    TWO_AND_HALF(2.5),
    THREE(3.0),
    THREE_AND_HALF(3.5),
    FOUR(4.0),
    FOUR_AND_HALF(4.5),
    FIVE(5.0),
    FIVE_AND_HALF(5.5),
    SIX(6.0),
    SIX_AND_HALF(6.5),
    SEVEN(7.0),
    SEVEN_AND_HALF(7.5),
    EIGHT(8.0),
    EIGHT_AND_HALF(8.5),
    NINE(9.0),
    NINE_AND_HALF(9.5),
    TEN(10.0);

    private final double value;

    Rate(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static Rate findByValue(double value){
        for (Rate rate: values()){
            if (rate.getValue() == value){
                return rate;
            }
        }
        return null;
    }

}
