package com.triminds.security.audit.infrastructure.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.audit.application.usecase.AppendAuditUseCase;
import com.triminds.security.shared.events.SecurityTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityEventsConsumer {

    private final AppendAuditUseCase append;
    private final ObjectMapper mapper;

    @KafkaListener(topics = SecurityTopics.EVENTS, groupId = "security-audit")
    public void onMessage(String json) {
        try {
            JsonNode n = mapper.readTree(json);
            UUID tenantId = UUID.fromString(n.path("tenantId").asText());
            String type   = n.path("type").asText();
            Instant when  = Instant.parse(n.path("occurredAt").asText());
            append.append(tenantId, type, when, n.path("payload").toString());
        } catch (Exception e) {
            log.warn("audit consume failed: {}", e.getMessage());
        }
    }
}
