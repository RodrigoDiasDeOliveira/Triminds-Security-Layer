package com.triminds.security.accesscontrol.infrastructure.persistence.adapter;

import com.triminds.security.accesscontrol.domain.Role;
import com.triminds.security.accesscontrol.domain.port.RoleRepositoryPort;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleJpaEntity;
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;        



@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter

implements RoleRepositoryPort {



private final RoleJpaRepository repository;



@Override
public Role save(Role role){


var entity =
RoleJpaEntity.builder()

.id(role.getId())

.tenantId(role.getTenantId())

.name(role.getName())

.description(role.getDescription())

.createdAt(role.getCreatedAt())

.build();


var saved =
repository.save(entity);



return Role.builder()

.id(saved.getId())

.tenantId(saved.getTenantId())

.name(saved.getName())

.description(saved.getDescription())

.createdAt(saved.getCreatedAt())

.build();

}



@Override
public Optional<Role> findById(UUID id){

return repository.findById(id)
.map(e -> Role.builder()

.id(e.getId())

.name(e.getName())

.description(e.getDescription())

.tenantId(e.getTenantId())

.createdAt(e.getCreatedAt())

.build());

}



}