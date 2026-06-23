package com.triminds.security.policy.application.port;

import com.triminds.security.policy.domain.Policy;
import com.triminds.security.policy.domain.PolicyVersion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PolicyRepositoryPort {
    Policy save(Policy p);
    Optional<Policy> findById(UUID tenantId, UUID id);
    List<Policy> findAll(UUID tenantId);

    PolicyVersion saveVersion(PolicyVersion v);
    Optional<PolicyVersion> findVersion(UUID policyId, int version);
    List<Policy> findAllEnabled();
}
