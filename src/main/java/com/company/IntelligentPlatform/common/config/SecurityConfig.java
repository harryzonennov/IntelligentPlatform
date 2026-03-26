package com.company.IntelligentPlatform.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security configuration for the unified IntelligentPlatform backend.
 *
 * <p>The security model is split into two tiers:</p>
 * <ul>
 *   <li><b>Legacy session tier</b> ({@code /common/**}, {@code /flowableTask/**},
 *       etc.) — the old {@code @Scope("session")} Spring MVC controllers manage
 *       their own {@code LogonUser} session state via
 *       {@link com.company.IntelligentPlatform.common.controller.LogonActionController}.
 *       These paths are permitted without a JWT so that the legacy front-end
 *       (JSP/jQuery) continues to work without changes.</li>
 *   <li><b>New REST tier</b> ({@code /api/v1/**}) — stateless JWT-protected
 *       endpoints.  Every request must carry a valid
 *       {@code Authorization: Bearer <token>} header.  The token is validated
 *       by {@link JwtAuthFilter} before the request reaches the controller.</li>
 * </ul>
 *
 * <p>CSRF protection is disabled because the API is consumed by SPAs and
 * mobile clients that rely on the {@code Authorization} header rather than
 * cookies.  The legacy session controllers accept AJAX requests from the
 * same origin, so disabling CSRF is safe here.</p>
 *
 * <p>Session creation is set to {@link SessionCreationPolicy#STATELESS} for
 * the REST tier; the legacy tier continues to use the servlet container's
 * HTTP session via Spring's {@code @Scope("session")} beans.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    /**
     * Comma-separated list of allowed CORS origins, injected from
     * {@code cors.allowed-origins} in {@code application.yml}.
     * Example: {@code http://localhost:3000,https://app.example.com}
     */
    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String allowedOriginsConfig;

    /**
     * Configures the main {@link SecurityFilterChain} for the application.
     *
     * <p>Route rules (evaluated top-to-bottom, first match wins):</p>
     * <ol>
     *   <li>{@code /api/v1/auth/**} — permit all; this is the JWT login endpoint.</li>
     *   <li>{@code /api/v1/**} — require authentication (valid JWT token).</li>
     *   <li>All other paths ({@code /common/**}, {@code /flowableTask/**},
     *       {@code /serviceFlowRuntime/**}, static resources, etc.) — permit all;
     *       the legacy session controllers enforce their own access checks via
     *       {@code LogonActionController} and {@code AuthorizationManager}.</li>
     * </ol>
     *
     * <p>The {@link JwtAuthFilter} is inserted before Spring Security's default
     * {@link UsernamePasswordAuthenticationFilter} so that it runs first and
     * can set the security context before any authorization decisions are made.</p>
     *
     * @param http the {@link HttpSecurity} builder provided by Spring Security
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if the configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — API is consumed via Authorization header, not cookies
            .csrf(AbstractHttpConfigurer::disable)

            // Explicit CORS configuration (origin whitelist via application.yml)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // HTTP security response headers
            .headers(headers -> headers
                // Prevent clickjacking
                .frameOptions(fo -> fo.deny())
                // Prevent MIME-type sniffing
                .contentTypeOptions(cto -> {})
                // Force HTTPS for 1 year (including subdomains) — enable only when TLS is terminated at app
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000))
                // Restrict information sent in Referer header
                .referrerPolicy(rp -> rp.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                // Basic Content-Security-Policy — tighten per deployment needs
                .contentSecurityPolicy(csp -> csp.policyDirectives(
                    "default-src 'self'; script-src 'self'; object-src 'none'; frame-ancestors 'none'"))
            )

            // Stateless session — JWT carries all authentication state
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Route authorization rules
            .authorizeHttpRequests(auth -> auth
                // JWT login endpoint — always open
                .requestMatchers("/api/v1/auth/**").permitAll()
                // All other /api/v1/** endpoints require a valid JWT
                .requestMatchers("/api/v1/**").authenticated()
                // Legacy session-based controllers — permit all (they guard themselves)
                .anyRequest().permitAll()
            )

            // Return 401 JSON instead of redirect-to-login for REST clients
            .exceptionHandling(ex ->
                ex.authenticationEntryPoint(jwtAuthEntryPoint))

            // Insert JWT validation filter before the default auth filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Explicit CORS policy.  Allowed origins are configured via
     * {@code cors.allowed-origins} in {@code application.yml} so that
     * production deployments can whitelist specific front-end origins
     * without modifying source code.
     *
     * @return a {@link CorsConfigurationSource} wired into the security chain
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> origins = Arrays.asList(allowedOriginsConfig.split(","));
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
