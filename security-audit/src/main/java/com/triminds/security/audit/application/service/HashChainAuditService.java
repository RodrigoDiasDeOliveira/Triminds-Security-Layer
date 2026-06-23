package com.triminds.security.audit.application.service;

import com.triminds.security.audit.application.port.AuditRepositoryPort;
import com.triminds.security.audit.application.usecase.AppendAuditUseCase;
import com.triminds.security.audit.domain.AuditEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

/** Append-only com hash chain por tenant: entryHash = sha256(prevHash | tenant | type | payload | seq). */
@Service
@RequiredArgsConstructor
public class HashChainAuditService implements AppendAuditUseCase {

    private final AuditRepositoryPort repo;

    @Override @Transactional
    public void append(UUID tenantId, String type, Instant occurredAt, String payloadJson) {
        var last = repo.lastFor(tenantId);
        String prev = last.map(AuditEntry::entryHash).orElse("GENESIS");
        long seq    = last.map(AuditEntry::sequence).orElse(0L) + 1;
        String hash = sha256(prev + "|" + tenantId + "|" + type + "|" + payloadJson + "|" + seq);
        repo.append(new AuditEntry(UUID.randomUUID(), tenantId, type, occurredAt,
                payloadJson, prev, hash, seq));
    }

    private String sha256(String s) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(s.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(d);
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
}
