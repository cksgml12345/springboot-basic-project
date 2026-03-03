package com.chani.springbootbasicproject.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path,
        Map<String, String> errors
) {
    public static ErrorResponse of(int status, String code, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, path, Map.of());
    }

    public static ErrorResponse ofValidation(int status, String code, String message, String path, Map<String, String> errors) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, path, errors);
    }
}
