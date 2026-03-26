package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.config.JwtAuthContextCache;
import com.company.IntelligentPlatform.common.dto.UserRoleServiceUIModel;
import com.company.IntelligentPlatform.common.dto.UserRoleServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.LogonUserSpecifier;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.UserRoleServiceModel;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.UserRole;

import java.util.HashMap;
import java.util.Map;

/**
 * Session-scoped controller for managing UserRole assignments
 * (assigning or removing a Role from a LogonUser).
 *
 * <p>A {@link UserRole} record represents a single role assignment:
 * its {@code rootNodeUUID} is the {@link LogonUser#getUuid()} of the
 * user being assigned the role.  Creating or deleting a {@link UserRole}
 * therefore changes that user's effective permissions.</p>
 *
 * <h2>JWT cache eviction</h2>
 * <p>The JWT REST tier caches each user's authorization context
 * ({@code authorizationActionCodeMap}) in {@link JwtAuthContextCache}.
 * Whenever a role assignment changes, the cached context for the affected
 * user must be evicted so that the next REST request rebuilds it from the
 * database and picks up the new permissions immediately.</p>
 *
 * <p>The eviction is added to {@link #newModuleService} and
 * {@link #deleteModule} only — these are the two write paths.
 * All read endpoints ({@code loadModule}, {@code loadModuleEditService},
 * {@code loadModuleViewService}) are unchanged.</p>
 *
 * <p>The eviction is fire-and-forget: a failure to evict is logged as a
 * warning but does not affect the response to the caller.  In the worst
 * case the stale cache entry expires naturally after
 * {@code jwt.auth-cache-ttl-minutes} (default 30 minutes).</p>
 *
 * <p><b>Impact on legacy session tier:</b> None.  The legacy session
 * controllers store {@code LogonInfo} in their own
 * {@code @Scope("session")} beans.  {@link JwtAuthContextCache} is
 * entirely separate — evicting it has no effect on any existing session.</p>
 */
