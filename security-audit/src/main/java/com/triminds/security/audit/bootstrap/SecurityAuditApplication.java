package com.triminds.security.audit.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.triminds.security.audit")
@EnableKafka
@EnableJpaAuditing
public class SecurityAuditApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityAuditApplication.class, args); }
}
