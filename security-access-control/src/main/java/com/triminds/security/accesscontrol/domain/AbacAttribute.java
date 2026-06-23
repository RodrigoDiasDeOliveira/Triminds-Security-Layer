package com.triminds.security.accesscontrol.domain;

import java.util.UUID;

public record AbacAttribute(UUID id, UUID tenantId, UUID identityId, String name, String value) {}
