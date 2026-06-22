package com.triminds.security.accesscontrol.application.port;


import com.triminds.security.accesscontrol.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface RoleRepositoryPort {


Role save(Role role);


Optional<Role> findById(UUID id);


List<Role> findByTenantId(UUID tenantId);


}