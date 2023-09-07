package com.modak.challenge;

import com.modak.challenge.dto.NotificationDto;
import com.modak.challenge.enums.NotificationType;
import com.modak.challenge.exception.UnprocessableEntityException;
import com.modak.challenge.repository.NotificationRepository;
import com.modak.challenge.repository.UserRepository;
import com.modak.challenge.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeApplicationTests {

    @InjectMocks
    NotificationService notificationService;
    @Mock
    NotificationRepository notificationRepository;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail_StatusNotificationWithinLimit() {
        // Caso de prueba: Envío de notificación de estado dentro del límite
        NotificationDto statusDto = new NotificationDto();
        statusDto.setType(NotificationType.STATUS);
        statusDto.setDestinationMail("user@example.com");

        // Simular que se ha enviado una notificación de estado en el último minuto
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.STATUS)
        )).thenReturn(1L);

        assertDoesNotThrow(() -> notificationService.sendEmail(statusDto));
    }

    @Test
    void testSendEmail_StatusNotificationExceedsLimit() {
        // Caso de prueba: Envío de notificación de estado que excede el límite
        NotificationDto statusDto = new NotificationDto();
        statusDto.setType(NotificationType.STATUS);
        statusDto.setDestinationMail("user@example.com");

        // Simular que se han enviado 3 notificaciones de estado en el último minuto
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.STATUS)
        )).thenReturn(3L);

        // El cuarto intento debería lanzar una excepción
        assertThrows(UnprocessableEntityException.class, () -> notificationService.sendEmail(statusDto));
    }


    @Test
    void testSendEmail_NewsNotificationWithinLimit() {
        NotificationDto newsDto = new NotificationDto();
        newsDto.setType(NotificationType.NEWS);
        newsDto.setDestinationMail("user@example.com");

        // Simular que no se han enviado notificaciones de noticias en el último día
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.NEWS)
        )).thenReturn(0L);

        assertDoesNotThrow(() -> notificationService.sendEmail(newsDto));
    }

    @Test
    void testSendEmail_NewsNotificationExceedsLimit() {
        NotificationDto newsDto = new NotificationDto();
        newsDto.setType(NotificationType.NEWS);
        newsDto.setDestinationMail("user@example.com");

        // Simular que se ha enviado una notificación de noticias en el último día
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.NEWS)
        )).thenReturn(1L);

        assertThrows(UnprocessableEntityException.class, () -> notificationService.sendEmail(newsDto));
    }

    @Test
    void testSendEmail_MarketingNotificationWithinLimit() {
        NotificationDto marketingDto = new NotificationDto();
        marketingDto.setType(NotificationType.MARKETING);
        marketingDto.setDestinationMail("user@example.com");

        // Simular que no se han enviado notificaciones de marketing en la última hora
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.MARKETING)
        )).thenReturn(0L);

        assertDoesNotThrow(() -> notificationService.sendEmail(marketingDto));
    }

    @Test
    void testSendEmail_MarketingNotificationExceedsLimit() {
        NotificationDto marketingDto = new NotificationDto();
        marketingDto.setType(NotificationType.MARKETING);
        marketingDto.setDestinationMail("user@example.com");

        // Simular que se han enviado 3 notificaciones de marketing en la última hora
        when(notificationRepository.countByLocalDateTimeSendedBetweenAndReceiverEmailAndType(
                any(LocalDateTime.class), any(LocalDateTime.class), eq("user@example.com"), eq(NotificationType.MARKETING)
        )).thenReturn(3L);

        assertThrows(UnprocessableEntityException.class, () -> notificationService.sendEmail(marketingDto));
    }

}