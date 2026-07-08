package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.port.RoleAssignmentRepositoryPort;
import com.triminds.security.accesscontrol.application.port.RoleRepositoryPort;
import com.triminds.security.accesscontrol.application.usecase.AssignRoleUseCase;
import com.triminds.security.accesscontrol.domain.RoleAssignment;
import com.triminds.security.accesscontrol.infrastructure.cache.RedisPermissionCache;
import com.triminds.security.accesscontrol.infrastructure.messaging.AccessEventsProducer;
import com.triminds.security.shared.events.payload.RoleAssignmentPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignRoleService implements AssignRoleUseCase {

    private final RoleRepositoryPort roleRepo;
    private final RoleAssignmentRepositoryPort assignmentRepo;
    private final RedisPermissionCache cache;
    private final AccessEventsProducer events;

    @Override
    @Transactional
    public void execute(UUID tenantId, UUID actorId, UUID identityId, UUID roleId) {
        roleRepo.findById(tenantId, roleId)
                .orElseThrow(() -> new IllegalArgumentException("role not found: " + roleId));

        if (assignmentRepo.exists(tenantId, identityId, roleId)) {
            return; // idempotente
        }

        assignmentRepo.save(new RoleAssignment(
                UUID.randomUUID(), tenantId, identityId, roleId, Instant.now()
        ));

        cache.evict(tenantId, identityId);
        events.publishRoleAssignment(tenantId,
                new RoleAssignmentPayload(identityId, roleId, actorId, true));
    }
}
