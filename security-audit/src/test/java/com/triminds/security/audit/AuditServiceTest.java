package com.triminds.security.audit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuditServiceTest {

    @Test
    void auditStatusReturnsRecordingMessage() {
        AuditService service = new AuditService();
        assertEquals("Audit engine is recording events", service.auditStatus());
    }
}
