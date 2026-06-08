package com.triminds.security.identity.domain.repository;
import com.triminds.security.identity.domain.model.*;
import java.util.List; import java.util.Optional;
public interface IdentityRepository {
    Identity save(Identity identity);
    Optional<Identity> findById(IdentityId id);
    Optional<Identity> findByUsername(String username);
    Optional<Identity> findByEmail(String email);
    Optional<Identity> findByFederation(FederationInfo.FederationProvider provider, String externalId);
    List<Identity> findByOrganization(OrganizationId organizationId);
    List<Identity> findByOrganizationAndStatus(OrganizationId organizationId, IdentityStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void delete(IdentityId id);
    long countByOrganization(OrganizationId organizationId);
}
