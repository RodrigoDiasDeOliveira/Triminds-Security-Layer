package com.triminds.security.accesscontrol.infrastructure.messaging;

import java.util.UUID;

public record RoleAssignedEvent(

        UUID tenantId,
        UUID identityId,
        UUID roleId

) {}