package com.triminds.security.shared.kafka;

public final class KafkaTopics {
    private KafkaTopics() {}
    public static final String IDENTITY_EVENTS = "triminds.identity.events.v1";
    public static final String AUTH_EVENTS     = "triminds.auth.events.v1";
    public static final String AUDIT_EVENTS    = "triminds.audit.events.v1";
    public static final String RISK_EVENTS     = "triminds.risk.events.v1";
    public static final String POLICY_EVENTS   = "triminds.policy.events.v1";
}
