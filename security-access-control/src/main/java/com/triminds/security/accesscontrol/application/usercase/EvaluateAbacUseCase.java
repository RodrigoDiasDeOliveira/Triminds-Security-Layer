package com.triminds.security.accesscontrol.application.usecase;

import java.util.Map;
import java.util.UUID;

public interface EvaluateAbacUseCase {

    boolean execute(UUID tenantId, UUID identityId, Map<String, Object> context);
}