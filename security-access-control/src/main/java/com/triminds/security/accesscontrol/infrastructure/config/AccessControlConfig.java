package com.triminds.security.accesscontrol.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableFeignClients(basePackages = "com.triminds.security.accesscontrol.infrastructure.policy")
@EnableKafka
public class AccessControlConfig {
}