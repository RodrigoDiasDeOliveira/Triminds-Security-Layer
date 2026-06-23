package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.port.PermissionRepositoryPort;
import com.triminds.security.accesscontrol.application.usecase.CheckPermissionUseCase;
import com.triminds.security.accesscontrol.application.usecase.EvaluateAbacUseCase;
import com.triminds.security.accesscontrol.infrastructure.cache.RedisPermissionCache;
import com.triminds.security.accesscontrol.infrastructure.messaging.AccessEventsProducer;
import com.triminds.security.shared.events.payload.AccessDecisionPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckPermissionService implements CheckPermissionUseCase {

    private final PermissionRepositoryPort permissionPort;
    private final RedisPermissionCache cache;
    private final EvaluateAbacUseCase abac;
    private final AccessEventsProducer events;

    @Override
    public boolean execute(UUID tenantId, UUID identityId, String action, String resource) {
        var cached = cache.get(tenantId, identityId, action, resource);
        if (cached.isPresent()) {
            return cached.get();
        }

        boolean rbac = permissionPort.existsPermission(tenantId, identityId, action, resource);
        if (rbac) {
            cache.put(tenantId, identityId, action, resource, true);
            events.publishAccessDecision(tenantId,
                    new AccessDecisionPayload(identityId, action, resource, true, "RBAC"));
            return true;
        }

        boolean abacAllowed = abac.execute(tenantId, identityId, action, resource, Map.of());
        cache.put(tenantId, identityId, action, resource, abacAllowed);
        events.publishAccessDecision(tenantId, new AccessDecisionPayload(
                identityId, action, resource, abacAllowed, abacAllowed ? "ABAC" : "DENY"));
        return abacAllowed;
    }
}
