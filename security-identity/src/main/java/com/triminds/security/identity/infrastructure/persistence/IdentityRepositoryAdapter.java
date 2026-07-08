package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.IdentityRepositoryPort;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityEntity;
import com.triminds.security.identity.infrastructure.persistence.mapper.IdentityMapper;
import com.triminds.security.identity.infrastructure.persistence.repository.IdentityJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class IdentityRepositoryAdapter implements IdentityRepositoryPort {

    private final IdentityJpaRepository jpaRepository;

    public IdentityRepositoryAdapter(IdentityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Identity save(Identity identity) {
        IdentityEntity entity = IdentityMapper.toEntity(identity);
        IdentityEntity saved = jpaRepository.save(entity);
        return IdentityMapper.toDomain(saved);
    }

    @Override
    public Optional<Identity> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(IdentityMapper::toDomain);
    }

    @Override
    public Optional<Identity> findByTenantAndUsername(String tenantId, String username) {
        return jpaRepository.findByTenantIdAndUsername(tenantId, username)
                .map(IdentityMapper::toDomain);
    }

    @Override
    public boolean existsByTenantAndUsername(String tenantId, String username) {
        return jpaRepository.existsByTenantIdAndUsername(tenantId, username);
    }
}