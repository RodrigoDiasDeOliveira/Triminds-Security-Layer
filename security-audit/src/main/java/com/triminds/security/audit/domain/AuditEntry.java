package com.triminds.security.audit.domain;

import java.time.Instant;
import java.util.UUID;

public record AuditEntry(
        UUID id, UUID tenantId, String type, Instant occurredAt,
        String payloadJson, String prevHash, String entryHash, long sequence) {}
