package com.triminds.security.policyengine;

import org.springframework.stereotype.Service;

@Service
public class PolicyEngineService {

    public String getHealth() {
        return "Policy engine is running";
    }
}
