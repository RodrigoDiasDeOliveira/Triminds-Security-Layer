package com.triminds.security.identity.domain.model;

import java.util.*;

import com.triminds.security.identity.domain.events.*;

public class Identity {

    private IdentityId id;
    private String email;
    private String passwordHash;
    private IdentityStatus status;

    private List<IdentityDomainEvent> events = new ArrayList<>();

    private Identity() {}

    public static Identity create(String email, String passwordHash) {

        Identity identity = new Identity();
        identity.id = IdentityId.generate();
        identity.email = email;
        identity.passwordHash = passwordHash;
        identity.status = IdentityStatus.ACTIVE;

        identity.events.add(new IdentityCreatedEvent(email));

        return identity;
    }

    public List<IdentityDomainEvent> pullEvents() {
        List<IdentityDomainEvent> copy = new ArrayList<>(events);
        events.clear();
        return copy;
    }

    public IdentityId getId() { return id; }
    public String getEmail() { return email; }
}