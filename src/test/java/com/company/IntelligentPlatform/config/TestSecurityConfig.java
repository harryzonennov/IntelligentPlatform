package com.company.IntelligentPlatform.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Test-only security configuration.
 *
 * <p>Registered at {@code @Order(1)} so it takes precedence over the production
 * {@code SecurityConfig} filter chain. Allows sessions (IF_REQUIRED) so that
 * the legacy {@code @Scope("session")} controllers function correctly, and
 * permits all requests without authentication so tests can call any endpoint.</p>
 */
@TestConfiguration
public class TestSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
