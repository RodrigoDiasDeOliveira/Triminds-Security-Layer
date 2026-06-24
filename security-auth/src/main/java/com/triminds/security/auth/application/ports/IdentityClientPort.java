package com.triminds.security.auth.application.ports;

public interface IdentityClientPort {
    AuthenticatedUser authenticate(String tenantId, String username, String password);
    record AuthenticatedUser(String userId, String tenantId, String username, java.util.List<String> roles) {}
}
