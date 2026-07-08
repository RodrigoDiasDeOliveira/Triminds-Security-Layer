package com.triminds.security.accesscontrol.infrastructure.persistence.adapter;

import com.triminds.security.accesscontrol.application.port.PermissionRepositoryPort;
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final PermissionJpaRepository repository;

    @Override
    public boolean existsPermission(UUID tenantId, UUID identityId, String action, String resource) {
        return repository.existsPermission(identityId, action, resource);
    }
}
