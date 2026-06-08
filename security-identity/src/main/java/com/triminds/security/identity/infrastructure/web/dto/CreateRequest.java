package com.triminds.security.identity.infrastructure.web.dto;
import jakarta.validation.constraints.*;
public record CreateRequest(
    @NotBlank String organizationId,
    @NotBlank @Size(min=3,max=50) @Pattern(regexp="^[a-z0-9._-]+$") String username,
    @NotBlank @Email String email,
    @Size(max=200) String displayName,
    String type) {}
