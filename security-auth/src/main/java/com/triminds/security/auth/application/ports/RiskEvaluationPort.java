package com.triminds.security.auth.application.ports;

import com.triminds.security.shared.risk.RiskScore;
import java.util.Map;

public interface RiskEvaluationPort {
    RiskScore evaluate(String tenantId, String userId, Map<String, Object> context);
}
