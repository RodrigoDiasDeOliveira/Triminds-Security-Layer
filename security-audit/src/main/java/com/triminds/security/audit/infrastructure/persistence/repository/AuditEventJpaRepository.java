package com.triminds.security.audit.infrastructure.persistence.repository;

import com.triminds.security.audit.infrastructure.persistence.entity.AuditEventJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AuditEventJpaRepository extends JpaRepository<AuditEventJpaEntity, UUID> {

    Optional<AuditEventJpaEntity> findTopByTenantIdOrderBySequenceDesc(UUID tenantId);

    @Query("""
       select a from AuditEventJpaEntity a
        where a.tenantId = :t
          and a.occurredAt between :from and :to
          and (:type is null or a.type = :type)
        order by a.sequence asc
    """)
    Page<AuditEventJpaEntity> query(@Param("t") UUID tenantId,
                                    @Param("from") Instant from,
                                    @Param("to")   Instant to,
                                    @Param("type") String  type,
                                    Pageable pageable);
}
