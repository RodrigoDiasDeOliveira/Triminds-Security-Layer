package com.triminds.security.accesscontrol.application.usecase;

import java.util.UUID;

public interface AssignRoleUseCase {
    void execute(UUID tenantId, UUID actorId, UUID identityId, UUID roleId);
}
