package com.triminds.security.accesscontrol.application.usecase;

import java.util.UUID;

public interface AssignRoleUseCase {

    void execute(UUID tenantId, UUID identityId, UUID roleId);
}