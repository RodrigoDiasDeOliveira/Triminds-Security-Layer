package com.triminds.security.shared.events;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        String eventType,
        String tenantId,
        String correlationId,
        Instant occurredAt,
        Map<String, String> headers,
        T payload
) {
    public static <T> EventEnvelope<T> of(String type, String tenantId, String correlationId, T payload) {
        return new EventEnvelope<>(UUID.randomUUID(), type, tenantId, correlationId, Instant.now(), Map.of(), payload);
    }
}
