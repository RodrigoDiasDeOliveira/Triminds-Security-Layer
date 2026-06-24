package com.triminds.security.identity.infrastructure.persistence.entity;

import com.triminds.security.identity.domain.IdentityStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "identities",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "username"}))
public class IdentityEntity {
    @Id private UUID id;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(nullable = false) private String username;
    @Column(nullable = false) private String email;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private IdentityStatus status;
    @Column(name = "failed_attempts", nullable = false) private int failedAttempts;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public String getTenantId() { return tenantId; } public void setTenantId(String t) { this.tenantId = t; }
    public String getUsername() { return username; } public void setUsername(String u) { this.username = u; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public IdentityStatus getStatus() { return status; } public void setStatus(IdentityStatus s) { this.status = s; }
    public int getFailedAttempts() { return failedAttempts; } public void setFailedAttempts(int f) { this.failedAttempts = f; }
    public Instant getCreatedAt() { return createdAt; } public void setCreatedAt(Instant c) { this.createdAt = c; }
    public Instant getUpdatedAt() { return updatedAt; } public void setUpdatedAt(Instant u) { this.updatedAt = u; }
}
