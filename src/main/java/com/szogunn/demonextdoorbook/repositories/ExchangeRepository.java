package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findExchangeByBook_IdAndEndRentAfter(Long bookId, LocalDate startRent);
    List<Exchange> findExchangesByStatusIsAndEndRentBefore(ExchangeStatus status, LocalDate endDate);
    List<Exchange> findExchangesByBook_User_IdAndStatusIsIn(Long userId, ExchangeStatus [] statuses);
}
