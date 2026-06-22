package com.triminds.identity.infrastructure.persistence.repository;

import com.triminds.identity.infrastructure.persistence.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CredentialJpaRepository extends JpaRepository<CredentialEntity, UUID> {
    Optional<CredentialEntity> findByIdentityId(UUID identityId);
}
