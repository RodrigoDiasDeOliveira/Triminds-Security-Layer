package com.triminds.security.accesscontrol.application.port;

import com.triminds.security.accesscontrol.domain.AbacAttribute;
import java.util.List;
import java.util.UUID;

public interface AbacAttributeRepositoryPort {
    List<AbacAttribute> findByIdentity(UUID tenantId, UUID identityId);
}
