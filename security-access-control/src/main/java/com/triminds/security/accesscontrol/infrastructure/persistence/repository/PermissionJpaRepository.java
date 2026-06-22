package com.triminds.security.accesscontrol.infrastructure.persistence.repository;
import com.triminds.security.accesscontrol.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;       
import java.util.UUID;





public interface PermissionJpaRepository
extends JpaRepository<PermissionJpaEntity,UUID>{



@Query("""
select count(p)>0
from PermissionJpaEntity p

join RolePermission rp
on rp.permissionId=p.id

join IdentityRole ir
on ir.roleId=rp.roleId


where ir.identityId=:identityId

and p.action=:action

and p.resource=:resource

""")
boolean existsPermission(

UUID identityId,

String action,

String resource

);



}