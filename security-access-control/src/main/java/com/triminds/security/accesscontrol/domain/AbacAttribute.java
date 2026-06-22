package com.triminds.security.accesscontrol.domain;


import lombok.Builder;
import lombok.Getter;


import java.util.UUID;


@Getter
@Builder
public class AbacAttribute {


private UUID id;


private UUID tenantId;


private UUID identityId;


private String attributeName;


private String attributeValue;


}