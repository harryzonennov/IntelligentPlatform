package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
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

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.SerExtendUIControlSetUIModel;
import com.company.IntelligentPlatform.common.dto.SerExtendUIControlSetServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.ServiceUIControlExtensionUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.SerExtendUIControlSetManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;

@Scope("session")
@Controller(value = "serExtendUIControlSetEditorController")
@RequestMapping(value = "/serExtendUIControlSet")
public class SerExtendUIControlSetEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected SerExtendUIControlSetServiceUIModelExtension serExtendUIControlSetServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendUIControlSetManager serExtendUIControlSetManager;

//	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
//		return new ServiceBasicUtilityController.ServiceUIModelRequest(
//				SerExtendUIControlSetServiceUIModel.class,
//				SerExtendUIControlSetServiceModel.class, AOID_RESOURCE,
//				SerExtendUIControlSetReference.NODENAME,
//				SerExtendUIControlSetReference.SENAME, serExtendUIControlSetServiceUIModelExtension,
//				materialStockKeepUnitManager
//		);
//	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
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
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) JSONObject
					.toBean(jsonObject, SerExtendUIControlSetUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = serviceExtensionSettingManager
					.genSeNodeListInExtensionUnion(
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							SerExtendUIControlSet.class,
							serExtendUIControlSetUIModel);
			serviceExtensionSettingManager.updateSeNodeListWrapper(
					rawSeNodeList, logonUser.getUuid(), organizationUUID);
			SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(serExtendUIControlSetUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);
			serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							SerExtendUIControlSetUIModel.class,
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serExtendUIControlSet, logonActionController
									.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
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

	@RequestMapping(value = "/initUIControlFieldService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String initUIControlFieldService(
			@RequestBody ServiceUIControlExtensionUIModel serviceUIControlExtensionUIModel) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = serExtendUIControlSetManager
					.initSerExtendUIControl(serviceUIControlExtensionUIModel,
							logonUser.getClient());
			// update timely
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newUIControlFieldService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newUIControlFieldService(
			@RequestBody SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
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
			SerExtendUIControlSet serExtendUIControlSet = serExtendUIControlSetManager
					.newSerExtendUIControlFieldWrapper(
							serExtendUIControlSetUIModel,
							logonUser.getClient(), logonActionController.getLogonInfo(), logonUser.getUuid(),
							organizationUUID);
			serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(serExtendUIControlSet.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);

			serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							SerExtendUIControlSetUIModel.class,
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serExtendUIControlSet, logonActionController
									.getLogonInfo(), null);
			// update timely
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newUIControlService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newUIControlService(
			@RequestBody SerUIControlGenRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SerExtendUIControlSet serExtendUIControlSet = serExtendUIControlSetManager
					.newSerExtendUIControl(request.getBaseUUID(),
							request.getRefFieldUUID(), logonUser.getClient());
			serExtendUIControlSet.setDisplayIndex(request.getDisplayIndex());
			serExtendUIControlSet.setRefFieldUUID(request.getRefFieldUUID());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			serviceExtensionSettingManager.updateSENode(serExtendUIControlSet,
					logonUser.getUuid(), organizationUUID);
			serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(serExtendUIControlSet.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);

			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							SerExtendUIControlSetUIModel.class,
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serExtendUIControlSet, logonActionController
									.getLogonInfo(), null);
			// update timely
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/removeUIControlService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String removeUIControlService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);
			if (serExtendUIControlSet != null) {
				serviceExtensionSettingManager.deleteSENode(
						serExtendUIControlSet, logonUser.getUuid(), null);
			}
			return ServiceJSONParser.genDeleteOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newFieldUIControlService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newFieldUIControlService(
			@RequestBody SerUIControlGenRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}

			SerExtendUIControlSet serExtendUIControlSet = serExtendUIControlSetManager
					.newSerExtendUIControl(request.getBaseUUID(),
							request.getRefFieldUUID(), logonUser.getClient());
			serExtendUIControlSet.setDisplayIndex(request.getDisplayIndex());
			serExtendUIControlSet.setRefFieldUUID(request.getRefFieldUUID());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			serviceExtensionSettingManager.updateSENode(serExtendUIControlSet,
					logonUser.getUuid(), organizationUUID);
			serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(serExtendUIControlSet.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);

			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							SerExtendUIControlSetUIModel.class,
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serExtendUIControlSet, logonActionController
									.getLogonInfo(), null);
			// update timely
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
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
			SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(serExtendUIControlSet);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = (SerExtendUIControlSetUIModel) serviceExtensionSettingManager
					.genUIModelFromUIModelExtension(
							SerExtendUIControlSetUIModel.class,
							serExtendUIControlSetServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serExtendUIControlSet, logonActionController
									.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serExtendUIControlSetUIModel);
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

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SerExtendUIControlSet.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = new ArrayList<>();
			if(serExtendUIControlSet == null){
				pageHeaderModelList = serExtendUIControlSetManager
						.getPageHeaderModelList(request.getBaseUUID(),
								logonUser.getClient());
			}else{
				pageHeaderModelList = serExtendUIControlSetManager
						.getPageHeaderModelList(serExtendUIControlSet,
								logonUser.getClient());
			}
			String result = ServiceJSONParser
					.genDefOKJSONArray(pageHeaderModelList);
			return result;
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serviceExtensionSettingManager);
	}

	@RequestMapping(value = "/getInputControlTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInputControlTypeMap(String baseUUID) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Map<String, String> inputControlTypeMap = serExtendUIControlSetManager
					.initInputControlTypeMap(logonActionController
							.getLogonInfo().getLanguageCode());
			return serviceExtensionSettingManager.getDefaultStrSelectMap(
					inputControlTypeMap, false);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getExtendControlTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getExtendControlTypeMap(String baseUUID) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Map<String, String> inputControlTypeMap = serExtendUIControlSetManager
					.initExtendControlTypeMap(logonActionController
							.getLogonInfo().getLanguageCode());
			return serviceExtensionSettingManager.getDefaultStrSelectMap(
					inputControlTypeMap, false);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getSwitchMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchMap(String baseUUID) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Map<Integer, String> switchMap = serExtendUIControlSetManager
					.initSwitchFlagMap(logonActionController.getLanguageCode());
			return serviceExtensionSettingManager.getDefaultSelectMap(
					switchMap, false);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
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
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = serviceExtendFieldSetting.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceExtendFieldSetting);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceExtendFieldSetting.getName(),
					serviceExtendFieldSetting.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
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
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceExtendFieldSetting);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
