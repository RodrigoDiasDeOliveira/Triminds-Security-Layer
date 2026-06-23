package com.triminds.security.policy.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.triminds.security.policy")
@EnableFeignClients(basePackages = "com.triminds.security.policy.infrastructure.opa")
@EnableJpaAuditing
public class SecurityPolicyEngineApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityPolicyEngineApplication.class, args); }
}
