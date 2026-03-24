package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InventoryTransferOrderSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected InventoryTransferItemServiceUIModelExtension inventoryTransferItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return InventoryTransferOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return InventoryTransferItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return inventoryTransferOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return inventoryTransferOrderManager.initStatusMap(languageCode);
	}

	@Override
	public BSearchResponse searchDocList(SearchContext searchContext)
			throws SearchConfigureException, ServiceEntityConfigureException, ServiceEntityInstallationException,
			AuthorizationException, LogonInfoException {
		String[] fieldNameArray = SearchDocConfigHelper.genDefaultMaterialFieldNameArray();
		List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
		searchContext.setFieldNameArray(fieldNameArray);
		searchContext.setFuzzyFlag(true);
		return bsearchService.doSearchWithContext(
				searchContext, searchNodeConfigList);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryTransferOrder.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryTransferItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryTransferOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InventoryTransferOrderActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(InventoryTransferOrderActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryTransferOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InventoryTransferOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(InventoryTransferOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InventoryTransferOrderParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InventoryTransferOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InventoryTransferOrderParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(InventoryTransferOrderParty.PARTY_ROLE_SALESORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(InventoryTransferOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InventoryTransferOrderParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InventoryTransferOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InventoryTransferOrderParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(InventoryTransferOrderParty.PARTY_ROLE_PURORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InventoryTransferOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InventoryTransferOrderParty.PARTY_NODEINST_PROD_ORG).nodeInstCode(InventoryTransferOrderParty.PARTY_ROLE_PRODORG).build());
		addDefaultDeliveryDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryTransferItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		return searchConfigureTemplateNodeList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicItemSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InventoryTransferOrder.SENAME,
				"refInboundWarehouse", "refInboundWarehouseArea", null, null);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InventoryTransferOrder.SENAME,
				"refOutboundWarehouse", "refOutboundWarehouseArea", null, null);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicSearchNodeConfigureList(searchContext);
		// Search node:[InventoryTransferOrder]
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InventoryTransferOrder.SENAME,
				"refInboundWarehouse", "refInboundWarehouseArea", null, null);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InventoryTransferOrder.SENAME,
				"refOutboundWarehouse", "refOutboundWarehouseArea", null, null);
		return searchNodeConfigList;
	}
	
}
