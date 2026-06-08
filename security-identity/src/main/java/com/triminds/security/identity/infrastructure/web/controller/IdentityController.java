package com.triminds.security.identity.infrastructure.web.controller;

import com.triminds.security.identity.application.usecase.*;
import com.triminds.security.identity.domain.model.*;
import com.triminds.security.identity.infrastructure.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/identities")
public class IdentityController {

    private final IdentityService svc;
    public IdentityController(IdentityService svc) { this.svc = svc; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse create(@Valid @RequestBody CreateRequest r) {
        return IdentityResponse.from(svc.createIdentity(r.organizationId(), r.username(), r.email(), r.displayName(), r.type()));
    }

    @PostMapping("/federated") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse createFederated(@Valid @RequestBody FederatedRequest r) {
        return IdentityResponse.from(svc.createFederated(r.organizationId(), r.username(), r.email(), r.displayName(), r.provider(), r.externalId(), r.issuer(), r.subject()));
    }

    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('IDENTITY_ADMIN','IDENTITY_VIEWER')")
    public IdentityResponse findById(@PathVariable String id) {
        return svc.findById(id).map(IdentityResponse::from).orElseThrow(() -> new IdentityNotFoundException("Não encontrado: " + id));
    }

    @GetMapping @PreAuthorize("hasAnyRole('IDENTITY_ADMIN','IDENTITY_VIEWER')")
    public List<IdentityResponse> list(@RequestParam String organizationId, @RequestParam(required = false) String status) {
        List<Identity> list = status != null
                ? svc.findByOrganizationAndStatus(organizationId, IdentityStatus.valueOf(status.toUpperCase()))
                : svc.findByOrganization(organizationId);
        return list.stream().map(IdentityResponse::from).toList();
    }

    @PostMapping("/{id}/activate") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse activate(@PathVariable String id) { return IdentityResponse.from(svc.activate(id)); }

    @PostMapping("/{id}/lock") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse lock(@PathVariable String id, @RequestBody LockRequest r) {
        return IdentityResponse.from(svc.lock(id, r.reason(), r.lockMinutes()));
    }

    @PostMapping("/{id}/unlock") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse unlock(@PathVariable String id) { return IdentityResponse.from(svc.unlock(id)); }

    @PostMapping("/{id}/disable") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse disable(@PathVariable String id, @RequestBody DisableRequest r) {
        return IdentityResponse.from(svc.disable(id, r.reason()));
    }

    @PutMapping("/{id}/profile") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse updateProfile(@PathVariable String id, @RequestBody ProfileRequest r) {
        return IdentityResponse.from(svc.updateProfile(id, r.displayName(), r.email()));
    }

    @PostMapping("/{id}/groups") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse addGroup(@PathVariable String id, @RequestBody GroupRequest r) {
        return IdentityResponse.from(svc.addToGroup(id, r.groupId()));
    }

    @DeleteMapping("/{id}/groups/{groupId}") @PreAuthorize("hasRole('IDENTITY_ADMIN')")
    public IdentityResponse removeGroup(@PathVariable String id, @PathVariable String groupId) {
        return IdentityResponse.from(svc.removeFromGroup(id, groupId));
    }

    @ExceptionHandler(IdentityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(IdentityNotFoundException ex) { return new ErrorResponse("IDENTITY_NOT_FOUND", ex.getMessage()); }

    @ExceptionHandler(IdentityAlreadyExistsException.class) @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(IdentityAlreadyExistsException ex) { return new ErrorResponse("ALREADY_EXISTS", ex.getMessage()); }

    @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(IllegalArgumentException ex) { return new ErrorResponse("INVALID_ARGUMENT", ex.getMessage()); }

    @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleState(IllegalStateException ex) { return new ErrorResponse("INVALID_STATE", ex.getMessage()); }

    record ErrorResponse(String code, String message) {}
}
