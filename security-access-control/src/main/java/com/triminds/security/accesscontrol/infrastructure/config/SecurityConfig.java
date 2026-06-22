package com.triminds.security.accesscontrol.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/access/**").authenticated()
            )

            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt()
            );

        return http.build();
    }
}