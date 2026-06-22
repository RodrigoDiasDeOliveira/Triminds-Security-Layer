package com.triminds.shared.errors;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        String code,
        String message,
        int status,
        Instant timestamp,
        String correlationId,
        Map<String, Object> details
) {
    public static ApiError of(String code, String message, int status, String correlationId) {
        return new ApiError(code, message, status, Instant.now(), correlationId, Map.of());
    }
}
