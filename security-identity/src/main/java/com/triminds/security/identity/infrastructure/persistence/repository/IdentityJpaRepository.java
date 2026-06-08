package com.triminds.security.identity.infrastructure.persistence.repository;
import com.triminds.security.identity.domain.model.IdentityStatus;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*; import java.util.UUID;
public interface IdentityJpaRepository extends JpaRepository<IdentityJpaEntity, UUID> {
    Optional<IdentityJpaEntity> findByUsername(String username);
    Optional<IdentityJpaEntity> findByEmail(String email);
    @Query("SELECT i FROM IdentityJpaEntity i WHERE i.federationProvider = :provider AND i.federationExternalId = :externalId")
    Optional<IdentityJpaEntity> findByFederation(@Param("provider") String provider, @Param("externalId") String externalId);
    List<IdentityJpaEntity> findByOrganizationId(UUID organizationId);
    List<IdentityJpaEntity> findByOrganizationIdAndStatus(UUID organizationId, IdentityStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    long countByOrganizationId(UUID organizationId);
}
