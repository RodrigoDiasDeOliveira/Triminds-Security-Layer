package com.triminds.security.accesscontrol.infrastructure.web;


import java.util.UUID;


public record AssignRoleRequest(

UUID identityId,

UUID roleId

){}