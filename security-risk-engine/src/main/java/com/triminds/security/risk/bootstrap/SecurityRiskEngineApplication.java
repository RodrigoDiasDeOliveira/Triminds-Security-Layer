package com.triminds.security.risk.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.triminds.security.risk")
@EnableKafka
public class SecurityRiskEngineApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityRiskEngineApplication.class, args); }
}
