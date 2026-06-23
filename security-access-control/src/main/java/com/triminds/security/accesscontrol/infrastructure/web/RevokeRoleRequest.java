package com.triminds.security.accesscontrol.infrastructure.web;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RevokeRoleRequest(@NotNull UUID identityId, @NotNull UUID roleId) {}
