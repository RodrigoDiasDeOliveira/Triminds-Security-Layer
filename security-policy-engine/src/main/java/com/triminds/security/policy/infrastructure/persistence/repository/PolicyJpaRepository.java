package com.triminds.security.policy.infrastructure.persistence.repository;

import com.triminds.security.policy.infrastructure.persistence.entity.PolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PolicyJpaRepository extends JpaRepository<PolicyJpaEntity, UUID> {
    Optional<PolicyJpaEntity> findByIdAndTenantId(UUID id, UUID tenantId);
    List<PolicyJpaEntity> findAllByTenantId(UUID tenantId);
    List<PolicyJpaEntity> findAllByEnabledTrue();
}
