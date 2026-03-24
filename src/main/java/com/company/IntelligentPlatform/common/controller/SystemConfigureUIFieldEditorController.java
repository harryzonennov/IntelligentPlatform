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
import com.company.IntelligentPlatform.common.dto.SystemConfigureUIFieldServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SystemConfigureUIFieldUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureUIFieldEditorController")
@RequestMapping(value = "/systemConfigureUIField")
public class SystemConfigureUIFieldEditorController extends SEEditorController {

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
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected SystemConfigureUIFieldServiceUIModelExtension systemConfigureUIFieldServiceUIModelExtension;

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		SimpleSEJSONRequest refineJSONRequest = new SimpleSEJSONRequest();
		refineJSONRequest.setNodeName(SystemConfigureUIField.NODENAME);
		refineJSONRequest.setId(simpleRequest.getId());
		refineJSONRequest.setUuid(simpleRequest.getUuid());
		refineJSONRequest.setClient(simpleRequest.getClient());
		return super.checkDuplicateIDCore(refineJSONRequest,
				systemConfigureCategoryManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			SystemConfigureUIField systemConfigureUIField = (SystemConfigureUIField) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureUIField.NODENAME, null);
			String baseUUID = systemConfigureUIField.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(systemConfigureUIField);
			SystemConfigureUIField subConfigureElement = (SystemConfigureUIField) systemConfigureCategoryManager
					.getEntityNodeByKey(systemConfigureUIField.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							SystemConfigureUIField.NODENAME, null);
			lockSEList.add(subConfigureElement);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					systemConfigureUIField.getName(),
					systemConfigureUIField.getId(), baseUUID);

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
			SystemConfigureUIFieldUIModel systemConfigureUIFieldUIModel = (SystemConfigureUIFieldUIModel) JSONObject
					.toBean(jsonObject, SystemConfigureUIFieldUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = systemConfigureCategoryManager
					.genSeNodeListInExtensionUnion(
							systemConfigureUIFieldServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							SystemConfigureUIField.class,
							systemConfigureUIFieldUIModel);
			systemConfigureCategoryManager.updateSeNodeListWrapper(
					rawSeNodeList, logonUser.getUuid(), organizationUUID);
			SystemConfigureUIField systemConfigureUIField = (SystemConfigureUIField) systemConfigureCategoryManager
					.getEntityNodeByKey(
							systemConfigureUIFieldUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureUIField.NODENAME,
							logonUser.getClient(), null);
			systemConfigureUIFieldUIModel = (SystemConfigureUIFieldUIModel) systemConfigureCategoryManager
					.genUIModelFromUIModelExtension(
							SystemConfigureUIFieldUIModel.class,
							systemConfigureUIFieldServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							systemConfigureUIField, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureUIFieldUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) systemConfigureCategoryManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureResource.NODENAME,
							logonUser.getClient(), null);
			SystemConfigureUIField systemConfigureUIField = (SystemConfigureUIField) systemConfigureCategoryManager
					.newEntityNode(systemConfigureResource,
							SystemConfigureUIField.NODENAME);
			SystemConfigureUIFieldUIModel systemConfigureUIFieldUIModel = new SystemConfigureUIFieldUIModel();
			systemConfigureCategoryManager.convSystemConfigureUIFieldToUI(
					systemConfigureUIField, systemConfigureUIFieldUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(systemConfigureUIFieldUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
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
			SystemConfigureUIField systemConfigureUIField = (SystemConfigureUIField) systemConfigureCategoryManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SystemConfigureUIField.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(systemConfigureUIField);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			SystemConfigureUIFieldUIModel inboundItemUIModel = (SystemConfigureUIFieldUIModel) systemConfigureCategoryManager
					.genUIModelFromUIModelExtension(
							SystemConfigureUIFieldUIModel.class,
							systemConfigureUIFieldServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							systemConfigureUIField, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser.genDefOKJSONObject(inboundItemUIModel);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
