package com.triminds.security.gateway.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.triminds.security.gateway")
public class SecurityGatewayApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityGatewayApplication.class, args); }
}
