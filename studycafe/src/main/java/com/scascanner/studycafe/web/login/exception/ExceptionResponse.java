package com.scascanner.studycafe.web.login.exception;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    @Builder
    public ExceptionResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
