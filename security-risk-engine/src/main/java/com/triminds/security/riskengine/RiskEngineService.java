package com.triminds.security.riskengine;

import org.springframework.stereotype.Service;

@Service
public class RiskEngineService {

    public String performRiskScan() {
        return "Risk scan completed successfully";
    }
}
