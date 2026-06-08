package com.triminds.security.identity.domain.model;

import com.triminds.security.identity.domain.events.IdentityDomainEvent;
import com.triminds.security.identity.domain.events.IdentityActivatedEvent;
import com.triminds.security.identity.domain.events.IdentityCreatedEvent;
import com.triminds.security.identity.domain.events.IdentityDisabledEvent;
import com.triminds.security.identity.domain.events.IdentityEmailChangedEvent;
import com.triminds.security.identity.domain.events.IdentityFederatedEvent;
import com.triminds.security.identity.domain.events.IdentityLockedEvent;
import com.triminds.security.identity.domain.events.IdentityUnlockedEvent;

import java.time.Instant;
import java.util.*;

public class Identity {

    private IdentityId id;
    private OrganizationId organizationId;
    private String username;
    private String email;
    private String displayName;
    private IdentityType type;
    private IdentityStatus status;
    private final Set<String> groups = new HashSet<>();
    private final Map<String, String> attributes = new HashMap<>();
    private FederationInfo federationInfo;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;
    private int failedLoginAttempts;
    private Instant lockedUntil;
    private final List<IdentityDomainEvent> domainEvents = new ArrayList<>();

    private Identity() {}

    public static Identity create(OrganizationId organizationId, String username, String email, String displayName, IdentityType type) {
        Objects.requireNonNull(organizationId); Objects.requireNonNull(username); Objects.requireNonNull(email); Objects.requireNonNull(type);
        if (username.isBlank()) throw new IllegalArgumentException("username não pode ser vazio");
        if (!email.contains("@")) throw new IllegalArgumentException("email inválido: " + email);
        Identity i = new Identity();
        i.id = IdentityId.generate(); i.organizationId = organizationId;
        i.username = username.toLowerCase().trim(); i.email = email.toLowerCase().trim();
        i.displayName = displayName != null ? displayName : username;
        i.type = type; i.status = IdentityStatus.PENDING_ACTIVATION;
        i.createdAt = Instant.now(); i.updatedAt = Instant.now(); i.failedLoginAttempts = 0;
        i.domainEvents.add(new IdentityCreatedEvent(i.id, organizationId, username, email, type));
        return i;
    }

    public static Identity createFederated(OrganizationId organizationId, String username, String email, String displayName, FederationInfo federationInfo) {
        Identity i = create(organizationId, username, email, displayName, IdentityType.FEDERATED_USER);
        i.federationInfo = Objects.requireNonNull(federationInfo);
        i.status = IdentityStatus.ACTIVE;
        i.domainEvents.add(new IdentityFederatedEvent(i.id, federationInfo));
        return i;
    }

    public void activate() {
        if (status != IdentityStatus.PENDING_ACTIVATION) throw new IllegalStateException("Status inválido para ativação: " + status);
        this.status = IdentityStatus.ACTIVE; this.updatedAt = Instant.now();
        domainEvents.add(new IdentityActivatedEvent(this.id));
    }

    public void lock(String reason, Instant until) {
        if (status == IdentityStatus.DISABLED) throw new IllegalStateException("Identidade desabilitada não pode ser bloqueada");
        this.status = IdentityStatus.LOCKED; this.lockedUntil = until; this.updatedAt = Instant.now();
        domainEvents.add(new IdentityLockedEvent(this.id, reason, until));
    }

    public void unlock() {
        if (status != IdentityStatus.LOCKED) throw new IllegalStateException("Identidade não está bloqueada");
        this.status = IdentityStatus.ACTIVE; this.lockedUntil = null; this.failedLoginAttempts = 0; this.updatedAt = Instant.now();
        domainEvents.add(new IdentityUnlockedEvent(this.id));
    }

    public void disable(String reason) {
        if (status == IdentityStatus.DISABLED) return;
        this.status = IdentityStatus.DISABLED; this.updatedAt = Instant.now();
        domainEvents.add(new IdentityDisabledEvent(this.id, reason));
    }

    public void recordFailedLogin(int maxAttempts, Instant lockUntil) {
        this.failedLoginAttempts++; this.updatedAt = Instant.now();
        if (this.failedLoginAttempts >= maxAttempts) lock("Excedeu tentativas de login: " + maxAttempts, lockUntil);
    }

    public void recordSuccessfulLogin() {
        this.failedLoginAttempts = 0; this.lastLoginAt = Instant.now(); this.updatedAt = Instant.now();
    }

    public void updateProfile(String displayName, String email) {
        if (displayName != null && !displayName.isBlank()) this.displayName = displayName;
        if (email != null && email.contains("@")) {
            String ne = email.toLowerCase().trim();
            if (!ne.equals(this.email)) { String old = this.email; this.email = ne; domainEvents.add(new IdentityEmailChangedEvent(this.id, old, ne)); }
        }
        this.updatedAt = Instant.now();
    }

    public void addToGroup(String groupId) { groups.add(Objects.requireNonNull(groupId)); updatedAt = Instant.now(); }
    public void removeFromGroup(String groupId) { groups.remove(groupId); updatedAt = Instant.now(); }
    public void setAttribute(String key, String value) { attributes.put(Objects.requireNonNull(key), value); updatedAt = Instant.now(); }
    public boolean isActive() { return status == IdentityStatus.ACTIVE; }
    public boolean isFederated() { return type == IdentityType.FEDERATED_USER && federationInfo != null; }

    public boolean isLocked() {
        if (status != IdentityStatus.LOCKED) return false;
        if (lockedUntil != null && Instant.now().isAfter(lockedUntil)) {
            this.status = IdentityStatus.ACTIVE; this.lockedUntil = null; this.failedLoginAttempts = 0; return false;
        }
        return true;
    }

    public List<IdentityDomainEvent> pullDomainEvents() {
        List<IdentityDomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return Collections.unmodifiableList(events);
    }

    public IdentityId getId() { return id; }
    public OrganizationId getOrganizationId() { return organizationId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public IdentityType getType() { return type; }
    public IdentityStatus getStatus() { return status; }
    public Set<String> getGroups() { return Collections.unmodifiableSet(groups); }
    public Map<String, String> getAttributes() { return Collections.unmodifiableMap(attributes); }
    public FederationInfo getFederationInfo() { return federationInfo; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public Instant getLockedUntil() { return lockedUntil; }

    @Override public boolean equals(Object o) { if (!(o instanceof Identity other)) return false; return id.equals(other.id); }
    @Override public int hashCode() { return id.hashCode(); }
}
