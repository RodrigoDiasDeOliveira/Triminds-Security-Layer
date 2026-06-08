package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.IdentityId;
import java.time.Instant; import java.util.UUID;
public record IdentityLockedEvent(UUID eventId, IdentityId identityId, String reason, Instant lockedUntil, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityLockedEvent(IdentityId id, String reason, Instant until) { this(UUID.randomUUID(), id, reason, until, Instant.now()); }
    @Override public String eventType() { return "identity.locked"; }
}
