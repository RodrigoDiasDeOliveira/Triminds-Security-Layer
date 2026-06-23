package com.triminds.security.accesscontrol.infrastructure.web;

import com.triminds.security.accesscontrol.application.usecase.AssignRoleUseCase;
import com.triminds.security.accesscontrol.application.usecase.CheckPermissionUseCase;
import com.triminds.security.accesscontrol.application.usecase.RevokeRoleUseCase;
import com.triminds.security.shared.web.SecurityHeaders;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
@Validated
public class AccessControlController {

    private final AssignRoleUseCase  assignRoleUseCase;
    private final RevokeRoleUseCase  revokeRoleUseCase;
    private final CheckPermissionUseCase checkPermissionUseCase;

    @PostMapping("/roles/assign")
    public ResponseEntity<Void> assignRole(
            @RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AssignRoleRequest req) {
        assignRoleUseCase.execute(tenantId, UUID.fromString(jwt.getSubject()), req.identityId(), req.roleId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/revoke")
    public ResponseEntity<Void> revokeRole(
            @RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody RevokeRoleRequest req) {
        revokeRoleUseCase.execute(tenantId, UUID.fromString(jwt.getSubject()), req.identityId(), req.roleId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<PermissionCheckResponse> checkPermission(
            @RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
            @RequestParam @NotNull UUID identityId,
            @RequestParam @NotBlank String action,
            @RequestParam @NotBlank String resource) {
        boolean allowed = checkPermissionUseCase.execute(tenantId, identityId, action, resource);
        return ResponseEntity.ok(new PermissionCheckResponse(allowed));
    }
}
