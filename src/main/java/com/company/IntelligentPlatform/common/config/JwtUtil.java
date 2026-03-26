package com.company.IntelligentPlatform.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Stateless JWT token utility for the new REST API layer.
 *
 * <p>Tokens are signed with HMAC-SHA256 using the secret from
 * {@code jwt.secret} in {@code application.yml}.  The payload carries four
 * claims used by downstream services to reconstruct a lightweight security
 * context without a DB round-trip:</p>
 * <ul>
 *   <li>{@code sub}  — the user's business ID (e.g. "U001"), used as the
 *       Spring Security principal name</li>
 *   <li>{@code uuid} — the user's UUID primary key; passed to service-layer
 *       methods that require {@code userUUID}</li>
 *   <li>{@code client} — the tenant/client discriminator; passed to service
 *       calls that require {@code client}</li>
 *   <li>{@code languageCode} — the user's preferred language; forwarded to
 *       UI-model conversion helpers</li>
 * </ul>
 *
 * <p>Used by: {@link JwtAuthFilter} (token extraction + validation),
 * {@link SecurityConfig} (wired into the filter chain),
 * {@code AuthController} (token generation on successful login).</p>
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /** Claim key for the user's UUID primary key. */
    public static final String CLAIM_UUID = "uuid";

    /** Claim key for the tenant/client discriminator. */
    public static final String CLAIM_CLIENT = "client";

    /** Claim key for the user's preferred UI language. */
    public static final String CLAIM_LANGUAGE = "languageCode";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    /**
     * Derives a deterministic HMAC-SHA256 signing key from the configured
     * {@code jwt.secret} string.
     *
     * @return a {@link SecretKey} suitable for HMAC-SHA256 signing
     */
    private SecretKey signingKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a signed JWT token for a successfully authenticated user.
     *
     * <p>The token {@code sub} (subject) is set to the user's business ID.
     * Extra claims ({@code uuid}, {@code client}, {@code languageCode}) are
     * embedded so that the filter can reconstruct the security context on
     * every subsequent request without querying the database.</p>
     *
     * @param userId       the user's business ID (e.g. "U001"); used as the JWT subject
     * @param uuid         the user's UUID primary key
     * @param client       the tenant/client code this session belongs to
     * @param languageCode the user's preferred language (e.g. "en", "zh")
     * @return a compact, URL-safe JWT string
     */
    public String generateToken(String userId, String uuid, String client, String languageCode) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .subject(userId)
                .claims(Map.of(
                        CLAIM_UUID, uuid,
                        CLAIM_CLIENT, client,
                        CLAIM_LANGUAGE, languageCode != null ? languageCode : ""
                ))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey())
                .compact();
    }

    /**
     * Parses and validates a JWT token, returning all claims on success.
     *
     * <p>Throws a {@link JwtException} (or one of its subtypes) if the token
     * is malformed, expired, or the signature does not match.</p>
     *
     * @param token a compact JWT string (without the {@code "Bearer "} prefix)
     * @return the verified {@link Claims} payload
     * @throws JwtException if the token is invalid for any reason
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a token and returns {@code true} if it is well-formed,
     * correctly signed, and not yet expired.
     *
     * <p>All exceptions from {@link #parseToken(String)} are swallowed and
     * mapped to {@code false} so callers can use a simple boolean check.</p>
     *
     * @param token a compact JWT string to validate
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    public boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.debug("JWT token expired: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            logger.warn("JWT token invalid (bad signature or malformed): {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.warn("JWT validation failed unexpectedly: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extracts the subject (user's business ID) from a valid JWT token.
     *
     * @param token a compact JWT string
     * @return the {@code sub} claim value (business user ID)
     * @throws JwtException if the token is invalid
     */
    public String extractUserId(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * Extracts the {@code uuid} claim (user's PK UUID) from a valid JWT token.
     *
     * @param token a compact JWT string
     * @return the user UUID string
     * @throws JwtException if the token is invalid
     */
    public String extractUuid(String token) {
        return parseToken(token).get(CLAIM_UUID, String.class);
    }

    /**
     * Extracts the {@code client} claim (tenant code) from a valid JWT token.
     *
     * @param token a compact JWT string
     * @return the client/tenant code
     * @throws JwtException if the token is invalid
     */
    public String extractClient(String token) {
        return parseToken(token).get(CLAIM_CLIENT, String.class);
    }

    /**
     * Extracts the {@code languageCode} claim from a valid JWT token.
     *
     * @param token a compact JWT string
     * @return the language code (e.g. "en", "zh"), or empty string if absent
     * @throws JwtException if the token is invalid
     */
    public String extractLanguageCode(String token) {
        return parseToken(token).get(CLAIM_LANGUAGE, String.class);
    }
}
