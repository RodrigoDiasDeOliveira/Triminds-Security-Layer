package com.triminds.security.accesscontrol.infrastructure.persistence.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name="roles")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleJpaEntity {


@Id
private UUID id;



@Column(nullable=false)
private UUID tenantId;



@Column(nullable=false)
private String name;



private String description;



@Column(nullable=false)
private Instant createdAt;



}