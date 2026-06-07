package com.triminds.security.shared.model;

import java.time.Instant;

public record AuditEvent(Long id, String actor, String action, String target, Instant timestamp, String details) {
}
