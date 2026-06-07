package com.triminds.security.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthResponse authenticate(AuthRequest request) {
        if ("admin".equalsIgnoreCase(request.username()) && "password".equals(request.password())) {
            return new AuthResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", "Bearer");
        }

        return new AuthResponse("", "Unauthorized");
    }
}
