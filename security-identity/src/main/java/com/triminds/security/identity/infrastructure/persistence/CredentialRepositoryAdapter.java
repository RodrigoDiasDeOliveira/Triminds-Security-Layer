package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.CredentialRepositoryPort;
import com.triminds.security.identity.domain.Credential;
import com.triminds.security.identity.infrastructure.persistence.entity.CredentialEntity;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CredentialRepositoryAdapter implements CredentialRepositoryPort {

    private final EntityManager entityManager;

    public CredentialRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Credential save(Credential credential) {

        CredentialEntity entity = new CredentialEntity();

        entity.setId(credential.id());
        entity.setIdentityId(credential.identityId());
        entity.setPasswordHash(credential.passwordHash());
        entity.setAlgorithm(credential.algorithm());
        entity.setUpdatedAt(credential.updatedAt());


        entityManager.persist(entity);


        return new Credential(
                entity.getId(),
                entity.getIdentityId(),
                entity.getPasswordHash(),
                entity.getAlgorithm(),
                entity.getUpdatedAt()
        );
    }


    @Override
    public Optional<Credential> findByIdentityId(UUID identityId) {

        CredentialEntity entity =
                entityManager.createQuery(
                        "select c from CredentialEntity c where c.identityId = :id",
                        CredentialEntity.class
                )
                .setParameter("id", identityId)
                .getResultStream()
                .findFirst()
                .orElse(null);


        if(entity == null){
            return Optional.empty();
        }


        return Optional.of(
                new Credential(
                        entity.getId(),
                        entity.getIdentityId(),
                        entity.getPasswordHash(),
                        entity.getAlgorithm(),
                        entity.getUpdatedAt()
                )
        );
    }
}