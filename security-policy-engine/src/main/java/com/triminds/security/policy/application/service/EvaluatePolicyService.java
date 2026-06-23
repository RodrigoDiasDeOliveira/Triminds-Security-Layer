package com.triminds.security.policy.application.service;

import com.triminds.security.policy.application.usecase.EvaluatePolicyUseCase;
import com.triminds.security.policy.domain.PolicyDecision;
import com.triminds.security.policy.infrastructure.cache.DecisionCache;
import com.triminds.security.policy.infrastructure.opa.OpaClient;
import com.triminds.security.policy.infrastructure.opa.OpaQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluatePolicyService implements EvaluatePolicyUseCase {

    private final OpaClient opa;
    private final DecisionCache cache;

    @Override
    public PolicyDecision evaluate(Map<String, Object> input) {
        var cached = cache.get(input);
        if (cached.isPresent()) return cached.get();

        try {
            OpaQueryResponse r = opa.queryAllow(Map.of("input", input.get("input")));
            boolean allow = r != null && r.result() != null && Boolean.TRUE.equals(r.result());
            var decision = allow ? PolicyDecision.allow() : PolicyDecision.deny("opa.deny");
            cache.put(input, decision);
            return decision;
        } catch (Exception e) {
            log.warn("OPA unreachable, denying: {}", e.getMessage());
            return new PolicyDecision(false, List.of("opa.unreachable"), List.of());
        }
    }
}
