package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.usecase.RevokeRoleUseCase;
import com.triminds.security.accesscontrol.application.port.RoleAssignmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevokeRoleService implements RevokeRoleUseCase {

    private final RoleAssignmentRepositoryPort repository;

    @Override
    public void execute(UUID tenantId, UUID identityId, UUID roleId) {

        // versão simples (pode evoluir para soft delete)
        if (repository.exists(identityId, roleId)) {
            // aqui você pode evoluir para delete real no adapter
        }
    }
}