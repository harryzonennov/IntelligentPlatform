package com.company.IntelligentPlatform.platform.controller;

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

import com.company.IntelligentPlatform.platform.controller.PageHeaderModel;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureExtensionUnionServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureExtensionUnionUIModel;
import com.company.IntelligentPlatform.platform.controller.SEEditorController;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.service.SpringContextBeanService;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.LockObjectFailureException;
import com.company.IntelligentPlatform.platform.service.LockObjectManager;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.platform.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.platform.service.SystemConfigureExtensionUnionManager;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.Organization;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.platform.model.SystemConfigureElement;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureExtensionUnionEditorController")
@RequestMapping(value = "/systemConfigureExtensionUnion")
public class SystemConfigureExtensionUnionEditorController extends SEEditorController {

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
	protected SystemConfigureExtensionUnionManager systemConfigureExtensionUnionManager;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	@Autowired
	protected SystemConfigureExtensionUnionServiceUIModelExtension systemConfigureExtensionUnionServiceUIModelExtension;

	
	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			SystemConfigureExtensionUnion systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureExtensionUnion.NODENAME, null);
			String baseUUID = systemConfigureExtensionUnion.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(systemConfigureExtensionUnion);
			SystemConfigureExtensionUnion subConfigureElement = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureExtensionUnion.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							SystemConfigureExtensionUnion.NODENAME, null);
			lockSEList.add(subConfigureElement);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					systemConfigureExtensionUnion.getName(),
					systemConfigureExtensionUnion.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
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
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel = (SystemConfigureExtensionUnionUIModel) JSONObject
					.toBean(jsonObject, SystemConfigureExtensionUnionUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = systemConfigureCategoryManager
					.genSeNodeListInExtensionUnion(
							systemConfigureExtensionUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							SystemConfigureExtensionUnion.class,
							systemConfigureExtensionUnionUIModel);
			systemConfigureCategoryManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			SystemConfigureExtensionUnion systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureExtensionUnionUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureExtensionUnion.NODENAME,
							logonUser.getClient(), null);
			systemConfigureExtensionUnionUIModel = (SystemConfigureExtensionUnionUIModel) systemConfigureCategoryManager
					.genUIModelFromUIModelExtension(
							SystemConfigureExtensionUnionUIModel.class,
							systemConfigureExtensionUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							systemConfigureExtensionUnion, logonActionController.getLogonInfo(),  null);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureExtensionUnionUIModel);
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
	
	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<PageHeaderModel> pageHeaderModelList = systemConfigureExtensionUnionManager.getPageHeaderModelList(request, logonUser.getClient());
			String result = ServiceJSONParser
					.genDefOKJSONArray(pageHeaderModelList);
			return result;
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
							SystemConfigureResource.NODENAME, logonUser.getClient(),
							null);
			SystemConfigureExtensionUnion systemConfigureExtensionUnion = null;
			if(systemConfigureResource != null){
				// In case parent node is systemConfigureResource
				systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
						.newEntityNode(systemConfigureResource,
								SystemConfigureExtensionUnion.NODENAME);
			}else{
				SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
						.getEntityNodeByKey(request.getBaseUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								SystemConfigureElement.NODENAME, logonUser.getClient(),
								null);
				systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
						.newEntityNode(systemConfigureElement,
								SystemConfigureExtensionUnion.NODENAME);
			}
			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel = new SystemConfigureExtensionUnionUIModel();
			systemConfigureExtensionUnionManager.convSystemConfigureExtensionUnionToUI(
					systemConfigureExtensionUnion, systemConfigureExtensionUnionUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureExtensionUnionUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
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
			SystemConfigureExtensionUnion systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureExtensionUnion.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureExtensionUnion);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel = (SystemConfigureExtensionUnionUIModel) systemConfigureCategoryManager
					.genUIModelFromUIModelExtension(
							SystemConfigureExtensionUnionUIModel.class,
							systemConfigureExtensionUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							systemConfigureExtensionUnion, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureExtensionUnionUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
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

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			SystemConfigureExtensionUnion systemConfigureExtensionUnion = (SystemConfigureExtensionUnion) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureExtensionUnion.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureExtensionUnion);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			SystemConfigureExtensionUnionUIModel systemConfigureExtensionUnionUIModel = (SystemConfigureExtensionUnionUIModel) systemConfigureCategoryManager
					.genUIModelFromUIModelExtension(
							SystemConfigureExtensionUnionUIModel.class,
							systemConfigureExtensionUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							systemConfigureExtensionUnion, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureExtensionUnionUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
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
	
	@RequestMapping(value = "/exitEditor")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
