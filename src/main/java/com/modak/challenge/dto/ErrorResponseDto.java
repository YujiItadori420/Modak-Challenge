package com.modak.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String statusCode;

    private String title;

    private String detail;
}