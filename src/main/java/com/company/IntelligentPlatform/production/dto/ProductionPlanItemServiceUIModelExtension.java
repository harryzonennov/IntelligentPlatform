package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanItemManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;

import com.company.IntelligentPlatform.production.model.ProductionPlanItemParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProductionPlanItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProdPlanItemReqProposalServiceUIModelExtension prodPlanItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected ProductionPlanItemManager productionPlanItemManager;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(prodPlanItemReqProposalServiceUIModelExtension);
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanItemParty.SENAME,
				ProductionPlanItemParty.NODENAME,
				ProductionPlanItemParty.PARTY_NODEINST_PUR_ORG,
				productionPlanManager,
				ProductionPlanItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanItemParty.SENAME,
				ProductionPlanItemParty.NODENAME,
				ProductionPlanItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				productionPlanManager,
				ProductionPlanItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanItemParty.SENAME,
				ProductionPlanItemParty.NODENAME,
				ProductionPlanItemParty.PARTY_NODEINST_SOLD_ORG,
				productionPlanManager,
				ProductionPlanItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanItemParty.SENAME,
				ProductionPlanItemParty.NODENAME,
				ProductionPlanItemParty.PARTY_NODEINST_PROD_ORG,
				productionPlanManager,
				ProductionPlanItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionPlanItemExtensionUnion = new ServiceUIModelExtensionUnion();
		productionPlanItemExtensionUnion
				.setNodeInstId(ProductionPlanItem.NODENAME);
		productionPlanItemExtensionUnion
				.setNodeName(ProductionPlanItem.NODENAME);

		// UI Model Configure of node:[ProductionPlanItem]
		UIModelNodeMapConfigure productionPlanItemMap = new UIModelNodeMapConfigure();
		productionPlanItemMap.setSeName(ProductionPlanItem.SENAME);
		productionPlanItemMap.setNodeName(ProductionPlanItem.NODENAME);
		productionPlanItemMap.setNodeInstID(ProductionPlanItem.NODENAME);
		productionPlanItemMap.setHostNodeFlag(true);
		productionPlanItemMap.setLogicManager(productionPlanItemManager);
		Class<?>[] productionPlanItemConvToUIParas = {
				ProductionPlanItem.class, ProductionPlanItemUIModel.class };
		productionPlanItemMap
				.setConvToUIMethodParas(productionPlanItemConvToUIParas);
		productionPlanItemMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvProductionPlanItemToUI);
		Class<?>[] ProductionPlanItemConvUIToParas = {
				ProductionPlanItemUIModel.class, ProductionPlanItem.class };
		productionPlanItemMap
				.setConvUIToMethodParas(ProductionPlanItemConvUIToParas);
		productionPlanItemMap
				.setConvUIToMethod(ProductionPlanItemManager.METHOD_ConvUIToProductionPlanItem);
		uiModelNodeMapList.add(productionPlanItemMap);

		// UI Model Configure of node:[productionPlan]
		UIModelNodeMapConfigure productionPlanMap = new UIModelNodeMapConfigure();
		productionPlanMap.setSeName(ProductionPlan.SENAME);
		productionPlanMap.setNodeName(ProductionPlan.NODENAME);
		productionPlanMap.setNodeInstID(ProductionPlan.SENAME);
		productionPlanMap.setBaseNodeInstID(ProductionPlanItem.NODENAME);
		productionPlanMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionPlanConvToUIParas = { ProductionPlan.class,
				ProductionPlanItemUIModel.class };
		productionPlanMap.setConvToUIMethodParas(productionPlanConvToUIParas);
		productionPlanMap.setLogicManager(productionPlanItemManager);
		productionPlanMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvProductionPlanToItemUI);
		uiModelNodeMapList.add(productionPlanMap);

		// UI Model Configure of node:[productionPlan]
		UIModelNodeMapConfigure planMaterialSKUMap = new UIModelNodeMapConfigure();
		planMaterialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		planMaterialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		planMaterialSKUMap.setNodeInstID("OrderMaterialStockKeepUnit");
		planMaterialSKUMap.setBaseNodeInstID(ProductionPlan.SENAME);
		planMaterialSKUMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		planMaterialSKUMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		Class<?>[] planMaterialSKUMapConvToUIParas = {
				MaterialStockKeepUnit.class, ProductionPlanItemUIModel.class };
		List<SearchConfigConnectCondition> planMaterialSKUConditionList = new ArrayList<>();
		SearchConfigConnectCondition planMaterialSKUCondition0 = new SearchConfigConnectCondition();
		planMaterialSKUCondition0.setSourceFieldName("refMaterialSKUUUID");
		planMaterialSKUCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		planMaterialSKUConditionList.add(planMaterialSKUCondition0);
		planMaterialSKUMap.setLogicManager(productionPlanItemManager);
		planMaterialSKUMap
				.setConnectionConditions(planMaterialSKUConditionList);
		planMaterialSKUMap
				.setConvToUIMethodParas(planMaterialSKUMapConvToUIParas);
		planMaterialSKUMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvPlanMaterialSKUToItemUI);
		uiModelNodeMapList.add(planMaterialSKUMap);

		// UI Model Configure of node:[reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProductionPlanItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefParentDocMapConfigureList(ProductionPlanItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(ProductionPlanItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(ProductionPlanItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProductionPlanItem.NODENAME));		
		uiModelNodeMapList
		.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						ProductionPlanItem.NODENAME));
		
		Class<?>[] itemMaterialSKUConvToUIParas = {
				MaterialStockKeepUnit.class, ProductionPlanItemUIModel.class };
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						ProductionPlanItem.NODENAME,
						ProductionPlanItemManager.METHOD_ConvItemMaterialSKUToUI,  productionPlanItemManager,itemMaterialSKUConvToUIParas));


		// UI Model Configure of node:[ItemBillOfMaterialItem]
		UIModelNodeMapConfigure itemBillOfMaterialItemMap = new UIModelNodeMapConfigure();
		itemBillOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		itemBillOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		itemBillOfMaterialItemMap.setNodeInstID("ItemBillOfMaterialItem");
		itemBillOfMaterialItemMap
				.setBaseNodeInstID(ProductionPlanItem.NODENAME);
		itemBillOfMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		itemBillOfMaterialItemMap
				.setServiceEntityManager(billOfMaterialOrderManager);
		List<SearchConfigConnectCondition> itemBillOfMaterialItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemBillOfMaterialItemCondition0 = new SearchConfigConnectCondition();
		itemBillOfMaterialItemCondition0.setSourceFieldName("refBOMItemUUID");
		itemBillOfMaterialItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemBillOfMaterialItemConditionList
				.add(itemBillOfMaterialItemCondition0);
		itemBillOfMaterialItemMap
				.setConnectionConditions(itemBillOfMaterialItemConditionList);
		Class<?>[] itemBillOfMaterialItemConvToUIParas = {
				BillOfMaterialItem.class, ProductionPlanItemUIModel.class };
		itemBillOfMaterialItemMap
				.setConvToUIMethodParas(itemBillOfMaterialItemConvToUIParas);
		itemBillOfMaterialItemMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvItemBillOfMaterialItemToUI);
		uiModelNodeMapList.add(itemBillOfMaterialItemMap);
		productionPlanItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionPlanItemExtensionUnion);
		return resultList;
	}

}
