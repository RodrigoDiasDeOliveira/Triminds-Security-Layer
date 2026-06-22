package com.triminds.auth.application.ports;

import com.triminds.shared.risk.RiskScore;
import java.util.Map;

public interface RiskEvaluationPort {
    RiskScore evaluate(String tenantId, String userId, Map<String, Object> context);
}
