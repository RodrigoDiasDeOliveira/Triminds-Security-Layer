package com.triminds.security.identity;

import com.triminds.security.shared.model.Role;
import com.triminds.security.shared.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IdentityService {

    public User getCurrentUser() {
        return new User(1L, "admin.triminds", "admin@triminds.com", Set.of(new Role("ADMIN", Set.of("identity:read", "identity:update"))));
    }

    public Set<String> getAvailableRoles() {
        return Set.of("ADMIN", "USER", "AUDITOR");
    }
}
