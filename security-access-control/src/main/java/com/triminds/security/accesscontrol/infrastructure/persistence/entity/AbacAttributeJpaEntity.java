package com.triminds.security.accesscontrol.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "abac_attributes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbacAttributeJpaEntity {

    @Id
    private UUID id;

    private UUID tenantId;

    private UUID identityId;

    private String attributeName;

    private String attributeValue;
}
