package com.triminds.security.identity.application.ports;


public interface PasswordHasherPort {

    String hash(String rawPassword);

    boolean matches(
            String rawPassword,
            String hashedPassword
    );
}