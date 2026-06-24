package com.triminds.security.shared.events;

import java.time.Instant;
import java.util.UUID;

/** Envelope comum publicado em "security.events.v1". O header Kafka "type" carrega SecurityEventType. */
public record SecurityEvent<T>(
        UUID eventId,
        SecurityEventType type,
        UUID tenantId,
        Instant occurredAt,
        T payload
) {
    public static <T> SecurityEvent<T> of(SecurityEventType type, UUID tenantId, T payload) {
        return new SecurityEvent<>(UUID.randomUUID(), type, tenantId, Instant.now(), payload);
    }
}
