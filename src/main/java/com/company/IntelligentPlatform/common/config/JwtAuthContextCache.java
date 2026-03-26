package com.company.IntelligentPlatform.common.config;

import com.company.IntelligentPlatform.common.model.LogonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory authorization context cache for the JWT REST tier.
 *
 * <h2>Why this cache exists</h2>
 * <p>The legacy session tier builds a full {@link LogonInfo} at login time —
 * including the {@code authorizationActionCodeMap} — and stores it in an
 * {@code @Scope("session")} bean.  Every subsequent request within that
 * session reads the map from memory with zero DB queries.</p>
 *
 * <p>The JWT tier is stateless; there is no server-side session.  Without a
 * cache, every request would need to reload roles and action codes from the
 * database (N+1 queries per request).  This cache replicates the session
 * tier's contract: the expensive rebuild happens at most once per
 * {@code jwt.auth-cache-ttl-minutes} window per user.</p>
 *
 * <h2>Cache key</h2>
 * <p>User UUID (the primary key from {@code ServiceEntityNode.uuid}).  The
 * UUID is embedded in the JWT token and extracted by {@link JwtAuthFilter}
 * without any DB lookup, making it a safe stateless key.</p>
 *
 * <h2>TTL behaviour</h2>
 * <p>Each {@link CacheEntry} records the time it was stored.
 * {@link #get(String)} returns {@code null} for entries older than
 * {@code jwt.auth-cache-ttl-minutes}, causing {@link JwtRestContext} to
 * rebuild from the DB and re-cache transparently.</p>
 *
 * <h2>Explicit eviction</h2>
 * <p>Call {@link #evict(String)} to force an immediate cache miss for a
 * specific user — use this when an admin changes a user's roles so that
 * the next request picks up the new permissions without waiting for TTL
 * expiry.</p>
 *
 * <h2>Future upgrade path</h2>
 * <p>For multi-node deployments, replace this class with a Spring
 * {@code @Cacheable("authContext")} annotation on {@link JwtRestContext}
 * and configure a Redis-backed {@code CacheManager}.  No changes are
 * needed in any caller.</p>
 *
 * <p>Used exclusively by: {@link JwtRestContext}.</p>
 */
@Component
public class JwtAuthContextCache {

    /**
     * TTL for cached entries in minutes.
     * Configured via {@code jwt.auth-cache-ttl-minutes} in {@code application.yml}.
     * Defaults to 30 minutes if the property is absent.
     */
    @Value("${jwt.auth-cache-ttl-minutes:30}")
    private long cacheTtlMinutes;

    /** Thread-safe map: user UUID → cached LogonInfo with its insertion timestamp. */
    private final ConcurrentHashMap<String, CacheEntry> store = new ConcurrentHashMap<>();

    /**
     * Returns the cached {@link LogonInfo} for the given user UUID if it exists
     * and has not yet expired.
     *
     * <p>Returns {@code null} in two cases:</p>
     * <ul>
     *   <li>No entry exists for this UUID (first request after login or after eviction).</li>
     *   <li>The entry exists but its age exceeds {@code cacheTtlMinutes}
     *       (the entry is silently removed).</li>
     * </ul>
     *
     * <p>On a {@code null} return, the caller ({@link JwtRestContext}) rebuilds
     * the {@link LogonInfo} from the database and stores it via {@link #put}.</p>
     *
     * @param userUuid the user's UUID primary key
     * @return the cached {@link LogonInfo}, or {@code null} on miss/expiry
     */
    public LogonInfo get(String userUuid) {
        CacheEntry entry = store.get(userUuid);
        if (entry == null) {
            return null;
        }
        long ageMinutes = (Instant.now().toEpochMilli() - entry.storedAt) / 60_000;
        if (ageMinutes >= cacheTtlMinutes) {
            // Entry has expired — remove it so memory does not accumulate stale entries
            store.remove(userUuid);
            return null;
        }
        return entry.logonInfo;
    }

    /**
     * Stores or replaces the {@link LogonInfo} for the given user UUID.
     *
     * <p>Called by {@link JwtRestContext} after a successful DB rebuild, and by
     * {@link com.company.IntelligentPlatform.common.controller.AuthController}
     * at login to pre-warm the cache so the very first API request also pays
     * zero DB cost.</p>
     *
     * @param userUuid  the user's UUID primary key
     * @param logonInfo the fully populated {@link LogonInfo} to cache
     */
    public void put(String userUuid, LogonInfo logonInfo) {
        store.put(userUuid, new CacheEntry(logonInfo, Instant.now().toEpochMilli()));
    }

    /**
     * Immediately removes the cache entry for the given user UUID.
     *
     * <p>Call this whenever user permissions change so that the next request
     * forces a fresh rebuild from the database rather than reading stale data:</p>
     * <ul>
     *   <li>In {@code RoleManager} after a role assignment is saved or deleted.</li>
     *   <li>In a future logout endpoint to clean up the cache entry.</li>
     * </ul>
     *
     * <p>If no entry exists for the UUID this method is a no-op.</p>
     *
     * @param userUuid the user's UUID primary key
     */
    public void evict(String userUuid) {
        store.remove(userUuid);
    }

    /**
     * Immutable holder pairing a {@link LogonInfo} with the millisecond
     * timestamp at which it was inserted into the cache.
     */
    private static class CacheEntry {
        final LogonInfo logonInfo;
        final long storedAt;

        CacheEntry(LogonInfo logonInfo, long storedAt) {
            this.logonInfo = logonInfo;
            this.storedAt  = storedAt;
        }
    }
}
