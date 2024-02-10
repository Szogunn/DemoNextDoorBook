package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Exchange;
import com.szogunn.demonextdoorbook.model.ExchangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findExchangeByBook_IdAndEndRentAfter(Long bookId, LocalDate startRent);
    List<Exchange> findExchangesByStatusIsAndEndRentBefore(ExchangeStatus status, LocalDate endDate);
    List<Exchange> findExchangesByBook_User_IdAndStatusIsIn(Long userId, ExchangeStatus [] statuses);
    @Query("SELECT e from Exchange e WHERE (e.renter.id = ?1 AND e.book.user.id = ?2 and e.status = ?3) OR (e.renter.id = ?2 AND e.book.user.id = ?1 and e.status = ?3)")
    List<Exchange> findExchangesBetweenUsersInStatus(Long renterId, Long ownerId, ExchangeStatus status);
    List<Exchange> findExchangesByRenter_IdAndStatusIsIn(Long renterId, ExchangeStatus [] statuses);
}
