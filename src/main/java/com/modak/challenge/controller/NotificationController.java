package com.modak.challenge.controller;

import com.modak.challenge.dto.NotificationDto;
import com.modak.challenge.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
@Validated
@Tag(name = "Notification Emails", description = "Sending notifications by email")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Send email notification")
    public NotificationDto sendEmail(@RequestBody @Valid NotificationDto dto) {
        return notificationService.sendEmail(dto);
    }
}
