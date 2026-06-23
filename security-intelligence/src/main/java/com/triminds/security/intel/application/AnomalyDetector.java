package com.triminds.security.intel.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.shared.events.SecurityTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

/** Detecta anomalia simples: > 10 failed logins do mesmo identity em 5 minutos. */
@Slf4j
@Component
@RequiredArgsConstructor
public class AnomalyDetector {

    private final ObjectMapper mapper;
    private final StringRedisTemplate redis;
    private final KafkaTemplate<String, String> kafka;

    @KafkaListener(topics = SecurityTopics.EVENTS, groupId = "security-intelligence")
    public void onEvent(String json) {
        try {
            JsonNode n = mapper.readTree(json);
            String type = n.path("type").asText();
            String tenant = n.path("tenantId").asText();
            if ("AUTH_LOGIN_FAILED".equals(type)) {
                String identity = n.path("payload").path("identityId").asText();
                String key = "intel:fail:" + tenant + ":" + identity;
                Long count = redis.opsForValue().increment(key);
                redis.expire(key, Duration.ofMinutes(5));
                if (count != null && count >= 10) {
                    publishAnomaly(tenant, identity, count);
                }
            }
        } catch (Exception e) { log.warn("intel consume failed: {}", e.getMessage()); }
    }

    private void publishAnomaly(String tenant, String identity, long count) {
        String payload = """
            {"type":"ANOMALY_DETECTED","tenantId":"%s","payload":{"identityId":"%s","kind":"LOGIN_FLOOD","count":%d}}
            """.formatted(tenant, identity, count);
        kafka.send(SecurityTopics.EVENTS, tenant, payload);
        log.warn("ANOMALY tenant={} identity={} fails={}", tenant, identity, count);
    }

    public AtomicLong noopFieldForCompilerHint() { return new AtomicLong(); }
}
