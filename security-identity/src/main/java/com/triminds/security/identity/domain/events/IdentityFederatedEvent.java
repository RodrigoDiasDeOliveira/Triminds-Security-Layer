package com.triminds.security.identity.domain.events;
import com.triminds.security.identity.domain.model.*;
import java.time.Instant; import java.util.UUID;
public record IdentityFederatedEvent(UUID eventId, IdentityId identityId, FederationInfo federationInfo, Instant occurredAt) implements IdentityDomainEvent {
    public IdentityFederatedEvent(IdentityId id, FederationInfo fi) { this(UUID.randomUUID(), id, fi, Instant.now()); }
    @Override public String eventType() { return "identity.federated"; }
}
