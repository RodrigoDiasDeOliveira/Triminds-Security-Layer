package com.triminds.security.accesscontrol.infrastructure.messaging;

import java.time.Instant;
import java.util.UUID;

public record AccessDeniedEvent(

        UUID tenantId,
        UUID identityId,
        String action,
        String resource,
        Instant timestamp

) {}