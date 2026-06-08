package com.triminds.security.identity.domain.events;

import com.triminds.security.identity.domain.model.*;
import java.time.Instant;
import java.util.UUID;

public sealed interface IdentityDomainEvent
        permits IdentityCreatedEvent, IdentityActivatedEvent, IdentityLockedEvent,
                IdentityUnlockedEvent, IdentityDisabledEvent, IdentityEmailChangedEvent, IdentityFederatedEvent {
    UUID eventId();
    IdentityId identityId();
    Instant occurredAt();
    String eventType();
}
