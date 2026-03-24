package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;

@Deprecated
@Scope("session")
@Controller(value = "serviceDocConsumerUnionEditorController")
@RequestMapping(value = "/serviceDocConsumerUnion")
public class ServiceDocConsumerUnionEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;


	protected void saveInternal(
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = serviceDocConsumerUnionUIModel.getUuid();
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
				.getEntityNodeByKey(baseUUID,
						IServiceEntityNodeFieldConstant.UUID,
						ServiceDocConsumerUnion.NODENAME,
						logonUser.getClient(), null);
		serviceDocConsumerUnionManager.convUIToServiceDocConsumerUnion(
				serviceDocConsumerUnionUIModel, serviceDocConsumerUnion);
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, serviceDocConsumerUnionManager, logonUser,
				organization);
	}

	/**
	 * Save module service
	 */
	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(
			@RequestBody ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel) {
		try {
			String baseUUID = serviceDocConsumerUnionUIModel.getUuid();
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
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			if (serviceDocConsumerUnion != null) {
				serviceDocConsumerUnionManager
						.convUIToServiceDocConsumerUnion(
								serviceDocConsumerUnionUIModel,
								serviceDocConsumerUnion);
				serviceDocConsumerUnionManager.updateSENode(
						serviceDocConsumerUnion, logonUser.getUuid(),
						organizationUUID);
			} else {
				serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
						.newRootEntityNode(logonUser.getClient());
				serviceDocConsumerUnionManager
						.convUIToServiceDocConsumerUnion(
								serviceDocConsumerUnionUIModel,
								serviceDocConsumerUnion);
				serviceDocConsumerUnionManager.insertSENode(
						serviceDocConsumerUnion, logonUser.getUuid(),
						organizationUUID);
			}

			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConsumerUnionUIModel);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LogonInfoException e) {
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
				serviceDocConsumerUnionManager);
	}



	/**
	 * New Module Edit Service
	 */
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
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.newRootEntityNode();
			// Convert SE node list to UIModel
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel = new ServiceDocConsumerUnionUIModel();
			serviceDocConsumerUnionManager.convServiceDocConsumerUnionToUI(
					serviceDocConsumerUnion, serviceDocConsumerUnionUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConsumerUnionUIModel);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException | AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	/**
	 * Load Module Edit Service
	 */
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
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceDocConsumerUnion);
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel = new ServiceDocConsumerUnionUIModel();
			serviceDocConsumerUnionManager.convServiceDocConsumerUnionToUI(
					serviceDocConsumerUnion, serviceDocConsumerUnionUIModel);
			lockSEList.add(serviceDocConsumerUnion);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConsumerUnionUIModel);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LockObjectFailureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
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
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = serviceDocConsumerUnion.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceDocConsumerUnion);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceDocConsumerUnion.getName(),
					serviceDocConsumerUnion.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}


	@RequestMapping(value = "chooseConsumerUnion", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseConsumerUnion(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String uuid = request.getUuid();
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(serviceDocConsumerUnion);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException | AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
