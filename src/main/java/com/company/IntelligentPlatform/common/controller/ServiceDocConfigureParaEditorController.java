package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceDocConfigureParaServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaUnion;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderException;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderTemplate;
import com.company.IntelligentPlatform.common.service.SimpleDataProviderFactory;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

@Scope("session")
@Controller(value = "serviceDocConfigureParaEditorController")
@RequestMapping(value = "/serviceDocConfigurePara")
public class ServiceDocConfigureParaEditorController extends SEEditorController {

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
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;

	@Autowired
	protected StandardValueComparatorProxy standardValueComparatorProxy;

	@Autowired
	protected ServiceDocParaConsumerValueModeProxy serviceDocParaConsumerValueModeProxy;

	@Autowired
	protected SimpleDataProviderFactory simpleDataProviderFactory;

	@Autowired
	protected ServiceDocConfigureParaServiceUIModelExtension serviceDocConfigureParaServiceUIModelExtension;

	protected void saveInternal(
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = serviceDocConfigureParaUIModel.getUuid();
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) getServiceEntityNodeFromBuffer(
				ServiceDocConfigurePara.NODENAME, baseUUID);
		serviceDocConfigureManager.convUIToServiceDocConfigurePara(
				serviceDocConfigureParaUIModel, serviceDocConfigurePara);
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
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigurePara);
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
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(serviceDocConfigurePara);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel = (ServiceDocConfigureParaUIModel) serviceDocConfigureManager
					.genUIModelFromUIModelExtension(
							ServiceDocConfigureParaUIModel.class,
							serviceDocConfigureParaServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceDocConfigurePara, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaUIModel);
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

	@RequestMapping(value = "/getParaDirection", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getParaDirection() {
		try {
			Map<Integer, String> paraDirectionMap = serviceDocConfigureManager
					.initParaDirectionMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(paraDirectionMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getFixValueOperator", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFixValueOperator() {
		try {
			Map<Integer, String> fixValueOperatorMap = serviceDocConfigureManager
					.initFixValueOperatorMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(fixValueOperatorMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getDataOffsetDirection", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDataOffsetDirection() {
		try {
			Map<Integer, String> dataOffsetDirectionMap = serviceDocConfigureManager
					.initDataOffsetDirectionMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(dataOffsetDirectionMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getLogicOperator", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLogicOperator() {
		try {
			Map<Integer, String> logicOperatorMap = serviceDocConfigureManager
					.initLogicOperatorMap(logonActionController.getLanguageCode());
			return serviceDocConfigureManager
					.getDefaultSelectMap(logicOperatorMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getDataOffsetUnitHigh", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDataOffsetUnitHigh() {
		try {
			Map<Integer, String> dataOffsetUnitHighMap = serviceDocConfigureManager
					.initDataOffsetUnitHighMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(dataOffsetUnitHighMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}
	

	@RequestMapping(value = "/getInputConsValueMode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInputConsValueMode() {
		try {
			Map<Integer, String> inputConsValueModeMap = serviceDocConfigureManager
					.initInputConsValueModeMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(inputConsValueModeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getOutputConsValueMode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getOutputConsValueMode() {
		try {
			Map<Integer, String> outputConsValueModeMap = serviceDocConfigureManager
					.initOutputConsValueModeMap();
			return serviceDocConfigureManager
					.getDefaultSelectMap(outputConsValueModeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}


	public void setFixValuePageElement(
			ServiceDocConfigParaUnion serviceDocConfigParaUnion,
			ModelAndView mav) {
		ServiceInputFieldUnion serviceInputFieldUnion = new ServiceInputFieldUnion();
		serviceInputFieldUnion
				.setInputType(ServiceInputFieldUnion.INPUTTYPE_INPUT);
		serviceInputFieldUnion.setElementClass("long");
		serviceInputFieldUnion.setElementId("x_serviceDocConfigurePara");
		serviceInputFieldUnion.setSize(20);
		// set information by type
		String typeSimName = serviceDocConfigParaUnion.getFieldTypeClass()
				.getSimpleName();
		if (typeSimName.equals(String.class.getSimpleName())) {
			serviceInputFieldUnion.setPath("fixValue");
		}
		if (typeSimName.equals(Integer.class.getSimpleName())
				|| typeSimName.equals(int.class.getSimpleName())) {
			serviceInputFieldUnion.setPath("fixValueInt");
			serviceInputFieldUnion.setSize(5);
		}
		if (typeSimName.equals(Double.class.getSimpleName())
				|| typeSimName.equals(double.class.getSimpleName())) {
			serviceInputFieldUnion.setPath("fixValueDouble");
			serviceInputFieldUnion.setSize(5);
		}
		if (typeSimName.equals(Date.class.getSimpleName())) {
			serviceInputFieldUnion.setPath("fixValueDate");
		}
		if (serviceDocConfigParaUnion.getDropdownMap() != null) {
			serviceInputFieldUnion
					.setInputType(ServiceInputFieldUnion.INPUTTYPE_SELECT);
			mav.addObject("dropdownMap",
					serviceDocConfigParaUnion.getDropdownMap());
		}
		List<ServiceInputFieldUnion> inputFieldList = new ArrayList<ServiceInputFieldUnion>();
		inputFieldList.add(serviceInputFieldUnion);
		mav.addObject("inputFieldList", inputFieldList);
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
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) serviceDocConfigureManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = serviceDocConfigurePara.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceDocConfigurePara);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceDocConfigurePara.getName(),
					serviceDocConfigurePara.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	public void convComDataValueSettingToUI(
			ServiceSimpleDataProviderTemplate dataProviderTemplate,
			ServiceDocConfigurePara serviceDocConfigurePara,
			ServiceDocComDataValueSettingModel serviceDocComDataValueSettingModel, LogonInfo logonInfo)
			throws ServiceSimpleDataProviderException {
		serviceDocComDataValueSettingModel.setBaseUUID(serviceDocConfigurePara
				.getUuid());
		serviceDocComDataValueSettingModel
				.setDataProviderID(serviceDocConfigurePara
						.getDataSourceProviderID());
		serviceDocComDataValueSettingModel
				.setOffsetValue(serviceDocConfigurePara.getDataOffsetValue());
		serviceDocComDataValueSettingModel
				.setOffsetDirection(serviceDocConfigurePara
						.getDataOffsetDirection());
		serviceDocComDataValueSettingModel
				.setOffsetUnit(serviceDocConfigurePara.getDataOffsetUnit());
		serviceDocComDataValueSettingModel
				.setOffsetUnitMap(dataProviderTemplate.getOffsetUnitDropdown());
		serviceDocComDataValueSettingModel
				.setDataProviderComments(dataProviderTemplate
						.getDataProviderComment());
		serviceDocComDataValueSettingModel
				.setStandardAmount(dataProviderTemplate
						.getStandardDataToString(dataProviderTemplate
								.getStandardData(logonInfo)));
	}

	public void convUIToComDataValueSetting(
			ServiceDocComDataValueSettingModel serviceDocComDataValueSettingModel,
			ServiceDocConfigurePara serviceDocConfigurePara) {
		serviceDocConfigurePara
				.setDataOffsetValue(serviceDocComDataValueSettingModel
						.getOffsetValue());
		serviceDocConfigurePara
				.setDataOffsetDirection(serviceDocComDataValueSettingModel
						.getOffsetDirection());
		serviceDocConfigurePara
				.setDataOffsetUnit(serviceDocComDataValueSettingModel
						.getOffsetUnit());
	}

	public void convToComDataValueSettingHigh(
			ServiceSimpleDataProviderTemplate dataProviderTemplate,
			ServiceDocConfigurePara serviceDocConfigurePara,
			ServiceDocComDataValueSettingModel serviceDocComDataValueSettingModel, LogonInfo logonInfo)
			throws ServiceSimpleDataProviderException {
		serviceDocComDataValueSettingModel.setBaseUUID(serviceDocConfigurePara
				.getUuid());
		serviceDocComDataValueSettingModel
				.setDataProviderID(serviceDocConfigurePara
						.getDataSourceProviderID());
		serviceDocComDataValueSettingModel
				.setOffsetValue(serviceDocConfigurePara
						.getDataOffsetValueHigh());
		serviceDocComDataValueSettingModel
				.setOffsetDirection(serviceDocConfigurePara
						.getDataOffsetDirectionHigh());
		serviceDocComDataValueSettingModel
				.setOffsetUnit(serviceDocConfigurePara.getDataOffsetUnitHigh());
		serviceDocComDataValueSettingModel
				.setOffsetUnitMap(dataProviderTemplate.getOffsetUnitDropdown());
		serviceDocComDataValueSettingModel
				.setDataProviderComments(dataProviderTemplate
						.getDataProviderComment());
		serviceDocComDataValueSettingModel
				.setStandardAmount(dataProviderTemplate
						.getStandardDataToString(dataProviderTemplate
								.getStandardData(logonInfo)));
	}

	public void convUIToComDataValueSettingHigh(
			ServiceDocComDataValueSettingModel serviceDocComDataValueSettingModel,
			ServiceDocConfigurePara serviceDocConfigurePara) {
		serviceDocConfigurePara
				.setDataOffsetValueHigh(serviceDocComDataValueSettingModel
						.getOffsetValue());
		serviceDocConfigurePara
				.setDataOffsetDirectionHigh(serviceDocComDataValueSettingModel
						.getOffsetDirection());
		serviceDocConfigurePara
				.setDataOffsetUnitHigh(serviceDocComDataValueSettingModel
						.getOffsetUnit());
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
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel = (ServiceDocConfigureParaUIModel) JSONObject
					.toBean(jsonObject, ServiceDocConfigureParaUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = serviceDocConfigureManager
					.genSeNodeListInExtensionUnion(
							serviceDocConfigureParaServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ServiceDocConfigurePara.class,
							serviceDocConfigureParaUIModel);
			serviceDocConfigureManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) serviceDocConfigureManager
					.getEntityNodeByKey(
							serviceDocConfigureParaUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			serviceDocConfigureParaUIModel = (ServiceDocConfigureParaUIModel) serviceDocConfigureManager
					.genUIModelFromUIModelExtension(
							ServiceDocConfigureParaUIModel.class,
							serviceDocConfigureParaServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							serviceDocConfigurePara, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaUIModel);
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
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceDocConfigure.NODENAME,
							logonUser.getClient(), null);
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) serviceDocConfigureManager
					.newEntityNode(serviceDocConfigure,
							ServiceDocConfigurePara.NODENAME);
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel = new ServiceDocConfigureParaUIModel();
			serviceDocConfigureManager.convServiceDocConfigureParaToUI(
					serviceDocConfigurePara, serviceDocConfigureParaUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(serviceDocConfigureParaUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
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
