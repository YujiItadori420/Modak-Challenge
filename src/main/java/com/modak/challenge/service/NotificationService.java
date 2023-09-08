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

    // Method to send an email notification
    public NotificationDto sendEmail(NotificationDto dto) {
        String destinationMail = dto.getDestinationMail();
        NotificationType notificationType = dto.getType();

        // Common validation for all types of notifications
        validateNotificationLimit(destinationMail, notificationType);

        // Create or retrieve the user
        User userToSave = userRepository.findByEmail(destinationMail)
                .orElse(User.builder().email(destinationMail).build());
        userRepository.save(userToSave);

        // Create and save the notification
        Notification notificationToSave = Notification.builder()
                .receiver(userToSave)
                .type(notificationType)
                .message(dto.getMessage())
                .localDateTimeSended(LocalDateTime.now())
                .build();
        notificationRepository.save(notificationToSave);
        return dto;
    }

    // Method to validate the notification limit for a specific recipient and type
    private void validateNotificationLimit(String destinationMail, NotificationType notificationType) {
        LocalDateTime now = LocalDateTime.now();
        long emailCount;
        LocalDateTime startTime;

        // Determine the limit and start time based on the notification type
        int limit = switch (notificationType) {
            case STATUS -> {
                startTime = now.minusMinutes(1); // Limit of 2 per minute
                yield 2;
            }
            case NEWS -> {
                startTime = now.minusDays(1); // Limit of 1 per day
                yield 1;
            }
            case MARKETING -> {
                startTime = now.minusHours(1); // Limit of 3 per hour
                yield 3;
            }
            default -> throw new IllegalArgumentException("Unsupported notification type");
        };
        // Count the number of emails sent within the specified time frame and type
        emailCount = notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                startTime,
                now,
                destinationMail,
                notificationType
        );
        // Check if the email limit has been exceeded and throw an exception if necessary
        if (emailCount >= limit) {
            throw new UnprocessableEntityException("The email sending limit for the recipient has been exceeded " + destinationMail);
        }
    }
}
