package com.triminds.security.accesscontrol.infrastructure.web;


import java.util.UUID;


public record PermissionCheckRequest(

UUID identityId,

String action,

String resource

){}