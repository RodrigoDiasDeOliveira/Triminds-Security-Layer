package com.triminds.security.auth.infrastructure.client;

import com.triminds.security.auth.application.ports.RiskEvaluationPort;
import com.triminds.security.shared.risk.RiskScore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.util.Map;

@Configuration
public class RiskNoopAdapter {
    @Component
    @ConditionalOnMissingBean(name = "remoteRiskAdapter")
    public static class NoopRisk implements RiskEvaluationPort {
        @Override public RiskScore evaluate(String tenantId, String userId, Map<String, Object> ctx) {
            return RiskScore.low();
        }
    }
}