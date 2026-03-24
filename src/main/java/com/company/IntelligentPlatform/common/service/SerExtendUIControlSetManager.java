package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendUIControlSetUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceUIControlExtensionUIModel;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class SerExtendUIControlSetManager {

	public static final String METHOD_ConvServiceFieldToUIControlSetUI = "convServiceFieldToUIControlSetUI";

	public static final String METHOD_ConvSysCodeValueToUIControlSetUI = "convSysCodeValueToUIControlSetUI";

	public static final String METHOD_ConvPageSectionToUIControlSetUI = "convPageSectionToUIControlSetUI";

	public static final String METHOD_ConvPageSettingToUIControlSetUI = "convPageSettingToUIControlSetUI";

	public static final String METHOD_ConvSerExtendUIControlSetToUI = "convSerExtendUIControlSetToUI";

	public static final String METHOD_ConvUIToSerExtendUIControl = "convUIToSerExtendUIControl";

	public static final String METHOD_ConvExtensionSettingToUIControlSetUI = "convExtensionSettingToUIControlSetUI";

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSectionManager serExtendPageSectionManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<PageHeaderModel> getPageHeaderModelList(
			SerExtendUIControlSet serExtendUIControlSet, String client)
			throws ServiceEntityConfigureException {
		List<PageHeaderModel> resultList = getPageHeaderModelList(
				serExtendUIControlSet.getParentNodeUUID(), client);
		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			int index = resultList.size();
			PageHeaderModel itemHeaderModel = getPageHeaderModel(
					serExtendUIControlSet, index);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	//TODO check or delete this method
	public List<PageHeaderModel> getPageHeaderModelList(String baseUUID,
			String client) throws ServiceEntityConfigureException {
		SerExtendPageSection serExtendPageSection = (SerExtendPageSection) serviceExtensionSettingManager
				.getEntityNodeByKey(baseUUID,
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSection.NODENAME, client, null);
		List<PageHeaderModel> resultList = new ArrayList<>();
		if (serExtendPageSection != null) {
			List<PageHeaderModel> pageHeaderModelList = serExtendPageSectionManager
					.getPageHeaderModelList(new SimpleSEJSONRequest(serExtendPageSection.getUuid(),
							serExtendPageSection.getParentNodeUUID()), client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(
			SerExtendUIControlSet serExtendUIControlSet, int index)
			throws ServiceEntityConfigureException {
		if (serExtendUIControlSet == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("serExtendUIControlSetPageTitle");
		pageHeaderModel.setNodeInstId(SerExtendUIControlSet.NODENAME);
		pageHeaderModel.setUuid(serExtendUIControlSet.getUuid());
		pageHeaderModel.setHeaderName(serExtendUIControlSet.getScreenId());
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	/**
	 * Logic to provide input Control type
	 * 
	 * @param lanCode
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> initInputControlTypeMap(String lanCode)
			throws IOException {
		String resFileName = "InputControlType";
		Locale locale = ServiceLanHelper.parseToLocale(lanCode);
		Map<String, String> inputControlTypeMap = serviceDropdownListHelper
				.getDropDownMap(this.getClass().getResource("").getPath(),
						resFileName, locale);
		return inputControlTypeMap;
	}

	protected static List<String> defFieldTypesSelect2() {
		List<String> resultList = new ArrayList<>();
		resultList.add(int.class.getSimpleName());
		return resultList;
	};

	protected static List<String> defFieldTypesTextArea() {
		List<String> resultList = new ArrayList<>();
		resultList.add(String.class.getSimpleName());
		return resultList;
	};

	protected static List<String> defFieldTypesInput() {
		List<String> resultList = new ArrayList<>();
		resultList.add(String.class.getSimpleName());
		resultList.add(int.class.getSimpleName());
		return resultList;
	};

	protected static List<String> defFieldTypesDate() {
		List<String> resultList = new ArrayList<>();
		resultList.add(Date.class.getSimpleName());
		return resultList;
	};

	public Map<Integer, String> initSwitchFlagMap(String lanCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(lanCode);
	}


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPageSectionToUIControlSetUI(
			SerExtendPageSection serExtendPageSection,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		serExtendUIControlSetUIModel.setSectionId(serExtendPageSection.getId());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPageSettingToUIControlSetUI(
			SerExtendPageSetting serExtendPageSetting,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		serExtendUIControlSetUIModel.setScreenId(serExtendPageSetting.getId());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convExtensionSettingToUIControlSetUI(
			ServiceExtensionSetting serviceExtensionSetting,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		serExtendUIControlSetUIModel.setRefNodeInstId(serviceExtensionSetting
				.getRefSEName());
		serExtendUIControlSetUIModel.setRefSEName(serviceExtensionSetting
				.getRefSEName());
		serExtendUIControlSetUIModel.setRefNodeInstId(serviceExtensionSetting
				.getId());
	}

	/**
	 * Initial new SerExtendUIControlSet from ServiceUIControlExtensionUIModel
	 * instance
	 *
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceModuleProxyException
	 */
	public SerExtendUIControlSetUIModel initSerExtendUIControl(
			ServiceUIControlExtensionUIModel serviceUIControlExtensionUIModel,
			String client) throws ServiceEntityConfigureException,
			ServiceEntityInstallationException {
		SerExtendUIControlSetUIModel serExtendUIControlSetUIModel = new SerExtendUIControlSetUIModel();
		serExtendUIControlSetUIModel
				.setParentNodeUUID(serviceUIControlExtensionUIModel
						.getBaseUUID());
		serExtendUIControlSetUIModel
				.setSystemCategory(StandardSystemCategoryProxy.CATE_SELF_DEFINED);
		if (ServiceUIControlExtensionUIModel.EXTENDCONTR_INPUT_TEXT
				.equals(serviceUIControlExtensionUIModel.getControlType())) {
			serExtendUIControlSetUIModel
					.setFieldType(ServiceExtendFieldSetting.FIELDTYPE_STRING);
			serExtendUIControlSetUIModel
					.setInputControlType(SerExtendUIControlSet.CONTRTYPE_INPUT);
		}
		if (ServiceUIControlExtensionUIModel.EXTENDCONTR_INPUT_NUM
				.equals(serviceUIControlExtensionUIModel.getControlType())) {
			serExtendUIControlSetUIModel
					.setFieldType(ServiceExtendFieldSetting.FIELDTYPE_DOUBLE);
			serExtendUIControlSetUIModel
					.setInputControlType(SerExtendUIControlSet.CONTRTYPE_INPUT);
		}
		if (ServiceUIControlExtensionUIModel.EXTENDCONTR_SELECT2
				.equals(serviceUIControlExtensionUIModel.getControlType())) {
			serExtendUIControlSetUIModel
					.setFieldType(ServiceExtendFieldSetting.FIELDTYPE_INT);
			serExtendUIControlSetUIModel
					.setInputControlType(SerExtendUIControlSet.CONTRTYPE_SELECT2);
		}
		if (ServiceUIControlExtensionUIModel.EXTENDCONTR_TEXTAREA
				.equals(serviceUIControlExtensionUIModel.getControlType())) {
			serExtendUIControlSetUIModel
					.setFieldType(ServiceExtendFieldSetting.FIELDTYPE_STRING);
			serExtendUIControlSetUIModel
					.setInputControlType(SerExtendUIControlSet.CONTRTYPE_TEXTAREA);
		}
		if (ServiceUIControlExtensionUIModel.EXTENDCONTR_DATE
				.equals(serviceUIControlExtensionUIModel.getControlType())) {
			serExtendUIControlSetUIModel
					.setFieldType(ServiceExtendFieldSetting.FIELDTYPE_DATE);
			serExtendUIControlSetUIModel
					.setInputControlType(SerExtendUIControlSet.CONTRTYPE_INPUT);
		}
		SerExtendPageSection serExtendPageSection = (SerExtendPageSection) serviceExtensionSettingManager
				.getEntityNodeByKey(
						serExtendUIControlSetUIModel.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSection.NODENAME, client, null);
		if (serExtendPageSection == null) {
			// should raise exception
		}
		SerExtendPageSetting serExtendPageSetting = (SerExtendPageSetting) serviceExtensionSettingManager
				.getEntityNodeByKey(serExtendPageSection.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSetting.NODENAME, client, null);
		serExtendUIControlSetUIModel.setScreenId(serExtendPageSetting.getId());
		serExtendUIControlSetUIModel.setSectionId(serExtendPageSection.getId());
		List<ServiceEntityNode> rawExtendFieldSettingList = serviceExtensionSettingManager
				.getEntityNodeListByKey(
						serviceUIControlExtensionUIModel.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ServiceExtendFieldSetting.NODENAME, null);
		String fieldName = serviceExtendFieldSettingManager
				.calculateDefFieldName(
						serExtendUIControlSetUIModel.getFieldType(),
						rawExtendFieldSettingList);
		serExtendUIControlSetUIModel.setFieldName(fieldName);
		return serExtendUIControlSetUIModel;
	}

	/**
	 * Logic to provide input Control type
	 * 
	 * @param lanCode
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> initExtendControlTypeMap(String lanCode)
			throws IOException {
		String resFileName = "ServiceUIControlExtension_controlType";
		Locale locale = ServiceLanHelper.parseToLocale(lanCode);
		Map<String, String> extendControlTypeMap = serviceDropdownListHelper
				.getDropDownMap(ServiceUIControlExtensionUIModel.class
						.getResource("").getPath(), resFileName, locale);
		return extendControlTypeMap;
	}

	/**
	 * Core Logic to create new field as well as Extend UI Control instance
	 *
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public SerExtendUIControlSet newSerExtendUIControlFieldWrapper(
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			String client, LogonInfo logonInfo, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException,
			ServiceModuleProxyException {
		SerExtendPageSection serExtendPageSection = (SerExtendPageSection) serviceExtensionSettingManager
				.getEntityNodeByKey(
						serExtendUIControlSetUIModel.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSection.NODENAME, client, null);
		if (serExtendPageSection == null) {
			// should raise exception
		}
		ServiceExtensionSetting serviceExtensionSetting = (ServiceExtensionSetting) serviceExtensionSettingManager
				.getEntityNodeByKey(serExtendPageSection.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						ServiceExtensionSetting.NODENAME, client, null);
		/*
		 * [Step1] new extend field service model as well as i18n
		 */
		ServiceExtendFieldSettingServiceModel serviceExtendFieldSettingServiceModel = serviceExtendFieldSettingManager
				.newServiceExtendFieldWrapper(serviceExtensionSetting,
						serExtendUIControlSetUIModel, logonInfo);
		// update to persistence
		serviceExtensionSettingManager.updateServiceModule(
				ServiceExtendFieldSettingServiceModel.class,
				serviceExtendFieldSettingServiceModel, logonUserUUID,
				organizationUUID);
		/*
		 * [Step2] new control type from field setting
		 */
		SerExtendUIControlSet serExtendUIControlSet = newSerExtendUIControl(
				serExtendUIControlSetUIModel.getParentNodeUUID(),
				serviceExtendFieldSettingServiceModel
						.getServiceExtendFieldSetting().getUuid(), client);
		convSerExtendUIControlSetToUI(serExtendUIControlSet,
				serExtendUIControlSetUIModel, logonInfo);
		serviceExtensionSettingManager.updateSENode(serExtendUIControlSet,
				logonUserUUID, organizationUUID);
		return serExtendUIControlSet;
	}

	/**
	 * Core Logic to create new Extend UI Control instance
	 * 
	 * @param baseUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public SerExtendUIControlSet newSerExtendUIControl(String baseUUID,
			String refFieldUUID, String client)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		SerExtendPageSection serExtendPageSection = (SerExtendPageSection) serviceExtensionSettingManager
				.getEntityNodeByKey(baseUUID,
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSection.NODENAME, client, null);
		ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
				.getEntityNodeByKey(refFieldUUID,
						IServiceEntityNodeFieldConstant.UUID,
						ServiceExtendFieldSetting.NODENAME, client, null);
		SerExtendPageSectionServiceModel serExtendPageSectionServiceModel = (SerExtendPageSectionServiceModel) serviceExtensionSettingManager
				.loadServiceModule(SerExtendPageSectionServiceModel.class,
						serExtendPageSection);
		int displayIndex = 1;
		//TODO think about how to define displayIndex of UIControl
//		if (!ServiceCollectionsHelper
//				.checkNullList(serExtendPageSectionServiceModel
//						.getSerExtendUIControlSetList())) {
//			for (ServiceEntityNode rawSENode : serExtendPageSectionServiceModel
//					.getSerExtendUIControlSetList()) {
//				SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) rawSENode;
//				if (serExtendUIControlSet.getDisplayIndex() > displayIndex) {
//					displayIndex = serExtendUIControlSet.getDisplayIndex();
//				}
//			}
//			displayIndex = displayIndex + 1;
//		}
		SerExtendUIControlSet serExtendUIControlSet = (SerExtendUIControlSet) serviceExtensionSettingManager
				.newEntityNode(serExtendPageSection,
						SerExtendUIControlSet.NODENAME);
		serExtendUIControlSet.setDisplayIndex(displayIndex);
		serExtendUIControlSet.setRefFieldUUID(refFieldUUID);
		initExtendUIControlFromField(serviceExtendFieldSetting,
				serExtendUIControlSet);
		return serExtendUIControlSet;
	}

	private void initExtendUIControlFromField(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			SerExtendUIControlSet serExtendUIControlSet) {
		serExtendUIControlSet.setInputControlType(serviceExtendFieldSetting
				.getInputControlType());
		serExtendUIControlSet.setEnableSwitch(serviceExtendFieldSetting
				.getEnableSwitch());
		serExtendUIControlSet.setEnableExpression(serviceExtendFieldSetting
				.getEnableExpression());
		serExtendUIControlSet.setEnableActionCode(serviceExtendFieldSetting
				.getEnableActionCode());
		serExtendUIControlSet.setFormatSelectMethod(serviceExtendFieldSetting
				.getFormatSelectMethod());
		serExtendUIControlSet.setVisibleActionCode(serviceExtendFieldSetting
				.getVisibleActionCode());
		serExtendUIControlSet.setVisibleExpression(serviceExtendFieldSetting
				.getVisibleExpression());
		serExtendUIControlSet.setVisibleSwitch(serviceExtendFieldSetting
				.getVisibleSwitch());
		serExtendUIControlSet.setEnableActionCode(serExtendUIControlSet
				.getEnableActionCode());
		serExtendUIControlSet.setDefaultValue(serExtendUIControlSet
				.getDefaultValue());
		serExtendUIControlSet.setDefaultValueExpression(serExtendUIControlSet
				.getDefaultValueExpression());
	}

	public void convSerExtendUIControlSetToUI(
			SerExtendUIControlSet serExtendUIControlSet,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		convSerExtendUIControlSetToUI(serExtendUIControlSet,
				serExtendUIControlSetUIModel, null);
	}


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSerExtendUIControlSetToUI(
			SerExtendUIControlSet serExtendUIControlSet,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			LogonInfo logonInfo) {
		if (serExtendUIControlSet != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSet.getUuid())) {
				serExtendUIControlSetUIModel.setUuid(serExtendUIControlSet
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSet.getParentNodeUUID())) {
				serExtendUIControlSetUIModel
						.setParentNodeUUID(serExtendUIControlSet
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSet.getRootNodeUUID())) {
				serExtendUIControlSetUIModel
						.setRootNodeUUID(serExtendUIControlSet
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSet.getClient())) {
				serExtendUIControlSetUIModel.setClient(serExtendUIControlSet
						.getClient());
			}
			try {
				if (logonInfo != null) {
					Map<String, String> inputControlTypeMap = this
							.initInputControlTypeMap(logonInfo.getLanguageCode());
					serExtendUIControlSetUIModel
							.setInputControlTypeValue(inputControlTypeMap
									.get(serExtendUIControlSet
											.getInputControlType()));
				}
			} catch (IOException e) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
			serExtendUIControlSetUIModel
					.setInputControlType(serExtendUIControlSet
							.getInputControlType());
			serExtendUIControlSetUIModel.setRefFieldUUID(serExtendUIControlSet
					.getRefFieldUUID());
			serExtendUIControlSetUIModel.setRowNumber(serExtendUIControlSet
					.getRowNumber());
			serExtendUIControlSetUIModel.setControlDomId(serExtendUIControlSet
					.getControlDomId());
			serExtendUIControlSetUIModel
					.setEnableExpression(serExtendUIControlSet
							.getEnableExpression());
			serExtendUIControlSetUIModel.setEnableSwitch(serExtendUIControlSet
					.getEnableSwitch());
			try {
				if (logonInfo != null) {
					Map<Integer, String> switchFlagMap = this
							.initSwitchFlagMap(logonInfo.getLanguageCode());
					serExtendUIControlSetUIModel
							.setEnableSwitchValue(switchFlagMap
									.get(serExtendUIControlSet
											.getEnableSwitch()));
					serExtendUIControlSetUIModel
							.setVisibleSwitchValue(switchFlagMap
									.get(serExtendUIControlSet
											.getVisibleSwitch()));
				}

			} catch (ServiceEntityInstallationException e) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
			serExtendUIControlSetUIModel
					.setEnableExpression(serExtendUIControlSet
							.getEnableExpression());
			serExtendUIControlSetUIModel.setVisibleSwitch(serExtendUIControlSet
					.getVisibleSwitch());
			serExtendUIControlSetUIModel
					.setVisibleExpression(serExtendUIControlSet
							.getVisibleExpression());
			serExtendUIControlSetUIModel
					.setVisibleActionCode(serExtendUIControlSet
							.getVisibleActionCode());
			serExtendUIControlSetUIModel
					.setEnableActionCode(serExtendUIControlSet
							.getEnableActionCode());
			serExtendUIControlSetUIModel.setDefaultValue(serExtendUIControlSet
					.getDefaultValue());
			serExtendUIControlSetUIModel
					.setDefaultValueExpression(serExtendUIControlSet
							.getDefaultValueExpression());
			serExtendUIControlSetUIModel
					.setFormatSelectMethod(serExtendUIControlSet
							.getFormatSelectMethod());
			serExtendUIControlSetUIModel.setDisplayIndex(serExtendUIControlSet
					.getDisplayIndex());
			serExtendUIControlSetUIModel
					.setGetMetaDataUrl(serExtendUIControlSet
							.getGetMetaDataUrl());
			serExtendUIControlSetUIModel
					.setRefMetaCodeUUID(serExtendUIControlSet
							.getRefMetaCodeUUID());
			serExtendUIControlSetUIModel.setSectionId(serExtendUIControlSet
					.getSectionId());
			serExtendUIControlSetUIModel.setScreenId(serExtendUIControlSet
					.getScreenId());

		}
	}

	public void convServiceFieldToUIControlSetUI(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		convServiceFieldToUIControlSetUI(serviceExtendFieldSetting,
				serExtendUIControlSetUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceFieldToUIControlSetUI(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			LogonInfo logonInfo) {
		serExtendUIControlSetUIModel.setFieldName(serviceExtendFieldSetting
				.getFieldName());
		// Set empty default value for [label value]
		serExtendUIControlSetUIModel.setLabelValue(serviceExtendFieldSetting
				.getFieldName());
		serExtendUIControlSetUIModel.setFieldType(serviceExtendFieldSetting
				.getFieldType());
		if (logonInfo != null) {
			try {
				Map<String, String> fieldTypeMap = serviceExtendFieldSettingManager
						.initFieldTypeMap(logonInfo.getLanguageCode());
				serExtendUIControlSetUIModel.setFieldTypeValue(fieldTypeMap
						.get(serviceExtendFieldSetting.getFieldType()));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
		}
		serExtendUIControlSetUIModel
				.setStoreModelName(serviceExtendFieldSetting
						.getStoreModelName());
		serExtendUIControlSetUIModel
				.setSystemCategory(serviceExtendFieldSetting
						.getSystemCategory());
		serExtendUIControlSetUIModel
				.setCustomI18nSwitch(serviceExtendFieldSetting
						.getCustomI18nSwitch());
		// In case no value already assigned from serExtendUIControlSet, then
		// use value from serviceExtendFieldSetting
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getInputControlType())) {
			serExtendUIControlSetUIModel
					.setInputControlType(serviceExtendFieldSetting
							.getInputControlType());
			if (logonInfo != null) {
				try {
					Map<String, String> inputControlTypeMap = this
							.initInputControlTypeMap(logonInfo.getLanguageCode());
					serExtendUIControlSetUIModel
							.setInputControlTypeValue(inputControlTypeMap
									.get(serviceExtendFieldSetting
											.getInputControlType()));
				} catch (IOException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, ""));
				}
			}
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getGetMetaDataUrl())) {
			serExtendUIControlSetUIModel
					.setGetMetaDataUrl(serviceExtendFieldSetting
							.getGetMetaDataUrl());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getRefMetaCodeUUID())) {
			serExtendUIControlSetUIModel
					.setRefMetaCodeUUID(serviceExtendFieldSetting
							.getRefMetaCodeUUID());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel.getDefaultValue())) {
			serExtendUIControlSetUIModel
					.setDefaultValue(serviceExtendFieldSetting
							.getDefaultValue());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getDefaultValueExpression())) {
			serExtendUIControlSetUIModel
					.setDefaultValueExpression(serviceExtendFieldSetting
							.getDefaultValueExpression());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getDefaultValueExpression())) {
			serExtendUIControlSetUIModel
					.setDefaultValueExpression(serviceExtendFieldSetting
							.getDefaultValueExpression());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getDefaultValueExpression())) {
			serExtendUIControlSetUIModel
					.setDefaultValueExpression(serviceExtendFieldSetting
							.getDefaultValueExpression());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getEnableActionCode())) {
			serExtendUIControlSetUIModel
					.setEnableActionCode(serviceExtendFieldSetting
							.getEnableActionCode());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getFormatSelectMethod())) {
			serExtendUIControlSetUIModel
					.setFormatSelectMethod(serviceExtendFieldSetting
							.getFormatSelectMethod());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getVisibleActionCode())) {
			serExtendUIControlSetUIModel
					.setVisibleActionCode(serviceExtendFieldSetting
							.getVisibleActionCode());
		}
		if (ServiceEntityStringHelper
				.checkNullString(serExtendUIControlSetUIModel
						.getVisibleExpression())) {
			serExtendUIControlSetUIModel
					.setVisibleExpression(serviceExtendFieldSetting
							.getVisibleExpression());
		}
		if (serExtendUIControlSetUIModel.getVisibleSwitch() == 0) {
			serExtendUIControlSetUIModel
					.setVisibleSwitch(serviceExtendFieldSetting
							.getVisibleSwitch());
		}
		try {
			if (logonInfo != null) {
				Map<Integer, String> switchFlagMap = this
						.initSwitchFlagMap(logonInfo.getLanguageCode());
				serExtendUIControlSetUIModel
						.setSystemCategoryValue(switchFlagMap
								.get(serviceExtendFieldSetting
										.getSystemCategory()));
				serExtendUIControlSetUIModel
						.setCustomI18nSwitchValue(switchFlagMap
								.get(serviceExtendFieldSetting
										.getCustomI18nSwitch()));
			}
		} catch (ServiceEntityInstallationException e) {
			// skip;
			logger.error(ServiceEntityStringHelper
					.genDefaultErrorMessage(e, ""));
		}
		// Logic to set label value from i18n
		if (logonInfo != null) {
			try {
				List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
				keyList.add(new ServiceBasicKeyStructure(ServiceLanHelper
						.formatLanCode(logonInfo.getLanguageCode()), "lanKey"));
				keyList.add(new ServiceBasicKeyStructure(
						serviceExtendFieldSetting.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID));
				ServiceExtendFieldI18nSetting serviceExtendFieldI18nSetting = (ServiceExtendFieldI18nSetting) serviceExtensionSettingManager
						.getEntityNodeByKeyList(keyList,
								ServiceExtendFieldI18nSetting.NODENAME,
								serviceExtendFieldSetting.getClient(), null);
				if (serviceExtendFieldI18nSetting != null) {
					serExtendUIControlSetUIModel
							.setLabelValue(serviceExtendFieldI18nSetting
									.getLabelValue());
				}
			} catch (ServiceEntityConfigureException e) {
				// skip;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, ""));
			}
		}
	}



	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSysCodeValueToUIControlSetUI(SystemCodeValueCollection systemCodeValueCollection,
												 SerExtendUIControlSetUIModel serExtendUIControlSetUIModel){
		convSysCodeValueToUIControlSetUI(systemCodeValueCollection, serExtendUIControlSetUIModel, null);
	}

	public void convSysCodeValueToUIControlSetUI(
			SystemCodeValueCollection systemCodeValueCollection,
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			LogonInfo logonInfo) {
		if (systemCodeValueCollection != null
				&& serExtendUIControlSetUIModel != null) {
			serExtendUIControlSetUIModel
					.setRefMetaCodeId(systemCodeValueCollection.getId());
			serExtendUIControlSetUIModel
					.setRefMetaCodeName(systemCodeValueCollection.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:serviceExtendFieldSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSerExtendUIControl(
			SerExtendUIControlSetUIModel serExtendUIControlSetUIModel,
			SerExtendUIControlSet rawEntity) {
		if (rawEntity != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel.getUuid())) {
				rawEntity.setUuid(serExtendUIControlSetUIModel.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getParentNodeUUID())) {
				rawEntity.setParentNodeUUID(serExtendUIControlSetUIModel
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getRootNodeUUID())) {
				rawEntity.setRootNodeUUID(serExtendUIControlSetUIModel
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel.getClient())) {
				rawEntity.setClient(serExtendUIControlSetUIModel.getClient());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getInputControlType())) {
				rawEntity.setInputControlType(serExtendUIControlSetUIModel
						.getInputControlType());
			}
			rawEntity.setControlDomId(serExtendUIControlSetUIModel
					.getControlDomId());
			rawEntity.setEnableExpression(serExtendUIControlSetUIModel
					.getEnableExpression());
			if (serExtendUIControlSetUIModel.getEnableSwitch() > 0) {
				rawEntity.setEnableSwitch(serExtendUIControlSetUIModel
						.getEnableSwitch());
			}
			rawEntity.setEnableActionCode(serExtendUIControlSetUIModel
					.getEnableActionCode());
			if (serExtendUIControlSetUIModel.getVisibleSwitch() > 0) {
				rawEntity.setVisibleSwitch(serExtendUIControlSetUIModel
						.getVisibleSwitch());
			}
			rawEntity.setVisibleActionCode(serExtendUIControlSetUIModel
					.getVisibleActionCode());
			rawEntity.setDefaultValue(serExtendUIControlSetUIModel
					.getDefaultValue());
			rawEntity.setDefaultValueExpression(serExtendUIControlSetUIModel
					.getDefaultValueExpression());
			rawEntity.setFormatSelectMethod(serExtendUIControlSetUIModel
					.getFormatSelectMethod());
			if (serExtendUIControlSetUIModel.getDisplayIndex() > 0) {
				rawEntity.setDisplayIndex(serExtendUIControlSetUIModel
						.getDisplayIndex());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serExtendUIControlSetUIModel
							.getRefFieldUUID())) {
				rawEntity.setRefFieldUUID(serExtendUIControlSetUIModel
						.getRefFieldUUID());
			}
			rawEntity.setRowNumber(serExtendUIControlSetUIModel.getRowNumber());
			rawEntity.setGetMetaDataUrl(serExtendUIControlSetUIModel
					.getGetMetaDataUrl());
			rawEntity.setRefMetaCodeUUID(serExtendUIControlSetUIModel
					.getRefMetaCodeUUID());
			rawEntity.setSectionId(serExtendUIControlSetUIModel.getSectionId());
			rawEntity.setScreenId(serExtendUIControlSetUIModel.getScreenId());
		}

	}

}
