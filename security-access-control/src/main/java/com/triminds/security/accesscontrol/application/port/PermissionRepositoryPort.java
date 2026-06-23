package com.triminds.security.accesscontrol.application.port;

import java.util.UUID;

public interface PermissionRepositoryPort {
    /** Existe permissão (action,resource) atribuída ao identity via algum role do tenant? */
    boolean existsPermission(UUID tenantId, UUID identityId, String action, String resource);
}
