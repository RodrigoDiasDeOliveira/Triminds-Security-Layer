package com.triminds.security.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    public String auditStatus() {
        return "Audit engine is recording events";
    }
}
