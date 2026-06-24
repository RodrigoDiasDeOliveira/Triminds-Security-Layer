package com.triminds.security.identity.infrastructure.persistence;

import com.triminds.security.identity.application.ports.IdentityRepositoryPort;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityEntity;
import com.triminds.security.identity.infrastructure.persistence.mapper.IdentityMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class IdentityRepositoryAdapter implements IdentityRepositoryPort {

    private final EntityManager entityManager;


    public IdentityRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Identity save(Identity identity) {

        IdentityEntity entity = IdentityMapper.toEntity(identity);

        IdentityEntity saved;

        if (entity.getId() == null) {

            entityManager.persist(entity);
            saved = entity;

        } else {

            saved = entityManager.merge(entity);

        }


        return IdentityMapper.toDomain(saved);
    }


    @Override
    public Optional<Identity> findById(UUID id) {

        IdentityEntity entity =
                entityManager.find(
                        IdentityEntity.class,
                        id
                );


        return Optional.ofNullable(entity)
                .map(IdentityMapper::toDomain);
    }


    @Override
    public Optional<Identity> findByTenantAndUsername(
            String tenantId,
            String username
    ) {


        TypedQuery<IdentityEntity> query =
                entityManager.createQuery(
                        """
                        select i 
                        from IdentityEntity i
                        where i.tenantId = :tenantId
                        and i.username = :username
                        """,
                        IdentityEntity.class
                );


        return query
                .setParameter("tenantId", tenantId)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .map(IdentityMapper::toDomain);
    }


    @Override
    public boolean existsByTenantAndUsername(
            String tenantId,
            String username
    ) {


        Long count =
                entityManager.createQuery(
                        """
                        select count(i)
                        from IdentityEntity i
                        where i.tenantId = :tenantId
                        and i.username = :username
                        """,
                        Long.class
                )
                .setParameter("tenantId", tenantId)
                .setParameter("username", username)
                .getSingleResult();


        return count > 0;
    }
}