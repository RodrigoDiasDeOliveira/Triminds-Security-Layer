package com.triminds.security.identity.domain;

import java.time.Instant;
import java.util.UUID;

public record Credential(UUID id, UUID identityId, String passwordHash, String algorithm,
                         Instant updatedAt) {
    public static Credential of(UUID identityId, String hash) {
        return new Credential(UUID.randomUUID(), identityId, hash, "bcrypt", Instant.now());
    }
}
