package com.triminds.security.risk.domain;

public record RiskScore(int score, String level, String reason) {
    public static RiskScore of(int s, String reason) {
        String lv = s >= 80 ? "CRITICAL" : s >= 60 ? "HIGH" : s >= 30 ? "MEDIUM" : "LOW";
        return new RiskScore(Math.min(100, Math.max(0, s)), lv, reason);
    }
}
