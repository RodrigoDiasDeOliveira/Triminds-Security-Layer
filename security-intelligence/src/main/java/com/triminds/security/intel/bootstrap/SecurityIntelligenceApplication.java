package com.triminds.security.intel.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.triminds.security.intel")
@EnableKafka
public class SecurityIntelligenceApplication {
    public static void main(String[] args) { SpringApplication.run(SecurityIntelligenceApplication.class, args); }
}
