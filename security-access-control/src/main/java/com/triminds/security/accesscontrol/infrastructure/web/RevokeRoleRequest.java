package com.triminds.security.accesscontrol.infrastructure.web;

import java.util.UUID;

public record RevokeRoleRequest(
        UUID identityId,
        UUID roleId
) {}