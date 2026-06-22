package com.triminds.shared.audit;

import java.time.Instant;
import java.util.Map;

public record AuditEvent(
        String id,
        String tenantId,
        String actor,
        String action,
        String resource,
        String outcome,
        Instant occurredAt,
        Map<String, Object> attributes,
        String previousHash,
        String hash
) {}
