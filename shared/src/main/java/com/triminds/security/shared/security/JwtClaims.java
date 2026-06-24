package com.triminds.security.shared.jwt;

import java.util.List;
import java.util.UUID;

/** Claims padronizadas emitidas pelo security-auth. */
public record JwtClaims(
        UUID sub,        // identityId
        UUID tid,        // tenantId
        String email,
        List<String> roles,
        List<String> scopes,
        long iat,
        long exp
) {}
