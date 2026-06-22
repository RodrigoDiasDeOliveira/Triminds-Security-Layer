package com.triminds.security.accesscontrol.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import jakarta.persistence.Column;
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
@Table(name="permissions")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionJpaEntity {


@Id
private UUID id;



@Column(nullable=false)
private String action;



@Column(nullable=false)
private String resource;



}