package com.company.IntelligentPlatform.common.dto;

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

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceEntityRegisterEntityUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityRegisterEntityManager;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serviceEntityRegisterEntityEditorController")
@RequestMapping(value = "/serviceEntityRegisterEntity")
public class ServiceEntityRegisterEntityEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceEntityRegisterEntityServiceUIModelExtension serviceEntityRegisterEntityServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceEntityRegisterEntityManager serviceEntityRegisterEntityManager;

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
			ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel = (ServiceEntityRegisterEntityUIModel) JSONObject
					.toBean(jsonObject,
							ServiceEntityRegisterEntityUIModel.class, classMap);
			List<ServiceEntityNode> rawSeNodeList = serviceEntityRegisterEntityManager
					.genSeNodeListInExtensionUnion(
							serviceEntityRegisterEntityServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ServiceEntityRegisterEntity.class,
							serviceEntityRegisterEntityUIModel);
			serviceEntityRegisterEntityManager.updateSeNodeListWrapper(
					rawSeNodeList, logonUser.getUuid(), organizationUUID);
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(
							serviceEntityRegisterEntityUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityRegisterEntity.NODENAME,null);
			serviceEntityRegisterEntityUIModel = (ServiceEntityRegisterEntityUIModel) serviceEntityRegisterEntityManager
					.genUIModelFromUIModelExtension(
							ServiceEntityRegisterEntityUIModel.class,
							serviceEntityRegisterEntityServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceEntityRegisterEntity,logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceEntityRegisterEntityUIModel);
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
	public @ResponseBody String newModuleService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}			
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.newRootEntityNode();
			ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel = new ServiceEntityRegisterEntityUIModel();
			serviceEntityRegisterEntityManager
					.convServiceEntityRegisterEntityToUI(
							serviceEntityRegisterEntity,
							serviceEntityRegisterEntityUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceEntityRegisterEntityUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/checkClassValidBatch", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkClassValidBatch(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}			
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityRegisterEntity.NODENAME, null);
			String seProxyType = serviceEntityRegisterEntity.getSeProxyType();
			serviceEntityRegisterEntityManager.checkClassTypeExist(seProxyType);
			String seDAOType = serviceEntityRegisterEntity.getSeDAOType();
			serviceEntityRegisterEntityManager.checkClassTypeExist(seDAOType);
			String seManagerType = serviceEntityRegisterEntity.getSeManagerType();
			serviceEntityRegisterEntityManager.checkClassTypeExist(seManagerType);
			String seModeType = serviceEntityRegisterEntity.getSeModuleType();
			serviceEntityRegisterEntityManager.checkClassTypeExist(seModeType);
			return ServiceJSONParser.genSimpleOKResponse();
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
		}
	}
	
	@RequestMapping(value = "/checkClassValid", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkClassValid(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceEntityRegisterEntityManager.checkClassTypeExist(request.getContent());
			return ServiceJSONParser.genSimpleOKResponse();
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
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		return super.checkDuplicateIDCore(simpleRequest,
				serviceEntityRegisterEntityManager);
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
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityRegisterEntity.NODENAME, null);
			String baseUUID = serviceEntityRegisterEntity.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceEntityRegisterEntity);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceEntityRegisterEntity.getName(),
					serviceEntityRegisterEntity.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
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
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityRegisterEntity.NODENAME, null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceEntityRegisterEntity);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityRegisterEntity.NODENAME, null);
			lockSEList.add(serviceEntityRegisterEntity);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel = (ServiceEntityRegisterEntityUIModel) serviceEntityRegisterEntityManager
					.genUIModelFromUIModelExtension(
							ServiceEntityRegisterEntityUIModel.class,
							serviceEntityRegisterEntityServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceEntityRegisterEntity, logonActionController.getLogonInfo(),
							null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceEntityRegisterEntityUIModel);
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

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
