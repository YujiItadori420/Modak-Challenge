package com.modak.challenge.service;

import com.modak.challenge.dto.NotificationDto;
import com.modak.challenge.enums.NotificationType;
import com.modak.challenge.exception.UnprocessableEntityException;
import com.modak.challenge.model.Notification;
import com.modak.challenge.model.User;
import com.modak.challenge.repository.NotificationRepository;
import com.modak.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationDto sendEmail(NotificationDto dto) {
        String destinationMail = dto.getDestinationMail();
        NotificationType notificationType = dto.getType();

        // Validación común para ambos tipos de notificación
        validateNotificationLimit(destinationMail, notificationType);

        // Crear o recuperar el usuario
        User userToSave = userRepository.findByEmail(destinationMail)
                .orElse(User.builder().email(destinationMail).build());
        userRepository.save(userToSave);

        // Crear y guardar la notificación
        Notification notificationToSave = Notification.builder()
                .receiver(userToSave)
                .type(notificationType)
                .message(dto.getMessage())
                .localDateTimeSended(LocalDateTime.now())
                .build();
        notificationRepository.save(notificationToSave);

        return dto;
    }
    private void validateNotificationLimit(String destinationMail, NotificationType notificationType) {
        LocalDateTime now = LocalDateTime.now();
        long emailCount;
        LocalDateTime startTime;

        int limit = switch (notificationType) {
            case STATUS -> {
                startTime = now.minusMinutes(1); // Límite de 2 por minuto
                yield 2;
            }
            case NEWS -> {
                startTime = now.minusDays(1); // Límite de 1 por día
                yield 1;
            }
            case MARKETING -> {
                startTime = now.minusHours(1); // Límite de 3 por hora
                yield 3;
            }
            default -> throw new IllegalArgumentException("Unsupported notification type");
        };

        emailCount = notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                startTime,
                now,
                destinationMail,
                notificationType
        );

        if (emailCount >= limit) {
            throw new UnprocessableEntityException("The email sending limit for the recipient has been exceeded " + destinationMail);
        }
    }
}
