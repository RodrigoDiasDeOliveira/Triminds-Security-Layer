package com.triminds.security.policy.domain;

import java.time.Instant;
import java.util.UUID;

public record PolicyVersion(UUID id, UUID policyId, int version, String rego, String author, Instant createdAt) {}
