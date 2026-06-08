package com.triminds.security.identity.application.port.out;
import com.triminds.security.identity.domain.events.IdentityDomainEvent;
import java.util.List;
public interface DomainEventPublisher {
    void publish(IdentityDomainEvent event);
    void publishAll(List<IdentityDomainEvent> events);
}
