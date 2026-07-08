package com.triminds.security.identity.infrastructure.persistence.repository;

import com.triminds.security.identity.infrastructure.persistence.entity.IdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface IdentityJpaRepository extends JpaRepository<IdentityEntity, UUID> {
    Optional<IdentityEntity> findByTenantIdAndUsername(String tenantId, String username);
    boolean existsByTenantIdAndUsername(String tenantId, String username);
}
