package com.triminds.auth.infrastructure.web;

import com.triminds.auth.application.usecase.LoginUseCase;
import com.triminds.auth.application.usecase.LogoutUseCase;
import com.triminds.auth.application.usecase.RefreshTokenUseCase;
import com.triminds.auth.domain.TokenPair;
import com.triminds.shared.tenant.TenantHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final LoginUseCase login;
    private final RefreshTokenUseCase refresh;
    private final LogoutUseCase logout;

    public AuthController(LoginUseCase login, RefreshTokenUseCase refresh, LogoutUseCase logout) {
        this.login = login; this.refresh = refresh; this.logout = logout;
    }

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    public record RefreshRequest(@NotBlank String refreshToken) {}
    public record LogoutRequest(@NotBlank String sessionId) {}

    @PostMapping("/login")
    public TokenPair login(@Valid @RequestBody LoginRequest req, HttpServletRequest http) {
        String tenant = TenantHolder.tenantId() != null ? TenantHolder.tenantId() : "default";
        Map<String,Object> ctx = Map.of("ip", http.getRemoteAddr(),
                "userAgent", String.valueOf(http.getHeader("User-Agent")));
        return login.execute(tenant, req.username(), req.password(), ctx);
    }

    @PostMapping("/refresh")
    public TokenPair refresh(@Valid @RequestBody RefreshRequest req) { return refresh.execute(req.refreshToken()); }

    @PostMapping("/logout")
    public Map<String,String> logout(@Valid @RequestBody LogoutRequest req) {
        logout.execute(req.sessionId()); return Map.of("status", "revoked");
    }
}
