package com.triminds.security.identity;

import com.triminds.security.shared.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityServiceTest {

    @Test
    void getCurrentUserReturnsAdmin() {
        IdentityService service = new IdentityService();
        User user = service.getCurrentUser();

        assertEquals("admin.triminds", user.username());
        assertEquals("admin@triminds.com", user.email());
    }
}
