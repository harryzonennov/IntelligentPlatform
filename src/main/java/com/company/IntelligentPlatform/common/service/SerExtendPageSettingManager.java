package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataUIModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingUIModel;
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
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class SerExtendPageSettingManager {

	public static final String METHOD_ConvSerExtendPageSettingToUI = "convSerExtendPageSettingToUI";

	public static final String METHOD_ConvUIToSerExtendPageSetting = "convUIToSerExtendPageSetting";

	public static final String METHOD_ConvExtensionSettingToPageSettingUI = "convExtensionSettingToPageSettingUI";

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;
	
	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Map<String, Map<Integer, String>> pageCategoryMapLan;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<Integer, String> initSwitchFlagMap(String lanuageCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(lanuageCode);
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceExtensionSetting.NODENAME,
						request.getUuid(), SerExtendPageSetting.NODENAME, serviceExtensionSettingManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<ServiceExtensionSetting>) serviceExtensionSetting -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							serviceExtensionSettingManager
									.getPageHeaderModelList(serviceExtensionSetting);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<SerExtendPageSetting>) (serExtendPageSetting, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(serExtendPageSetting.getId());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public Map<Integer, String> initPageCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.pageCategoryMapLan, SerExtendPageSettingUIModel.class,
				"pageCategory");
	}

	public Map<Integer, String> initStatus(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, SerExtendPageSettingUIModel.class,
				"status");
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convExtensionSettingToPageSettingUI(
			ServiceExtensionSetting serviceExtensionSetting,
			SerExtendPageSettingUIModel serExtendPageSettingUIModel) {
		if (serviceExtensionSetting != null) {
			serExtendPageSettingUIModel.setSettingId(serviceExtensionSetting
					.getId());
			serExtendPageSettingUIModel.setRefNodeName(serviceExtensionSetting
					.getRefNodeName());
			serExtendPageSettingUIModel.setRefSEName(serviceExtensionSetting
					.getRefSEName());
		}
	}

	public void convSerExtendPageSettingToUI(
			SerExtendPageSetting serExtendPageSetting,
			SerExtendPageSettingUIModel serExtendPageSettingUIModel) {
		convSerExtendPageSettingToUI(serExtendPageSetting, serExtendPageSettingUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSerExtendPageSettingToUI(
			SerExtendPageSetting serExtendPageSetting,
			SerExtendPageSettingUIModel serExtendPageSettingUIModel, LogonInfo logonInfo) {
		if (serExtendPageSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serExtendPageSetting, serExtendPageSettingUIModel);
			serExtendPageSettingUIModel.setId(serExtendPageSetting.getId());
			serExtendPageSettingUIModel.setNote(serExtendPageSetting.getNote());
			serExtendPageSettingUIModel.setName(serExtendPageSetting.getName());
			serExtendPageSettingUIModel.setNavigationId(serExtendPageSetting
					.getNavigationId());
			serExtendPageSettingUIModel
					.setAccessResourceId(serExtendPageSetting
							.getAccessResourceId());
			serExtendPageSettingUIModel.setAccessPageUrl(serExtendPageSetting
					.getAccessPageUrl());
			serExtendPageSettingUIModel.setTabArray(serExtendPageSetting
					.getTabArray());
			serExtendPageSettingUIModel.setPageCategory(serExtendPageSetting
					.getPageCategory());
			serExtendPageSettingUIModel.setActiveSwitch(serExtendPageSetting
					.getActiveSwitch());
			serExtendPageSettingUIModel.setSystemCategory(serExtendPageSetting
					.getSystemCategory());
			serExtendPageSettingUIModel.setStatus(serExtendPageSetting
					.getStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> pageCategoryMap = this
							.initPageCategoryMap(logonInfo.getLanguageCode());
					serExtendPageSettingUIModel
							.setPageCategoryValue(pageCategoryMap
									.get(serExtendPageSetting.getPageCategory()));
				} catch (ServiceEntityInstallationException e) {
					// continue;
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
							e, serExtendPageSetting.getUuid()));
				}
				try {
					Map<Integer, String> switchFlagMap = this.initSwitchFlagMap(logonInfo.getLanguageCode());
					serExtendPageSettingUIModel.setActiveSwitchValue(switchFlagMap
							.get(serExtendPageSetting.getActiveSwitch()));
				} catch (ServiceEntityInstallationException e) {
					// continue;
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
							e, serExtendPageSetting.getUuid()));
				}
				try {
					Map<Integer, String> statusMap = this.initStatus(logonInfo.getLanguageCode());
					serExtendPageSettingUIModel.setStatusValue(statusMap
							.get(serExtendPageSetting.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					// continue;
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
							e, serExtendPageSetting.getUuid()));
				}
				try {
					Map<Integer, String> systemCategoryMap = standardSystemCategoryProxy.getSystemCategoryMap(logonInfo.getLanguageCode());
					serExtendPageSettingUIModel.setSystemCategoryValue(systemCategoryMap
							.get(serExtendPageSetting.getSystemCategory()));
				} catch (ServiceEntityInstallationException e) {
					// continue;
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
							e, serExtendPageSetting.getUuid()));
				}
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:serExtendPageSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSerExtendPageSetting(
			SerExtendPageSettingUIModel serExtendPageSettingUIModel,
			SerExtendPageSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serExtendPageSettingUIModel, rawEntity);
		rawEntity.setTabArray(serExtendPageSettingUIModel
				.getTabArray());
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageSettingUIModel.getNavigationId())) {
			rawEntity.setNavigationId(serExtendPageSettingUIModel
					.getNavigationId());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageSettingUIModel
						.getAccessResourceId())) {
			rawEntity.setAccessResourceId(serExtendPageSettingUIModel
					.getAccessResourceId());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageSettingUIModel.getAccessPageUrl())) {
			rawEntity.setAccessPageUrl(serExtendPageSettingUIModel
					.getAccessPageUrl());
		}
		if (serExtendPageSettingUIModel.getPageCategory() > 0) {
			rawEntity.setPageCategory(serExtendPageSettingUIModel
					.getPageCategory());
		}
		if (serExtendPageSettingUIModel.getActiveSwitch() > 0) {
			rawEntity.setActiveSwitch(serExtendPageSettingUIModel
					.getActiveSwitch());
		}
		if (serExtendPageSettingUIModel.getSystemCategory() > 0) {
			rawEntity.setSystemCategory(serExtendPageSettingUIModel
					.getSystemCategory());
		}
	}

	public List<ServiceEntityNode> searchInternal(
			SerExtendPageSettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[SerExtendPageSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SerExtendPageSetting.SENAME);
		searchNodeConfig0.setNodeName(SerExtendPageSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(SerExtendPageSetting.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);

		// Search node:[ServiceExtensionSetting]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(ServiceExtensionSetting.SENAME);
		searchNodeConfig1.setNodeName(ServiceExtensionSetting.NODENAME);
		searchNodeConfig1.setNodeInstID(ServiceExtensionSetting.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig1);

		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}
	

}
