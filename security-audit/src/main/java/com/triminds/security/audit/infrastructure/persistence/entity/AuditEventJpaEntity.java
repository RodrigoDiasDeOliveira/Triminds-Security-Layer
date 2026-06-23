package com.triminds.security.audit.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "audit_events",
       indexes = {
         @Index(name = "idx_audit_tenant_seq",  columnList = "tenant_id, sequence"),
         @Index(name = "idx_audit_tenant_when", columnList = "tenant_id, occurred_at")
       })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditEventJpaEntity {
    @Id private UUID id;
    @Column(name = "tenant_id",  nullable = false) private UUID tenantId;
    @Column(nullable = false, length = 80) private String type;
    @Column(name = "occurred_at",nullable = false) private Instant occurredAt;
    @Lob @Column(name = "payload_json", nullable = false) private String payloadJson;
    @Column(name = "prev_hash",  nullable = false, length = 80) private String prevHash;
    @Column(name = "entry_hash", nullable = false, length = 80) private String entryHash;
    @Column(nullable = false) private long sequence;
}
