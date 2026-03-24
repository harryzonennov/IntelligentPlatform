package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.company.IntelligentPlatform.common.dto.ServiceDocConfigureParaGroupServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;

@Scope("session")
@Controller(value = "serviceDocConfigureParaGroupEditorController")
@RequestMapping(value = "/serviceDocConfigureParaGroup")
public class ServiceDocConfigureParaGroupEditorController extends
		SEEditorController {

	@Autowired
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
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected ServiceDocConfigureParaGroupServiceUIModelExtension serviceDocConfigureParaGroupServiceUIModelExtension;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ServiceDocConfigureParaGroupUIModel.class.getResource("")
				.getPath();
		String resFileName = ServiceDocConfigureParaGroup.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected void saveInternal(
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = serviceDocConfigureParaGroupUIModel.getUuid();
		ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) getServiceEntityNodeFromBuffer(
				ServiceDocConfigureParaGroup.NODENAME, baseUUID);
		serviceDocConfigureManager.convUIToServiceDocConfigureParaGroup(
				serviceDocConfigureParaGroupUIModel,
				serviceDocConfigureParaGroup);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, serviceDocConfigureManager, logonUser, organization);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serviceDocConfigureManager);
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
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = serviceDocConfigureParaGroup.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceDocConfigureParaGroup);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceDocConfigureParaGroup.getName(),
					serviceDocConfigureParaGroup.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "loadParaGroup", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadParaGroup(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String uuid = request.getUuid();
			ServiceDocConfigureParaGroup parentParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			ServiceDocConfigureParaGroupUIModel parentParaGroupUIModel = new ServiceDocConfigureParaGroupUIModel();
			serviceDocConfigureManager.convServiceDocConfigureParaGroupToUI(
					parentParaGroup, parentParaGroupUIModel);
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(parentParaGroupUIModel);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "chooseParentGroup", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseParentGroup(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			ServiceDocConfigureParaGroup parentParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			ServiceDocConfigureParaGroup homeParaGroup = (ServiceDocConfigureParaGroup) getServiceEntityNodeFromBuffer(
					ServiceDocConfigureParaGroup.NODENAME, baseUUID);
			int targetLayer = homeParaGroup.getLayer();
			// avoid the assign to the group itself
			if (!uuid.equals(baseUUID)) {
				targetLayer = parentParaGroup.getLayer() + 1;
			}
			homeParaGroup.setLayer(targetLayer);
			ServiceDocConfigureParaGroupUIModel parentParaGroupUIModel = new ServiceDocConfigureParaGroupUIModel();
			serviceDocConfigureManager.convServiceDocConfigureParaGroupToUI(
					parentParaGroup, parentParaGroupUIModel);
			parentParaGroupUIModel.setTargetLayer(targetLayer);
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(parentParaGroupUIModel);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
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
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel = (ServiceDocConfigureParaGroupUIModel) JSONObject
					.toBean(jsonObject,
							ServiceDocConfigureParaGroupUIModel.class, classMap);
			List<ServiceEntityNode> rawSeNodeList = serviceDocConfigureManager
					.genSeNodeListInExtensionUnion(
							serviceDocConfigureParaGroupServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ServiceDocConfigureParaGroup.class,
							serviceDocConfigureParaGroupUIModel);
			serviceDocConfigureManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(
							serviceDocConfigureParaGroupUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			serviceDocConfigureParaGroupUIModel = (ServiceDocConfigureParaGroupUIModel) serviceDocConfigureManager
					.genUIModelFromUIModelExtension(
							ServiceDocConfigureParaGroupUIModel.class,
							serviceDocConfigureParaGroupServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceDocConfigureParaGroup, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaGroupUIModel);
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
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaGroup);
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
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(serviceDocConfigureParaGroup);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel = (ServiceDocConfigureParaGroupUIModel) serviceDocConfigureManager
					.genUIModelFromUIModelExtension(
							ServiceDocConfigureParaGroupUIModel.class,
							serviceDocConfigureParaGroupServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceDocConfigureParaGroup, logonActionController.getLogonInfo(),
							null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaGroupUIModel);
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
	
	/**
	 * [Service Entrance] for New Para group inside a serivice doc configure
	 * 
	 * @param uuid
	 * @return json request
	 */
	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody ServiceDocConfigureParaGroupInitModel request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME, request.getClient(),
							null);
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
					.newEntityNode(serviceDocConfigure,
							ServiceDocConfigureParaGroup.NODENAME);
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel = new ServiceDocConfigureParaGroupUIModel();
			serviceDocConfigureManager.convServiceDocConfigureParaGroupToUI(
					serviceDocConfigureParaGroup,
					serviceDocConfigureParaGroupUIModel);
			serviceDocConfigureManager.convConfigureToParaGroupUI(serviceDocConfigure, serviceDocConfigureParaGroupUIModel);
			if(ServiceEntityStringHelper.checkNullString(request.getParentGroupUUID())){
				ServiceDocConfigureParaGroup parentGroup = (ServiceDocConfigureParaGroup) serviceDocConfigureManager
						.getEntityNodeByKey(request.getParentGroupUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ServiceDocConfigureParaGroup.NODENAME, request.getClient(),
								null);
				serviceDocConfigureManager.convParentServiceDocConfigureParaGroupToUI(parentGroup, serviceDocConfigureParaGroupUIModel);
			}
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaGroupUIModel);
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


}
