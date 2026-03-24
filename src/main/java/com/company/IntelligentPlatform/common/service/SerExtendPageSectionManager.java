package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSectionSearchModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSectionUIModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class SerExtendPageSectionManager {

	public static final String METHOD_ConvSerExtendPageSectionToUI = "convSerExtendPageSectionToUI";

	public static final String METHOD_ConvUIToSerExtendPageSection = "convUIToSerExtendPageSection";

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected BsearchService bsearchService;

	protected Map<Integer, String> sectionCategoryMap;

	protected Map<Integer, String> switchFlagMap;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<Integer, String> initSwitchFlagMap()
			throws ServiceEntityInstallationException {
		if (this.switchFlagMap == null) {
			this.switchFlagMap = standardSwitchProxy.getSimpleSwitchMap();
		}
		return this.switchFlagMap;
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SerExtendPageSetting.NODENAME,
						request.getUuid(), SerExtendPageSection.NODENAME, serviceExtensionSettingManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<SerExtendPageSetting>) serExtendPageSetting -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							serExtendPageSettingManager
									.getPageHeaderModelList(new SimpleSEJSONRequest(serExtendPageSetting.getUuid(),
											serExtendPageSetting.getParentNodeUUID()), client);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<SerExtendPageSection>) (serExtendPageSection, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(serExtendPageSection.getId());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public Map<Integer, String> initSectionCategoryMap()
			throws ServiceEntityInstallationException {
		if (this.sectionCategoryMap == null) {
			this.sectionCategoryMap = serviceDropdownListHelper
					.getUIDropDownMap(SerExtendPageSectionUIModel.class,
							"sectionCategory");
		}
		return this.sectionCategoryMap;
	}

	/**
	 * Core Logic to create new Extend page Section instance
	 * 
	 * @param baseUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public SerExtendPageSection newSerExtendPageSection(String baseUUID,
			String client) throws ServiceEntityConfigureException,
			ServiceModuleProxyException {
		SerExtendPageSetting serExtendPageSetting = (SerExtendPageSetting) serviceExtensionSettingManager
				.getEntityNodeByKey(baseUUID,
						IServiceEntityNodeFieldConstant.UUID,
						SerExtendPageSetting.NODENAME, client, null);
		SerExtendPageSettingServiceModel serExtendPageSettingServiceModel = (SerExtendPageSettingServiceModel) serviceExtensionSettingManager
				.loadServiceModule(SerExtendPageSettingServiceModel.class,
						serExtendPageSetting);
		int processIndex = 1;
		if(!ServiceCollectionsHelper.checkNullList(serExtendPageSettingServiceModel.getSerExtendPageSectionList())){
			for(SerExtendPageSectionServiceModel serExtendPageSectionServiceModel:serExtendPageSettingServiceModel.getSerExtendPageSectionList()){
				if(serExtendPageSectionServiceModel.getSerExtendPageSection().getProcessIndex() > processIndex){
					processIndex = serExtendPageSectionServiceModel.getSerExtendPageSection().getProcessIndex();
				}
			}
			processIndex = processIndex + 1;
		}
		SerExtendPageSection serExtendPageSection = (SerExtendPageSection) serviceExtensionSettingManager
				.newEntityNode(serExtendPageSetting,
						SerExtendPageSection.NODENAME);
		serExtendPageSection.setProcessIndex(processIndex);
		return serExtendPageSection;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSerExtendPageSectionToUI(
			SerExtendPageSection serExtendPageSection,
			SerExtendPageSectionUIModel serExtendPageSectionUIModel) {
		if (serExtendPageSection != null) {
			if (!ServiceEntityStringHelper.checkNullString(serExtendPageSection
					.getUuid())) {
				serExtendPageSectionUIModel.setUuid(serExtendPageSection
						.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(serExtendPageSection
					.getParentNodeUUID())) {
				serExtendPageSectionUIModel
						.setParentNodeUUID(serExtendPageSection
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serExtendPageSection
					.getRootNodeUUID())) {
				serExtendPageSectionUIModel
						.setRootNodeUUID(serExtendPageSection.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serExtendPageSection
					.getClient())) {
				serExtendPageSectionUIModel.setClient(serExtendPageSection
						.getClient());
			}
			serExtendPageSectionUIModel.setId(serExtendPageSection.getId());
			serExtendPageSectionUIModel.setName(serExtendPageSection.getName());
			serExtendPageSectionUIModel.setTabId(serExtendPageSection
					.getTabId());
			serExtendPageSectionUIModel.setProcessIndex(serExtendPageSection
					.getProcessIndex());
			serExtendPageSectionUIModel.setSectionCategory(serExtendPageSection
					.getSectionCategory());
			serExtendPageSectionUIModel.setRefDomId(serExtendPageSection
					.getRefDomId());
			serExtendPageSectionUIModel.setVisibleSwitch(serExtendPageSection
					.getVisibleSwitch());
			serExtendPageSectionUIModel
					.setVisibleExpression(serExtendPageSection
							.getVisibleExpression());
			serExtendPageSectionUIModel
					.setVisibleActionCode(serExtendPageSection
							.getVisibleActionCode());
			serExtendPageSectionUIModel.setVisibleSwitch(serExtendPageSection
					.getVisibleSwitch());
			try {
				Map<Integer, String> sectionCategoryMap = this
						.initSectionCategoryMap();
				serExtendPageSectionUIModel
						.setSectionCategoryValue(sectionCategoryMap
								.get(serExtendPageSection.getSectionCategory()));
			} catch (ServiceEntityInstallationException e) {
				// continue;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, serExtendPageSection.getUuid()));
			}
			try {
				Map<Integer, String> switchFlagMap = this.initSwitchFlagMap();
				serExtendPageSectionUIModel.setVisibleSwitchValue(switchFlagMap
						.get(serExtendPageSection.getVisibleSwitch()));
			} catch (ServiceEntityInstallationException e) {
				// continue;
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, serExtendPageSection.getUuid()));
			}

		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:serExtendPageSection
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSerExtendPageSection(
			SerExtendPageSectionUIModel serExtendPageSectionUIModel,
			SerExtendPageSection rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serExtendPageSectionUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(serExtendPageSectionUIModel.getTabId())) {
			rawEntity.setTabId(serExtendPageSectionUIModel.getTabId());
		}
		if (serExtendPageSectionUIModel.getProcessIndex() > 0) {
			rawEntity.setProcessIndex(serExtendPageSectionUIModel
					.getProcessIndex());
		}
		if (serExtendPageSectionUIModel.getSectionCategory() > 0) {
			rawEntity.setSectionCategory(serExtendPageSectionUIModel
					.getSectionCategory());
		}
		if (serExtendPageSectionUIModel.getVisibleSwitch() > 0) {
			rawEntity.setVisibleSwitch(serExtendPageSectionUIModel
					.getVisibleSwitch());
		}
		rawEntity.setRefDomId(serExtendPageSectionUIModel.getRefDomId());
		rawEntity.setVisibleExpression(serExtendPageSectionUIModel
				.getVisibleExpression());
		rawEntity.setVisibleActionCode(serExtendPageSectionUIModel
				.getVisibleActionCode());
	}

	public List<ServiceEntityNode> searchInternal(
			SerExtendPageSectionSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[SerExtendPageSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SerExtendPageSection.SENAME);
		searchNodeConfig0.setNodeName(SerExtendPageSection.NODENAME);
		searchNodeConfig0.setNodeInstID(SerExtendPageSection.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);

		// Search node:[ServiceExtensionSetting]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(SerExtendPageSetting.SENAME);
		searchNodeConfig1.setNodeName(SerExtendPageSetting.NODENAME);
		searchNodeConfig1.setNodeInstID(SerExtendPageSetting.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig1);

		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
