package com.triminds.security.accesscontrol.application.port;

import com.triminds.security.accesscontrol.domain.Role;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepositoryPort {
    Optional<Role> findById(UUID tenantId, UUID roleId);
}
