package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.IdentityId;
import java.time.Instant; import java.util.UUID;
public record IdentityActivatedEvent(UUID eventId, IdentityId identityId, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityActivatedEvent(IdentityId id) { this(UUID.randomUUID(), id, Instant.now()); }
    @Override public String eventType() { return "identity.activated"; }
}
