package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.CredentialRepositoryPort;
import com.triminds.security.identity.domain.Credential;
import com.triminds.security.identity.infrastructure.persistence.entity.CredentialEntity;
import com.triminds.security.identity.infrastructure.persistence.repository.CredentialJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CredentialRepositoryAdapter implements CredentialRepositoryPort {

    private final CredentialJpaRepository jpaRepository;

    public CredentialRepositoryAdapter(CredentialJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Credential save(Credential credential) {
        CredentialEntity entity = new CredentialEntity();

        entity.setId(credential.id());
        entity.setIdentityId(credential.identityId());
        entity.setPasswordHash(credential.passwordHash());
        entity.setAlgorithm(credential.algorithm());
        entity.setUpdatedAt(credential.updatedAt());

        CredentialEntity saved = jpaRepository.save(entity);

        return new Credential(
                saved.getId(),
                saved.getIdentityId(),
                saved.getPasswordHash(),
                saved.getAlgorithm(),
                saved.getUpdatedAt()
        );
    }

    @Override
    public Optional<Credential> findByIdentityId(UUID identityId) {
        return jpaRepository.findByIdentityId(identityId)
                .map(entity -> new Credential(
                        entity.getId(),
                        entity.getIdentityId(),
                        entity.getPasswordHash(),
                        entity.getAlgorithm(),
                        entity.getUpdatedAt()
                ));
    }
}