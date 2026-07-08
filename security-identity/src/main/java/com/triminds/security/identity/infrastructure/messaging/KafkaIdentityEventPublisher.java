package com.triminds.security.identity.infrastructure.messaging;

import com.triminds.security.identity.application.ports.IdentityEventPublisherPort;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.shared.events.EventEnvelope;
import com.triminds.security.shared.kafka.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaIdentityEventPublisher implements IdentityEventPublisherPort {

    private final KafkaTemplate<String, Object> kafka;

    public KafkaIdentityEventPublisher(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    private void publish(String type, Identity i) {
        Map<String, Object> payload = Map.of(
                "id", i.getId().toString(),
                "tenantId", i.getTenantId(),
                "username", i.getUsername(),
                "status", i.getStatus().name()
        );
        var env = EventEnvelope.of(type, i.getTenantId(), null, payload);
        kafka.send(KafkaTopics.IDENTITY_EVENTS, i.getId().toString(), env);
    }

    @Override public void identityCreated(Identity i)   { publish("identity.created.v1", i); }
    @Override public void identityActivated(Identity i) { publish("identity.activated.v1", i); }
    @Override public void identityDisabled(Identity i)  { publish("identity.disabled.v1", i); }
    @Override public void identityLocked(Identity i)    { publish("identity.locked.v1", i); }
}