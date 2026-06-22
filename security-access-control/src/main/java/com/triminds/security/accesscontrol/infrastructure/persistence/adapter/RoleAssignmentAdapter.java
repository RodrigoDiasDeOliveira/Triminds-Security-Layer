package com.triminds.security.accesscontrol.infrastructure.persistence.adapter; 

import com.triminds.security.accesscontrol.domain.RoleAssignment;
import com.triminds.security.accesscontrol.domain.port.RoleAssignmentRepositoryPort;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleAssignmentJpaEntity;   
import com.triminds.security.accesscontrol.infrastructure.persistence.repository.RoleAssignmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class RoleAssignmentRepositoryAdapter

implements RoleAssignmentRepositoryPort {


private final RoleAssignmentJpaRepository repository;



@Override
public RoleAssignment save(RoleAssignment assignment){


var entity =
RoleAssignmentJpaEntity.builder()

.id(assignment.getId())

.identityId(assignment.getIdentityId())

.roleId(assignment.getRoleId())

.tenantId(assignment.getTenantId())

.createdAt(Instant.now())

.build();



repository.save(entity);



return assignment;

}



@Override
public boolean exists(

UUID identityId,

UUID roleId){


return repository.existsByIdentityIdAndRoleId(
identityId,
roleId
);


}



}