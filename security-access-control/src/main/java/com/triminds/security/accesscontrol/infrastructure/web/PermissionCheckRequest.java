package com.triminds.security.accesscontrol.infrastructure.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PermissionCheckRequest(
        @NotNull UUID identityId,
        @NotBlank String action,
        @NotBlank String resource
) {}
