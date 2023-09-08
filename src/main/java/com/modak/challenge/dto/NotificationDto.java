package com.modak.challenge.dto;

import com.modak.challenge.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "This is a status mail", description = "The information contained in the email")
    private String message;
    @NotNull
    @Schema(example = "STATUS", description = "Can be NEWS, STATUS or MARKETING")
    private NotificationType type;
    @NotNull
    @Schema(example = "leoMessi@gmail.com", description = "The recipient's email")
    private String destinationMail;
}
