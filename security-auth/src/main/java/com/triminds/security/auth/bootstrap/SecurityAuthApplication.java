package com.triminds.security.auth.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.triminds.auth", "com.triminds.shared"})
@EnableFeignClients(basePackages = "com.triminds.auth.infrastructure.client")
public class SecurityAuthApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityAuthApplication.class, args); }
}
