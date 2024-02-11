package com.szogunn.demonextdoorbook.mappers;

import com.szogunn.demonextdoorbook.model.Rate;
import com.szogunn.demonextdoorbook.model.User;

public class MappersUtil {
    private MappersUtil() {
        //private constructor due to utils character of the class
    }

    protected static double getUserRate(User user) {
        return user.getExchanges()
                .stream()
                .mapToDouble(ex -> {
                    Rate rate = ex.getRate();
                    if (rate != null){
                        return rate.getValue();
                    }
                    return 0;
                })
                .filter(rate -> rate != 0)
                .reduce((a, b) -> (a + b) / 2)
                .stream()
                .sum();
    }
}
