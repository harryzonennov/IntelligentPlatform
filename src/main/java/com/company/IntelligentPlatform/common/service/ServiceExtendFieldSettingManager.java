package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendUIControlSetUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceExtendFieldI18nSettingUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceExtendFieldSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceExtendFieldSettingUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceExtendFieldSettingManager {

	public static final String METHOD_ConvParentToFieldSettingUI = "convParentToFieldSettingUI";

	public static final String METHOD_ConvServiceExtendFieldSettingToUI = "convServiceExtendFieldSettingToUI";

	public static final String METHOD_ConvUIToServiceExtendFieldSetting = "convUIToServiceExtendFieldSetting";

	public static final String METHOD_ConvParentToFieldI18nSettingUI = "convParentToFieldI18nSettingUI";

	public static final String METHOD_ConvServiceExtendFieldI18nSettingToUI = "convServiceExtendFieldI18nSettingToUI";

	public static final String METHOD_ConvUIToServiceExtendFieldI18nSetting = "convUIToServiceExtendFieldI18nSetting";

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendUIControlSetManager serExtendUIControlSetManager;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected ServiceDefaultIdGenerateHelper serviceDefaultIdGenerateHelper;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	protected Map<String, Map<String, String>> fieldTypeMapLan;

	protected Map<String, String> storeModelNameMap;

	protected Map<Integer, String> fieldMaxLengthMap;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceExtensionSetting.NODENAME,
						request.getUuid(), ServiceExtendFieldSetting.NODENAME, serviceExtensionSettingManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<ServiceExtensionSetting>) serviceExtensionSetting -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							serviceExtensionSettingManager
									.getPageHeaderModelList(serviceExtensionSetting);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<ServiceExtendFieldSetting>) (serviceExtendFieldSetting, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(serviceExtendFieldSetting.getFieldName());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public Map<Integer, String> initSwitchFlagMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSwitchMap(languageCode);
	}

	public Map<String, String> initFieldTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageStrMapUIModel(languageCode,
				this.fieldTypeMapLan, ServiceExtendFieldSettingUIModel.class,
				"fieldType");
	}

	public Map<Integer, String> initFieldMaxLengthMap()
			throws ServiceEntityInstallationException {
		if (this.fieldMaxLengthMap == null) {
			this.fieldMaxLengthMap = new HashMap<>();
			fieldMaxLengthMap.put(100, 100 + "");
			fieldMaxLengthMap.put(800, 800 + "");
			fieldMaxLengthMap.put(3000, 3000 + "");
		}
		return this.fieldMaxLengthMap;
	}

	public Map<String, String> initStoreModelNameMap()
			throws ServiceEntityInstallationException {
		if (this.storeModelNameMap == null) {
			this.storeModelNameMap = new HashMap<>();
			storeModelNameMap.put(IServiceModelConstants.SerExtendDoubleField,
					IServiceModelConstants.SerExtendDoubleField);
			storeModelNameMap.put(IServiceModelConstants.SerExtendIntField,
					IServiceModelConstants.SerExtendIntField);
			storeModelNameMap.put(IServiceModelConstants.SerExtendStr100Field,
					IServiceModelConstants.SerExtendStr100Field);
			storeModelNameMap.put(IServiceModelConstants.SerExtendStr800Field,
					IServiceModelConstants.SerExtendStr800Field);
			storeModelNameMap.put(IServiceModelConstants.SerExtendStr3000Field,
					IServiceModelConstants.SerExtendStr3000Field);
		}
		return this.storeModelNameMap;
	}

	/**
	 * Logic to assign store model name to field setting model. call this method
	 * before update to persistence
	 * 
	 * @param serviceExtendFieldSetting
	 */
	public void assignStoreModelName(
			ServiceExtendFieldSetting serviceExtendFieldSetting) {
		if (ServiceEntityStringHelper.checkNullString(serviceExtendFieldSetting
				.getStoreModelName())) {
			String storeModelName = getStoreModelLogic(serviceExtendFieldSetting);
			serviceExtendFieldSetting.setStoreModelName(storeModelName);
		}
	}

	/**
	 * Logic to create new extend field setting
	 * 
	 * @param serviceExtensionSetting
	 * @param systemCategory
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceExtendFieldSetting newExtendFieldSetting(
			ServiceExtensionSetting serviceExtensionSetting, int systemCategory)
			throws ServiceEntityConfigureException {
		ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
				.newEntityNode(serviceExtensionSetting,
						ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSetting.setSystemCategory(systemCategory);
		serviceExtendFieldSetting
				.setEnableSwitch(StandardSwitchProxy.SWITCH_ON);
		serviceExtendFieldSetting
				.setVisibleSwitch(StandardSwitchProxy.SWITCH_ON);
		return serviceExtendFieldSetting;
	}

	/**
	 * Logic to get / assign store model name
	 * 
	 * @param serviceExtendFieldSetting
	 * @return
	 */
	public String getStoreModelLogic(
			ServiceExtendFieldSetting serviceExtendFieldSetting) {
		if (serviceExtendFieldSetting.getFieldType().equals(
				ServiceExtendFieldSetting.FIELDTYPE_INT)) {
			return IServiceModelConstants.SerExtendIntField;
		}
		if (serviceExtendFieldSetting.getFieldType().equals(
				ServiceExtendFieldSetting.FIELDTYPE_DOUBLE)) {
			return IServiceModelConstants.SerExtendDoubleField;
		}
		if (serviceExtendFieldSetting.getFieldType().equals(
				ServiceExtendFieldSetting.FIELDTYPE_DATE)) {
			return IServiceModelConstants.SerExtendStr100Field;
		}
		if (serviceExtendFieldSetting.getFieldType().equals(
				ServiceExtendFieldSetting.FIELDTYPE_STRING)) {
			if (serviceExtendFieldSetting.getFieldMaxLength() == 100) {
				return IServiceModelConstants.SerExtendStr100Field;
			}
			if (serviceExtendFieldSetting.getFieldMaxLength() == 800) {
				return IServiceModelConstants.SerExtendStr800Field;
			}
			if (serviceExtendFieldSetting.getFieldMaxLength() == 3000) {
				return IServiceModelConstants.SerExtendStr3000Field;
			}
		}
		return IServiceModelConstants.SerExtendStr100Field;
	}

	/**
	 * Logic to new service extension field as well as i18n model
	 * 
	 * @param serviceExtensionSetting
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceExtendFieldSettingServiceModel newServiceExtendFieldWrapper(
			ServiceExtensionSetting serviceExtensionSetting,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel, LogonInfo logonInfo)
			throws ServiceEntityConfigureException {
		ServiceExtendFieldSettingServiceModel serviceExtendFieldSettingServiceModel = new ServiceExtendFieldSettingServiceModel();
		ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
				.newEntityNode(serviceExtensionSetting,
						ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSetting.setFieldType(serExtendUIControlSetUIModel
				.getFieldType());
		serviceExtendFieldSetting.setFieldName(serExtendUIControlSetUIModel
				.getFieldName());
		serviceExtendFieldSettingServiceModel
				.setServiceExtendFieldSetting(serviceExtendFieldSetting);
		// storeModelName should be assigned
		this.assignStoreModelName(serviceExtendFieldSetting);
		convUIControlSetUIToServiceField(serExtendUIControlSetUIModel, serviceExtendFieldSetting, logonInfo);
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel.getLanKey())) {
			ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
					.newEntityNode(serviceExtendFieldSetting,
							ServiceExtendFieldI18nSetting.NODENAME);
			serviceExtendFieldI18nSetting
					.setLanKey(serExtendUIControlSetUIModel.getLanKey());
			serviceExtendFieldI18nSetting
					.setLabelValue(serExtendUIControlSetUIModel.getLabelValue());
			List<ServiceEntityNode> serviceExtendFieldI18nSettingList = new ArrayList<>();
			serviceExtendFieldI18nSettingList.add(serviceExtendFieldI18nSetting);
			serviceExtendFieldSettingServiceModel
					.setServiceExtendFieldI18nSettingList(serviceExtendFieldI18nSettingList);
		}
		return serviceExtendFieldSettingServiceModel;
	}

	public void convParentToFieldSettingUI(
			ServiceExtensionSetting serviceExtensionSetting,
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel) {
		if (serviceExtensionSetting != null) {
			serviceExtendFieldSettingUIModel
					.setParentNodeId(serviceExtensionSetting.getRefSEName()
							+ "-" + serviceExtensionSetting.getRefNodeName());
		}
	}
	

	/**
	 * Only used in case of new field setting from UI Control UI !
	 * 
	 * @param serExtendUIControlSetUIModel
	 * @param serviceExtendFieldSetting
	 * @param logonInfo
	 */
	private void convUIControlSetUIToServiceField(
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			LogonInfo logonInfo) {
		if (serviceExtendFieldSetting != null
				&& serviceExtendFieldSetting != null) {
			serviceExtendFieldSetting.setFieldName(serExtendUIControlSetUIModel
					.getFieldName());
			serviceExtendFieldSetting.setFieldType(serExtendUIControlSetUIModel
					.getFieldType());
			serviceExtendFieldSetting
					.setStoreModelName(serExtendUIControlSetUIModel
							.getStoreModelName());
			if(serExtendUIControlSetUIModel
							.getSystemCategory() > 0){
				serviceExtendFieldSetting
				.setSystemCategory(serExtendUIControlSetUIModel
						.getSystemCategory());
			}
			serviceExtendFieldSetting
					.setCustomI18nSwitch(serExtendUIControlSetUIModel
							.getCustomI18nSwitch());
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getInputControlType())) {
				serviceExtendFieldSetting
						.setInputControlType(serExtendUIControlSetUIModel
								.getInputControlType());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getRefMetaCodeUUID())) {
				serviceExtendFieldSetting
						.setRefMetaCodeUUID(serExtendUIControlSetUIModel
								.getRefMetaCodeUUID());
			}
			serviceExtendFieldSetting
			.setDefaultValue(serExtendUIControlSetUIModel
					.getDefaultValue());			
			serviceExtendFieldSetting
			.setDefaultValueExpression(serExtendUIControlSetUIModel
					.getDefaultValueExpression());
			serviceExtendFieldSetting
					.setDefaultValueExpression(serExtendUIControlSetUIModel
							.getDefaultValueExpression());
			serviceExtendFieldSetting
					.setEnableActionCode(serExtendUIControlSetUIModel
							.getEnableActionCode());
			serviceExtendFieldSetting
			.setVisibleActionCode(serExtendUIControlSetUIModel
					.getVisibleActionCode());
			serviceExtendFieldSetting
			.setVisibleExpression(serExtendUIControlSetUIModel
					.getVisibleExpression());
			serviceExtendFieldSetting
			.setVisibleSwitch(serExtendUIControlSetUIModel
					.getVisibleSwitch());

		}

	}

	/**
	 * Logic to calculate default field name, based on field type, and previous
	 * fieldList
	 * 
	 * @param fieldType
	 * @param rawExtendFieldSettingList
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws SearchConfigureException
	 */
	public String calculateDefFieldName(String fieldType,
			List<ServiceEntityNode> rawExtendFieldSettingList)
			throws ServiceEntityInstallationException {
		int indexLength = 3;
		List<ServiceEntityNode> resultFieldSettingList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(rawExtendFieldSettingList)) {
			resultFieldSettingList = ServiceCollectionsHelper
					.filterSENodeListOnline(
							fieldType,
							seNode -> {
								ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) seNode;
								return serviceExtendFieldSetting.getFieldType();
							}, rawExtendFieldSettingList, false);
		}
		int lastIndex = serviceDefaultIdGenerateHelper
				.getLastIndexOnline(
						resultFieldSettingList,
						seNode -> {
							ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) seNode;
							return serviceExtendFieldSetting.getFieldName();
						}, indexLength);
		String defFieldNamePrefix = getDefFieldNamePrefix(fieldType);
		String newFieldName = serviceDefaultIdGenerateHelper
				.genSerialNumberIdCore(lastIndex, defFieldNamePrefix,
						SerialNumberSetting.SEPERATOR_NONE,
						ServiceEntityStringHelper.EMPTYSTRING,
						DefaultDateFormatConstant.FORT_NONE,
						ServiceEntityStringHelper.EMPTYSTRING,
						SerialNumberSetting.SEPERATOR_NONE, 0, 0, 1);
		return newFieldName;
	}

	public static String getDefFieldNamePrefix(String fieldType) {
		if (fieldType.equals(ServiceExtendFieldSetting.FIELDTYPE_STRING)) {
			return "strField";
		}
		if (fieldType.equals(ServiceExtendFieldSetting.FIELDTYPE_INT)) {
			return "intField";
		}
		if (fieldType.equals(ServiceExtendFieldSetting.FIELDTYPE_DOUBLE)) {
			return "doubleField";
		}
		if (fieldType.equals(ServiceExtendFieldSetting.FIELDTYPE_DATE)) {
			return "dateField";
		}
		return "strField";
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convServiceExtendFieldSettingToUI(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel) {
		convServiceExtendFieldSettingToUI(serviceExtendFieldSetting,
				serviceExtendFieldSettingUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convServiceExtendFieldSettingToUI(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel,
			LogonInfo logonInfo) {
		if (serviceExtendFieldSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serviceExtendFieldSetting, serviceExtendFieldSettingUIModel);
			serviceExtendFieldSettingUIModel
					.setSearchFlag(serviceExtendFieldSetting.getSearchFlag());
			serviceExtendFieldSettingUIModel
					.setFieldType(serviceExtendFieldSetting.getFieldType());
			if (logonInfo != null) {
				try {
					Map<String, String> fieldTypeMap = this
							.initFieldTypeMap(logonInfo.getLanguageCode());
					serviceExtendFieldSettingUIModel
							.setFieldTypeValue(fieldTypeMap
									.get(serviceExtendFieldSetting
											.getFieldType()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, ""));
				}
			}
			serviceExtendFieldSettingUIModel.setNote(serviceExtendFieldSetting
					.getNote());
			serviceExtendFieldSettingUIModel
					.setStoreModelName(serviceExtendFieldSetting
							.getStoreModelName());
			serviceExtendFieldSettingUIModel
					.setFieldName(serviceExtendFieldSetting.getFieldName());
			// Set default empty value for [label value]
			serviceExtendFieldSettingUIModel
					.setLabelValue(serviceExtendFieldSetting.getFieldName());
			serviceExtendFieldSettingUIModel
					.setFieldMaxLength(serviceExtendFieldSetting
							.getFieldMaxLength());
			serviceExtendFieldSettingUIModel
					.setCustomI18nSwitch(serviceExtendFieldSetting
							.getCustomI18nSwitch());
			serviceExtendFieldSettingUIModel
					.setInputControlType(serviceExtendFieldSetting
							.getInputControlType());
			serviceExtendFieldSettingUIModel
					.setSystemCategory(serviceExtendFieldSetting
							.getSystemCategory());
			serviceExtendFieldSettingUIModel
					.setActiveSwitch(serviceExtendFieldSetting
							.getActiveSwitch());
			try {
				if (logonInfo != null) {
					Map<String, String> inputControlTypeMap = serExtendUIControlSetManager
							.initInputControlTypeMap(logonInfo.getLanguageCode());
					serviceExtendFieldSettingUIModel
							.setInputControlTypeValue(inputControlTypeMap
									.get(serviceExtendFieldSetting
											.getInputControlType()));
				}
			} catch (IOException e) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
			try {
				Map<Integer, String> systemCategoryMap = standardSystemCategoryProxy
						.getSystemCategoryMap(logonInfo.getLanguageCode());
				serviceExtendFieldSettingUIModel
						.setSystemCategoryValue(systemCategoryMap
								.get(serviceExtendFieldSetting
										.getSystemCategory()));
			} catch (ServiceEntityInstallationException ex) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						ex, ""));
			}
			try {
				if (logonInfo != null) {
					Map<Integer, String> switchFlagMap = this
							.initSwitchFlagMap(logonInfo.getLanguageCode());
					serviceExtendFieldSettingUIModel
							.setCustomI18nSwitchValue(switchFlagMap
									.get(serviceExtendFieldSetting
											.getCustomI18nSwitch()));
					serviceExtendFieldSettingUIModel
							.setActiveSwitchValue(switchFlagMap
									.get(serviceExtendFieldSetting
											.getActiveSwitch()));
					serviceExtendFieldSettingUIModel
							.setEnableSwitchValue(switchFlagMap
									.get(serviceExtendFieldSetting
											.getEnableSwitch()));
					serviceExtendFieldSettingUIModel
							.setVisibleSwitchValue(switchFlagMap
									.get(serviceExtendFieldSetting
											.getVisibleSwitch()));
				}
			} catch (ServiceEntityInstallationException e) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
			serviceExtendFieldSettingUIModel
					.setCustomI18nFlag(serviceExtendFieldSetting
							.getCustomI18nFlag());
			serviceExtendFieldSettingUIModel
					.setInitialValue(serviceExtendFieldSetting
							.getInitialValue());
			serviceExtendFieldSettingUIModel
					.setInitialPrevModelName(serviceExtendFieldSetting
							.getInitialPrevModelName());
			serviceExtendFieldSettingUIModel
					.setExtendedFieldFlag(serviceExtendFieldSetting
							.getExtendedFieldFlag());
			serviceExtendFieldSettingUIModel
					.setHideInEditor(serviceExtendFieldSetting
							.getHideInEditor());
			serviceExtendFieldSettingUIModel
					.setHideInList(serviceExtendFieldSetting.getHideInList());
			serviceExtendFieldSettingUIModel
					.setHideInSearchPanel(serviceExtendFieldSetting
							.getHideInSearchPanel());
			serviceExtendFieldSettingUIModel
					.setGetMetaDataUrl(serviceExtendFieldSetting
							.getGetMetaDataUrl());
			serviceExtendFieldSettingUIModel
					.setRefMetaCodeUUID(serviceExtendFieldSetting
							.getRefMetaCodeUUID());
			serviceExtendFieldSettingUIModel
					.setVisibleSwitch(serviceExtendFieldSetting
							.getVisibleSwitch());
			serviceExtendFieldSettingUIModel
					.setVisibleExpression(serviceExtendFieldSetting
							.getVisibleExpression());
			serviceExtendFieldSettingUIModel
					.setVisibleActionCode(serviceExtendFieldSetting
							.getVisibleActionCode());
			serviceExtendFieldSettingUIModel
					.setDefaultValue(serviceExtendFieldSetting
							.getDefaultValue());
			serviceExtendFieldSettingUIModel
					.setDefaultValueExpression(serviceExtendFieldSetting
							.getDefaultValueExpression());
			serviceExtendFieldSettingUIModel
					.setFormatSelectMethod(serviceExtendFieldSetting
							.getFormatSelectMethod());
			serviceExtendFieldSettingUIModel
					.setEnableSwitch(serviceExtendFieldSetting
							.getEnableSwitch());
			serviceExtendFieldSettingUIModel
					.setEnableExpression(serviceExtendFieldSetting
							.getEnableExpression());
			serviceExtendFieldSettingUIModel
					.setEnableActionCode(serviceExtendFieldSetting
							.getEnableActionCode());
			// Logic to set label value from i18n
			if (logonInfo != null) {
				try {
					List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
					keyList.add(new ServiceBasicKeyStructure(ServiceLanHelper
							.formatLanCode(logonInfo.getLanguageCode()),
							"lanKey"));
					keyList.add(new ServiceBasicKeyStructure(
							serviceExtendFieldSetting.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID));
					ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
							.getEntityNodeByKeyList(keyList,
									ServiceExtendFieldI18nSetting.NODENAME,
									serviceExtendFieldSetting.getClient(), null);
					if (serviceExtendFieldI18nSetting != null) {
						serviceExtendFieldSettingUIModel
								.setLabelValue(serviceExtendFieldI18nSetting
										.getLabelValue());
					}
				} catch (ServiceEntityConfigureException e) {
					// skip;
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, ""));
				}
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:serviceExtendFieldSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceExtendFieldSetting(
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel,
			ServiceExtendFieldSetting rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel.getUuid())) {
			rawEntity.setUuid(serviceExtendFieldSettingUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(serviceExtendFieldSettingUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(serviceExtendFieldSettingUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel.getClient())) {
			rawEntity.setClient(serviceExtendFieldSettingUIModel.getClient());
		}
		rawEntity.setSearchFlag(serviceExtendFieldSettingUIModel
				.getSearchFlag());
		rawEntity.setFieldType(serviceExtendFieldSettingUIModel.getFieldType());
		rawEntity.setClient(serviceExtendFieldSettingUIModel.getClient());
		rawEntity.setNote(serviceExtendFieldSettingUIModel.getNote());
		rawEntity.setUuid(serviceExtendFieldSettingUIModel.getUuid());
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel
						.getStoreModelName())) {
			rawEntity.setStoreModelName(serviceExtendFieldSettingUIModel
					.getStoreModelName());
		}
		if (serviceExtendFieldSettingUIModel.getCustomI18nSwitch() > 0) {
			rawEntity.setCustomI18nSwitch(serviceExtendFieldSettingUIModel
					.getCustomI18nSwitch());
		}
		if (serviceExtendFieldSettingUIModel.getActiveSwitch() > 0) {
			rawEntity.setActiveSwitch(serviceExtendFieldSettingUIModel
					.getActiveSwitch());
		}
		if (serviceExtendFieldSettingUIModel.getSystemCategory() > 0) {
			rawEntity.setSystemCategory(serviceExtendFieldSettingUIModel
					.getSystemCategory());
		}
		rawEntity.setFieldName(serviceExtendFieldSettingUIModel.getFieldName());
		rawEntity.setFieldMaxLength(serviceExtendFieldSettingUIModel
				.getFieldMaxLength());
		rawEntity.setExtendedFieldFlag(serviceExtendFieldSettingUIModel
				.getExtendedFieldFlag());
		rawEntity.setHideInEditor(serviceExtendFieldSettingUIModel
				.getHideInEditor());
		rawEntity.setHideInList(serviceExtendFieldSettingUIModel
				.getHideInList());
		rawEntity.setHideInSearchPanel(serviceExtendFieldSettingUIModel
				.getHideInSearchPanel());
		rawEntity.setInitialValue(serviceExtendFieldSettingUIModel
				.getInitialValue());
		if (!ServiceEntityStringHelper
				.checkNullString(serviceExtendFieldSettingUIModel
						.getInputControlType())) {
			rawEntity.setInputControlType(serviceExtendFieldSettingUIModel
					.getInputControlType());
		}
		rawEntity.setGetMetaDataUrl(serviceExtendFieldSettingUIModel
				.getGetMetaDataUrl());
		rawEntity.setRefMetaCodeUUID(serviceExtendFieldSettingUIModel
				.getRefMetaCodeUUID());
		rawEntity.setVisibleSwitch(serviceExtendFieldSettingUIModel
				.getVisibleSwitch());
		rawEntity.setVisibleActionCode(serviceExtendFieldSettingUIModel
				.getVisibleActionCode());
		rawEntity.setVisibleExpression(serviceExtendFieldSettingUIModel
				.getVisibleExpression());
		rawEntity.setDefaultValue(serviceExtendFieldSettingUIModel
				.getDefaultValue());
		rawEntity.setDefaultValueExpression(serviceExtendFieldSettingUIModel
				.getDefaultValueExpression());
		rawEntity.setFormatSelectMethod(serviceExtendFieldSettingUIModel
				.getFormatSelectMethod());
		rawEntity.setEnableSwitch(serviceExtendFieldSettingUIModel
				.getEnableSwitch());
		rawEntity.setEnableExpression(serviceExtendFieldSettingUIModel
				.getEnableExpression());
		rawEntity.setEnableActionCode(serviceExtendFieldSettingUIModel
				.getEnableActionCode());

	}

	public List<ServiceEntityNode> searchInternal(
			ServiceExtendFieldSettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node:[SerExtendPageSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ServiceExtendFieldSetting.SENAME);
		searchNodeConfig0.setNodeName(ServiceExtendFieldSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(ServiceExtendFieldSetting.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);

		// Search node:[ServiceExtensionSetting]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(ServiceExtensionSetting.SENAME);
		searchNodeConfig1.setNodeName(ServiceExtensionSetting.NODENAME);
		searchNodeConfig1.setNodeInstID(ServiceExtensionSetting.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1.setBaseNodeInstID(ServiceExtendFieldSetting.NODENAME);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig1);

		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	public void convParentToFieldI18nSettingUI(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel) {
		if (serviceExtendFieldSetting != null) {
			serviceExtendFieldI18nSettingUIModel
					.setParentNodeId(serviceExtendFieldSetting.getFieldName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceExtendFieldI18nSettingToUI(
			ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting,
			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel) {
		if (serviceExtendFieldI18nSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serviceExtendFieldI18nSetting, serviceExtendFieldI18nSettingUIModel);
			serviceExtendFieldI18nSettingUIModel
					.setLanKey(serviceExtendFieldI18nSetting.getLanKey());
			serviceExtendFieldI18nSettingUIModel
					.setLabelValue(serviceExtendFieldI18nSetting
							.getLabelValue());
			serviceExtendFieldI18nSettingUIModel
					.setActiveFlag(serviceExtendFieldI18nSetting
							.getActiveFlag());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:serviceExtendFieldSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceExtendFieldI18nSetting(
			ServiceExtendFieldI18nSettingUIModel serviceExtendFieldI18nSettingUIModel,
			ServiceExtendFieldI18nSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serviceExtendFieldI18nSettingUIModel, rawEntity);
		rawEntity.setActiveFlag(serviceExtendFieldI18nSettingUIModel
				.getActiveFlag());
		rawEntity.setLabelValue(serviceExtendFieldI18nSettingUIModel
				.getLabelValue());
		rawEntity.setLanKey(serviceExtendFieldI18nSettingUIModel.getLanKey());
	}

}
