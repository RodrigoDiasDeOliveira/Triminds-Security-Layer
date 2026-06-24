package com.triminds.security.shared.events.payload;

import java.util.UUID;

public record RoleAssignmentPayload(
        UUID identityId,
        UUID roleId,
        UUID actorId,
        boolean assigned
) {}
