package com.triminds.security.accesscontrol.infrastructure.persistence.repository;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.RoleAssignmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;   
import java.util.UUID;




public interface RoleAssignmentJpaRepository

extends JpaRepository<RoleAssignmentJpaEntity,UUID>{



boolean existsByIdentityIdAndRoleId(

UUID identityId,

UUID roleId

);



}