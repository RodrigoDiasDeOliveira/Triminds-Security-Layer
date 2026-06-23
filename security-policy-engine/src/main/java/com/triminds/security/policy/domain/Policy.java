package com.triminds.security.policy.domain;

import java.time.Instant;
import java.util.UUID;

public record Policy(UUID id, UUID tenantId, String name, String description,
                     int currentVersion, boolean enabled, Instant createdAt) {}
