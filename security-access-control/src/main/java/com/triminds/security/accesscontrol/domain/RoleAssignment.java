package com.triminds.security.accesscontrol.domain;

import java.time.Instant;
import java.util.UUID;

public record RoleAssignment(UUID id, UUID tenantId, UUID identityId, UUID roleId, Instant createdAt) {}
