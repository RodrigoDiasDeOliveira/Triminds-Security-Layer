package com.triminds.security.identity.infrastructure.persistence.mapper;

import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityEntity;

public final class IdentityMapper {

    private IdentityMapper() {
    }


    public static Identity toDomain(IdentityEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Identity(
                entity.getId(),
                entity.getTenantId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getFailedAttempts(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }


    public static IdentityEntity toEntity(Identity identity) {

        if (identity == null) {
            return null;
        }

        IdentityEntity entity = new IdentityEntity();

        entity.setId(identity.getId());
        entity.setTenantId(identity.getTenantId());
        entity.setUsername(identity.getUsername());
        entity.setEmail(identity.getEmail());
        entity.setStatus(identity.getStatus());
        entity.setFailedAttempts(identity.getFailedAttempts());
        entity.setCreatedAt(identity.getCreatedAt());
        entity.setUpdatedAt(identity.getUpdatedAt());

        return entity;
    }
}