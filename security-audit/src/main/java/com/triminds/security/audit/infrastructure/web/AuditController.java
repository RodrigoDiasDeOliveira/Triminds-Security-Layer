package com.triminds.security.audit.infrastructure.web;

import com.triminds.security.audit.application.port.AuditRepositoryPort;
import com.triminds.security.audit.domain.AuditEntry;
import com.triminds.security.shared.web.SecurityHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditRepositoryPort repo;

    @GetMapping
    public Page<AuditEntry> query(
            @RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return repo.query(tenantId, from, to, type, PageRequest.of(page, Math.min(size, 500)));
    }

    @GetMapping(value = "/export.ndjson", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseEntity<String> export(
            @RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        var sb = new StringBuilder();
        repo.query(tenantId, from, to, null, PageRequest.of(0, 10_000))
            .forEach(e -> sb.append('{')
                .append("\"id\":\"").append(e.id()).append("\",")
                .append("\"seq\":").append(e.sequence()).append(',')
                .append("\"type\":\"").append(e.type()).append("\",")
                .append("\"occurredAt\":\"").append(e.occurredAt()).append("\",")
                .append("\"hash\":\"").append(e.entryHash()).append("\",")
                .append("\"prevHash\":\"").append(e.prevHash()).append("\",")
                .append("\"payload\":").append(e.payloadJson())
                .append("}\n"));
        return ResponseEntity.ok(sb.toString());
    }
}
