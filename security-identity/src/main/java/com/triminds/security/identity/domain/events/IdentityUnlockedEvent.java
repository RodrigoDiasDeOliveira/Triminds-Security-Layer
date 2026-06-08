package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.IdentityId;
import java.time.Instant; import java.util.UUID;
public record IdentityUnlockedEvent(UUID eventId, IdentityId identityId, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityUnlockedEvent(IdentityId id) { this(UUID.randomUUID(), id, Instant.now()); }
    @Override public String eventType() { return "identity.unlocked"; }
}
