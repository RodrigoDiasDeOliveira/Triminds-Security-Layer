package com.triminds.security.policyengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicyEngineServiceTest {

    @Test
    void healthReturnsRunning() {
        PolicyEngineService service = new PolicyEngineService();
        assertEquals("Policy engine is running", service.getHealth());
    }
}
