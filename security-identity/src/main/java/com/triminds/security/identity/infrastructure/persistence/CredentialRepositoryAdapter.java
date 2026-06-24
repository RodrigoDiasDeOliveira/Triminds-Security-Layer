package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.CredentialRepositoryPort;
import com.triminds.security.identity.domain.Credential;
import com.triminds.security.identity.infrastructure.persistence.entity.CredentialEntity;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CredentialRepositoryAdapter implements CredentialRepositoryPort {
    private final CredentialJpaRepository repo;
    public CredentialRepositoryAdapter(CredentialJpaRepository repo) { this.repo = repo; }

    @Override public Credential save(Credential c) {
        CredentialEntity e = new CredentialEntity();
        e.setId(c.id()); e.setIdentityId(c.identityId());
        e.setPasswordHash(c.passwordHash()); e.setAlgorithm(c.algorithm()); e.setUpdatedAt(c.updatedAt());
        CredentialEntity saved = repo.save(e);
        return new Credential(saved.getId(), saved.getIdentityId(), saved.getPasswordHash(),
                saved.getAlgorithm(), saved.getUpdatedAt());
    }
    @Override public Optional<Credential> findByIdentityId(UUID id) {
        return repo.findByIdentityId(id).map(e -> new Credential(e.getId(), e.getIdentityId(),
                e.getPasswordHash(), e.getAlgorithm(), e.getUpdatedAt()));
    }
}
