package com.triminds.security.accesscontrol.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;  
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;       


@Entity
@Table(name="identity_roles")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignmentJpaEntity {


@Id
private UUID id;



@Column(nullable=false)
private UUID tenantId;



@Column(nullable=false)
private UUID identityId;



@Column(nullable=false)
private UUID roleId;



private Instant createdAt;



}