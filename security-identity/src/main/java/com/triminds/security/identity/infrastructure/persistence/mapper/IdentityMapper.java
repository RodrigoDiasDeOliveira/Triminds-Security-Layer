package com.triminds.security.identity.infrastructure.persistence.mapper;

import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityEntity;

public final class IdentityMapper {
    private IdentityMapper() {}
    public static Identity toDomain(IdentityEntity e) {
        return new Identity(e.getId(), e.getTenantId(), e.getUsername(), e.getEmail(),
                e.getStatus(), e.getFailedAttempts(), e.getCreatedAt(), e.getUpdatedAt());
    }
    public static IdentityEntity toEntity(Identity i) {
        IdentityEntity e = new IdentityEntity();
        e.setId(i.getId()); e.setTenantId(i.getTenantId()); e.setUsername(i.getUsername());
        e.setEmail(i.getEmail()); e.setStatus(i.getStatus()); e.setFailedAttempts(i.getFailedAttempts());
        e.setCreatedAt(i.getCreatedAt()); e.setUpdatedAt(i.getUpdatedAt());
        return e;
    }
}
