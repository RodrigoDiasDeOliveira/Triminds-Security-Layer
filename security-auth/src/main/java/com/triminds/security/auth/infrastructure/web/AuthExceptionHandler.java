package com.triminds.security.auth.infrastructure.web;

import com.triminds.security.shared.errors.ApiError;
import com.triminds.security.shared.errors.DomainException;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> domain(DomainException ex) {
        int status = switch (ex.getCode()) {
            case "INVALID_CREDENTIALS", "INVALID_REFRESH", "SESSION_EXPIRED" -> 401;
            case "RISK_DENIED" -> 403;
            default -> 400;
        };
        return ResponseEntity.status(status).body(
                ApiError.of(ex.getCode(), ex.getMessage(), status, MDC.get("correlationId")));
    }
}
