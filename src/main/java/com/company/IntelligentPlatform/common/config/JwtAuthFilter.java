package com.company.IntelligentPlatform.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Servlet filter that validates a JWT Bearer token on every incoming request
 * and populates the Spring Security {@link org.springframework.security.core.context.SecurityContext}.
 *
 * <p>The filter is executed exactly once per request (extends
 * {@link OncePerRequestFilter}) and runs before Spring Security's
 * authorization checks.  It only acts when a well-formed
 * {@code Authorization: Bearer <token>} header is present; requests without
 * a header are passed through unchanged, allowing the
 * {@link SecurityConfig#filterChain} rule set to reject them as
 * unauthenticated.</p>
 *
 * <p>On successful validation the filter stores a minimal
 * {@link UsernamePasswordAuthenticationToken} in the
 * {@link SecurityContextHolder}.  The principal is set to a
 * {@link JwtPrincipal} that carries {@code userId}, {@code uuid},
 * {@code client}, and {@code languageCode} so that REST controllers can
 * retrieve tenant and user context from the security context without an
 * extra DB query.</p>
 *
 * <p>Token parsing and validation are delegated to {@link JwtUtil}.</p>
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Extracts and validates the JWT from the {@code Authorization} header.
     *
     * <p>Processing steps:</p>
     * <ol>
     *   <li>Read the {@code Authorization} header; skip if absent or not
     *       prefixed with {@code "Bearer "}.</li>
     *   <li>Strip the prefix and validate the token via
     *       {@link JwtUtil#isValid(String)}.</li>
     *   <li>If valid, extract claims and build a
     *       {@link UsernamePasswordAuthenticationToken} carrying a
     *       {@link JwtPrincipal}; set it on the
     *       {@link SecurityContextHolder}.</li>
     *   <li>Continue the filter chain regardless of outcome — the
     *       authorization decision is made downstream.</li>
     * </ol>
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the remainder of the filter chain
     * @throws ServletException if the chain throws a servlet exception
     * @throws IOException      if the chain throws an I/O exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length());
            if (jwtUtil.isValid(token) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                String userId       = jwtUtil.extractUserId(token);
                String uuid         = jwtUtil.extractUuid(token);
                String client       = jwtUtil.extractClient(token);
                String languageCode = jwtUtil.extractLanguageCode(token);

                JwtPrincipal principal = new JwtPrincipal(userId, uuid, client, languageCode);

                // Minimal authority — role-based access is handled by the legacy
                // AuthorizationManager inside each service method.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
