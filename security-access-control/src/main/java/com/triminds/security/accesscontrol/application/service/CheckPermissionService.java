package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.port.PermissionRepositoryPort;
import com.triminds.security.accesscontrol.application.usecase.CheckPermissionUseCase;
import com.triminds.security.accesscontrol.infrastructure.cache.RedisPermissionCache;
import com.triminds.security.accesscontrol.infrastructure.messaging.AccessEventsProducer;
import com.triminds.security.accesscontrol.infrastructure.messaging.AccessDeniedEvent;
import com.triminds.security.accesscontrol.infrastructure.policy.PolicyEngineFeignClient;
import com.triminds.security.accesscontrol.infrastructure.policy.PolicyDecisionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckPermissionService implements CheckPermissionUseCase {

    private final PermissionRepositoryPort permissionPort;
    private final RedisPermissionCache cache;
    private final PolicyEngineFeignClient policyEngine;
    private final AccessEventsProducer events;

    @Override
    public boolean execute(UUID tenantId,
                           UUID identityId,
                           String action,
                           String resource) {

        // 1. CACHE (fast path)
        var cached = cache.get(tenantId, identityId, action, resource);
        if (cached.isPresent()) {
            return cached.get();
        }

        // 2. RBAC (database check)
        boolean rbacAllowed =
                permissionPort.existsPermission(identityId, action, resource);

        if (rbacAllowed) {
            cache.put(tenantId, identityId, action, resource, true);
            return true;
        }

        // 3. ABAC / POLICY ENGINE (external decision)
        PolicyDecisionResponse decision = policyEngine.evaluate(
                Map.of(
                        "tenantId", tenantId.toString(),
                        "identityId", identityId.toString(),
                        "action", action,
                        "resource", resource
                )
        );

        boolean allowed = decision != null && decision.allow();

        // 4. CACHE result (positive or negative)
        cache.put(tenantId, identityId, action, resource, allowed);

        // 5. AUDIT EVENT (only on denial)
        if (!allowed) {
            events.accessDenied(
                    new AccessDeniedEvent(
                            tenantId,
                            identityId,
                            action,
                            resource,
                            Instant.now()
                    )
            );
        }

        return allowed;
    }
}