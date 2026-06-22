package com.triminds.security.accesscontrol.infrastructure.policy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
        name = "policy-engine",
        url = "${policy.service.url}"
)
public interface PolicyEngineFeignClient {

    @PostMapping("/policies/evaluate")
    PolicyDecisionResponse evaluate(Map<String, Object> input);
}