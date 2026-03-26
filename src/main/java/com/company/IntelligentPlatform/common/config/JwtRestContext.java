package com.company.IntelligentPlatform.common.config;

import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.repository.LogonUserRepository;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Central helper that REST controllers use to obtain a fully populated
 * {@link LogonInfo} for the current authenticated request.
 *
 * <h2>Role in the JWT tier</h2>
 * <p>In the legacy session tier, {@link com.company.IntelligentPlatform.common.controller.LogonActionController}
 * is an {@code @Scope("session")} bean that holds the {@link LogonInfo}
 * (including {@code authorizationActionCodeMap}) as an instance field across
 * all requests in the same HTTP session.  That bean is not available to
 * stateless REST controllers.</p>
 *
 * <p>This class fills that gap for the JWT tier.  On each request it:</p>
 * <ol>
 *   <li>Reads the {@link JwtPrincipal} that {@link JwtAuthFilter} placed on
 *       the {@link SecurityContextHolder}.</li>
 *   <li>Checks {@link JwtAuthContextCache} for a live entry keyed by
 *       {@code userUuid}.</li>
 *   <li>On a cache hit: returns the cached {@link LogonInfo} immediately —
 *       zero DB queries, matching the session tier's per-request cost.</li>
 *   <li>On a cache miss: rebuilds the full {@link LogonInfo} from the
 *       database (same queries as {@code LogonActionController.setCompoundLogon}),
 *       stores the result in the cache, then returns it.</li>
 * </ol>
 *
 * <h2>How REST controllers use this class</h2>
 * <p>Inject {@code JwtRestContext} and call {@link #getLogonInfo()} at the
 * top of each endpoint method instead of injecting the session-scoped
 * {@code LogonActionController}:</p>
 * <pre>
 *   // Legacy session controller pattern:
 *   LogonInfo logonInfo = logonActionController.getLogonInfo();
 *
 *   // Equivalent REST pattern using JwtRestContext:
 *   LogonInfo logonInfo = jwtRestContext.getLogonInfo();
 *
 *   // The returned LogonInfo has the same shape in both cases —
 *   // authorizationActionCodeMap is populated and ready to use.
 * </pre>
 *
 * <p>To check permissions in a REST controller, pass the {@link LogonInfo}
 * to the existing {@link AuthorizationManager}:</p>
 * <pre>
 *   authorizationManager.checkAuthorizationWrapper(
 *       logonInfo, IServiceModelConstants.PurchaseOrder, ISystemActionCode.ACID_EDIT);
 * </pre>
 *
 * <p>Used by: all new {@code /api/v1/**} REST controllers that need
 * authorization or user/tenant context.</p>
 */
@Component
public class JwtRestContext {

    @Autowired
    private JwtAuthContextCache authContextCache;

    @Autowired
    private LogonUserRepository logonUserRepository;

    @Autowired
    private LogonUserManager logonUserManager;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private OrganizationManager organizationManager;

    @Autowired
    private OrganizationFactoryService organizationFactoryService;

    /**
     * Returns the fully populated {@link LogonInfo} for the user who made the
     * current request, using the cache where possible.
     *
     * <h3>Cache hit path (zero DB queries)</h3>
     * <p>If a live entry exists in {@link JwtAuthContextCache} for the current
     * user's UUID, it is returned immediately.  This is the common path for
     * all requests after the first within the TTL window — identical cost to
     * the session tier.</p>
     *
     * <h3>Cache miss path (DB rebuild)</h3>
     * <p>On a miss the method performs the same sequence as
     * {@code LogonActionController.setCompoundLogon}:</p>
     * <ol>
     *   <li>Load {@link LogonUser} by UUID from the database.</li>
     *   <li>Load the user's {@link LogonUserOrgReference} to find the home
     *       {@link Organization}.</li>
     *   <li>Load the full organization list for the client.</li>
     *   <li>Call {@link AuthorizationManager#getAuthorizationACListMap} to
     *       build the role/action-code map.</li>
     *   <li>Call {@link AuthorizationManager#getAuthorizationACList} to build
     *       the union list.</li>
     *   <li>Assemble and cache the resulting {@link LogonInfo}.</li>
     * </ol>
     *
     * @return fully populated {@link LogonInfo} for the current request's user
     * @throws LogonInfoException             if the JWT principal is missing or the
     *                                        user no longer exists in the database
     * @throws ServiceEntityConfigureException if a configuration or DB lookup fails
     */
    public LogonInfo getLogonInfo() throws LogonInfoException, ServiceEntityConfigureException {
        // Step 1: read the principal that JwtAuthFilter placed on the security context.
        // This is always present for authenticated requests — SecurityConfig ensures
        // unauthenticated requests are rejected before reaching the controller.
        JwtPrincipal principal = getCurrentPrincipal();

        // Step 2: check the cache first — this is the fast path for all requests
        // after the first within the TTL window (zero DB queries, same as the
        // session tier).
        LogonInfo cached = authContextCache.get(principal.getUuid());
        if (cached != null) {
            return cached;
        }

        // Step 3: cache miss — rebuild from DB and store in cache.
        // Delegates to buildAndCache which is also called by AuthController
        // at login for pre-warming.
        return buildAndCache(principal);
    }

    /**
     * Convenience method that checks a permission and throws
     * {@link AuthorizationException} if the current user does not have access.
     *
     * <p>This replicates the pattern used by legacy session controllers via
     * {@code ServiceBasicUtilityController.preCheckResourceAccessCore()}.
     * REST controllers call this method instead:</p>
     * <pre>
     *   // In a REST controller method:
     *   jwtRestContext.checkAuthorization(
     *       IServiceModelConstants.PurchaseOrder, ISystemActionCode.ACID_EDIT);
     * </pre>
     *
     * <p>Internally this calls {@link #getLogonInfo()} (which uses the cache),
     * then delegates to
     * {@link AuthorizationManager#checkAuthorizationWrapper(LogonInfo, String, String)}.
     * All existing authorization rules and data-access logic in
     * {@code AuthorizationManager} are reused without modification.</p>
     *
     * @param resourceId the authorization object ID (e.g.
     *                   {@code IServiceModelConstants.PurchaseOrder})
     * @param acId       the action code (e.g. {@code ISystemActionCode.ACID_EDIT})
     * @throws LogonInfoException             if the current user cannot be resolved
     * @throws AuthorizationException         if the user lacks the required permission
     * @throws ServiceEntityConfigureException if a DB or configuration error occurs
     */
    public void checkAuthorization(String resourceId, String acId)
            throws LogonInfoException, AuthorizationException, ServiceEntityConfigureException {
        LogonInfo logonInfo = getLogonInfo();
        // Delegates to the unchanged AuthorizationManager — no new authorization
        // logic lives here.  The LogonInfo passed in already has the full
        // authorizationActionCodeMap populated (from cache or DB rebuild above).
        authorizationManager.checkAuthorizationWrapper(logonInfo, resourceId, acId);
    }

    /**
     * Builds a full {@link LogonInfo} from the database for the given principal
     * and stores it in {@link JwtAuthContextCache}.
     *
     * <p>This method is called by
     * {@link com.company.IntelligentPlatform.common.controller.AuthController}
     * at login time to pre-warm the cache.  The result is identical to what
     * {@link #getLogonInfo()} would produce on a cache miss — calling this at
     * login means the very first API request after login hits the cache
     * directly (zero DB cost), rather than triggering a miss.</p>
     *
     * <p>It is also called internally by {@link #getLogonInfo()} on a cache
     * miss during normal request processing.</p>
     *
     * @param principal the JWT principal whose {@link LogonInfo} to build
     * @return the fully populated and cached {@link LogonInfo}
     * @throws LogonInfoException             if the user UUID is not found in the DB
     * @throws ServiceEntityConfigureException if a DB or configuration lookup fails
     */
    public LogonInfo buildAndCache(JwtPrincipal principal)
            throws LogonInfoException, ServiceEntityConfigureException {
        LogonInfo logonInfo = rebuildFromDatabase(principal);
        authContextCache.put(principal.getUuid(), logonInfo);
        return logonInfo;
    }

    /**
     * Returns the {@link JwtPrincipal} from the current thread's
     * {@link SecurityContextHolder}.
     *
     * <p>This is always populated for requests that reach a protected
     * {@code /api/v1/**} endpoint because {@link JwtAuthFilter} runs before
     * the controller and {@link com.company.IntelligentPlatform.common.config.SecurityConfig}
     * rejects unauthenticated requests at the filter chain level.</p>
     *
     * @return the current request's {@link JwtPrincipal}
     * @throws LogonInfoException if the security context does not contain a
     *                            {@link JwtPrincipal} (should not happen in normal flow)
     */
    public JwtPrincipal getCurrentPrincipal() throws LogonInfoException {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!(principal instanceof JwtPrincipal)) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        return (JwtPrincipal) principal;
    }

    /**
     * Rebuilds a full {@link LogonInfo} from the database for the given principal.
     *
     * <p>Performs the same sequence as
     * {@code LogonActionController.setCompoundLogon}: loads the user, home
     * organization, full org list, authorization action-code map, and
     * authorization union list.  This is the expensive path — it is called
     * at most once per TTL window per user.</p>
     *
     * @param principal the JWT principal from the current request
     * @return a fully populated {@link LogonInfo} ready to be cached
     * @throws LogonInfoException             if the user UUID is no longer in the DB
     * @throws ServiceEntityConfigureException if any DB lookup fails
     */
    private LogonInfo rebuildFromDatabase(JwtPrincipal principal)
            throws LogonInfoException, ServiceEntityConfigureException {

        // Load the LogonUser entity — the JWT uuid is the PK so this is a
        // single indexed lookup.
        LogonUser logonUser = logonUserRepository.findById(principal.getUuid())
                .orElseThrow(() -> new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER));

        // Build a LogonInfo with the base fields matching what LogonInfoManager.logon sets.
        LogonInfo logonInfo = new LogonInfo(logonUser.getUuid(), logonUser.getClient());
        logonInfo.setLogonUser(logonUser);
        logonInfo.setLanguageCode(principal.getLanguageCode());

        // Load home organization via the user's LogonUserOrgReference — same
        // lookup that setCompoundLogon performs.
        LogonUserOrgReference logonOrg = (LogonUserOrgReference)
                logonUserManager.getEntityNodeByKey(
                        logonUser.getUuid(),
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        LogonUserOrgReference.NODENAME,
                        logonUser.getClient(), null);

        if (logonOrg != null) {
            Organization homeOrganization = organizationFactoryService.getRefOrganization(logonOrg);
            logonInfo.setHomeOrganization(homeOrganization);
            logonInfo.setResOrgUUID(homeOrganization.getUuid());

            // Load the full organization list — used by data-access checks in
            // AuthorizationManager.filterDataAccessByAuthorization.
            List<ServiceEntityNode> allOrganizationList =
                    organizationManager.getAllOrganizationList(logonUser.getClient());
            logonInfo.setOrganizationList(allOrganizationList);
        }

        // Build the authorization map: AuthorizationObject → List<ActionCode>.
        // This is the N+1 query that the cache avoids on subsequent requests.
        // The result is the same object that the session tier stores in LogonInfo
        // after login — all existing AuthorizationManager methods work against it
        // without any changes.
        Map<AuthorizationObject, List<ActionCode>> authMap =
                authorizationManager.getAuthorizationACListMap(logonUser);
        logonInfo.setAuthorizationActionCodeMap(authMap);

        // Build the flattened union list used by compound authorization checks.
        List<AuthorizationManager.AuthorizationACUnion> acUnionList =
                authorizationManager.getAuthorizationACList(logonUser);
        logonInfo.setAuthorizationACUnionList(acUnionList);

        return logonInfo;
    }
}
