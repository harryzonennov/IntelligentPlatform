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

import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.BootStrapTreeUIModel;
import com.company.IntelligentPlatform.common.dto.IBootStrapTreeModelConstants;
import com.company.IntelligentPlatform.common.dto.ServiceDocConfigureServiceUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocConfigureServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BootStrapTreeModelHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceSelect2Model;
import com.company.IntelligentPlatform.common.service.ServiceSelect2ModelProxy;
import com.company.IntelligentPlatform.common.service.StandardErrorTypeProxy;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocActiveMessageItem;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaFactory;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaUnion;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;

@Scope("session")
@Controller(value = "serviceDocConfigureEditorController")
@RequestMapping(value = "/serviceDocConfigure")
public class ServiceDocConfigureEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;

	@Autowired
	protected ServiceDocConfigParaFactory serviceDocConfigParaFactory;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected StandardErrorTypeProxy standardErrorTypeProxy;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;
	
	@Autowired
	protected StandardValueComparatorProxy standardValueComparatorProxy;

	@Autowired
	protected ServiceDocConfigureServiceUIModelExtension serviceDocConfigureServiceUIModelExtension;

	@RequestMapping(value = "/loadServiceResource", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadServiceResource() {
		try {
			Map<String, String> serviceResourceMap = serviceDocConfigParaFactory
					.generateServiceResourceMap();
			List<ServiceSelect2Model> resultList = ServiceSelect2ModelProxy
					.convertToSelec2JSONList(serviceResourceMap);
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getSwitchFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchFlagMap() {
		try {
			Map<Integer, String> switchFlagMap = standardSwitchProxy
					.getSimpleSwitchMap();
			List<ServiceSelect2Model> resultList = ServiceSelect2ModelProxy
					.convertToSelec2JSONList(switchFlagMap);
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getSearchSwitchFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSearchSwitchFlagMap() {
		try {
			Map<Integer, String> switchFlagMap = standardSwitchProxy
					.getSimpleSwitchMap();
			List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
			ServiceSelect2Model zeroSelectModel = new ServiceSelect2Model();
			zeroSelectModel.setId("0");
			resultList.add(zeroSelectModel);
			resultList.addAll(ServiceSelect2ModelProxy
					.convertToSelec2JSONList(switchFlagMap));
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("serviceDocConfigureParaGroupUIModelList",
				ServiceDocConfigureParaGroupUIModel.class);
		ServiceDocConfigureServiceUIModel serviceDocConfigureServiceUIModel = (ServiceDocConfigureServiceUIModel) JSONObject
				.toBean(jsonObject, ServiceDocConfigureServiceUIModel.class,
						classMap);
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
			ServiceDocConfigureServiceModel serviceDocConfigureServiceModel = (ServiceDocConfigureServiceModel) serviceDocConfigureManager
					.genServiceModuleFromServiceUIModel(
							ServiceDocConfigureServiceModel.class,
							ServiceDocConfigureServiceUIModel.class,
							serviceDocConfigureServiceUIModel,
							serviceDocConfigureServiceUIModelExtension);
			serviceDocConfigureManager.updateServiceModuleWithDelete(
					ServiceDocConfigureServiceModel.class,
					serviceDocConfigureServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(serviceDocConfigureServiceModel
							.getServiceDocConfigure().getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME,
							logonUser.getClient(), null);
			serviceDocConfigureServiceModel = (ServiceDocConfigureServiceModel) serviceDocConfigureManager
					.loadServiceModule(ServiceDocConfigureServiceModel.class,
							serviceDocConfigure);
			serviceDocConfigureServiceUIModel = (ServiceDocConfigureServiceUIModel) serviceDocConfigureManager
					.genServiceUIModuleFromServiceModel(
							ServiceDocConfigureServiceUIModel.class,
							ServiceDocConfigureServiceModel.class,
							serviceDocConfigureServiceModel,
							serviceDocConfigureServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureServiceUIModel);
		} catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException |
                 ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
    }

	@RequestMapping(value = "/saveModuleService2", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService2(
			@RequestBody ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		String baseUUID = serviceDocConfigureUIModel.getUuid();
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
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME,
							logonUser.getClient(), null);
			if (serviceDocConfigure != null) {
				serviceDocConfigureManager.convUIToServiceDocConfigure(
						serviceDocConfigureUIModel, serviceDocConfigure);
				serviceDocConfigureManager.updateSENode(serviceDocConfigure,
						logonUser.getUuid(), organizationUUID);
			} else {
				serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
						.newRootEntityNode(logonUser.getClient());
				serviceDocConfigureManager.convUIToServiceDocConfigure(
						serviceDocConfigureUIModel, serviceDocConfigure);
				serviceDocConfigureManager.insertSENode(serviceDocConfigure,
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureUIModel);
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

	protected void saveInternal(
			ServiceDocConfigureUIModel serviceDocConfigureUIModel, String client)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = serviceDocConfigureUIModel.getUuid();
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) getServiceEntityNodeFromBuffer(
				ServiceDocConfigure.NODENAME, baseUUID);
		serviceDocConfigureManager.convUIToServiceDocConfigure(
				serviceDocConfigureUIModel, serviceDocConfigure);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, serviceDocConfigureManager, logonUser, organization);
	}



	@RequestMapping(value = "/preSetSwitchOn", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preSetSwitchOn(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String uuid = request.getUuid();
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) getServiceEntityNodeFromBuffer(
					ServiceDocConfigure.NODENAME, uuid);
			List<ServiceEntityNode> serviceDocConfigureParaList = serviceDocConfigureManager
					.getEntityNodeListByKey(uuid,
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			List<ServiceDocActiveMessageItem> messageItemList = serviceDocConfigureManager
					.checkSetSwitchOn(serviceDocConfigure,
							serviceDocConfigureParaList);
			ServiceDocActiveMessageHeadUIModel serviceDocActiveMessageHeadUIModel =
					generateMessageHeadUIModel(messageItemList, logonActionController.getSerialLogonInfo());
			serviceDocActiveMessageHeadUIModel.setBaseUUID(uuid);
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(serviceDocActiveMessageHeadUIModel);
			return responseData;
		} catch (LogonInfoException | ServiceDocConfigureException | AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	public ServiceDocActiveMessageHeadUIModel generateMessageHeadUIModel(
			List<ServiceDocActiveMessageItem> messageItemList, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocActiveMessageHeadUIModel headUIModel = new ServiceDocActiveMessageHeadUIModel();
		Map<Integer, String> errorTypeMap = standardErrorTypeProxy
				.getErrorTypeMap(serialLogonInfo.getLanguageCode());
		if (!ServiceCollectionsHelper.checkNullList(messageItemList)) {
			List<ServiceDocActiveMessageItemUIModel> itemList = new ArrayList<>();
			for (ServiceDocActiveMessageItem messageItem : messageItemList) {
				ServiceDocActiveMessageItemUIModel itemUIModel = generateMessageItemUIModel(messageItem, serialLogonInfo);
				itemList.add(itemUIModel);
				if (itemUIModel.getErrorType() == StandardErrorTypeProxy.MESSAGE_TYPE_WARNING
						&& headUIModel.getErrorType() == StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION) {
					headUIModel
							.setErrorType(StandardErrorTypeProxy.MESSAGE_TYPE_WARNING);

					headUIModel.setErrorTypeValue(errorTypeMap
							.get(StandardErrorTypeProxy.MESSAGE_TYPE_WARNING));
				}
				if (itemUIModel.getErrorType() == StandardErrorTypeProxy.MESSAGE_TYPE_ERROR) {
					headUIModel
							.setErrorType(StandardErrorTypeProxy.MESSAGE_TYPE_ERROR);
					headUIModel.setErrorTypeValue(errorTypeMap
							.get(StandardErrorTypeProxy.MESSAGE_TYPE_ERROR));
				}
			}
			headUIModel.setItemList(itemList);
			return headUIModel;
		} else {
			headUIModel
					.setErrorType(StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION);
			headUIModel.setErrorTypeValue(errorTypeMap
					.get(StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION));
			return headUIModel;
		}
	}

	protected ServiceDocActiveMessageItemUIModel generateMessageItemUIModel(
			ServiceDocActiveMessageItem serviceDocActiveMessageItem, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocActiveMessageItemUIModel itemUIModel = new ServiceDocActiveMessageItemUIModel();
		itemUIModel.setErrorType(serviceDocActiveMessageItem.getErrorType());
		itemUIModel.setErrorTypeValue(standardErrorTypeProxy
				.getErrorTypeValue(serviceDocActiveMessageItem.getErrorType(), serialLogonInfo.getLanguageCode()));
		itemUIModel.setMessageContent(serviceDocActiveMessageItem
				.getMessageContent());
		return itemUIModel;
	}

	public String generateGroupJSONContent(
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupList) {
		if (ServiceCollectionsHelper
				.checkNullList(serviceDocConfigureParaGroupList)) {
			return null;
		}
		if (serviceDocConfigureParaGroupList.size() == 1) {
			return null;
		}
		List<BootStrapTreeUIModel> resultList = new ArrayList<>();
		ServiceDocConfigureParaGroupUIModel defaultGroupUIModel = filterOutDefaultInputGroup(serviceDocConfigureParaGroupList);
		if (defaultGroupUIModel != null) {
			List<BootStrapTreeUIModel> defaultInputParaTreeNodeList = generateComInputGroupTreeNodeList(
					defaultGroupUIModel, null, 1,
					defaultGroupUIModel.getUuid(),
					defaultGroupUIModel.getUuid());
			if (defaultInputParaTreeNodeList != null
					&& defaultInputParaTreeNodeList.size() > 0) {
				resultList.addAll(defaultInputParaTreeNodeList);
			}
		}
		for (ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel : serviceDocConfigureParaGroupList) {
			// Generate default group input parameter list
			if (ServiceDocConfigureParaGroup.GROUPID_DEFAULT
					.equals(serviceDocConfigureParaGroupUIModel.getGroupId())) {
				// Skip the default group
				continue;
			}
			List<BootStrapTreeUIModel> defaultInputParaTreeNodeList = generateComInputGroupTreeNodeList(
					serviceDocConfigureParaGroupUIModel,
					null,
					serviceDocConfigureParaGroupUIModel.getLayer(),
					serviceDocConfigureParaGroupUIModel.getRefParentGroupUUID(),
					serviceDocConfigureParaGroupUIModel.getRefParentGroupUUID());
			if (defaultInputParaTreeNodeList != null
					&& defaultInputParaTreeNodeList.size() > 0) {
				resultList.addAll(defaultInputParaTreeNodeList);
			}
		}
		BootStrapTreeUIModel treeModel = BootStrapTreeModelHelper
				.generateTreeComModel(resultList);
		String groupArray = ServiceJSONParser.genDefOKJSONObject(treeModel);
		return groupArray;
	}


	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody ServiceDocInitConfigureModel serviceDocInitConfigureModel) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceDocConfigure serviceDocConfigure = serviceDocConfigureManager
					.newServiceDocConfigure(serviceDocInitConfigureModel,
							logonUser.getClient());
			// Default create input para group			
			Map<Integer, String> switchMap = standardSwitchProxy
					.getSimpleSwitchMap();
			ServiceDocConfigureParaGroup defConfigureParaGroup = serviceDocConfigureManager
					.initServiceDocConfigureParaGroup(serviceDocConfigure);
			List<ServiceEntityNode> rawServiceDocParaGroupList = new ArrayList<>();
			rawServiceDocParaGroupList.add(defConfigureParaGroup);
			List<ServiceEntityNode> rawServiceDocInputParaList = serviceDocConfigureManager
					.newInitInputParaList(
							serviceDocInitConfigureModel.getResourceID(),
							serviceDocConfigure,
							defConfigureParaGroup.getUuid());
			List<ServiceDocConfigureParaUIModel> inputParaUnionList = serviceDocConfigureManager
					.convServiceDocConfigureParaUIList(
							rawServiceDocInputParaList,
							ServiceDocConfigParaUnion.DIRECT_INPUT,
							serviceDocConfigure.getResourceID());
			List<ServiceEntityNode> rawServiceDocOutputParaList = serviceDocConfigureManager
					.newInitOutputParaList(
							serviceDocInitConfigureModel.getResourceID(),
							serviceDocConfigure,
							defConfigureParaGroup.getUuid());
			List<ServiceDocConfigureParaUIModel> outputParaUnionList = serviceDocConfigureManager
					.convServiceDocConfigureParaUIList(
							rawServiceDocOutputParaList,
							ServiceDocConfigParaUnion.DIRECT_OUTPUT,
							serviceDocConfigure.getResourceID());
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupList = serviceDocConfigureManager
					.convServiceDocConfigureParaGroupUIList(rawServiceDocParaGroupList);
			Map<String, String> preWarnMap = null;
			String inputParaArray = this.generateInputParaJSONContent(
					inputParaUnionList, serviceDocConfigureParaGroupList,
					preWarnMap);

			String outputParaArray = this.generateOutputParaJSONContent(
					outputParaUnionList, serviceDocConfigureParaGroupList,
					preWarnMap);
			ServiceDocConfigureUIModel serviceDocConfigureUIModel = new ServiceDocConfigureUIModel();
			Map<String, String> serviceResourceMap = serviceDocConfigParaFactory
					.generateServiceResourceMap();
			serviceDocConfigureManager.convServiceDocConfigureToUI(
					serviceDocConfigure, serviceDocConfigureUIModel,
					serviceResourceMap, switchMap);
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(
							serviceDocConfigure.getConsumerUnionUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			serviceDocConfigureManager.convServiceDocConsumerUnionToUI(
					serviceDocConsumerUnion, serviceDocConfigureUIModel);
			ServiceDocConsumerUnion inputUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(
							serviceDocConfigure.getInputUnionUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConsumerUnion.NODENAME,
							logonUser.getClient(), null);
			serviceDocConfigureUIModel.setInputParaArray(inputParaArray);
			serviceDocConfigureUIModel.setOutputParaArray(outputParaArray);
			serviceDocConfigureManager.convInputConsumerUnionToUI(inputUnion,
					serviceDocConfigureUIModel);
			return outputJSONResponse(serviceDocConfigureUIModel,
					serviceDocConfigureParaGroupList);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceDocConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	public String outputJSONResponse(
			ServiceDocConfigureUIModel serviceDocConfigureUIModel,
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupList) {
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("serviceDocConfigure", serviceDocConfigureUIModel);
		resultJsonObject.put("paraGroupList", serviceDocConfigureParaGroupList);
		return ServiceJSONParser.genDefOKJSONObject(resultJsonObject);
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
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME, null);
			String baseUUID = serviceDocConfigure.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceDocConfigure);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceDocConfigure.getName(), serviceDocConfigure.getId(),
					baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
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
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(serviceDocConfigure);
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
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(serviceDocConfigure);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			ServiceDocConfigureServiceModel serviceDocConfigureServiceModel = (ServiceDocConfigureServiceModel) serviceDocConfigureManager
					.loadServiceModule(ServiceDocConfigureServiceModel.class,
							serviceDocConfigure);			
			List<ServiceDocConfigureParaUIModel> inputParaUIModelList = serviceDocConfigureManager
					.convServiceDocConfigureParaUIList(
							serviceDocConfigureServiceModel.getServiceDocConfigureParaList(),
							ServiceDocConfigParaUnion.DIRECT_INPUT,
							serviceDocConfigure.getResourceID());
			List<ServiceDocConfigureParaUIModel> outputParaUIModelList = serviceDocConfigureManager
					.convServiceDocConfigureParaUIList(
							serviceDocConfigureServiceModel.getServiceDocConfigureParaList(),
							ServiceDocConfigParaUnion.DIRECT_OUTPUT,
							serviceDocConfigure.getResourceID());
			ServiceDocConfigureServiceUIModel serviceDocConfigureServiceUIModel = (ServiceDocConfigureServiceUIModel) serviceDocConfigureManager
					.genServiceUIModuleFromServiceModel(
							ServiceDocConfigureServiceUIModel.class,
							ServiceDocConfigureServiceModel.class,
							serviceDocConfigureServiceModel, 
							serviceDocConfigureServiceUIModelExtension, logonActionController.getLogonInfo());
			serviceDocConfigureServiceUIModel.setInputParaUIModelList(inputParaUIModelList);
			serviceDocConfigureServiceUIModel.setOutputParaUIModelList(outputParaUIModelList);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureServiceUIModel);
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
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceDocConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}



	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	/**
	 * Generate bootstrap-styple tree node content for all input para list and
	 * group
	 * 
	 * @param inputParaUnionList
	 * @param serviceDocConfigParaGroupList
	 * @param preWarnMap
	 * @return
	 */
	public String generateInputParaJSONContent(
			List<ServiceDocConfigureParaUIModel> inputParaUnionList,
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigParaGroupList,
			Map<String, String> preWarnMap) {
		if (serviceDocConfigParaGroupList == null
				|| serviceDocConfigParaGroupList.size() == 0) {
			return null;
		}
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		ServiceDocConfigureParaGroupUIModel defaultGroupUIModel = filterOutDefaultInputGroup(serviceDocConfigParaGroupList);
		if (defaultGroupUIModel != null) {
			List<ServiceDocConfigureParaUIModel> subInputParaList = filterParaListByGroup(
					inputParaUnionList, defaultGroupUIModel.getUuid());
			List<BootStrapTreeUIModel> defaultInputParaTreeNodeList = generateComInputGroupTreeNodeList(
					defaultGroupUIModel, subInputParaList, 1,
					defaultGroupUIModel.getUuid(),
					defaultGroupUIModel.getUuid());
			if (defaultInputParaTreeNodeList != null
					&& defaultInputParaTreeNodeList.size() > 0) {
				resultList.addAll(defaultInputParaTreeNodeList);
			}
		}
		for (ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel : serviceDocConfigParaGroupList) {
			// Generate default group input parameter list
			if (ServiceDocConfigureParaGroup.GROUPID_DEFAULT
					.equals(serviceDocConfigureParaGroupUIModel.getGroupId())) {
				// Skip the default group
				continue;
			}
			List<ServiceDocConfigureParaUIModel> subInputParaList = filterParaListByGroup(
					inputParaUnionList,
					serviceDocConfigureParaGroupUIModel.getUuid());
			List<BootStrapTreeUIModel> defaultInputParaTreeNodeList = generateComInputGroupTreeNodeList(
					serviceDocConfigureParaGroupUIModel,
					subInputParaList,
					serviceDocConfigureParaGroupUIModel.getLayer(),
					serviceDocConfigureParaGroupUIModel.getRefParentGroupUUID(),
					serviceDocConfigureParaGroupUIModel.getRefParentGroupUUID());
			if (defaultInputParaTreeNodeList != null
					&& defaultInputParaTreeNodeList.size() > 0) {
				resultList.addAll(defaultInputParaTreeNodeList);
			}
		}
		BootStrapTreeUIModel treeModel = BootStrapTreeModelHelper
				.generateTreeComModel(resultList);
		String inputParaArray = ServiceJSONParser.genDefOKJSONObject(treeModel);
		return inputParaArray;
	}

	/**
	 * [Internal method] Generate tree node list from group as well as sub input
	 * para list
	 *
	 * @param inputParaList
	 * @return
	 */
	protected List<BootStrapTreeUIModel> generateComInputGroupTreeNodeList(
			ServiceDocConfigureParaGroupUIModel groupUIModel,
			List<ServiceDocConfigureParaUIModel> inputParaList, int layer,
			String parentGroupUUID, String rootGroupUUID) {
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		if (groupUIModel != null) {
			// Add group node
			BootStrapTreeUIModel groupNode = new BootStrapTreeUIModel();
			groupNode.setUuid(groupUIModel.getUuid());
			groupNode.setParentNodeUUID(parentGroupUUID);
			groupNode.setRootNodeUUID(rootGroupUUID);
			groupNode.setLayer(layer);
			String groupName = groupUIModel.getGroupName();
			groupNode.setSpanContent(groupUIModel.getGroupId() + "-"
					+ groupName);
			groupNode.setIconClass(IBootStrapTreeModelConstants.ICON_TH_LIST);
			String editURL = "../serviceDocConfigureParaGroup/loadModuleEdit.html?uuid="
					+ groupUIModel.getUuid();
			groupNode.setEditURL(editURL);
			resultList.add(groupNode);
			// Add sub para node list
			if (inputParaList != null && inputParaList.size() > 0) {
				for (ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel : inputParaList) {
					BootStrapTreeUIModel paraResourceNode = generateInputParaTreeNodeUnion(
							serviceDocConfigureParaUIModel,
							groupUIModel.getLayer(), groupUIModel.getUuid(),
							rootGroupUUID);
					resultList.add(paraResourceNode);
				}
			}
		}
		return resultList;
	}

	/**
	 * [Internal] method generate the bootstrap-style tree node from each input
	 * para ui model
	 * 
	 * @param serviceDocConfigureParaUIModel
	 */
	protected BootStrapTreeUIModel generateInputParaTreeNodeUnion(
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel,
			int groupLayer, String groupUUID, String rootGroupUUID) {
		BootStrapTreeUIModel paraResourceNode = new BootStrapTreeUIModel();
		paraResourceNode.setUuid(serviceDocConfigureParaUIModel.getUuid());
		paraResourceNode.setParentNodeUUID(groupUUID);
		paraResourceNode.setRootNodeUUID(rootGroupUUID);
		paraResourceNode.setLayer(groupLayer + 1);
		// set status and background by switch
		if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
			paraResourceNode.setIconClass(IBootStrapTreeModelConstants.ICON_OK);
			paraResourceNode
					.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_SUCCESS);
		} else {
			paraResourceNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_REMOVE);
			paraResourceNode
					.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
		}
		String resourceFieldTitle = " 源字段 ";
		paraResourceNode.setSpanContent(resourceFieldTitle + ":"
				+ serviceDocConfigureParaUIModel.getResourceFieldName() + "-["
				+ serviceDocConfigureParaUIModel.getResourceFieldTypeLabel()
				+ "] ");
		String editURL = "../serviceDocConfigurePara/loadModuleEdit.html?uuid="
				+ paraResourceNode.getUuid();
		paraResourceNode.setEditURL(editURL);
		paraResourceNode
				.setPostIconClass(IBootStrapTreeModelConstants.ICON_PENCIL);
		// set node for cosumer node
		BootStrapTreeUIModel paraConsumerNode = new BootStrapTreeUIModel();
		if (serviceDocConfigureParaUIModel.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
			// In case pass value from consumer node
			paraConsumerNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_RANDOM);
			String consumerFieldTitle = " 消费输出字段 ";
			String consumerFieldName = " : "
					+ serviceDocConfigureParaUIModel.getConsumerFieldName();
			if (serviceDocConfigureParaUIModel.getConsumerFieldName() == null) {
				consumerFieldName = ServiceEntityStringHelper.EMPTYSTRING;
			}
			paraConsumerNode.setSpanContent(consumerFieldTitle
					+ consumerFieldName);
			// set status and background by switch
			if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_ORGANIZATION);
			} else {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
			}

		} else {
			// In case manual set value
			paraConsumerNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_WRENCH);
			String consumerFieldTitle = "手动设置";
			paraConsumerNode.setSpanContent(consumerFieldTitle);
			// set status and background by switch
			if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_ORGANIZATION);
			} else {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
			}
		}
		paraResourceNode.addPostModelList(paraConsumerNode);
		return paraResourceNode;
	}

	/**
	 * Generate bootstrap-styple tree node content for all output para list and
	 * group
	 *
	 * @param serviceDocConfigParaGroupList
	 * @param preWarnMap
	 * @return
	 */
	public String generateOutputParaJSONContent(
			List<ServiceDocConfigureParaUIModel> outputParaUnionList,
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigParaGroupList,
			Map<String, String> preWarnMap) {
		if (outputParaUnionList == null || outputParaUnionList.size() == 0) {
			return null;
		}
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		ServiceDocConfigureParaGroupUIModel defaultGroupUIModel = filterOutDefaultInputGroup(serviceDocConfigParaGroupList);
		if (defaultGroupUIModel != null) {
			List<BootStrapTreeUIModel> defaultOutputParaTreeNodeList = generateComOutputGroupTreeNodeList(
					defaultGroupUIModel, outputParaUnionList, 1,
					defaultGroupUIModel.getUuid(),
					defaultGroupUIModel.getUuid());
			if (defaultOutputParaTreeNodeList != null
					&& defaultOutputParaTreeNodeList.size() > 0) {
				resultList.addAll(defaultOutputParaTreeNodeList);
			}
		}
		BootStrapTreeUIModel treeModel = BootStrapTreeModelHelper
				.generateTreeComModel(resultList);
		String outputArray = ServiceJSONParser.genDefOKJSONObject(treeModel);
		return outputArray;
	}

	protected List<BootStrapTreeUIModel> generateComOutputGroupTreeNodeList(
			ServiceDocConfigureParaGroupUIModel groupUIModel,
			List<ServiceDocConfigureParaUIModel> outputParaList, int layer,
			String parentGroupUUID, String rootGroupUUID) {
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		if (groupUIModel != null) {
			// Add group node
			BootStrapTreeUIModel groupNode = new BootStrapTreeUIModel();
			groupNode.setUuid(groupUIModel.getUuid());
			groupNode.setParentNodeUUID(parentGroupUUID);
			groupNode.setRootNodeUUID(rootGroupUUID);
			groupNode.setLayer(layer);
			String groupName = groupUIModel.getGroupName();
			groupNode.setSpanContent(groupUIModel.getGroupId() + "-"
					+ groupName);
			groupNode.setIconClass(IBootStrapTreeModelConstants.ICON_TH_LIST);
			String editURL = "../serviceDocConfigureParaGroup/loadModuleEdit.html?uuid="
					+ groupUIModel.getUuid();
			groupNode.setEditURL(editURL);
			resultList.add(groupNode);
			// Add sub para node list
			if (outputParaList != null && outputParaList.size() > 0) {
				for (ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel : outputParaList) {
					BootStrapTreeUIModel paraResourceNode = generateOutputParaTreeNodeUnion(
							serviceDocConfigureParaUIModel,
							groupUIModel.getLayer(), groupUIModel.getUuid(),
							rootGroupUUID);
					resultList.add(paraResourceNode);
				}
			}
		}
		return resultList;
	}

	/**
	 * [Internal] method generate the bootstrap-style tree node from each input
	 * para ui model
	 * 
	 * @param serviceDocConfigureParaUIModel
	 */
	protected BootStrapTreeUIModel generateOutputParaTreeNodeUnion(
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel,
			int layer, String groupUUID, String rootGroupUUID) {
		BootStrapTreeUIModel paraResourceNode = new BootStrapTreeUIModel();
		paraResourceNode.setUuid(serviceDocConfigureParaUIModel.getUuid());
		paraResourceNode.setParentNodeUUID(groupUUID);
		paraResourceNode.setRootNodeUUID(rootGroupUUID);
		paraResourceNode.setLayer(layer + 1);
		// set status and background by switch
		if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
			paraResourceNode.setIconClass(IBootStrapTreeModelConstants.ICON_OK);
			paraResourceNode
					.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_SUCCESS);
		} else {
			paraResourceNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_REMOVE);
			paraResourceNode
					.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
		}
		String resourceFieldTitle = " 源字段 ";
		paraResourceNode.setSpanContent(resourceFieldTitle + ":"
				+ serviceDocConfigureParaUIModel.getResourceFieldName() + "-["
				+ serviceDocConfigureParaUIModel.getResourceFieldTypeLabel()
				+ "] ");
		String editURL = "../serviceDocConfigurePara/loadModuleEdit.html?uuid="
				+ paraResourceNode.getUuid();
		paraResourceNode.setEditURL(editURL);
		paraResourceNode
				.setPostIconClass(IBootStrapTreeModelConstants.ICON_PENCIL);
		// set node for cosumer node
		BootStrapTreeUIModel paraConsumerNode = new BootStrapTreeUIModel();
		if (serviceDocConfigureParaUIModel.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
			// In case pass value from consumer node
			paraConsumerNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_RANDOM);
			String consumerFieldTitle = " 消费回调字段 ";
			String consumerFieldName = " : "
					+ serviceDocConfigureParaUIModel.getConsumerFieldName();
			if (serviceDocConfigureParaUIModel.getConsumerFieldName() == null) {
				consumerFieldName = ServiceEntityStringHelper.EMPTYSTRING;
			}
			paraConsumerNode.setSpanContent(consumerFieldTitle
					+ consumerFieldName + " ");
			// set status and background by switch
			if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_ORGANIZATION);
			} else {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
			}

		} else {
			// In case manual set value
			paraConsumerNode
					.setIconClass(IBootStrapTreeModelConstants.ICON_WRENCH);
			String consumerFieldTitle = "手动输出";
			paraConsumerNode.setSpanContent(consumerFieldTitle);
			// set status and background by switch
			if (serviceDocConfigureParaUIModel.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_ORGANIZATION);
			} else {
				paraConsumerNode
						.setSpanSubClass(IBootStrapTreeModelConstants.BADGE_LIGHTGREY);
			}
		}
		paraResourceNode.addPostModelList(paraConsumerNode);
		return paraResourceNode;
	}

	/**
	 * Filter out the default group
	 * 
	 * @param serviceDocConfigParaGroupList
	 * @return
	 */
	public ServiceDocConfigureParaGroupUIModel filterOutDefaultInputGroup(
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigParaGroupList) {
		if (serviceDocConfigParaGroupList == null
				|| serviceDocConfigParaGroupList.size() == 0) {
			return null;
		}
		for (ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel : serviceDocConfigParaGroupList) {
			if (ServiceDocConfigureParaGroup.GROUPID_DEFAULT
					.equals(serviceDocConfigureParaGroupUIModel.getGroupId())) {
				return serviceDocConfigureParaGroupUIModel;
			}
		}
		return null;
	}

	/**
	 * Filter out the default group
	 *
	 * @return
	 */
	public List<ServiceDocConfigureParaUIModel> filterParaListByGroup(
			List<ServiceDocConfigureParaUIModel> paraUnionList, String groupUUID) {
		if (paraUnionList == null || paraUnionList.size() == 0) {
			return null;
		}
		List<ServiceDocConfigureParaUIModel> resultGroupList = new ArrayList<ServiceDocConfigureParaUIModel>();
		for (ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel : paraUnionList) {
			if (groupUUID.equals(serviceDocConfigureParaUIModel
					.getRefGroupUUID())) {
				resultGroupList.add(serviceDocConfigureParaUIModel);
			}
		}
		return resultGroupList;
	}

	@RequestMapping(value = "/getSwitchFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchFlag() {
		try {
			Map<Integer, String> switchFlagMap = serviceDocConfigureManager
					.initSwitchFlagMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(switchFlagMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}
	

	@RequestMapping(value = "/getValueOperator", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getValueOperator() {
		try {
			Map<Integer, String> fixValueOperatorMap = standardValueComparatorProxy
					.getValueComparatorMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(fixValueOperatorMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}
	
	

}
