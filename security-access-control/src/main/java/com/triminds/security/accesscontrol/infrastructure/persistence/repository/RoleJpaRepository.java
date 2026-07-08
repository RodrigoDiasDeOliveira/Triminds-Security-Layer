package com.triminds.security.accesscontrol.infrastructure.persistence.repository;

import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleJpaRepository
        extends JpaRepository<RoleJpaEntity, UUID> {

    List<RoleJpaEntity> findByTenantId(UUID tenantId);

}