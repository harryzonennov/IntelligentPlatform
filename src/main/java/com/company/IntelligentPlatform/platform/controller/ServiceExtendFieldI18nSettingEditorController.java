package com.company.IntelligentPlatform.platform.controller;

import java.lang.SuppressWarnings;
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

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.ServiceExtendFieldI18nSettingUIModel;
import com.company.IntelligentPlatform.platform.dto.ServiceExtendFieldI18nSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.SEEditorController;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.LockObjectFailureException;
import com.company.IntelligentPlatform.platform.service.LockObjectManager;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.service.ServiceExtendFieldSettingManager;
import com.company.IntelligentPlatform.platform.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.Organization;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;

import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldSetting;

@Scope("session")
@Controller(value = "serviceExtendFieldI18nSettingEditorController")
@RequestMapping(value = "/serviceExtendFieldI18nSetting")
public class ServiceExtendFieldI18nSettingEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceExtendFieldI18nSettingServiceUIModelExtension serviceExtendFieldI18nSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
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
			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel = (ServiceExtendFieldI18nSettingUIModel) JSONObject
					.toBean(jsonObject, ServiceExtendFieldI18nSettingUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = serviceExtensionSettingManager
					.genSeNodeListInExtensionUnion(
							serviceExtendFieldI18nSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ServiceExtendFieldI18nSetting.class,
							serviceExtendFieldI18nSettingUIModel);
			serviceExtensionSettingManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(serviceExtendFieldI18nSettingUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldI18nSetting.NODENAME,
							logonUser.getClient(), null);
			serviceExtendFieldI18nSettingUIModel = (ServiceExtendFieldI18nSettingUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							ServiceExtendFieldI18nSettingUIModel.class,
							serviceExtendFieldI18nSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceExtendFieldI18nSetting, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceExtendFieldI18nSettingUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}		
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting)serviceExtensionSettingManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
					.newEntityNode(serviceExtendFieldSetting, ServiceExtendFieldI18nSetting.NODENAME);
			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel = new ServiceExtendFieldI18nSettingUIModel();
			serviceExtendFieldSettingManager.convServiceExtendFieldI18nSettingToUI(
					serviceExtendFieldI18nSetting, serviceExtendFieldI18nSettingUIModel);
			serviceExtendFieldSettingManager.convParentToFieldI18nSettingUI(serviceExtendFieldSetting, serviceExtendFieldI18nSettingUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceExtendFieldI18nSettingUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldI18nSetting.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(serviceExtendFieldI18nSetting);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel = (ServiceExtendFieldI18nSettingUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							ServiceExtendFieldI18nSettingUIModel.class,
							serviceExtendFieldI18nSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceExtendFieldI18nSetting, logonActionController.getLogonInfo(),  null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceExtendFieldI18nSettingUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
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
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serviceExtensionSettingManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = serviceExtendFieldSetting.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceExtendFieldSetting);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceExtendFieldSetting.getName(),
					serviceExtendFieldSetting.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		}
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
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceExtendFieldSetting);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
