package com.triminds.security.shared.risk;

import java.util.List;

public record RiskScore(double score, String level, List<String> reasons) {
    public static RiskScore low() { return new RiskScore(0.05, "LOW", List.of()); }
}
