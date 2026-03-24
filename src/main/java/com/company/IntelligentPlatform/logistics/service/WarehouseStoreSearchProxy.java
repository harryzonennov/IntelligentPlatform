package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreSearchModel;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class WarehouseStoreSearchProxy extends ServiceSearchProxy {
	
	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return WarehouseStoreSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return WarehouseStoreItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.WarehouseStoreItem;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return warehouseStoreItemManager.initItemStatus(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WarehouseStore.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WarehouseStoreItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WarehouseStoreActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(WarehouseStoreActionNode.NODEINST_ACTION_INSTOCK)
				.nodeInstCode(WarehouseStoreActionNode.DOC_ACTION_INSTOCK).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WarehouseStoreActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(WarehouseStoreActionNode.NODEINST_ACTION_ARCHIVE)
				.nodeInstCode(WarehouseStoreActionNode.DOC_ACTION_ARCHIVE).build());
		addDefaultDeliveryDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_CUSTOMER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_SUPPLIER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_PROD_ORG).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_PRODORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_PURORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_SALESORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(WarehouseStoreParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WarehouseStoreParty.PARTY_NODEINST_SUPPORT_ORG).nodeInstCode(WarehouseStoreParty.PARTY_ROLE_SUPPORTORG).build());
		return searchConfigureTemplateNodeList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicItemSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, WarehouseStoreItem.NODENAME);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicSearchNodeConfigureList(searchContext);
		// Search node:[InboundDelivery]
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, WarehouseStore.SENAME);
		return searchNodeConfigList;
	}

	public BSearchResponse searchItemLogList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getItemLogSearchNodeConfigureList();
		searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
		searchContext.setFuzzyFlag(true);
		return bsearchService.doSearchWithContext(
				searchContext, searchNodeConfigList);
	}

	public List<BSearchNodeComConfigure> getItemLogSearchNodeConfigureList() throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(WarehouseStoreItemLog.class).startNodeFlag(true).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(WarehouseStoreItem.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).
				baseNodeInstId(WarehouseStoreItemLog.SENAME).build());
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, WarehouseStoreItem.SENAME);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialStockKeepUnit.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMaterialSKUUUID").
				baseNodeInstId(WarehouseStoreItem.SENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(InboundItem.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("prevDocMatItemUUID").
				baseNodeInstId(WarehouseStoreItem.SENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(OutboundItem.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("nextDocMatItemUUID").
				baseNodeInstId(WarehouseStoreItem.SENAME).build());
		return searchNodeConfigList;
	}

}
