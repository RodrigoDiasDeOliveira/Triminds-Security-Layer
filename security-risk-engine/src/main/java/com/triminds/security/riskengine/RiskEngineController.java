package com.triminds.security.riskengine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risk")
public class RiskEngineController {

    private final RiskEngineService riskEngineService;

    public RiskEngineController(RiskEngineService riskEngineService) {
        this.riskEngineService = riskEngineService;
    }

    @GetMapping("/scan")
    public String scan() {
        return riskEngineService.performRiskScan();
    }
}
