package com.triminds.identity.infrastructure.web;

import com.triminds.shared.errors.ApiError;
import com.triminds.shared.errors.DomainException;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> domain(DomainException ex) {
        int status = switch (ex.getCode()) {
            case "NOT_FOUND" -> 404;
            case "IDENTITY_EXISTS" -> 409;
            case "LOCKED", "DISABLED" -> 423;
            case "INVALID_CREDENTIALS" -> 401;
            default -> 400;
        };
        return ResponseEntity.status(status).body(ApiError.of(ex.getCode(), ex.getMessage(), status, MDC.get("correlationId")));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400).body(ApiError.of("VALIDATION_ERROR", ex.getMessage(), 400, MDC.get("correlationId")));
    }
}
