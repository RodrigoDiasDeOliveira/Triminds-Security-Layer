package com.triminds.security.accesscontrol.infrastructure.persistence.adapter;


import com.triminds.security.accesscontrol.domain.Permission;   
import com.triminds.security.accesscontrol.domain.port.PermissionRepositoryPort;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.PermissionJpaEntity;   
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class PermissionRepositoryAdapter

implements PermissionRepositoryPort {



private final PermissionJpaRepository repository;



@Override
public boolean existsPermission(

UUID identityId,

String action,

String resource){


return repository.existsPermission(
identityId,
action,
resource
);


}


@Override
public Permission save(Permission permission){

return permission;

}


@Override
public Optional<Permission> findById(UUID id){

return repository.findById(id)

.map(e ->
Permission.builder()

.id(e.getId())

.action(e.getAction())

.resource(e.getResource())

.build());

}



}