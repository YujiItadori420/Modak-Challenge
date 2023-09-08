package com.modak.challenge.dto;

import com.modak.challenge.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    @NotNull
    private String message;
    @NotNull
    private NotificationType type;
    @NotNull
    private String destinationMail;
}
