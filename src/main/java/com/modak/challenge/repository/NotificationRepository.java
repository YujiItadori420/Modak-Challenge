package com.modak.challenge.repository;

import com.modak.challenge.enums.NotificationType;
import com.modak.challenge.model.Notification;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /*@Query("SELECT COUNT(n) FROM Notification n WHERE n.localDateTimeSended >= :date AND n.receiver.email = :receiver AND n.type = :type")
    long countByDateAndReceiverAndType(
            @Param("date") LocalDateTime date,
            @Param("receiver") String receiver,
            @Param("type") String type
    );*/

    long countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String receiverEmail,
            NotificationType type
    );

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.receiver.email = :receiverEmail AND n.type = 'NEWS' AND DATE(n.localDateTimeSended) = CURRENT_DATE")
    long countNewsNotificationsByReceiverEmailToday(@Param("receiverEmail") String receiverEmail);

}

