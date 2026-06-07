package com.triminds.security.policyengine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/policy")
public class PolicyEngineController {

    private final PolicyEngineService policyEngineService;

    public PolicyEngineController(PolicyEngineService policyEngineService) {
        this.policyEngineService = policyEngineService;
    }

    @GetMapping("/health")
    public String health() {
        return policyEngineService.getHealth();
    }
}
