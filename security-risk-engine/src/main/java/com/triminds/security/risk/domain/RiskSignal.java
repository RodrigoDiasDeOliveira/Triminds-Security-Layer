package com.triminds.security.risk.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RiskSignal(
        @NotNull UUID tenantId,
        @NotNull UUID identityId,
        @NotBlank String ip,
        String userAgent,
        String geoCountry,
        String deviceFingerprint,
        String action) {}
