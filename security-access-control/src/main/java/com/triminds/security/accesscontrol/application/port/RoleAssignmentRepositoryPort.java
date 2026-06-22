package com.triminds.security.accesscontrol.application.port;


import com.triminds.security.accesscontrol.domain.RoleAssignment;


import java.util.UUID;


public interface RoleAssignmentRepositoryPort {


RoleAssignment save(RoleAssignment assignment);



boolean exists(

UUID identityId,

UUID roleId

);



}