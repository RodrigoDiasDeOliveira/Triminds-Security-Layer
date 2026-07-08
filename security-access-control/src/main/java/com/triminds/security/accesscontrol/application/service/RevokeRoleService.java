package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.port.RoleAssignmentRepositoryPort;
import com.triminds.security.accesscontrol.application.usecase.RevokeRoleUseCase;
import com.triminds.security.accesscontrol.infrastructure.cache.RedisPermissionCache;
import com.triminds.security.accesscontrol.infrastructure.messaging.AccessEventsProducer;
import com.triminds.security.shared.events.payload.RoleAssignmentPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevokeRoleService implements RevokeRoleUseCase {

    private final RoleAssignmentRepositoryPort assignmentRepo;
    private final RedisPermissionCache cache;
    private final AccessEventsProducer events;

    @Override
    @Transactional
    public void execute(UUID tenantId, UUID actorId, UUID identityId, UUID roleId) {
        assignmentRepo.delete(tenantId, identityId, roleId);
        cache.evict(tenantId, identityId);
        events.publishRoleAssignment(tenantId,
                new RoleAssignmentPayload(identityId, roleId, actorId, false));
    }
}
