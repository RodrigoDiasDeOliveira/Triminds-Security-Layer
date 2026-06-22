package com.triminds.shared.security;

import java.time.Instant;
import java.util.List;

public record JwtClaims(
        String subject,
        String tenantId,
        String sessionId,
        List<String> roles,
        List<String> scopes,
        Instant issuedAt,
        Instant expiresAt,
        String issuer,
        String audience
) {}
