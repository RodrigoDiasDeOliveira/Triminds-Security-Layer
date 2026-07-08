package com.triminds.security.accesscontrol.infrastructure.persistence.adapter;

import com.triminds.security.accesscontrol.application.port.RoleRepositoryPort;
import com.triminds.security.accesscontrol.domain.Role;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleJpaEntity;
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository repository;

    @Override
    public Optional<Role> findById(UUID tenantId, UUID roleId) {
        return repository.findById(roleId)
                .filter(e -> e.getTenantId().equals(tenantId))
                .map(e -> new Role(e.getId(), e.getTenantId(), e.getName(), e.getDescription(), e.getCreatedAt()));
    }
}
