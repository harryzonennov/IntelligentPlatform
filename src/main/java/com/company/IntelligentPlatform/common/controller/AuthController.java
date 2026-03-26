package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.config.JwtAuthContextCache;
import com.company.IntelligentPlatform.common.config.JwtPrincipal;
import com.company.IntelligentPlatform.common.config.JwtRestContext;
import com.company.IntelligentPlatform.common.config.JwtUtil;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.repository.LogonUserRepository;
import com.company.IntelligentPlatform.common.service.IServiceEncodeProxy;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceEncodeException;
import com.company.IntelligentPlatform.common.service.ServiceEncodeProxyFactory;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * REST authentication controller for the new {@code /api/v1/**} tier.
 *
 * <p>Provides a single stateless login endpoint that validates credentials
 * against the {@code platform.LogonUser} table and returns a signed JWT token.
 * Subsequent REST calls must include this token in the
 * {@code Authorization: Bearer <token>} header.</p>
 *
 * <p>The login flow mirrors {@link com.company.IntelligentPlatform.common.service.LogonInfoManager#logon}
 * but is intentionally kept lightweight — it only validates credentials and
 * issues a token; it does not run the full {@code setCompoundLogon} session
 * setup (navigation lists, org tree, etc.) that the legacy session tier
 * performs, because REST controllers receive all required context from the
 * JWT claims on every request.</p>
 *
 * <p>This controller is stateless (no {@code @Scope("session")}) and is
 * exempt from JWT authentication via the {@code /api/v1/auth/**} permit rule
 * in {@link com.company.IntelligentPlatform.common.config.SecurityConfig}.</p>
 *
 * <p>After issuing a token, this controller pre-warms the
 * {@link com.company.IntelligentPlatform.common.config.JwtAuthContextCache}
 * by building the full {@link LogonInfo} immediately.  This means the very
 * first API call after login also pays zero DB cost for authorization — the
 * cache is already populated.</p>
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LogonUserRepository logonUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ServiceEncodeProxyFactory serviceEncodeProxyFactory;

    /**
     * JwtRestContext is used here only for cache pre-warming after login.
     * It builds the full LogonInfo (same queries as setCompoundLogon) and
     * stores it in JwtAuthContextCache so the very first API call after
     * login also pays zero DB cost for the authorization check.
     */
    @Autowired
    private JwtRestContext jwtRestContext;

    @Autowired
    private JwtAuthContextCache jwtAuthContextCache;

    /**
     * Authenticates a user by business ID + client + password and returns a JWT.
     *
     * <p>Request body (JSON):</p>
     * <pre>
     *   {
     *     "userId":       "U001",
     *     "client":       "C001",
     *     "password":     "plainTextPassword",
     *     "languageCode": "en"      // optional; defaults to ""
     *   }
     * </pre>
     *
     * <p>Success response (HTTP 200):</p>
     * <pre>
     *   {
     *     "token":  "&lt;JWT&gt;",
     *     "uuid":   "&lt;user UUID&gt;",
     *     "userId": "U001",
     *     "client": "C001"
     *   }
     * </pre>
     *
     * <p>Failure response (HTTP 401):</p>
     * <pre>
     *   { "error": "Invalid credentials" }
     * </pre>
     *
     * <p>Validation steps (matching {@code LogonInfoManager.logon}):</p>
     * <ol>
     *   <li>Locate the {@link LogonUser} by {@code userId} + {@code client}.</li>
     *   <li>Encode the submitted password with MD5 via
     *       {@link ServiceEncodeProxyFactory} and compare to the stored hash.</li>
     *   <li>Reject locked ({@code lockUserFlag != 0}) or inactive
     *       ({@code status != STATUS_ACTIVE}) accounts.</li>
     *   <li>On success, generate and return a JWT via {@link JwtUtil#generateToken}.</li>
     * </ol>
     *
     * @param request a map containing {@code userId}, {@code client},
     *                {@code password}, and optionally {@code languageCode}
     * @return {@code 200 OK} with token payload, or {@code 401 Unauthorized}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String userId       = request.get("userId");
        String client       = request.get("client");
        String password     = request.get("password");
        String languageCode = request.getOrDefault("languageCode", "");

        if (userId == null || client == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "userId, client and password are required"));
        }

        // Step 1: look up user by business ID + client
        Optional<LogonUser> userOpt = logonUserRepository.findByIdAndClient(userId, client);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
        LogonUser user = userOpt.get();

        // Step 2: validate password (MD5-encoded, matching LogonInfoManager.logon)
        try {
            IServiceEncodeProxy encodeProxy =
                    serviceEncodeProxyFactory.getEncodeProxy(ServiceEncodeProxyFactory.ENCODE_MD5);
            String encodedPassword = encodeProxy.getEncodeValue(password);
            if (!encodedPassword.equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }
        } catch (ServiceEncodeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Authentication system error"));
        }

        // Step 3: check account is active and not locked
        if (user.getStatus() != LogonUser.STATUS_ACTIVE) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Account is not active"));
        }
        if (user.getLockUserFlag() != 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Account is locked"));
        }

        // Step 4: generate JWT and return token payload
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUuid(),
                user.getClient(),
                languageCode
        );

        // Step 5: pre-warm the authorization cache so the very first API request
        // after login also pays zero DB cost.
        // JwtRestContext.rebuildFromDatabase is the same sequence as
        // LogonActionController.setCompoundLogon — it runs here once at login
        // and stores the result in JwtAuthContextCache.  All subsequent requests
        // within the TTL window read from the cache directly.
        try {
            JwtPrincipal principal = new JwtPrincipal(
                    user.getId(), user.getUuid(), user.getClient(), languageCode);
            LogonInfo logonInfo = jwtRestContext.buildAndCache(principal);
            // logonInfo is now in the cache; no return value needed here
        } catch (LogonInfoException | ServiceEntityConfigureException e) {
            // Cache pre-warm failed — not a fatal error.
            // The first API call will trigger a cache miss and rebuild from DB.
            logger.warn("Auth cache pre-warm failed for user {}: {}", user.getId(), e.getMessage());
        } catch (RuntimeException e) {
            // Catches UnexpectedRollbackException and other runtime failures from
            // the authorization-map build (getRoleList, getAuthorizationACListMap, etc.).
            // Pre-warm failure is non-fatal — login still succeeds and the first
            // API call after login will rebuild the cache on demand.
            logger.warn("Auth cache pre-warm runtime failure for user {}: {} — {}",
                    user.getId(), e.getClass().getSimpleName(), e.getMessage());
        }

        return ResponseEntity.ok(Map.of(
                "token",  token,
                "uuid",   user.getUuid(),
                "userId", user.getId(),
                "client", user.getClient()
        ));
    }
}
