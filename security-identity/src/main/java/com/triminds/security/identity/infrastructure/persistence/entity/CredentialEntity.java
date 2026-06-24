package com.triminds.security.identity.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "credentials")
public class CredentialEntity {
    @Id private UUID id;
    @Column(name = "identity_id", nullable = false, unique = true) private UUID identityId;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Column(nullable = false) private String algorithm;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public UUID getIdentityId() { return identityId; } public void setIdentityId(UUID i) { this.identityId = i; }
    public String getPasswordHash() { return passwordHash; } public void setPasswordHash(String p) { this.passwordHash = p; }
    public String getAlgorithm() { return algorithm; } public void setAlgorithm(String a) { this.algorithm = a; }
    public Instant getUpdatedAt() { return updatedAt; } public void setUpdatedAt(Instant u) { this.updatedAt = u; }
}
