package com.triminds.security.shared.model;

import java.time.Instant;

public record RiskEvent(Long id, String entity, String category, String severity, String message, Instant timestamp) {
}
