package com.triminds.security.policy.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "policy_versions",
    uniqueConstraints = @UniqueConstraint(name = "uk_policy_versions", columnNames = {"policy_id","version"}))
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyVersionJpaEntity {
    @Id private UUID id;
    @Column(name = "policy_id", nullable = false) private UUID policyId;
    @Column(nullable = false) private int version;
    @Lob @Column(nullable = false) private String rego;
    @Column(length = 120) private String author;
    @CreatedDate @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
}
