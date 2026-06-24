package com.triminds.security.shared.events.payload;

import java.util.UUID;

public record RiskScorePayload(
        UUID identityId,
        int score,           // 0..100
        String level,        // LOW / MEDIUM / HIGH / CRITICAL
        String reason
) {}
