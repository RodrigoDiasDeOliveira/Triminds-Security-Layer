package com.triminds.security.accesscontrol.application.usecase;

import java.util.UUID;

public interface RevokeRoleUseCase {
    void execute(UUID tenantId, UUID actorId, UUID identityId, UUID roleId);
}
