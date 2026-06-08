package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.IdentityId;
import java.time.Instant; import java.util.UUID;
public record IdentityEmailChangedEvent(UUID eventId, IdentityId identityId, String oldEmail, String newEmail, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityEmailChangedEvent(IdentityId id, String oldEmail, String newEmail) { this(UUID.randomUUID(), id, oldEmail, newEmail, Instant.now()); }
    @Override public String eventType() { return "identity.email_changed"; }
}
