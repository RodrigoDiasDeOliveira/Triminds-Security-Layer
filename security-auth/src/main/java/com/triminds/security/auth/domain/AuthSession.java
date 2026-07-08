package com.triminds.security.auth.domain;

import java.time.Instant;
import java.util.List;

public record AuthSession(
        String sessionId, String userId, String tenantId,
        List<String> roles, List<String> scopes,
        Instant issuedAt, Instant expiresAt, String refreshToken
) {}
