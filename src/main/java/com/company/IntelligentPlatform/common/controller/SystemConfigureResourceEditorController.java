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

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureExtensionUnionUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "systemConfigureResourceEditorController")
@RequestMapping(value = "/systemConfigureResource")
public class SystemConfigureResourceEditorController extends SEEditorController {

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
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	@Autowired
	protected SystemConfigureElementListController systemConfigureElementListController;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SystemConfigureResourceServiceUIModelExtension systemConfigureResourceServiceUIModelExtension;


	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		SimpleSEJSONRequest refineJSONRequest = new SimpleSEJSONRequest();
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		refineJSONRequest.setNodeName(SystemConfigureResource.NODENAME);
		refineJSONRequest.setId(simpleRequest.getId());
		refineJSONRequest.setUuid(simpleRequest.getUuid());
		refineJSONRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(refineJSONRequest, systemConfigureCategoryManager);
	}

	private SystemConfigureResourceServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("systemConfigureElementUIModelList", SystemConfigureElementServiceUIModel.class);
		classMap.put("systemConfigureExtensionUnionUIModelList", SystemConfigureExtensionUnionUIModel.class);
		SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = (SystemConfigureResourceServiceUIModel) JSONObject
				.toBean(jsonObject, SystemConfigureResourceServiceUIModel.class, classMap);
		return systemConfigureResourceServiceUIModel;
	}

	private SystemConfigureResourceServiceUIModel refreshLoadServiceUIModel(String uuid, String client)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException {
		SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, SystemConfigureResource.NODENAME, client, null);
		SystemConfigureResourceServiceModel systemConfigureResourceServiceModel = (SystemConfigureResourceServiceModel) systemConfigureCategoryManager
				.loadServiceModule(SystemConfigureResourceServiceModel.class, systemConfigureResource);
		SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = (SystemConfigureResourceServiceUIModel) systemConfigureCategoryManager
				.genServiceUIModuleFromServiceModel(SystemConfigureResourceServiceUIModel.class,
						SystemConfigureResourceServiceModel.class, systemConfigureResourceServiceModel,
						systemConfigureResourceServiceUIModelExtension, logonActionController.getLogonInfo());
		return systemConfigureResourceServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			SystemConfigureResourceServiceModel systemConfigureResourceServiceModel = (SystemConfigureResourceServiceModel) systemConfigureCategoryManager
					.genServiceModuleFromServiceUIModel(SystemConfigureResourceServiceModel.class,
							SystemConfigureResourceServiceUIModel.class, systemConfigureResourceServiceUIModel,
							systemConfigureResourceServiceUIModelExtension);
			systemConfigureCategoryManager
					.updateServiceModuleWithDelete(SystemConfigureResourceServiceModel.class, systemConfigureResourceServiceModel,
							logonUser.getUuid(), organizationUUID);
			// Refresh service model
			systemConfigureResourceServiceUIModel = refreshLoadServiceUIModel(
					systemConfigureResourceServiceModel.getSystemConfigureResource().getUuid(), logonUser.getClient());
			return ServiceJSONParser.genDefOKJSONObject(systemConfigureResourceServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, SystemConfigureResource.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = systemConfigureResourceManager
					.getPageHeaderModelList(request, logonUser.getClient());
			String result = ServiceJSONParser.genDefOKJSONArray(pageHeaderModelList);
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
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) systemConfigureCategoryManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, SystemConfigureCategory.NODENAME,
							logonUser.getClient(), null);
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.newEntityNode(systemConfigureCategory, SystemConfigureResource.NODENAME);
			SystemConfigureResourceServiceModel systemConfigureResourceServiceModel = new SystemConfigureResourceServiceModel();
			systemConfigureResourceServiceModel.setSystemConfigureResource(systemConfigureResource);
			SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = (SystemConfigureResourceServiceUIModel) systemConfigureCategoryManager
					.genServiceUIModuleFromServiceModel(SystemConfigureResourceServiceUIModel.class,
							SystemConfigureResourceServiceModel.class, systemConfigureResourceServiceModel,
							systemConfigureResourceServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(systemConfigureResourceServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preLock(String uuid) {
		try {
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, SystemConfigureResource.NODENAME, null);
			String baseUUID = systemConfigureResource.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(systemConfigureResource);
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureResource.getUuid(), IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							SystemConfigureElement.NODENAME, null);
			lockSEList.add(systemConfigureElement);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, systemConfigureResource.getName(), systemConfigureResource.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, SystemConfigureResource.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureResource);
			lockObjectManager
					.lockServiceEntityList(lockSEList, logonUser, logonActionController.getOrganizationByUser(logonUser.getUuid()));
			SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = refreshLoadServiceUIModel(
					uuid, logonUser.getClient());
			return ServiceJSONParser.genDefOKJSONObject(systemConfigureResourceServiceUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}


	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, SystemConfigureResource.NODENAME,
							logonUser.getClient(), null);
			SystemConfigureResourceServiceUIModel systemConfigureResourceServiceUIModel = refreshLoadServiceUIModel(
					uuid, logonUser.getClient());
			return ServiceJSONParser.genDefOKJSONObject(systemConfigureResourceServiceUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
