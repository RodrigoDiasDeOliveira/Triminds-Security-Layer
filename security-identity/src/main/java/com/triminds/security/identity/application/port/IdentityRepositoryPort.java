package com.triminds.security.identity.application.ports;

import com.triminds.security.identity.domain.Identity;
import java.util.Optional;
import java.util.UUID;

public interface IdentityRepositoryPort {
    Identity save(Identity identity);
    Optional<Identity> findById(UUID id);
    Optional<Identity> findByTenantAndUsername(String tenantId, String username);
    boolean existsByTenantAndUsername(String tenantId, String username);
}
