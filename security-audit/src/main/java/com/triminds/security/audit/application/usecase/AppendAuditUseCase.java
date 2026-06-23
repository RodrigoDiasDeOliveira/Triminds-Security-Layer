package com.triminds.security.audit.application.usecase;

import java.time.Instant;
import java.util.UUID;

public interface AppendAuditUseCase {
    void append(UUID tenantId, String type, Instant occurredAt, String payloadJson);
}
