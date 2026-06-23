package com.triminds.security.policy.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "policies",
    uniqueConstraints = @UniqueConstraint(name = "uk_policies_tenant_name", columnNames = {"tenant_id","name"}))
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyJpaEntity {
    @Id private UUID id;
    @Column(name = "tenant_id", nullable = false) private UUID tenantId;
    @Column(nullable = false, length = 120) private String name;
    private String description;
    @Column(name = "current_version", nullable = false) private int currentVersion;
    @Column(nullable = false) private boolean enabled;
    @CreatedDate @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
}
