package com.company.IntelligentPlatform.finance.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleException;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "finAccountTitleEditorController")
@RequestMapping(value = "/finAccountTitle")
public class FinAccountTitleEditorController extends SEEditorController {

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected FinAccountTitleServiceUIModelExtension finAccountTitleServiceUIModelExtension;

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;

	public FinAccountTitleEditorController() {
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
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
			FinAccountTitleUIModel finAccountTitleUIModel = (FinAccountTitleUIModel) JSONObject
					.toBean(jsonObject, FinAccountTitleUIModel.class, classMap);
			List<ServiceEntityNode> rawSeNodeList = finAccountTitleManager
					.genSeNodeListInExtensionUnion(
							finAccountTitleServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							FinAccountTitle.class, finAccountTitleUIModel);
			finAccountTitleManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(finAccountTitleUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);
			finAccountTitleUIModel = (FinAccountTitleUIModel) finAccountTitleManager
					.genUIModelFromUIModelExtension(
							FinAccountTitleUIModel.class,
							finAccountTitleServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							finAccountTitle, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser.genDefOKJSONObject(finAccountTitleUIModel);
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
			String baseUUID = request.getBaseUUID();
			FinAccountTitle finAccountTitle = finAccountTitleManager.newAccountTitle(baseUUID, "", logonUser.getClient());
			FinAccountTitleUIModel finAccountTitleUIModel = new FinAccountTitleUIModel();
			finAccountTitleManager.convFinAccountTitleToUI(finAccountTitle,
					finAccountTitleUIModel);
			return ServiceJSONParser.genDefOKJSONObject(finAccountTitleUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (FinAccountTitleException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}
	

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<String, Class>();
			SimpleSEJSONRequest simpleRequest = (SimpleSEJSONRequest) JSONObject
					.toBean(jsonObject, SimpleSEJSONRequest.class, classMap);
			if (ServiceEntityStringHelper.checkNullString(simpleRequest
					.getUuid())) {
				// UUID should not be null
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.TYPE_SYSTEM_WRONG);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
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
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(simpleRequest.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);	
			if (finAccountTitle != null) {
				finAccountTitleManager.archiveDeleteTitle(finAccountTitle, logonUser.getClient(),
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | FinAccountTitleException e) {
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
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);

			FinAccountTitleUIModel finAccountTitleUIModel = (FinAccountTitleUIModel) finAccountTitleManager
					.genUIModelFromUIModelExtension(
							FinAccountTitleUIModel.class,
							finAccountTitleServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							finAccountTitle, logonActionController.getLogonInfo(),null);
			return ServiceJSONParser.genDefOKJSONObject(finAccountTitleUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
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
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);
			lockSEList.add(finAccountTitle);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			FinAccountTitleUIModel finAccountTitleUIModel = (FinAccountTitleUIModel) finAccountTitleManager
					.genUIModelFromUIModelExtension(
							FinAccountTitleUIModel.class,
							finAccountTitleServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							finAccountTitle, logonActionController.getLogonInfo(),null);
			return ServiceJSONParser.genDefOKJSONObject(finAccountTitleUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
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

	@RequestMapping(value = "/getSettleType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSettleType() {
		try {
			Map<Integer, String> settleTypeMap = finAccountTitleManager
					.initSettleTypeMap();
			return finAccountTitleManager.getDefaultSelectMap(settleTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getOriginalType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getOriginalType() {
		try {
			Map<Integer, String> originalTypeMap = finAccountTitleManager
					.initOriginalTypeMap();
			return finAccountTitleManager.getDefaultSelectMap(originalTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getFinAccountType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFinAccountType() {
		try {
			Map<Integer, String> finAccountTypeMap = finAccountTitleManager
					.initFinAccountTypeMap();
			return finAccountTitleManager
					.getDefaultSelectMap(finAccountTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getGenerateType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getGenerateType() {
		try {
			Map<Integer, String> generateTypeMap = finAccountTitleManager
					.initGenerateTypeMap();
			return finAccountTitleManager.getDefaultSelectMap(generateTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCategory() {
		try {
			Map<Integer, String> categoryMap = finAccountTitleManager
					.initCategoryMap();
			return finAccountTitleManager.getDefaultSelectMap(categoryMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/deleteFinAccountTitle", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteFinAccountTitle(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
			String titleUUID = request.getUuid();
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			finAccountTitleManager.deleteFinAccountTitle(titleUUID, logonUser,
					organizationUUID);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (FinAccountTitleException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected void saveInternal(FinAccountTitleUIModel finAccountTitleUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		// Convert UI Model to SE entity list
		String baseUUID = finAccountTitleUIModel.getUuid();
		FinAccountTitle finAccountTitle = (FinAccountTitle) getServiceEntityNodeFromBuffer(
				FinAccountTitle.NODENAME, baseUUID);
		finAccountTitleManager.convUIToFinAccountTitle(finAccountTitleUIModel,
				finAccountTitle);
		this.save(baseUUID, finAccountTitleManager,
				logonActionController.getLogonUser(),
				logonActionController.getOrganization());
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
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);
			String baseUUID = finAccountTitle.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(finAccountTitle);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					finAccountTitle.getName(), finAccountTitle.getId(),
					baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, finAccountTitleManager);
	}

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
