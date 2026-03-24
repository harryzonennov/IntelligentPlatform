package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.service.ResFinSystemResourceServiceModel;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.IFinanceControllerResource;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureException;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinSystemResourceEditorController")
@RequestMapping(value = "/resFinSystemResource")
public class ResFinSystemResourceEditorController extends SEEditorController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	@Autowired
	protected ResFinAccountSettingListController resFinAccountSettingListController;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ResFinSystemResourceServiceUIModelExtension resFinSystemResourceServiceUIModelExtension;

	public static final String LABEL_PRE_WARNMSG_DELETE = "preWarnMsgDelete";

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	public static final String AOID_RESOURCE = ResFinSystemResourceListController.AOID_RESOURCE;

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("resFinAccountSettingUIModelList",
				ResourceAuthorizationUIModel.class);
		classMap.put("resFinAccountSettingUIModelList",
				ResFinAccountSettingServiceUIModel.class);
		classMap.put("resFinAccountFieldSettingUIModelList",
				ResFinAccountFieldSettingUIModel.class);
		classMap.put("resFinAccountProcessCodeUIModelList",
				ResFinAccountProcessCodeUIModel.class);
		classMap.put("resourceAuthorizationUIModelList",
				ResourceAuthorizationUIModel.class);
		ResFinSystemResourceServiceUIModel systemResourceServiceUIModel = (ResFinSystemResourceServiceUIModel) JSONObject
				.toBean(jsonObject, ResFinSystemResourceServiceUIModel.class,
						classMap);
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
			ResFinSystemResourceServiceModel systemResourceServiceModel = (ResFinSystemResourceServiceModel) systemResourceManager
					.genServiceModuleFromServiceUIModel(
							ResFinSystemResourceServiceModel.class,
							ResFinSystemResourceServiceUIModel.class,
							systemResourceServiceUIModel,
							resFinSystemResourceServiceUIModelExtension);
			systemResourceManager.updateServiceModuleWithDelete(
					ResFinSystemResourceServiceModel.class,
					systemResourceServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			SystemResource systemResource = (SystemResource) systemResourceManager
					.getEntityNodeByKey(systemResourceServiceModel
							.getSystemResource().getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemResource.NODENAME, logonUser.getClient(),
							null);
			systemResourceServiceModel = (ResFinSystemResourceServiceModel) systemResourceManager
					.loadServiceModule(ResFinSystemResourceServiceModel.class,
							systemResource);
			systemResourceServiceUIModel = (ResFinSystemResourceServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinSystemResourceServiceUIModel.class,
							ResFinSystemResourceServiceModel.class,
							systemResourceServiceModel,
							resFinSystemResourceServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(systemResourceServiceUIModel);
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
			SystemResource systemResource = (SystemResource) systemResourceManager
					.newRootEntityNode(logonUser.getClient());
			ResFinSystemResourceServiceModel systemResourceServiceModel = new ResFinSystemResourceServiceModel();
			systemResourceServiceModel.setSystemResource(systemResource);
			ResFinSystemResourceServiceUIModel systemResourceServiceUIModel = (ResFinSystemResourceServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinSystemResourceServiceUIModel.class,
							ResFinSystemResourceServiceModel.class,
							systemResourceServiceModel, resFinSystemResourceServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(systemResourceServiceUIModel);
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

	@RequestMapping(value = "/checkFinResourceClass", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkFinResourceClass(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Class<?> classType = Class.forName(request.getContent());
			boolean result = systemConfigureResourceManager
					.checkClassImplementInterface(classType,
							IFinanceControllerResource.class.getSimpleName());
			if (result == true) {
				return ServiceJSONParser.genSimpleOKResponse();
			}
			throw new SystemConfigureException(
					SystemConfigureException.PARA2_WRG_SWITCH_INTERFACE,
					classType.getSimpleName(),
					IFinanceControllerResource.class.getSimpleName());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ClassNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SystemConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
			SystemResource systemResource = (SystemResource) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemResource.NODENAME, logonUser.getClient(),
							null);
			return ServiceJSONParser.genDefOKJSONObject(systemResource);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
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
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			SystemResource systemResource = (SystemResource) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemResource.NODENAME, logonUser.getClient(),
							null);
			lockSEList.add(systemResource);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			ResFinSystemResourceServiceModel resFinSystemResourceServiceModel = (ResFinSystemResourceServiceModel) systemResourceManager
					.loadServiceModule(ResFinSystemResourceServiceModel.class,
							systemResource);
			ResFinSystemResourceServiceUIModel resFinSystemResourceUIModel = (ResFinSystemResourceServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinSystemResourceServiceUIModel.class,
							ResFinSystemResourceServiceModel.class,
							resFinSystemResourceServiceModel,
							resFinSystemResourceServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(resFinSystemResourceUIModel);
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
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getSwitchFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchFlag() {
		try {
			Map<Integer, String> switchFlagMap = standardSwitchProxy
					.getSimpleSwitchMap(logonActionController.getLanguageCode());
			return systemResourceManager.getDefaultSelectMap(switchFlagMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}



	@RequestMapping(value = "/checkSaveModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkSaveModule(
			@RequestBody ResFinSystemResourceUIModel systemResourceUIModel) {
		try {
			// Check UI model and controller class model firstly
			systemResourceManager.checkUIModel(systemResourceUIModel
					.getUiModelClassName());
			systemResourceManager
					.checkControllerClassValid(systemResourceUIModel
							.getControllerClassName());
			String responseData = ServiceJSONParser.genSimpleOKResponse();
			return responseData;
		} catch (SystemResourceException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected void saveInternal(
			ResFinSystemResourceUIModel resFinSystemResourceUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = resFinSystemResourceUIModel.getUuid();
		SystemResource systemResource = (SystemResource) getServiceEntityNodeFromBuffer(
				SystemResource.NODENAME, baseUUID);
		systemResourceManager.convUIToResFinSystemResource(
				resFinSystemResourceUIModel, systemResource);
		this.save(baseUUID, systemResourceManager,
				logonActionController.getLogonUser(),
				logonActionController.getOrganization());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, systemResourceManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			SystemResource systemResource = (SystemResource) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemResource.NODENAME, null);
			String baseUUID = systemResource.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(systemResource);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					systemResource.getName(), systemResource.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "chooseAuthorizationObjectToSystemResource", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseAuthorizationObjectToSystemResource(
			SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			AuthorizationObject authorizationObject = (AuthorizationObject) authorizationObjectManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							AuthorizationObject.NODENAME, null);
			if (authorizationObject == null) {
				// should raise exception

			}
			SystemResource systemResource = (SystemResource) getServiceEntityNodeFromBuffer(
					SystemResource.NODENAME, baseUUID);
			systemResource.setRefSimAuthorObjectUUID(authorizationObject
					.getUuid());
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(authorizationObject);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}


}
