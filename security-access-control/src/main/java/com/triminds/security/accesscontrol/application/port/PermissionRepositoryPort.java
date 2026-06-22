package com.triminds.security.accesscontrol.application.port;


import com.triminds.security.accesscontrol.domain.Permission;


import java.util.Optional;
import java.util.UUID;


public interface PermissionRepositoryPort {


Permission save(Permission permission);



Optional<Permission> findById(UUID id);



boolean existsPermission(

UUID identityId,

String action,

String resource

);


}