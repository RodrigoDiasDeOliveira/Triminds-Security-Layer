package com.triminds.security.policy.application.usecase;

import com.triminds.security.policy.domain.PolicyDecision;
import java.util.Map;

public interface EvaluatePolicyUseCase {
    PolicyDecision evaluate(Map<String, Object> input);
}
