package com.triminds.security.identity.infrastructure.persistence.entity;

import com.triminds.security.identity.domain.model.IdentityStatus;
import com.triminds.security.identity.domain.model.IdentityType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "identities")
public class IdentityJpaEntity {

    @Id @Column(name = "id", nullable = false, updatable = false) private UUID id;
    @Column(name = "organization_id", nullable = false) private UUID organizationId;
    @Column(name = "username", nullable = false, unique = true, length = 100) private String username;
    @Column(name = "email", nullable = false, length = 255) private String email;
    @Column(name = "display_name", length = 200) private String displayName;
    @Enumerated(EnumType.STRING) @Column(name = "type", nullable = false, length = 30) private IdentityType type;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 30) private IdentityStatus status;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "identity_groups", joinColumns = @JoinColumn(name = "identity_id"))
    @Column(name = "group_id") private Set<String> groups = new HashSet<>();
    @JdbcTypeCode(SqlTypes.JSON) @Column(name = "attributes", columnDefinition = "jsonb") private Map<String, String> attributes = new HashMap<>();
    @Column(name = "federation_provider", length = 50) private String federationProvider;
    @Column(name = "federation_external_id", length = 500) private String federationExternalId;
    @Column(name = "federation_issuer", length = 500) private String federationIssuer;
    @Column(name = "federation_subject", length = 500) private String federationSubject;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @Column(name = "last_login_at") private Instant lastLoginAt;
    @Column(name = "failed_login_attempts", nullable = false) private int failedLoginAttempts;
    @Column(name = "locked_until") private Instant lockedUntil;
    @Version @Column(name = "version") private Long version;

    protected IdentityJpaEntity() {}

    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public UUID getOrganizationId() { return organizationId; } public void setOrganizationId(UUID v) { this.organizationId = v; }
    public String getUsername() { return username; } public void setUsername(String v) { this.username = v; }
    public String getEmail() { return email; } public void setEmail(String v) { this.email = v; }
    public String getDisplayName() { return displayName; } public void setDisplayName(String v) { this.displayName = v; }
    public IdentityType getType() { return type; } public void setType(IdentityType v) { this.type = v; }
    public IdentityStatus getStatus() { return status; } public void setStatus(IdentityStatus v) { this.status = v; }
    public Set<String> getGroups() { return groups; } public void setGroups(Set<String> v) { this.groups = v; }
    public Map<String, String> getAttributes() { return attributes; } public void setAttributes(Map<String, String> v) { this.attributes = v; }
    public String getFederationProvider() { return federationProvider; } public void setFederationProvider(String v) { this.federationProvider = v; }
    public String getFederationExternalId() { return federationExternalId; } public void setFederationExternalId(String v) { this.federationExternalId = v; }
    public String getFederationIssuer() { return federationIssuer; } public void setFederationIssuer(String v) { this.federationIssuer = v; }
    public String getFederationSubject() { return federationSubject; } public void setFederationSubject(String v) { this.federationSubject = v; }
    public Instant getCreatedAt() { return createdAt; } public void setCreatedAt(Instant v) { this.createdAt = v; }
    public Instant getUpdatedAt() { return updatedAt; } public void setUpdatedAt(Instant v) { this.updatedAt = v; }
    public Instant getLastLoginAt() { return lastLoginAt; } public void setLastLoginAt(Instant v) { this.lastLoginAt = v; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; } public void setFailedLoginAttempts(int v) { this.failedLoginAttempts = v; }
    public Instant getLockedUntil() { return lockedUntil; } public void setLockedUntil(Instant v) { this.lockedUntil = v; }
    public Long getVersion() { return version; } public void setVersion(Long v) { this.version = v; }
}
