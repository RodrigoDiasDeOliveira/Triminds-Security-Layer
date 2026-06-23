package com.triminds.security.accesscontrol.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.triminds.security.accesscontrol")
@EnableFeignClients(basePackages = "com.triminds.security.accesscontrol.infrastructure.policy")
@EnableJpaAuditing
@EnableKafka
public class SecurityAccessControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityAccessControlApplication.class, args);
    }
}
