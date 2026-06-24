package com.triminds.security.identity.domain;

import java.time.Instant;
import java.util.UUID;

public class Identity {
    private UUID id;
    private String tenantId;
    private String username;
    private String email;
    private IdentityStatus status;
    private int failedAttempts;
    private Instant createdAt;
    private Instant updatedAt;

    public Identity() {}
    public Identity(UUID id, String tenantId, String username, String email,
                    IdentityStatus status, int failedAttempts, Instant createdAt, Instant updatedAt) {
        this.id = id; this.tenantId = tenantId; this.username = username; this.email = email;
        this.status = status; this.failedAttempts = failedAttempts;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }
    public static Identity create(String tenantId, String username, String email) {
        Instant now = Instant.now();
        return new Identity(UUID.randomUUID(), tenantId, username, email, IdentityStatus.PENDING, 0, now, now);
    }
    public void activate() { this.status = IdentityStatus.ACTIVE; this.updatedAt = Instant.now(); }
    public void disable()  { this.status = IdentityStatus.DISABLED; this.updatedAt = Instant.now(); }
    public void lock()     { this.status = IdentityStatus.LOCKED;   this.updatedAt = Instant.now(); }
    public void registerFailedAttempt() {
        this.failedAttempts++;
        if (failedAttempts >= 5) this.status = IdentityStatus.LOCKED;
        this.updatedAt = Instant.now();
    }
    public void resetFailedAttempts() { this.failedAttempts = 0; this.updatedAt = Instant.now(); }

    public UUID getId() { return id; } public String getTenantId() { return tenantId; }
    public String getUsername() { return username; } public String getEmail() { return email; }
    public IdentityStatus getStatus() { return status; } public int getFailedAttempts() { return failedAttempts; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}