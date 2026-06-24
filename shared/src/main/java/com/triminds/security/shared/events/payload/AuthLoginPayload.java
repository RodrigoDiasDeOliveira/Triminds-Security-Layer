package com.triminds.security.shared.events.payload;

import java.util.UUID;

public record AuthLoginPayload(
        UUID identityId,
        String email,
        String ip,
        String userAgent,
        boolean success,
        String failureReason
) {}
