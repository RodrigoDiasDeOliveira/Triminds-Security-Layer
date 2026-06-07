package com.triminds.security.riskengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiskEngineServiceTest {

    @Test
    void performRiskScanReturnsCompleted() {
        RiskEngineService service = new RiskEngineService();
        assertEquals("Risk scan completed successfully", service.performRiskScan());
    }
}
