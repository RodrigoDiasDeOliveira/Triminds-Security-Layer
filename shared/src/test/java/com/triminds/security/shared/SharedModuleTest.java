package com.triminds.security.shared;

import com.triminds.security.shared.model.User;
import com.triminds.security.shared.model.Role;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedModuleTest {

    @Test
    void createUserWithRole() {
        User user = new User(1L, "admin", "admin@triminds.com", Set.of(new Role("ADMIN", Set.of("read", "write"))));
        assertEquals("admin", user.username());
        assertEquals(1, user.roles().size());
    }
}
