package com.company.IntelligentPlatform.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Handles unauthenticated access to protected {@code /api/v1/**} endpoints.
 *
 * <p>Spring Security calls this entry point when a request reaches a
 * protected resource without a valid JWT token.  Instead of redirecting to
 * a login page (the default Spring Security behavior), this implementation
 * returns a machine-readable JSON error payload with HTTP 401 so that REST
 * clients receive a consistent error format matching the rest of the API.</p>
 *
 * <p>Response body example:</p>
 * <pre>
 *   { "error": "Unauthorized", "message": "Authentication required" }
 * </pre>
 *
 * <p>Registered in {@link SecurityConfig#filterChain} via
 * {@code exceptionHandling().authenticationEntryPoint(...)}.</p>
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Writes a 401 JSON response when the caller is not authenticated.
     *
     * <p>The method sets {@code Content-Type: application/json} and writes a
     * simple JSON object so that REST clients can detect auth failures
     * programmatically without parsing HTML error pages.</p>
     *
     * @param request       the request that triggered the auth failure
     * @param response      the response to write the 401 body into
     * @param authException the exception that caused the entry point to be invoked
     * @throws IOException if writing the response body fails
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(),
                Map.of("error", "Unauthorized", "message", "Authentication required"));
    }
}
