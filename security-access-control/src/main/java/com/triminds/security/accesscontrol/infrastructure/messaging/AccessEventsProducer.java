package com.triminds.security.accesscontrol.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessEventsProducer {

    private final KafkaTemplate<String, Object> kafka;

    private static final String TOPIC = "access.events";

    public void roleAssigned(Object event) {
        kafka.send(TOPIC, event);
    }

    public void accessDenied(Object event) {
        kafka.send(TOPIC, event);
    }
}