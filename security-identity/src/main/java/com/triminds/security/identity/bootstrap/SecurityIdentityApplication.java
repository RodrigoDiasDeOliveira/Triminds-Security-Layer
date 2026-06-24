package com.triminds.security.identity.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.triminds.identity", "com.triminds.shared"})
@EntityScan("com.triminds.identity.infrastructure.persistence.entity")
@EnableJpaRepositories("com.triminds.identity.infrastructure.persistence.repository")
public class SecurityIdentityApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityIdentityApplication.class, args); }
}
