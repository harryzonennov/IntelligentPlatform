package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.ResFinAccountSettingServiceModel;
import com.company.IntelligentPlatform.finance.service.ResFinSystemResourceServiceModel;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONDataException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinAccountSettingEditorController")
@RequestMapping(value = "/resFinAccountSetting")
public class ResFinAccountSettingEditorController extends SEEditorController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected ResFinAccountFieldSettingListController resFinAccountFieldSettingListController;

	@Autowired
	protected ResFinAccountProcessCodeListController resFinAccountProcessCodeListController;

	@Autowired
	protected ResFinAccountSettingServiceUIModelExtension resFinAccountSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	public static final String AOID_RESOURCE = ResFinSystemResourceListController.AOID_RESOURCE;

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
	
	@RequestMapping(value = "/getProcessCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProcessCodeMap(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
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
			Map<Integer, String> processCodeMap = systemResourceManager.getProcessCodeMap(systemResource.getControllerClassName());
			return systemResourceManager.getDefaultSelectMap(processCodeMap);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SystemResourceException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/getFinAccObjectKeyMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFinAccObjectKeyMap(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
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
			Map<Integer, String> finAccObjectKeyMap =systemResourceManager.getFinAccObjectKeyMap(systemResource.getControllerClassName());
			return systemResourceManager.getDefaultSelectMap(finAccObjectKeyMap);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SystemResourceException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
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
			SystemResource systemResource = (SystemResource) systemResourceManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemResource.NODENAME, logonUser.getClient(), null);
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.newEntityNode(systemResource,
							ResFinAccountSetting.NODENAME);
			ResFinAccountSettingServiceModel resFinAccountSettingServiceModel = new ResFinAccountSettingServiceModel();
			resFinAccountSettingServiceModel
					.setResFinAccountSetting(resFinAccountSetting);
			ResFinAccountSettingServiceUIModel inquiryMaterialItemServiceUIModel = (ResFinAccountSettingServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinAccountSettingServiceUIModel.class,
							ResFinAccountSettingServiceModel.class,
							resFinAccountSettingServiceModel,
							resFinAccountSettingServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(inquiryMaterialItemServiceUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("resFinAccountFieldSettingUIModelList",
				ResFinAccountFieldSettingUIModel.class);
		classMap.put("resFinAccountProcessCodeUIModelList",
				ResFinAccountProcessCodeUIModel.class);
		ResFinAccountSettingServiceUIModel resFinAccountSettingServiceUIModel = (ResFinAccountSettingServiceUIModel) JSONObject
				.toBean(jsonObject, ResFinAccountSettingServiceUIModel.class,
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
			ResFinAccountSettingServiceModel resFinAccountSettingServiceModel = (ResFinAccountSettingServiceModel) systemResourceManager
					.genServiceModuleFromServiceUIModel(
							ResFinAccountSettingServiceModel.class,
							ResFinAccountSettingServiceUIModel.class,
							resFinAccountSettingServiceUIModel,
							resFinAccountSettingServiceUIModelExtension);
			systemResourceManager.updateServiceModuleWithDelete(
					ResFinAccountSettingServiceModel.class,
					resFinAccountSettingServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.getEntityNodeByKey(resFinAccountSettingServiceModel
							.getResFinAccountSetting().getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountSetting.NODENAME, logonUser
									.getClient(), null);
			resFinAccountSettingServiceModel = (ResFinAccountSettingServiceModel) systemResourceManager
					.loadServiceModule(ResFinAccountSettingServiceModel.class,
							resFinAccountSetting);
			resFinAccountSettingServiceUIModel = (ResFinAccountSettingServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinAccountSettingServiceUIModel.class,
							ResFinSystemResourceServiceModel.class,
							resFinAccountSettingServiceModel,
							resFinAccountSettingServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountSettingServiceUIModel);
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
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountSetting.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(resFinAccountSetting);
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
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountSetting.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(resFinAccountSetting);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			ResFinAccountSettingServiceModel resFinAccountSettingServiceModel = (ResFinAccountSettingServiceModel) systemResourceManager
					.loadServiceModule(ResFinAccountSettingServiceModel.class,
							resFinAccountSetting);
			ResFinAccountSettingServiceUIModel resFinAccountSettingServiceUIModel = (ResFinAccountSettingServiceUIModel) systemResourceManager
					.genServiceUIModuleFromServiceModel(
							ResFinAccountSettingServiceUIModel.class,
							ResFinAccountSettingServiceModel.class,
							resFinAccountSettingServiceModel, resFinAccountSettingServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountSettingServiceUIModel);
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

	protected void saveInternal(
			ResFinAccountSettingUIModel resFinAccountSettingUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = resFinAccountSettingUIModel.getUuid();
		ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) getServiceEntityNodeFromBuffer(
				ResFinAccountSetting.NODENAME, baseUUID);
		systemResourceManager.convUIToResFinAccountSetting(
				resFinAccountSettingUIModel, resFinAccountSetting);
		this.save(baseUUID, systemResourceManager,
				logonActionController.getLogonUser(),
				logonActionController.getOrganization());
	}

	@RequestMapping(value = "/checkSaveModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkSaveModule(
			@RequestBody ResFinAccountSettingUIModel resFinAccountSettingUIModel) {
		try {
			boolean ignoreUIModel = false;
			if (resFinAccountSettingUIModel.getSettleUIModelName() == null
					|| resFinAccountSettingUIModel.getSettleUIModelName()
							.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				ignoreUIModel = true;
			}
			if (!ignoreUIModel) {
				// Check Fin settle UI model
				systemResourceManager.checkUIModel(resFinAccountSettingUIModel
						.getSettleUIModelName());
			}
			boolean ignoreAccProxy = false;
			if (resFinAccountSettingUIModel.getRefFinAccObjectProxyClass() == null
					|| resFinAccountSettingUIModel
							.getRefFinAccObjectProxyClass().equals(
									ServiceEntityStringHelper.EMPTYSTRING)) {
				ignoreAccProxy = true;
			}
			if (!ignoreAccProxy) {
				systemResourceManager
						.checkAccObjectProxyClassValid(resFinAccountSettingUIModel
								.getRefFinAccObjectProxyClass());
			}
			String responseData = ServiceJSONParser.genSimpleOKResponse();
			return responseData;
		} catch (SystemResourceException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
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
	public String preLock(String uuid) {
		try {
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountSetting.NODENAME, null);
			String baseUUID = resFinAccountSetting.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(resFinAccountSetting);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					resFinAccountSetting.getName(),
					resFinAccountSetting.getId(), baseUUID);

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

	@RequestMapping(value = "/deleteModule")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
			String uuid = serviceExitLockJSONModule.getUuid();
			systemResourceManager.admDeleteResFinAccSetting(uuid);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "chooseFinAccountTitleToSystemResource", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseFinAccountTitleToSystemResource(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, null);
			if (finAccountTitle == null) {
				// Should raise exception here
			}
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) getServiceEntityNodeFromBuffer(
					ResFinAccountSetting.NODENAME, baseUUID);
			systemResourceManager.buildReferenceNode(finAccountTitle,
					resFinAccountSetting, ServiceEntityFieldsHelper
							.getCommonPackage(finAccountTitle.getClass()));
			String apiKey = "chooseFinAccountTitleToSystemResource";
			String responseData = ServiceJSONParser.genDefSingleEntityJSONData(
					apiKey, finAccountTitle);
			return responseData;
			// return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceJSONDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected void convFinAccountTitleToUI(FinAccountTitle finAccountTitle,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel) {
		if (finAccountTitle != null) {
			resFinAccountSettingUIModel.setFinAccountTitleId(finAccountTitle
					.getId());
			resFinAccountSettingUIModel.setFinAccountTitleName(finAccountTitle
					.getName());
		}
	}

}
