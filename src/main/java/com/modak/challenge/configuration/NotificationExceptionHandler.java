package com.modak.challenge.configuration;

import com.modak.challenge.dto.ErrorResponseDto;
import com.modak.challenge.exception.UnprocessableEntityException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestControllerAdvice
public class NotificationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleUnprocessableEntityException(UnprocessableEntityException ex) {

        log.error("The email sending limit for the recipient has been exceeded.", ex);

        var errorResponseDTO = ErrorResponseDto.builder()
                .title("Rate Limit error")
                .statusCode(String.valueOf(UNPROCESSABLE_ENTITY.value()))
                .detail(ex.getMessage())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(errorResponseDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleApiException(ConstraintViolationException ex) {

        log.error("ConstraintViolationException", ex);

        var errorResponseDTO = ErrorResponseDto.builder()
                .title("Parameter type mismatch")
                .statusCode(String.valueOf(BAD_REQUEST.value()))
                .detail("Fail")
                .build();
        return ResponseEntity.status(BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(errorResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIlegalArgumentException(IllegalArgumentException ex) {

        log.error("Unsupported notification type", ex);

        var errorResponseDTO = ErrorResponseDto.builder()
                .title("Unsupported notification type")
                .statusCode(String.valueOf(BAD_REQUEST.value()))
                .detail(ex.getMessage())
                .build();
        return ResponseEntity.status(BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {

        log.error(ex.getMessage(), ex);

        var errorResponseDTO = ErrorResponseDto.builder()
                .title("Unexpected Error")
                .statusCode(INTERNAL_SERVER_ERROR.name())
                .detail("Check logs for more details")
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(errorResponseDTO);
    }


}
