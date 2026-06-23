package com.triminds.security.accesscontrol.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.triminds.security.accesscontrol.infrastructure.persistence.repository")
public class AccessControlConfig {}
