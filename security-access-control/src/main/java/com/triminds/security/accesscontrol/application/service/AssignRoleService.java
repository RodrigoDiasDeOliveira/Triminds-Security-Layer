package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.usecase.AssignRoleUseCase;
import com.triminds.security.accesscontrol.application.port.RoleAssignmentRepositoryPort;
import com.triminds.security.accesscontrol.domain.RoleAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignRoleService implements AssignRoleUseCase {

    private final RoleAssignmentRepositoryPort repository;

    @Override
    public void execute(UUID tenantId, UUID identityId, UUID roleId) {

        boolean exists = repository.exists(identityId, roleId);

        if (exists) return;

        RoleAssignment assignment = RoleAssignment.builder()
                .id(UUID.randomUUID())
                .tenantId(tenantId)
                .identityId(identityId)
                .roleId(roleId)
                .build();

        repository.save(assignment);
    }
}