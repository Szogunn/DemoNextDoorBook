package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findExchangeByBook_IdAndEndRentAfter(Long bookId, LocalDate startRent);

    List<Exchange> findExchangesByRenter_IdOrBook_User_Id(Long userId, Long userId2);  //TODO do poprawy arguemnty w funkcji
}
