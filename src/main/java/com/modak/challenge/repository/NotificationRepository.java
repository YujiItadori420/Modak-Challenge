package com.modak.challenge.repository;

import com.modak.challenge.enums.NotificationType;
import com.modak.challenge.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String receiverEmail,
            NotificationType type
    );
}

