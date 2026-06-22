package com.triminds.security.accesscontrol.infrastructure.web;

import com.triminds.security.accesscontrol.application.usecase.AssignRoleUseCase;
import com.triminds.security.accesscontrol.application.usecase.CheckPermissionUseCase;
import com.triminds.security.accesscontrol.application.usecase.RevokeRoleUseCase;
import com.triminds.security.accesscontrol.infrastructure.web.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessControlController {

    private final AssignRoleUseCase assignRoleUseCase;
    private final RevokeRoleUseCase revokeRoleUseCase;
    private final CheckPermissionUseCase checkPermissionUseCase;

    @PostMapping("/roles/assign")
    public ResponseEntity<Void> assignRole(@RequestHeader("X-Tenant-Id") UUID tenantId,
                                           @RequestBody AssignRoleRequest request) {

        assignRoleUseCase.execute(
                tenantId,
                request.identityId(),
                request.roleId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/revoke")
    public ResponseEntity<Void> revokeRole(@RequestHeader("X-Tenant-Id") UUID tenantId,
                                           @RequestBody RevokeRoleRequest request) {

        revokeRoleUseCase.execute(
                tenantId,
                request.identityId(),
                request.roleId()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<PermissionCheckResponse> checkPermission(
            @RequestHeader("X-Tenant-Id") UUID tenantId,
            @RequestParam UUID identityId,
            @RequestParam String action,
            @RequestParam String resource
    ) {

        boolean allowed = checkPermissionUseCase.execute(
                tenantId,
                identityId,
                action,
                resource
        );

        return ResponseEntity.ok(
                new PermissionCheckResponse(allowed)
        );
    }
}