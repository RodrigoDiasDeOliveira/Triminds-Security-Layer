package com.triminds.identity.domain;

import java.time.Instant;
import java.util.UUID;

public record MfaFactor(UUID id, UUID identityId, String type, String secret, boolean enabled, Instant createdAt) {
    public static MfaFactor totp(UUID identityId, String secret) {
        return new MfaFactor(UUID.randomUUID(), identityId, "TOTP", secret, false, Instant.now());
    }
}
