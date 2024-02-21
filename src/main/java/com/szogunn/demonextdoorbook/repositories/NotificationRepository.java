package com.szogunn.demonextdoorbook.repositories;

import com.szogunn.demonextdoorbook.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
