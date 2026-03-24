package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategoryServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategoryServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceServiceUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureCategoryEditorController")
@RequestMapping(value = "/systemConfigureCategory")
public class SystemConfigureCategoryEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemConfigureCategory;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	@Autowired
	protected SystemConfigureResourceListController systemConfigureResourceListController;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SystemConfigureCategoryServiceUIModelExtension systemConfigureCategoryServiceUIModelExtension;

	@RequestMapping(value = "/getScenarioModeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getScenarioModeMap() {
		try {
			Map<Integer, String> scenarioModeMap = systemConfigureCategoryManager
					.initScenarioModeMap(logonActionController.getLanguageCode());
			return systemConfigureCategoryManager.getDefaultSelectMap(
					scenarioModeMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getSubScenarioModeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSubScenarioModeMap() {
		try {
			Map<Integer, String> subScenarioModeMap = systemConfigureCategoryManager
					.initSubScenarioModeMap(logonActionController.getLanguageCode());
			return systemConfigureCategoryManager.getDefaultSelectMap(
					subScenarioModeMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getSystemStandardCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSystemStandardCategoryMap() {
		try {
			Map<Integer, String> systemStandardCategoryMap = systemConfigureCategoryManager
					.initSystemStandardCategoryMap(logonActionController.getLanguageCode());
			return systemConfigureCategoryManager.getDefaultSelectMap(
					systemStandardCategoryMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getElementTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getElementTypeMap() {
		try {
			Map<Integer, String> elementTypeMap = systemConfigureCategoryManager
					.initElementTypeMap(logonActionController.getLanguageCode());
			return systemConfigureCategoryManager.getDefaultSelectMap(
					elementTypeMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("systemConfigureResourceUIModelList",
				SystemConfigureResourceServiceUIModel.class);
		SystemConfigureCategoryServiceUIModel systemConfigureCategoryServiceUIModel = (SystemConfigureCategoryServiceUIModel) JSONObject
				.toBean(jsonObject,
						SystemConfigureCategoryServiceUIModel.class, classMap);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			SystemConfigureCategoryServiceModel systemConfigureCategoryServiceModel = (SystemConfigureCategoryServiceModel) systemConfigureCategoryManager
					.genServiceModuleFromServiceUIModel(
							SystemConfigureCategoryServiceModel.class,
							SystemConfigureCategoryServiceUIModel.class,
							systemConfigureCategoryServiceUIModel,
							systemConfigureCategoryServiceUIModelExtension);
			systemConfigureCategoryManager.updateServiceModuleWithDelete(
					SystemConfigureCategoryServiceModel.class,
					systemConfigureCategoryServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			systemConfigureCategoryServiceUIModel = refreshLoadSeviceUIModel(systemConfigureCategoryServiceModel.getSystemConfigureCategory().getUuid(), logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureCategoryServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
					.newRootEntityNode(logonUser.getClient());
			SystemConfigureCategoryServiceModel systemConfigureCategoryServiceModel = new SystemConfigureCategoryServiceModel();
			systemConfigureCategoryServiceModel
					.setSystemConfigureCategory(systemConfigureCategory);
			SystemConfigureCategoryServiceUIModel systemConfigureCategoryServiceUIModel = (SystemConfigureCategoryServiceUIModel) systemConfigureCategoryManager
					.genServiceUIModuleFromServiceModel(
							SystemConfigureCategoryServiceUIModel.class,
							SystemConfigureCategoryServiceModel.class,
							systemConfigureCategoryServiceModel,
							systemConfigureCategoryServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureCategoryServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				systemConfigureCategoryManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(
			@RequestBody SimpleSEJSONRequest request) {
		return preLock(request.getUuid());
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureCategory.NODENAME, null);
			String baseUUID = systemConfigureCategory.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(systemConfigureCategory);
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureCategory.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							SystemConfigureResource.NODENAME, null);
			lockSEList.add(systemConfigureResource);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					systemConfigureCategory.getName(),
					systemConfigureCategory.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureCategory.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureCategory);
		} catch (AuthorizationException ex) {
			return ex.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private SystemConfigureCategoryServiceUIModel refreshLoadSeviceUIModel(String uuid, String client)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException {
		SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
				.getEntityNodeByKey(uuid,
						IServiceEntityNodeFieldConstant.UUID,
						SystemConfigureCategory.NODENAME,
						client, null);
		SystemConfigureCategoryServiceModel systemConfigureCategoryServiceModel = (SystemConfigureCategoryServiceModel) systemConfigureCategoryManager
				.loadServiceModule(
						SystemConfigureCategoryServiceModel.class,
						systemConfigureCategory);
		SystemConfigureCategoryServiceUIModel systemConfigureCategoryServiceUIModel = (SystemConfigureCategoryServiceUIModel) systemConfigureCategoryManager
				.genServiceUIModuleFromServiceModel(
						SystemConfigureCategoryServiceUIModel.class,
						SystemConfigureCategoryServiceModel.class,
						systemConfigureCategoryServiceModel,
						systemConfigureCategoryServiceUIModelExtension,
						logonActionController.getLogonInfo());
		return systemConfigureCategoryServiceUIModel;
	}


	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureCategoryServiceUIModel systemConfigureCategoryServiceUIModel = refreshLoadSeviceUIModel(uuid, logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureCategoryServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureCategory.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureCategory);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			SystemConfigureCategoryServiceUIModel systemConfigureCategoryServiceUIModel = refreshLoadSeviceUIModel(uuid, logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureCategoryServiceUIModel);
		} catch (AuthorizationException ex) {
			return ex.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
