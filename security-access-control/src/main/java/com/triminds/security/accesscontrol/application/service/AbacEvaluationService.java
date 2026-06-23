package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.port.AbacAttributeRepositoryPort;
import com.triminds.security.accesscontrol.application.usecase.EvaluateAbacUseCase;
import com.triminds.security.accesscontrol.infrastructure.policy.PolicyDecisionResponse;
import com.triminds.security.accesscontrol.infrastructure.policy.PolicyEngineFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbacEvaluationService implements EvaluateAbacUseCase {

    private final AbacAttributeRepositoryPort abacRepo;
    private final PolicyEngineFeignClient policyEngine;

    @Override
    public boolean execute(UUID tenantId, UUID identityId, String action, String resource,
                           Map<String, Object> extraContext) {

        var attrs = abacRepo.findByIdentity(tenantId, identityId).stream()
                .collect(Collectors.toMap(a -> a.name(), a -> (Object) a.value(), (a, b) -> a));

        Map<String, Object> input = new HashMap<>();
        input.put("tenantId",  tenantId.toString());
        input.put("identityId", identityId.toString());
        input.put("action",    action);
        input.put("resource",  resource);
        input.put("attributes", attrs);
        input.putAll(extraContext);

        try {
            PolicyDecisionResponse decision = policyEngine.evaluate(Map.of("input", input));
            return decision != null && decision.allow();
        } catch (Exception e) {
            log.warn("policy engine unreachable, denying by default", e);
            return false;
        }
    }
}
