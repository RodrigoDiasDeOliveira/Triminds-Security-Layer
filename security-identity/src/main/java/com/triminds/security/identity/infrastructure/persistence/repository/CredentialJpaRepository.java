package com.triminds.security.identity.infrastructure.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CredentialJpaRepository extends JpaRepository<CredentialEntity, UUID> {
    Optional<CredentialEntity> findByIdentityId(UUID identityId);
}
