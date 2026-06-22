package com.triminds.security.accesscontrol.domain;


import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;


@Getter
@Builder
public class Role {


private UUID id;


private UUID tenantId;


private String name;


private String description;


private Instant createdAt;



}