package com.triminds.security.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthServiceTest {

    @Test
    void authenticateReturnsBearerTokenForValidUser() {
        AuthService authService = new AuthService();
        AuthResponse response = authService.authenticate(new AuthRequest("admin", "password"));

        assertEquals("Bearer", response.type());
    }
}
