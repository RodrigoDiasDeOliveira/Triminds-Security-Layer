package com.triminds.identity.infrastructure.web;

import com.triminds.identity.application.usecase.ActivateIdentityUseCase;
import com.triminds.identity.application.usecase.AuthenticateIdentityUseCase;
import com.triminds.identity.application.usecase.CreateIdentityUseCase;
import com.triminds.identity.domain.Identity;
import com.triminds.shared.tenant.TenantHolder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/identities")
public class IdentityController {

    private final CreateIdentityUseCase create;
    private final ActivateIdentityUseCase activate;
    private final AuthenticateIdentityUseCase authenticate;

    public IdentityController(CreateIdentityUseCase create, ActivateIdentityUseCase activate,
                              AuthenticateIdentityUseCase authenticate) {
        this.create = create; this.activate = activate; this.authenticate = authenticate;
    }

    public record CreateIdentityRequest(
            @NotBlank String username, @Email @NotBlank String email,
            @NotBlank @Size(min = 12, max = 256) String password) {}

    public record AuthenticateRequest(@NotBlank String username, @NotBlank String password) {}

    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@Valid @RequestBody CreateIdentityRequest req) {
        Identity i = create.execute(tenant(), req.username(), req.email(), req.password());
        return ResponseEntity.status(201).body(toView(i));
    }

    @PostMapping("/{id}/activate")
    public Map<String,Object> activate(@PathVariable UUID id) { return toView(activate.execute(id)); }

    @PostMapping("/authenticate")
    public Map<String,Object> authenticate(@Valid @RequestBody AuthenticateRequest req) {
        return toView(authenticate.execute(tenant(), req.username(), req.password()));
    }

    private String tenant() {
        String t = TenantHolder.tenantId();
        return t != null ? t : "default";
    }
    private Map<String,Object> toView(Identity i) {
        return Map.of("id", i.getId(), "tenantId", i.getTenantId(), "username", i.getUsername(),
                "email", i.getEmail(), "status", i.getStatus().name());
    }
}
