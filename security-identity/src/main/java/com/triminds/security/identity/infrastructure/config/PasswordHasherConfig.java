package com.triminds.security.identity.infrastructure.config;

import com.triminds.security.identity.application.ports.PasswordHasherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordHasherConfig {
    @Bean
    public PasswordHasherPort passwordHasher() {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder(12);
        return new PasswordHasherPort() {
            @Override public String hash(String raw) { return enc.encode(raw); }
            @Override public boolean matches(String raw, String hashed) { return enc.matches(raw, hashed); }
        };
    }
}
