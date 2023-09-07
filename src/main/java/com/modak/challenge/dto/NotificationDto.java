package com.modak.challenge.dto;

import com.modak.challenge.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    @NotNull
    private String message;
    @NotNull
    private NotificationType type;
    @NotNull
    private String destinationMail;
}
