# JWT Authentication Design — REST vs Session Tier

## Overview

The IntelligentPlatform backend runs two authentication tiers side by side:

| Tier | URL prefix | Auth mechanism | Scope |
|---|---|---|---|
| Legacy session tier | `/common/**`, `/flowableTask/**`, etc. | HTTP session (`@Scope("session")`) | Unchanged from legacy |
| New REST tier | `/api/v1/**` | JWT Bearer token | Stateless, new endpoints only |

---

## Legacy Session Tier (Unchanged)

At login (`POST /common/loginService`), `LogonActionController.setCompoundLogon()` runs:

```java
Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap =
    authorizationManager.getAuthorizationACListMap(logonUser);   // DB query — runs ONCE
logonInfo.setAuthorizationActionCodeMap(authorizationActionCodeMap);
setLogonUser(logonUser);
setLogonInfo(logonInfo);   // stored in @Scope("session") bean
```

On every subsequent request within the same session, `preCheckResourceAccessCore()` reads
directly from the in-memory `logonInfo.getAuthorizationActionCodeMap()` — **no DB query**.
This behavior is completely unchanged.

---

## New REST Tier — Authorization Strategy Options

Because JWT is stateless there is no server-side session.  Three strategies were considered
for how to handle the `AuthorizationManager` check on each `/api/v1/**` request.

### Option A — Reload authorization on every request

```
Every API request:
  validate JWT → load LogonUser (1 query)
               → getAuthorizationACListMap() (N+1 queries, one per role)
               → check → proceed
```

**Con:** The N+1 authorization queries run on every single request.  A user who saves a
document 20 times triggers the full role/action-code load 20 times.  The legacy session
tier pays this cost exactly once per login.  Option A is a performance regression relative
to the existing system contract.

**Pro:** Always reflects the latest permissions immediately.

---

### Option B — Embed permissions in the JWT token

Encode role UUID(s) or action-code fingerprints as JWT claims at login time.  Each request
reads roles from the token instead of the DB.

**Con:** If an admin changes a user's roles while they are logged in, the change does not
take effect until the token expires (up to 24 hours).  This silently degrades the security
model that your existing permission management UI relies on.

**Pro:** Zero DB queries for auth checks.

---

### Option C — Short-lived server-side cache keyed by UUID (Recommended)

Cache the `Map<AuthorizationObject, List<ActionCode>>` in memory after the first request,
keyed by `userUuid`, with a TTL matching your typical session lifetime.

```
First request for a given user in the TTL window:
  validate JWT → rebuild auth map from DB (N+1 queries, same as login)
              → store in cache under userUuid
              → check → proceed

Subsequent requests within TTL:
  validate JWT → read from cache (0 DB queries)
              → check → proceed
```

