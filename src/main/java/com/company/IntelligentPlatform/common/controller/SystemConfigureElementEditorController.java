package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureExtensionUnionUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureUIFieldUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureElementManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureElementServiceModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureException;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureElementEditorController")
@RequestMapping(value = "/systemConfigureElement")
public class SystemConfigureElementEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_LOGONUSER;

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
	protected SystemConfigureElementListController systemConfigureElementListController;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected SystemConfigureElementManager systemConfigureElementManager;

	@Autowired
	protected SystemConfigureElementServiceUIModelExtension systemConfigureElementServiceUIModelExtension;

	@RequestMapping(value = "/getScenarioModeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getScenarioModeMap(String uuid,
			String switchProxy) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			if (!ServiceEntityStringHelper.checkNullString(switchProxy)) {
				Map<Integer, String> scenarioModeMap = systemConfigureElementManager
						.getScenarioModeMap(switchProxy);
				return systemConfigureCategoryManager.getDefaultSelectMap(
						scenarioModeMap, false);

			} else {
				SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.getEntityNodeByKey(uuid,
								IServiceEntityNodeFieldConstant.UUID,
								SystemConfigureElement.NODENAME,
								logonUser.getClient(), null);
				Map<Integer, String> scenarioModeMap = systemConfigureElementManager
						.getElementScenarioModeMap(systemConfigureElement);
				return systemConfigureCategoryManager.getDefaultSelectMap(
						scenarioModeMap, false);
			}
		} catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}

	@RequestMapping(value = "/getSubScenarioModeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSubScenarioModeMap(String uuid,
			String subSwitchProxy) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			if (!ServiceEntityStringHelper.checkNullString(subSwitchProxy)) {
				Map<Integer, String> subScenarioModeMap = systemConfigureElementManager
						.getScenarioModeMap(subSwitchProxy);
				return systemConfigureCategoryManager.getDefaultSelectMap(
						subScenarioModeMap, false);

			} else {
				SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.getEntityNodeByKey(uuid,
								IServiceEntityNodeFieldConstant.UUID,
								SystemConfigureElement.NODENAME,
								logonUser.getClient(), null);
				Map<Integer, String> subScenarioModeMap = systemConfigureElementManager
						.getElementSubScenarioModeMap(systemConfigureElement);
				return systemConfigureCategoryManager.getDefaultSelectMap(
						subScenarioModeMap, false);
			}
		} catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}


	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		SimpleSEJSONRequest refineJSONRequest = new SimpleSEJSONRequest();
		refineJSONRequest.setNodeName(SystemConfigureElement.NODENAME);
		refineJSONRequest.setId(simpleRequest.getId());
		refineJSONRequest.setUuid(simpleRequest.getUuid());
		refineJSONRequest.setClient(simpleRequest.getClient());
		return super.checkDuplicateIDCore(refineJSONRequest,
				systemConfigureCategoryManager);
	}

	@RequestMapping(value = "/checkSaveModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkSaveModule(
			@RequestBody SystemConfigureElementUIModel systemConfigureElementUIModel) {
		try {
			checkScenarioModeSwitchProxy(systemConfigureElementUIModel
					.getScenarioModeSwitchProxy());
			checkScenarioModeSwitchProxy(systemConfigureElementUIModel
					.getSubScenarioModeSwitchProxy());
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (SystemConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected void checkScenarioModeSwitchProxy(String scenarioModeSwitchProxy)
			throws SystemConfigureException {
		if (scenarioModeSwitchProxy == null
				|| scenarioModeSwitchProxy
						.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return;
		}
		checkConfigureSwitchClass(scenarioModeSwitchProxy);
	}

	protected void checkConfigureSwitchClass(String proxyClassName)
			throws SystemConfigureException {
		try {
			Class<?> proxyClass = Class.forName(proxyClassName);
			String targetInterface = ISystemConfigureSwitch.class
					.getSimpleName();
			Class<?>[] interfaceArray = proxyClass.getInterfaces();
			if (interfaceArray.length == 0) {
				throw new SystemConfigureException(
						SystemConfigureResourceException.PARA2_WRG_CONTROL_INTERFACE,
						proxyClassName, targetInterface);
			}
			for (int i = 0; i < interfaceArray.length; i++) {
				if (targetInterface.equals(interfaceArray[i].getSimpleName())) {
					return;
				}
			}
			throw new SystemConfigureException(
					SystemConfigureException.PARA2_WRG_SWITCH_INTERFACE,
					proxyClassName, targetInterface);
		} catch (ClassNotFoundException e) {
			throw new SystemConfigureException(
					SystemConfigureException.PARA_WRG_SWITCH_CLASS,
					proxyClassName);
		}
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureElement.NODENAME, null);
			String baseUUID = systemConfigureElement.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(systemConfigureElement);
			SystemConfigureElement subConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureElement.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							SystemConfigureElement.NODENAME, null);
			lockSEList.add(subConfigureElement);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					systemConfigureElement.getName(),
					systemConfigureElement.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	private SystemConfigureElementServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("systemConfigureExtensionUnionUIModelList",
				SystemConfigureExtensionUnionUIModel.class);
		classMap.put("systemConfigureElementUIModelList",
				SystemConfigureElementUIModel.class);
		classMap.put("systemConfigureUIFieldUIModelList",
				SystemConfigureUIFieldUIModel.class);
		return (SystemConfigureElementServiceUIModel) JSONObject
				.toBean(jsonObject, SystemConfigureElementServiceUIModel.class,
						classMap);
	}

	private SystemConfigureElementServiceUIModel refreshLoadServiceUIModel(String uuid, String client)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException {
		SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
				.getEntityNodeByKey(uuid,
						IServiceEntityNodeFieldConstant.UUID,
						SystemConfigureElement.NODENAME, client, null);
		SystemConfigureElementServiceModel systemConfigureElementServiceModel = (SystemConfigureElementServiceModel) systemConfigureCategoryManager
				.loadServiceModule(
						SystemConfigureElementServiceModel.class,
						systemConfigureElement);
		return (SystemConfigureElementServiceUIModel) systemConfigureCategoryManager
				.genServiceUIModuleFromServiceModel(
						SystemConfigureElementServiceUIModel.class,
						SystemConfigureElementServiceModel.class,
						systemConfigureElementServiceModel,
						systemConfigureElementServiceUIModelExtension,
						logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		SystemConfigureElementServiceUIModel systemConfigureElementServiceUIModel = parseToServiceUIModel(request);
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
			SystemConfigureElementServiceModel systemConfigureElementServiceModel = (SystemConfigureElementServiceModel) systemConfigureCategoryManager
					.genServiceModuleFromServiceUIModel(
							SystemConfigureElementServiceModel.class,
							SystemConfigureElementServiceUIModel.class,
							systemConfigureElementServiceUIModel,
							systemConfigureElementServiceUIModelExtension);
			systemConfigureCategoryManager.updateServiceModuleWithDelete(
					SystemConfigureElementServiceModel.class,
					systemConfigureElementServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			systemConfigureElementServiceUIModel = refreshLoadServiceUIModel(systemConfigureElementServiceModel
					.getSystemConfigureElement().getUuid(), logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureElementServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<PageHeaderModel> pageHeaderModelList = systemConfigureElementManager
					.getPageHeaderModelList(request,
							logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONArray(pageHeaderModelList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureResource.NODENAME,
							logonUser.getClient(), null);
			SystemConfigureElement systemConfigureElement = null;
			if (systemConfigureResource == null) {
				SystemConfigureElement parentemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.getEntityNodeByKey(request.getBaseUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								SystemConfigureElement.NODENAME,
								logonUser.getClient(), null);
				systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.newEntityNode(parentemConfigureElement,
								SystemConfigureElement.NODENAME);
			} else {
				systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.newEntityNode(systemConfigureResource,
								SystemConfigureElement.NODENAME);
			}
			SystemConfigureElementServiceModel systemConfigureElementServiceModel = new SystemConfigureElementServiceModel();
			systemConfigureElementServiceModel
					.setSystemConfigureElement(systemConfigureElement);
			SystemConfigureElementServiceUIModel systemConfigureResourceServiceUIModel = (SystemConfigureElementServiceUIModel) systemConfigureCategoryManager
					.genServiceUIModuleFromServiceModel(
							SystemConfigureElementServiceUIModel.class,
							SystemConfigureElementServiceModel.class,
							systemConfigureElementServiceModel,
							systemConfigureElementServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureResourceServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
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
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureElement.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureElement);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			SystemConfigureElementServiceUIModel systemConfigureElementServiceUIModel = refreshLoadServiceUIModel(uuid, logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureElementServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureElement.NODENAME,
							logonUser.getClient(), null);
			SystemConfigureElementServiceUIModel systemConfigureElementServiceUIModel = refreshLoadServiceUIModel(uuid, logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureElementServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
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
