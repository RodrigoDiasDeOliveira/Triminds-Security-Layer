package com.triminds.security.identity.infrastructure.config;
import com.triminds.security.identity.application.port.out.DomainEventPublisher;
import com.triminds.security.identity.domain.events.IdentityDomainEvent;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(SpringDomainEventPublisher.class);
    private final ApplicationEventPublisher pub;
    public SpringDomainEventPublisher(ApplicationEventPublisher pub) { this.pub = pub; }
    @Override public void publish(IdentityDomainEvent e) { log.debug("Evento: {}", e.eventType()); pub.publishEvent(e); }
    @Override public void publishAll(List<IdentityDomainEvent> events) { events.forEach(this::publish); }
}