This **exactly replicates the session tier's performance contract**:
- The expensive N+1 load happens once per TTL window, not once per request.
- The same `AuthorizationManager` code is reused without modification.
- Permissions can be invalidated immediately by evicting the cache entry
  (e.g. when an admin changes a user's role).

---

## Option C vs Option A vs Option B — Comparison

| Property | Legacy Session | Option A | Option B | Option C |
|---|---|---|---|---|
| DB queries per request | 0 (after login) | N+1 every time | 1 (role lookup) | 0 (after first in TTL) |
| Responds to permission changes | Yes (re-login) | Yes (always fresh) | No (until token expiry) | Yes (TTL + explicit eviction) |
| Reuses existing `AuthorizationManager` | Yes | Yes | No (new logic) | Yes |
| Works across multiple server instances | N/A (sticky session) | Yes | Yes | Needs shared cache (Redis) for multi-node |

---

## Option C — Known Limitation (Multi-node Deployment)

The default implementation uses an in-memory `ConcurrentHashMap`.  If the application is
deployed across **multiple server instances behind a load balancer without sticky sessions**,
each instance maintains its own cache — a request routed to a different instance will
cache-miss and rebuild from DB.

**Resolution path:** Replace the `ConcurrentHashMap` with Spring `@Cacheable` backed by
Redis.  The calling code does not change because Spring Cache abstracts the storage backend.

For the current single-server deployment this is not a concern.

---

---

## Option C — Code Changes Summary

The following files need to be created or modified to implement Option C.
No existing service or controller code requires changes.

### New files

| File | What it does |
|---|---|
| `common/config/JwtAuthContextCache.java` | In-memory cache (`ConcurrentHashMap`) that stores the rebuilt `LogonInfo` (including the full `authorizationActionCodeMap`) keyed by user UUID, with a configurable TTL. Exposes `get(uuid)`, `put(uuid, logonInfo)`, and `evict(uuid)`. |
| `common/config/JwtRestContext.java` | Helper that REST controllers call to get a ready-to-use `LogonInfo` for the current request. Reads `JwtPrincipal` from `SecurityContextHolder`, checks the cache, rebuilds from DB on miss, and stores the result back in the cache. |

### Modified files

| File | What changes |
|---|---|
| `common/config/JwtAuthFilter.java` | No logic change — already sets `JwtPrincipal` on the security context. The cache is consulted later by `JwtRestContext`, not here. |
| `common/controller/AuthController.java` | On successful login, pre-warm the cache by building and storing the `LogonInfo` immediately (same queries as `setCompoundLogon`). This means the first API request after login also pays zero DB cost. Optionally evict the cache entry on a future logout endpoint. |
| `application.yml` | Add one property: `jwt.auth-cache-ttl-minutes: 30` (or match your session timeout). |

### How a REST controller uses it

Instead of injecting `LogonActionController` (session-scoped, not available in stateless controllers), a REST controller injects `JwtRestContext` and calls:

```
LogonInfo logonInfo = jwtRestContext.getLogonInfo();
// logonInfo.getAuthorizationActionCodeMap() is populated — same object
// shape as the session tier, zero extra DB queries within the TTL window
```

The existing `AuthorizationManager.checkResourceAuthorization()` and
`preCheckResourceAccessCore()` calls work unchanged against this `LogonInfo`.

### Cache invalidation

| Event | Action |
|---|---|
| Admin changes user roles | Call `jwtAuthContextCache.evict(userUuid)` in `RoleManager` after update |
| User logs out | Call `jwtAuthContextCache.evict(userUuid)` in logout endpoint |
| TTL expires | Cache entry is automatically dropped; next request rebuilds from DB |

### Future upgrade path to Redis

Replace `JwtAuthContextCache` internals with `@Cacheable("authContext")` and configure a
Redis `CacheManager` in `JpaConfig` (or a new `CacheConfig`).  `JwtRestContext` and all
callers remain unchanged.

---

## Option C — Implementation Files

| File | Role |
|---|---|
| `common/config/JwtAuthContextCache.java` | In-memory TTL cache (`ConcurrentHashMap`). Stores `LogonInfo` keyed by user UUID. Exposes `get`, `put`, `evict`. |
| `common/config/JwtRestContext.java` | Per-request helper. Reads principal from `SecurityContextHolder`, checks cache, rebuilds from DB on miss, exposes `checkAuthorization()`. |
| `common/controller/AuthController.java` | Updated: calls `jwtRestContext.buildAndCache()` after issuing the JWT so the very first API call after login also hits the cache. |
| `application.yml` | Added `jwt.auth-cache-ttl-minutes: 30`. |

---

## How Permission Checking Works in REST Controllers

### Legacy session controller (unchanged)

```java
// ServiceBasicUtilityController.preCheckResourceAccessCore is called,
// which reads logonInfo stored in the @Scope("session") LogonActionController bean.
serviceBasicUtilityController.preCheckResourceAccessCore(
    IServiceModelConstants.PurchaseOrder, ISystemActionCode.ACID_EDIT);
```

The `LogonInfo` (including `authorizationActionCodeMap`) already lives in the
session-scoped bean — no DB query needed.

### New REST controller (JWT tier)

Inject `JwtRestContext` instead of `LogonActionController`. There are two patterns:

**Pattern 1 — one-liner permission check (most common):**
```java
@Autowired
private JwtRestContext jwtRestContext;

@PostMapping("/save")
public ResponseEntity<?> save(@RequestBody ...) throws ... {
    // Checks permission; throws AuthorizationException if denied.
    // Internally reads LogonInfo from cache (zero DB) or rebuilds on miss.
    jwtRestContext.checkAuthorization(
        IServiceModelConstants.PurchaseOrder, ISystemActionCode.ACID_EDIT);

    // ... business logic unchanged ...
}
```

**Pattern 2 — retrieve LogonInfo for use in service calls:**
```java
@PostMapping("/save")
public ResponseEntity<?> save(@RequestBody ...) throws ... {
    // Get LogonInfo from cache (or rebuild on miss).
    // The returned object has the same shape as the session tier's LogonInfo —
    // authorizationActionCodeMap, homeOrganization, organizationList all populated.
    LogonInfo logonInfo = jwtRestContext.getLogonInfo();

    // Pass directly to any existing service method that accepts LogonInfo.
    purchaseOrderService.savePurchaseOrder(dto, logonInfo);
}
```

### What does NOT change

- `AuthorizationManager.checkAuthorizationWrapper()` — called unchanged inside `JwtRestContext.checkAuthorization()`
- All service methods that accept `LogonInfo` — the object they receive is identical in shape to what the session tier provides
- Authorization rules, role definitions, action codes — none of these change

### Cache invalidation — when to call `evict`

| Event | Where to add the evict call |
|---|---|
| Admin saves a role assignment change | `RoleManager` after `updateSENode` |
| Admin deletes a user's role | `RoleManager` after `deleteSENode` |
| User logs out via REST | Logout endpoint: `jwtAuthContextCache.evict(principal.getUuid())` |
| TTL expiry | Automatic — no code needed |

---

## Implementation Files (current state)

| File | Purpose |
|---|---|
| `common/config/JwtUtil.java` | Token generation, validation, claim extraction |
| `common/config/JwtPrincipal.java` | Immutable principal stored in `SecurityContextHolder` |
| `common/config/JwtAuthFilter.java` | `OncePerRequestFilter` — validates Bearer token per request |
| `common/config/JwtAuthEntryPoint.java` | Returns 401 JSON (not HTML redirect) for unauthenticated REST calls |
| `common/config/SecurityConfig.java` | `SecurityFilterChain` — route rules, filter registration |
| `common/controller/AuthController.java` | `POST /api/v1/auth/login` — issues JWT on valid credentials |

## JWT Token Claims

| Claim | Value | Used for |
|---|---|---|
| `sub` | `LogonUser.id` (e.g. "U001") | Spring Security principal name |
| `uuid` | `LogonUser.uuid` | Passed as `userUUID` to service methods |
| `client` | `LogonUser.client` | Tenant discriminator for all service calls |
| `languageCode` | User's preferred language | UI-model conversion helpers |

## Login Endpoint

```
POST /api/v1/auth/login
Content-Type: application/json

{
  "userId":       "U001",
  "client":       "C001",
  "password":     "plainTextPassword",
  "languageCode": "en"
}
```

Success response (HTTP 200):
```json
{
  "token":  "<JWT>",
  "uuid":   "<user UUID>",
  "userId": "U001",
  "client": "C001"
}
```

All subsequent `/api/v1/**` calls must include:
```
Authorization: Bearer <token>
```
