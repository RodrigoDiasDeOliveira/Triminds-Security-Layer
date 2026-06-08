package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.IdentityId;
import java.time.Instant; import java.util.UUID;
public record IdentityDisabledEvent(UUID eventId, IdentityId identityId, String reason, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityDisabledEvent(IdentityId id, String reason) { this(UUID.randomUUID(), id, reason, Instant.now()); }
    @Override public String eventType() { return "identity.disabled"; }
}
