package com.triminds.shared.security;

import java.util.List;

public record AuthenticatedPrincipal(
        String userId,
        String tenantId,
        String sessionId,
        List<String> roles,
        List<String> scopes
) {}
