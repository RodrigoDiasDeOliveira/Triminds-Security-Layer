package com.triminds.security.audit.application.port;

import com.triminds.security.audit.domain.AuditEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AuditRepositoryPort {
    AuditEntry append(AuditEntry e);
    Optional<AuditEntry> lastFor(UUID tenantId);
    Page<AuditEntry> query(UUID tenantId, Instant from, Instant to, String type, Pageable pageable);
}
