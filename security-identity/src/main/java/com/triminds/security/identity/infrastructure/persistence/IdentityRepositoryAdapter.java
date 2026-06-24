package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.IdentityRepositoryPort;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.infrastructure.persistence.mapper.IdentityMapper;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class IdentityRepositoryAdapter implements IdentityRepositoryPort {
    private final IdentityJpaRepository repo;
    public IdentityRepositoryAdapter(IdentityJpaRepository repo) { this.repo = repo; }
    @Override public Identity save(Identity i) { return IdentityMapper.toDomain(repo.save(IdentityMapper.toEntity(i))); }
    @Override public Optional<Identity> findById(UUID id) { return repo.findById(id).map(IdentityMapper::toDomain); }
    @Override public Optional<Identity> findByTenantAndUsername(String t, String u) {
        return repo.findByTenantIdAndUsername(t, u).map(IdentityMapper::toDomain);
    }
    @Override public boolean existsByTenantAndUsername(String t, String u) { return repo.existsByTenantIdAndUsername(t, u); }
}
