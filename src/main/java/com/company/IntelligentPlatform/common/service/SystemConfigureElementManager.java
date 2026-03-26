package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementUIModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class SystemConfigureElementManager {

	public static final String METHOD_ConvSystemConfigureElementToUI = "convSystemConfigureElementToUI";

	public static final String METHOD_ConvUIToSystemConfigureElement = "convUIToSystemConfigureElement";

	public static final String METHOD_ConvResourceToElementUI = "convResourceToElementUI";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;
	
	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SystemConfigureResource.NODENAME,
						request.getUuid(), SystemConfigureElement.NODENAME, systemConfigureCategoryManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<SystemConfigureResource>() {
			@Override
			public List<com.company.IntelligentPlatform.common.controller.PageHeaderModel> execute(SystemConfigureResource systemConfigureResource) throws ServiceEntityConfigureException {
				// How to get the base page header model list
				List<PageHeaderModel> pageHeaderModelList =
						systemConfigureResourceManager.getPageHeaderModelList(DocPageHeaderModelProxy.getDefRequest(systemConfigureResource),
						client);
				return pageHeaderModelList;
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<SystemConfigureElement>() {
			@Override
			public com.company.IntelligentPlatform.common.controller.PageHeaderModel execute(SystemConfigureElement systemConfigureElement, com.company.IntelligentPlatform.common.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				pageHeaderModel.setHeaderName(systemConfigureElement.getId());
				if(!ServiceEntityStringHelper.checkNullString(systemConfigureElement.getName())){
					pageHeaderModel.setHeaderName(systemConfigureElement.getId() + "-" + systemConfigureElement.getName());
				}
				return pageHeaderModel;
			}
		});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public Map<Integer, String> getElementScenarioModeMap(
			SystemConfigureElement systemConfigureElement)
			throws ServiceEntityInstallationException {
		Map<Integer, String> scenarioModeMap = getScenarioModeMap(systemConfigureElement
				.getScenarioModeSwitchProxy());
		return scenarioModeMap;
	}

	public Map<Integer, String> getElementSubScenarioModeMap(
			SystemConfigureElement systemConfigureElement)
			throws ServiceEntityInstallationException {

		Map<Integer, String> subScenarioModeMap = getScenarioModeMap(systemConfigureElement
				.getSubScenarioModeSwitchProxy());
		return subScenarioModeMap;
	}

	/**
	 * 
	 * @param scenarioModeSwitchProxy
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getScenarioModeMap(
			String scenarioModeSwitchProxy)
			throws ServiceEntityInstallationException {
		if (scenarioModeSwitchProxy == null
				|| scenarioModeSwitchProxy
						.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			Map<Integer, String> scenarioModeMap = serviceDropdownListHelper
					.getUIDropDownMap(SystemConfigureElementUIModel.class,
							"scenarioMode");
			return scenarioModeMap;
		} else {
			try {
				Class<?> switchClass = Class.forName(scenarioModeSwitchProxy);
				ISystemConfigureSwitch configureSwitch = (ISystemConfigureSwitch) springContextBeanService
						.getBean(ServiceEntityStringHelper
								.headerToLowerCase(switchClass.getSimpleName()));
				return configureSwitch.getSwitchMap();
			} catch (ClassNotFoundException e) {
				Map<Integer, String> scenarioModeMap = serviceDropdownListHelper
						.getUIDropDownMap(SystemConfigureElementUIModel.class,
								"scenarioMode");
				return scenarioModeMap;
			}
		}
	}

	public void convSystemConfigureElementToUI(
			SystemConfigureElement systemConfigureElement,
			SystemConfigureElementUIModel systemConfigureElementUIModel) throws ServiceEntityInstallationException {
		convSystemConfigureElementToUI(systemConfigureElement, systemConfigureElementUIModel, null);
	}

	public void convSystemConfigureElementToUI(
			SystemConfigureElement systemConfigureElement,
			SystemConfigureElementUIModel systemConfigureElementUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemConfigureElement != null) {
			systemConfigureElementUIModel.setUuid(systemConfigureElement
					.getUuid());
			systemConfigureElementUIModel
					.setParentNodeUUID(systemConfigureElement
							.getParentNodeUUID());
			systemConfigureElementUIModel
					.setRootNodeUUID(systemConfigureElement.getRootNodeUUID());
			systemConfigureElementUIModel.setClient(systemConfigureElement
					.getClient());
			systemConfigureElementUIModel.setId(systemConfigureElement.getId());
			systemConfigureElementUIModel.setName(systemConfigureElement
					.getName());
			systemConfigureElementUIModel.setNote(systemConfigureElement
					.getNote());
			systemConfigureElementUIModel
					.setScenarioMode(systemConfigureElement.getScenarioMode());
			if(logonInfo != null){
				Map<Integer, String> scenarioModeMap = systemConfigureCategoryManager.initScenarioModeMap(logonInfo.getLanguageCode());
				systemConfigureElementUIModel
						.setScenarioModeValue(scenarioModeMap
								.get(systemConfigureElement.getScenarioMode()));
			}
			systemConfigureElementUIModel
					.setSubScenarioMode(systemConfigureElement
							.getSubScenarioMode());
			if(logonInfo != null){
				Map<Integer, String> subScenarioModeMap = systemConfigureCategoryManager.initSubScenarioModeMap(logonInfo.getLanguageCode());
				systemConfigureElementUIModel
						.setSubScenarioModeValue(subScenarioModeMap
								.get(systemConfigureElement.getScenarioMode()));
			}
			systemConfigureElementUIModel
					.setSubScenarioModeSwitchProxy(systemConfigureElement
							.getSubScenarioModeSwitchProxy());
			systemConfigureElementUIModel
					.setScenarioModeSwitchProxy(systemConfigureElement
							.getScenarioModeSwitchProxy());
			systemConfigureElementUIModel
					.setSubScenarioMode(systemConfigureElement
							.getSubScenarioMode());
			systemConfigureElementUIModel
					.setStandardSystemCategory(systemConfigureElement
							.getStandardSystemCategory());
			systemConfigureElementUIModel.setElementType(systemConfigureElement
					.getElementType());
			if(logonInfo != null){
				Map<Integer, String> subScenarioModeMap = systemConfigureCategoryManager.initSubScenarioModeMap(logonInfo.getLanguageCode());
				systemConfigureElementUIModel
						.setSubScenarioModeValue(subScenarioModeMap
								.get(systemConfigureElement.getScenarioMode()));
				Map<Integer, String> elementTypeMap = systemConfigureCategoryManager.initElementTypeMap(logonInfo.getLanguageCode());
				systemConfigureElementUIModel
						.setElementTypeValue(elementTypeMap
								.get(systemConfigureElement.getElementType()));
				Map<Integer, String> standardSystemCategoryMap = systemConfigureCategoryManager.initSystemStandardCategoryMap(logonInfo.getLanguageCode());
				String systemStandardCategoryValue = standardSystemCategoryMap
						.get(systemConfigureElement.getStandardSystemCategory());
				systemConfigureElementUIModel
						.setStandardSystemCategoryValue(systemStandardCategoryValue);
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:systemConfigureElement
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemConfigureElement(
			SystemConfigureElementUIModel systemConfigureElementUIModel,
			SystemConfigureElement rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureElementUIModel.getUuid())) {
			rawEntity.setUuid(systemConfigureElementUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureElementUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(systemConfigureElementUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureElementUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(systemConfigureElementUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureElementUIModel.getClient())) {
			rawEntity.setClient(systemConfigureElementUIModel.getClient());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureElementUIModel.getRefUUID())) {
			rawEntity.setRefUUID(systemConfigureElementUIModel.getRefUUID());
		}
		rawEntity.setId(systemConfigureElementUIModel.getId());
		rawEntity.setName(systemConfigureElementUIModel.getName());
		rawEntity
				.setElementType(systemConfigureElementUIModel.getElementType());
		rawEntity.setScenarioMode(systemConfigureElementUIModel
				.getScenarioMode());
		rawEntity.setSubScenarioMode(systemConfigureElementUIModel
				.getSubScenarioMode());
		rawEntity.setSubScenarioModeSwitchProxy(systemConfigureElementUIModel
				.getSubScenarioModeSwitchProxy());
		rawEntity.setScenarioModeSwitchProxy(systemConfigureElementUIModel
				.getScenarioModeSwitchProxy());
	}

	public void convResourceToElementUI(
			SystemConfigureResource systemConfigureResource,
			SystemConfigureElementUIModel systemConfigureElementUnionUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureElementUnionUIModel != null) {
			systemConfigureElementUnionUIModel
					.setParentNodeId(systemConfigureResource.getId());
		}
	}
	
	
}
