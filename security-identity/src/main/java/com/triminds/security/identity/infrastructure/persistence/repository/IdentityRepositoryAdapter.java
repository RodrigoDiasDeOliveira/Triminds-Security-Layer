package com.triminds.security.identity.infrastructure.persistence.repository;

import com.triminds.security.identity.domain.model.*;
import com.triminds.security.identity.domain.repository.IdentityRepository;
import com.triminds.security.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import org.springframework.stereotype.Repository;
import java.util.*; import java.util.UUID;

@Repository
public class IdentityRepositoryAdapter implements IdentityRepository {
    private final IdentityJpaRepository jpa;
    private final IdentityPersistenceMapper mapper;
    public IdentityRepositoryAdapter(IdentityJpaRepository jpa, IdentityPersistenceMapper mapper) { this.jpa = jpa; this.mapper = mapper; }
    @Override public Identity save(Identity i) { return mapper.toDomain(jpa.save(mapper.toJpa(i))); }
    @Override public Optional<Identity> findById(IdentityId id) { return jpa.findById(UUID.fromString(id.toString())).map(mapper::toDomain); }
    @Override public Optional<Identity> findByUsername(String u) { return jpa.findByUsername(u).map(mapper::toDomain); }
    @Override public Optional<Identity> findByEmail(String e) { return jpa.findByEmail(e).map(mapper::toDomain); }
    @Override public Optional<Identity> findByFederation(FederationInfo.FederationProvider p, String extId) { return jpa.findByFederation(p.name(), extId).map(mapper::toDomain); }
    @Override public List<Identity> findByOrganization(OrganizationId org) { return jpa.findByOrganizationId(UUID.fromString(org.toString())).stream().map(mapper::toDomain).toList(); }
    @Override public List<Identity> findByOrganizationAndStatus(OrganizationId org, IdentityStatus s) { return jpa.findByOrganizationIdAndStatus(UUID.fromString(org.toString()), s).stream().map(mapper::toDomain).toList(); }
    @Override public boolean existsByUsername(String u) { return jpa.existsByUsername(u); }
    @Override public boolean existsByEmail(String e) { return jpa.existsByEmail(e); }
    @Override public void delete(IdentityId id) { jpa.deleteById(UUID.fromString(id.toString())); }
    @Override public long countByOrganization(OrganizationId org) { return jpa.countByOrganizationId(UUID.fromString(org.toString())); }
}
