package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
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
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinAccountProcessCodeEditorController")
@RequestMapping(value = "/resFinAccountProcessCode")
public class ResFinAccountProcessCodeEditorController extends
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
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected ResFinAccountProcessCodeServiceUIModelExtension resFinAccountProcessCodeServiceUIModelExtension;
	
	public static final String AOID_RESOURCE = ResFinSystemResourceListController.AOID_RESOURCE;

	protected void convUIToResFinAccountProcessCode(
			ResFinAccountProcessCode rawEntity,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel) {
		rawEntity.setUuid(resFinAccountProcessCodeUIModel.getUuid());
		rawEntity.setParentNodeUUID(resFinAccountProcessCodeUIModel
				.getParentNodeUUID());
		rawEntity.setProcessCode(resFinAccountProcessCodeUIModel
				.getProcessCode());
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
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountProcessCode.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(resFinAccountProcessCode);
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
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountProcessCode.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(resFinAccountProcessCode);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel = (ResFinAccountProcessCodeUIModel) systemResourceManager
					.genUIModelFromUIModelExtension(
							ResFinAccountProcessCodeUIModel.class,
							resFinAccountProcessCodeServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							resFinAccountProcessCode, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountProcessCodeUIModel);
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
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel = (ResFinAccountProcessCodeUIModel) JSONObject
					.toBean(jsonObject, ResFinAccountProcessCodeUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = systemResourceManager
					.genSeNodeListInExtensionUnion(
							resFinAccountProcessCodeServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ResFinAccountProcessCode.class,
							resFinAccountProcessCodeUIModel);
			systemResourceManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) systemResourceManager
					.getEntityNodeByKey(resFinAccountProcessCodeUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountProcessCode.NODENAME,
							logonUser.getClient(), null);
			resFinAccountProcessCodeUIModel = (ResFinAccountProcessCodeUIModel) systemResourceManager
					.genUIModelFromUIModelExtension(
							ResFinAccountProcessCodeUIModel.class,
							resFinAccountProcessCodeServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							resFinAccountProcessCode, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountProcessCodeUIModel);
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
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) systemResourceManager
					.newEntityNode(resFinAccountSetting,
							ResFinAccountProcessCode.NODENAME);
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel = new ResFinAccountProcessCodeUIModel();
			systemResourceManager.convResFinAccountProcessCodeToUI(
					resFinAccountProcessCode, resFinAccountProcessCodeUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(resFinAccountProcessCodeUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public String preLock(String uuid) {
		try {
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) systemResourceManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ResFinAccountProcessCode.NODENAME, null);
			String baseUUID = resFinAccountProcessCode.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(resFinAccountProcessCode);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					resFinAccountProcessCode.getName(),
					resFinAccountProcessCode.getId(), baseUUID);

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

	protected void convResFinAccountProcessCodeToUI(
			SystemResource systemResource,
			ResFinAccountProcessCode resFinAccountProcessCode,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel)
			throws ServiceEntityInstallationException, SystemResourceException {
		if (resFinAccountProcessCode != null) {
			resFinAccountProcessCodeUIModel.setUuid(resFinAccountProcessCode
					.getUuid());
			resFinAccountProcessCodeUIModel
					.setParentNodeUUID(resFinAccountProcessCode
							.getParentNodeUUID());
			Map<Integer, String> processCodeMap = systemResourceManager
					.getProcessCodeMap(systemResource.getControllerClassName());
			resFinAccountProcessCodeUIModel.setProcessCodeValue(processCodeMap
					.get(resFinAccountProcessCode.getProcessCode()));
			resFinAccountProcessCodeUIModel
					.setProcessCode(resFinAccountProcessCode.getProcessCode());
		}
	}

	protected void convResFinAccountSettingToUI(
			ResFinAccountSetting resFinAccountSetting,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel) {
		if (resFinAccountSetting != null) {
			resFinAccountProcessCodeUIModel.setId(resFinAccountSetting.getId());
			resFinAccountProcessCodeUIModel.setName(resFinAccountSetting
					.getName());
		}
	}

}
