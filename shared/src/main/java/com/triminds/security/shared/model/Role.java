package com.triminds.security.shared.model;

import java.util.Set;

public record Role(String name, Set<String> permissions) {
}
