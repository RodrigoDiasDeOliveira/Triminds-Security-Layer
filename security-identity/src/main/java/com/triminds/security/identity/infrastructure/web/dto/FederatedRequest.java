package com.triminds.security.identity.infrastructure.web.dto;
import jakarta.validation.constraints.*;
public record FederatedRequest(@NotBlank String organizationId, @NotBlank String username, @Email @NotBlank String email, String displayName, @NotBlank String provider, @NotBlank String externalId, @NotBlank String issuer, @NotBlank String subject) {}
