package com.modak.challenge.controller;

import com.modak.challenge.dto.NotificationDto;
import com.modak.challenge.service.NotificationService;
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
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public NotificationDto sendEmail(@RequestBody @Valid NotificationDto dto) {
        return notificationService.sendEmail(dto);
    }
}
