package com.triminds.security.accesscontrol.application.port;

import com.triminds.security.accesscontrol.domain.RoleAssignment;
import java.util.UUID;

public interface RoleAssignmentRepositoryPort {
    RoleAssignment save(RoleAssignment ra);
    void delete(UUID tenantId, UUID identityId, UUID roleId);
    boolean exists(UUID tenantId, UUID identityId, UUID roleId);
}
