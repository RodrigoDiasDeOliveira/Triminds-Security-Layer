package com.triminds.security.risk.application.usecase;

import com.triminds.security.risk.domain.RiskScore;
import com.triminds.security.risk.domain.RiskSignal;

public interface ScoreRiskUseCase {
    RiskScore score(RiskSignal signal);
}
