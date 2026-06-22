package com.triminds.security.accesscontrol.application.usecase;

import java.util.UUID;

public interface CheckPermissionUseCase {

    boolean execute(UUID tenantId, UUID identityId, String action, String resource);
}