package com.triminds.security.accesscontrol.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.shared.events.SecurityEvent;
import com.triminds.security.shared.events.SecurityEventType;
import com.triminds.security.shared.events.SecurityTopics;
import com.triminds.security.shared.events.payload.AccessDecisionPayload;
import com.triminds.security.shared.events.payload.RoleAssignmentPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessEventsProducer {

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    public void publishAccessDecision(UUID tenantId, AccessDecisionPayload p) {
        var t = p.allowed() ? SecurityEventType.ACCESS_GRANTED : SecurityEventType.ACCESS_DENIED;
        send(SecurityEvent.of(t, tenantId, p), tenantId.toString());
    }

    public void publishRoleAssignment(UUID tenantId, RoleAssignmentPayload p) {
        var t = p.assigned() ? SecurityEventType.ROLE_ASSIGNED : SecurityEventType.ROLE_REVOKED;
        send(SecurityEvent.of(t, tenantId, p), tenantId.toString());
    }

    private void send(SecurityEvent<?> evt, String key) {
        try {
            String json = mapper.writeValueAsString(evt);
            kafka.send(SecurityTopics.EVENTS, key, json)
                 .whenComplete((SendResult<String, String> r, Throwable ex) -> {
                     if (ex != null) log.warn("kafka publish failed type={} : {}", evt.type(), ex.getMessage());
                 });
        } catch (JsonProcessingException e) {
            log.error("failed to serialize event {}", evt.type(), e);
        }
    }
}
