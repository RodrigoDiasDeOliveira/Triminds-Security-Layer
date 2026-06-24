package com.triminds.security.shared.events;

public enum SecurityEventType {
    AUTH_LOGIN_SUCCESS,
    AUTH_LOGIN_FAILED,
    AUTH_MFA_CHALLENGE,
    ACCESS_GRANTED,
    ACCESS_DENIED,
    ROLE_ASSIGNED,
    ROLE_REVOKED,
    POLICY_DECISION,
    RISK_SCORE_COMPUTED,
    ANOMALY_DETECTED
}
