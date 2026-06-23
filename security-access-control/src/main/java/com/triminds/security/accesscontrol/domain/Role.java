package com.triminds.security.accesscontrol.domain;

import java.time.Instant;
import java.util.UUID;

public record Role(UUID id, UUID tenantId, String name, String description, Instant createdAt) {}
