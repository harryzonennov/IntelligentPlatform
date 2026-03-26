package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
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
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinAccountFieldSettingEditorController")
@RequestMapping(value = "/resFinAccountFieldSetting")
public class ResFinAccountFieldSettingEditorController extends
		SEEditorController {

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
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected ResFinAccountFieldSettingServiceUIModelExtension resFinAccountFieldSettingServiceUIModelExtension;
	
	public static final String AOID_RESOURCE = ResFinSystemResourceListController.AOID_RESOURCE;
	
	
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
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountFieldSetting.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(resFinAccountFieldSetting);
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
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountFieldSetting.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(resFinAccountFieldSetting);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel = (ResFinAccountFieldSettingUIModel) systemResourceManager
					.genUIModelFromUIModelExtension(
							ResFinAccountFieldSettingUIModel.class,
							resFinAccountFieldSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							resFinAccountFieldSetting, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountFieldSettingUIModel);
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
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel = (ResFinAccountFieldSettingUIModel) JSONObject
					.toBean(jsonObject, ResFinAccountFieldSettingUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = systemResourceManager
					.genSeNodeListInExtensionUnion(
							resFinAccountFieldSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ResFinAccountFieldSetting.class,
							resFinAccountFieldSettingUIModel);
			systemResourceManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) systemResourceManager
					.getEntityNodeByKey(resFinAccountFieldSettingUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountFieldSetting.NODENAME,
							logonUser.getClient(), null);
			resFinAccountFieldSettingUIModel = (ResFinAccountFieldSettingUIModel) systemResourceManager
					.genUIModelFromUIModelExtension(
							ResFinAccountFieldSettingUIModel.class,
							resFinAccountFieldSettingServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							resFinAccountFieldSetting, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountFieldSettingUIModel);
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
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountSetting.NODENAME, logonUser.getClient(),
							null);
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) systemResourceManager
					.newEntityNode(resFinAccountSetting,
							ResFinAccountFieldSetting.NODENAME);
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel = new ResFinAccountFieldSettingUIModel();
			systemResourceManager.convResFinAccountFieldSettingToUI(
					resFinAccountFieldSetting, resFinAccountFieldSettingUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountFieldSettingUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkSaveModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkSaveModule(
			@RequestBody ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel) {
		try {
			if (resFinAccountFieldSettingUIModel.getFinAccProxyClassName() == null
					|| resFinAccountFieldSettingUIModel.getFinAccProxyClassName()
							.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				throw new SystemResourceException(SystemResourceException.PARA_WRG_CONTROLLER_CLASS, "");
			}
			// Check UI model and controller class model firstly
			checkFinAccProxyClass(resFinAccountFieldSettingUIModel
					.getFinAccProxyClassName());
			String responseData = ServiceJSONParser.genSimpleOKResponse();
			return responseData;
		} catch (SystemResourceException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected void checkFinAccProxyClass(String proxyClassName)
			throws SystemResourceException {
		try {
			Class<?> proxyClass = Class.forName(proxyClassName);
			String targetInterface = IFinanceAccountValueProxy.class
					.getSimpleName();
			Class<?>[] interfaceArray = proxyClass.getInterfaces();
			if (interfaceArray.length == 0) {
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						proxyClassName, targetInterface);
			}
			for (int i = 0; i < interfaceArray.length; i++) {
				if (targetInterface.equals(interfaceArray[i].getSimpleName())) {
					return;
				}
			}
			throw new SystemResourceException(
					SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
					proxyClassName, targetInterface);
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					proxyClassName);
		}
	}

	protected void saveInternal(
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = resFinAccountFieldSettingUIModel.getUuid();
		ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) getServiceEntityNodeFromBuffer(
				ResFinAccountFieldSetting.NODENAME, baseUUID);
		systemResourceManager.convUIToResFinAccountFieldSetting(resFinAccountFieldSettingUIModel,
				resFinAccountFieldSetting);
		this.save(baseUUID, systemResourceManager,logonActionController.getLogonUser(), logonActionController.getOrganization());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
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
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountFieldSetting.NODENAME, null);
			String baseUUID = resFinAccountFieldSetting.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(resFinAccountFieldSetting);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					resFinAccountFieldSetting.getName(),
					resFinAccountFieldSetting.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
