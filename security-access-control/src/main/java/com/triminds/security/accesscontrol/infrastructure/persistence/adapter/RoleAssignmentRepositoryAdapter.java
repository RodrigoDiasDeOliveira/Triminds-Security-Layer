package com.triminds.security.accesscontrol.infrastructure.persistence.adapter;

import com.triminds.security.accesscontrol.application.port.RoleAssignmentRepositoryPort;
import com.triminds.security.accesscontrol.domain.RoleAssignment;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleAssignmentJpaEntity;
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.RoleAssignmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleAssignmentRepositoryAdapter implements RoleAssignmentRepositoryPort {

    private final RoleAssignmentJpaRepository repository;

    @Override
    public RoleAssignment save(RoleAssignment assignment) {
        var entity = RoleAssignmentJpaEntity.builder()
                .id(assignment.id())
                .identityId(assignment.identityId())
                .roleId(assignment.roleId())
                .tenantId(assignment.tenantId())
                .createdAt(Instant.now())
                .build();

        repository.save(entity);
        return assignment;
    }

    @Override
    public void delete(UUID tenantId, UUID identityId, UUID roleId) {
        repository.deleteByTenantIdAndIdentityIdAndRoleId(tenantId, identityId, roleId);
    }

    @Override
    public boolean exists(UUID tenantId, UUID identityId, UUID roleId) {
        return repository.existsByIdentityIdAndRoleId(identityId, roleId);
    }
}
