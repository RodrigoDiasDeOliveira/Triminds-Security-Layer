package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.*;
import java.time.Instant; import java.util.UUID;
public record IdentityCreatedEvent(UUID eventId, IdentityId identityId, OrganizationId organizationId, String username, String email, IdentityType type, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityCreatedEvent(IdentityId id, OrganizationId org, String u, String e, IdentityType t) { this(UUID.randomUUID(), id, org, u, e, t, Instant.now()); }
    @Override public String eventType() { return "identity.created"; }
}
