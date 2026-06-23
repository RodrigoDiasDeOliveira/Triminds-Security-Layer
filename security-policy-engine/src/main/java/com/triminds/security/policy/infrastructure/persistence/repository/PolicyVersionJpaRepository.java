package com.triminds.security.policy.infrastructure.persistence.repository;

import com.triminds.security.policy.infrastructure.persistence.entity.PolicyVersionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PolicyVersionJpaRepository extends JpaRepository<PolicyVersionJpaEntity, UUID> {
    Optional<PolicyVersionJpaEntity> findByPolicyIdAndVersion(UUID policyId, int version);
}
