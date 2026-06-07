package com.triminds.security.shared.model;

import java.util.Set;

public record User(Long id, String username, String email, Set<Role> roles) {
}
