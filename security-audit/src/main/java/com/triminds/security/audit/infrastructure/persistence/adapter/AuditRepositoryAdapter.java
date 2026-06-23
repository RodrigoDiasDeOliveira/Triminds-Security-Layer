package com.triminds.security.audit.infrastructure.persistence.adapter;

import com.triminds.security.audit.application.port.AuditRepositoryPort;
import com.triminds.security.audit.domain.AuditEntry;
import com.triminds.security.audit.infrastructure.persistence.entity.AuditEventJpaEntity;
import com.triminds.security.audit.infrastructure.persistence.repository.AuditEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuditRepositoryAdapter implements AuditRepositoryPort {

    private final AuditEventJpaRepository repo;

    private AuditEntry toDomain(AuditEventJpaEntity e) {
        return new AuditEntry(e.getId(), e.getTenantId(), e.getType(), e.getOccurredAt(),
                e.getPayloadJson(), e.getPrevHash(), e.getEntryHash(), e.getSequence());
    }

    @Override
    public AuditEntry append(AuditEntry e) {
        var saved = repo.save(AuditEventJpaEntity.builder()
                .id(e.id()).tenantId(e.tenantId()).type(e.type())
                .occurredAt(e.occurredAt()).payloadJson(e.payloadJson())
                .prevHash(e.prevHash()).entryHash(e.entryHash()).sequence(e.sequence())
                .build());
        return toDomain(saved);
    }

    @Override
    public Optional<AuditEntry> lastFor(UUID tenantId) {
        return repo.findTopByTenantIdOrderBySequenceDesc(tenantId).map(this::toDomain);
    }

    @Override
    public Page<AuditEntry> query(UUID tenantId, Instant from, Instant to, String type, Pageable pageable) {
        return repo.query(tenantId, from, to, type, pageable).map(this::toDomain);
    }
}
