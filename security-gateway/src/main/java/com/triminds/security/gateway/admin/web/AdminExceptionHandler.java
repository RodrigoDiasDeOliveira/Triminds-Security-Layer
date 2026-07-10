package com.triminds.security.gateway.admin.web;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Map;
@ControllerAdvice
public class AdminExceptionHandler {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, Object>> downstream(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode())
            .body(Map.of(
                "code", "downstream_error",
                "status", ex.getStatusCode().value(),
                "message", ex.getStatusText(),
                "details", ex.getResponseBodyAsString()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("code", "internal_error", "message", ex.getMessage()));
    }
}