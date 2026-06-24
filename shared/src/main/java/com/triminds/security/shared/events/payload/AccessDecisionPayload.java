package com.triminds.security.shared.events.payload;

import java.util.UUID;

public record AccessDecisionPayload(
        UUID identityId,
        String action,
        String resource,
        boolean allowed,
        String reason
) {}