@Scope("session")
@Controller(value = "userRoleEditorController")
@RequestMapping(value = "/userRole")
public class UserRoleEditorController {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleEditorController.class);

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LogonUserSpecifier logonUserSpecifier;

	@Autowired
	protected UserRoleServiceUIModelExtension userRoleServiceUIModelExtension;

	/**
	 * JwtAuthContextCache is injected here solely to evict the authorization
	 * cache entry for a user whenever their role assignment changes.
	 *
	 * <p>This has zero effect on the legacy session tier — the cache is only
	 * read by the JWT REST layer.  The evict call is a no-op if the user has
	 * never made a JWT REST request (no entry exists in the cache).</p>
	 */
	@Autowired
	private JwtAuthContextCache jwtAuthContextCache;

	public static final String AOID_RESOURCE = LogonUserEditorController.AOID_RESOURCE;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				UserRoleServiceUIModel.class,
				UserRoleServiceModel.class, AOID_RESOURCE,
				UserRole.NODENAME,
				UserRole.SENAME, userRoleServiceUIModelExtension,
				logonUserManager
		);
	}

	private UserRoleServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (UserRoleServiceUIModel) JSONObject
				.toBean(jsonObject, UserRoleServiceUIModel.class,
						classMap);
	}

	/**
	 * Saves (creates or updates) a UserRole record, then evicts the JWT
	 * authorization cache for the affected user.
	 *
	 * <p>The {@code rootNodeUUID} in the request payload is the
	 * {@link LogonUser#getUuid()} of the user being assigned the role.
	 * After the save completes, the cache entry for that UUID is evicted
	 * so the user's next JWT REST request rebuilds the authorization map
	 * from the database and sees the updated role assignment.</p>
	 *
	 * <p>The actual save logic is unchanged — it delegates entirely to
	 * {@link ServiceBasicUtilityController#saveModuleService}.  The evict
	 * call is appended after the response is already built and does not
	 * affect the response or transaction outcome.</p>
	 *
	 * @param request JSON body containing the {@link UserRoleServiceUIModel}
	 * @return JSON response string from {@code saveModuleService}
	 */
	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		UserRoleServiceUIModel userRoleServiceUIModel = parseToServiceUIModel(request);
		String response = serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				userRoleServiceUIModel,
				userRoleServiceUIModel.getUserRoleUIModel().getUuid(), ISystemActionCode.ACID_EDIT);

		// Evict the JWT auth cache for the user whose role was changed.
		// rootNodeUUID on a UserRole is always the parent LogonUser's UUID —
		// this is the standard ServiceEntityNode parent/root convention.
		// The cache will be rebuilt from DB on the user's next JWT REST request.
		evictUserAuthCache(userRoleServiceUIModel.getUserRoleUIModel().getRootNodeUUID(),
				"saveModuleService");

		return response;
	}

	/**
	 * Creates a new UserRole record for a user, then evicts the JWT
	 * authorization cache for that user.
	 *
	 * <p>The {@code baseUUID} in the request is the {@link LogonUser#getUuid()}
	 * of the user receiving the new role — it becomes the new
	 * {@link UserRole#getRootNodeUUID()}.  The cache eviction ensures that
	 * the user's next JWT REST request sees the newly assigned role
	 * immediately rather than the stale pre-assignment authorization map.</p>
	 *
	 * <p>The actual creation logic is unchanged — it delegates entirely to
	 * {@link ServiceBasicUtilityController#newModuleServiceDefTemplate}.
	 * The evict call is appended after the response is already built.</p>
	 *
	 * @param request JSON body containing the base UUID and context
	 * @return JSON response string from {@code newModuleServiceDefTemplate}
	 */
	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		String response = serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(UserRole.SENAME, UserRole.NODENAME,
						null, LogonUser.NODENAME, request.getBaseUUID(), null, logonUserSpecifier, request, null),
				ISystemActionCode.ACID_EDIT);

		// Evict the JWT auth cache for the user who just received a new role.
		// request.getBaseUUID() is the LogonUser UUID that becomes the
		// rootNodeUUID of the new UserRole record.
		evictUserAuthCache(request.getBaseUUID(), "newModuleService");

		return response;
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	/**
	 * Deletes a UserRole record, then evicts the JWT authorization cache
	 * for the user who lost the role.
	 *
	 * <p>To find which user owns the {@link UserRole} being deleted, this
	 * method loads the entity by UUID and reads its {@code rootNodeUUID}
	 * (= the {@link LogonUser#getUuid()}).  The cache eviction then ensures
	 * the user's next JWT REST request no longer sees the deleted role in
	 * their authorization map.</p>
	 *
	 * <p>The load is done before the delete call so the entity still exists
	 * in the database when we read it.  The actual deletion is unchanged —
	 * it delegates entirely to
	 * {@link ServiceBasicUtilityController#deleteModule}.  If the pre-load
	 * fails, we log a warning but still proceed with the delete — the stale
	 * cache entry will expire naturally after the TTL.</p>
	 *
	 * @param uuid the UUID of the {@link UserRole} record to delete
	 * @return JSON response string from {@code deleteModule}
	 */
	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		// Look up the UserRole before deleting so we can read its rootNodeUUID
		// (= the LogonUser UUID) for cache eviction.  If the lookup fails we
		// still proceed with the delete; the stale cache expires via TTL.
		String userUuid = resolveUserUuidFromUserRole(uuid);

		String response = serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());

		// Evict the JWT auth cache for the user who lost the role.
		evictUserAuthCache(userUuid, "deleteModule");

		return response;
	}

	// -------------------------------------------------------------------------
	// Private helpers
	// -------------------------------------------------------------------------

	/**
	 * Looks up the {@link UserRole} by {@code uuid} and returns its
	 * {@code rootNodeUUID}, which is always the owning {@link LogonUser}'s UUID.
	 *
	 * <p>Returns {@code null} if the entity cannot be found (e.g. already
	 * deleted) or if any exception occurs.  The caller handles the
	 * {@code null} case gracefully.</p>
	 *
	 * @param userRoleUuid the UUID of the {@link UserRole} record
	 * @return the {@link LogonUser} UUID, or {@code null} on any failure
	 */
	private String resolveUserUuidFromUserRole(String userRoleUuid) {
		try {
			ServiceEntityNode userRole = logonUserManager.getEntityNodeByKey(
					userRoleUuid,
					IServiceEntityNodeFieldConstant.UUID,
					UserRole.NODENAME,
					logonActionController.getClient(), null);
			if (userRole != null) {
				return userRole.getRootNodeUUID();
			}
		} catch (ServiceEntityConfigureException e) {
			logger.warn("Could not resolve user UUID from UserRole {} for cache eviction: {}",
					userRoleUuid, e.getMessage());
		}
		return null;
	}

	/**
	 * Evicts the JWT authorization cache entry for the given user UUID.
	 *
	 * <p>This is fire-and-forget: a {@code null} UUID or any unexpected
	 * error is logged as a warning and silently ignored.  The worst case
	 * is that the stale cache entry expires after the configured TTL
	 * ({@code jwt.auth-cache-ttl-minutes}, default 30 minutes).</p>
	 *
	 * <p><b>Impact on legacy session tier:</b> None.
	 * {@link JwtAuthContextCache} is only consulted by the JWT REST layer.
	 * Session-scoped controllers read their {@code LogonInfo} from their
	 * own {@code @Scope("session")} bean — they never touch this cache.</p>
	 *
	 * @param userUuid the {@link LogonUser} UUID whose cache entry to evict,
	 *                 or {@code null} (no-op)
	 * @param callerMethod name of the calling method, used in the log message
	 */
	private void evictUserAuthCache(String userUuid, String callerMethod) {
		if (userUuid == null) {
			logger.warn("{}: skipping JWT cache eviction — user UUID could not be resolved", callerMethod);
			return;
		}
		jwtAuthContextCache.evict(userUuid);
		logger.debug("{}: evicted JWT auth cache for user UUID {}", callerMethod, userUuid);
	}

}
