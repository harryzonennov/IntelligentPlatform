package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;

import com.company.IntelligentPlatform.production.model.ProductionOrderItemParty;
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
public class ProductionOrderItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProdOrderItemReqProposalServiceUIModelExtension prodOrderItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(prodOrderItemReqProposalServiceUIModelExtension);
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderItemParty.SENAME,
				ProductionOrderItemParty.NODENAME,
				ProductionOrderItemParty.PARTY_NODEINST_PUR_ORG,
				productionOrderManager,
				ProductionOrderItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderItemParty.SENAME,
				ProductionOrderItemParty.NODENAME,
				ProductionOrderItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				productionOrderManager,
				ProductionOrderItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderItemParty.SENAME,
				ProductionOrderItemParty.NODENAME,
				ProductionOrderItemParty.PARTY_NODEINST_SOLD_ORG,
				productionOrderManager,
				ProductionOrderItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderItemParty.SENAME,
				ProductionOrderItemParty.NODENAME,
				ProductionOrderItemParty.PARTY_NODEINST_PROD_ORG,
				productionOrderManager,
				ProductionOrderItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionOrderItemExtensionUnion = new ServiceUIModelExtensionUnion();
		productionOrderItemExtensionUnion
				.setNodeInstId(ProductionOrderItem.NODENAME);
		productionOrderItemExtensionUnion
				.setNodeName(ProductionOrderItem.NODENAME);

		// UI Model Configure of node:[ProductionOrderItem]
		UIModelNodeMapConfigure productionOrderItemMap = new UIModelNodeMapConfigure();
		productionOrderItemMap.setSeName(ProductionOrderItem.SENAME);
		productionOrderItemMap.setNodeName(ProductionOrderItem.NODENAME);
		productionOrderItemMap.setNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderItemMap.setHostNodeFlag(true);
		productionOrderItemMap.setLogicManager(productionOrderItemManager);
		Class<?>[] productionOrderItemConvToUIParas = {
				ProductionOrderItem.class, ProductionOrderItemUIModel.class };
		productionOrderItemMap
				.setConvToUIMethodParas(productionOrderItemConvToUIParas);
		productionOrderItemMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderItemToUI);
		Class<?>[] ProductionOrderItemConvUIToParas = {
				ProductionOrderItemUIModel.class, ProductionOrderItem.class };
		productionOrderItemMap
				.setConvUIToMethodParas(ProductionOrderItemConvUIToParas);
		productionOrderItemMap
				.setConvUIToMethod(ProductionOrderItemManager.METHOD_ConvUIToProductionOrderItem);
		uiModelNodeMapList.add(productionOrderItemMap);

		// UI Model Configure of node:[productionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setLogicManager(productionOrderItemManager);
		productionOrderMap.setBaseNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProductionOrderItemUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderToItemUI);
		uiModelNodeMapList.add(productionOrderMap);

		// UI Model Configure of node:[productionOrder]
		UIModelNodeMapConfigure orderMaterialSKUMap = new UIModelNodeMapConfigure();
		orderMaterialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		orderMaterialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		orderMaterialSKUMap.setNodeInstID("OrderMaterialStockKeepUnit");
		orderMaterialSKUMap.setBaseNodeInstID(ProductionOrder.SENAME);
		orderMaterialSKUMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		orderMaterialSKUMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		orderMaterialSKUMap.setLogicManager(productionOrderItemManager);
		Class<?>[] orderMaterialSKUMapConvToUIParas = {
				MaterialStockKeepUnit.class, ProductionOrderItemUIModel.class };
		List<SearchConfigConnectCondition> orderMaterialSKUConditionList = new ArrayList<>();
		SearchConfigConnectCondition orderMaterialSKUCondition0 = new SearchConfigConnectCondition();
		orderMaterialSKUCondition0.setSourceFieldName("refMaterialSKUUUID");
		orderMaterialSKUCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		orderMaterialSKUConditionList.add(orderMaterialSKUCondition0);
		orderMaterialSKUMap
				.setConnectionConditions(orderMaterialSKUConditionList);
		orderMaterialSKUMap
				.setConvToUIMethodParas(orderMaterialSKUMapConvToUIParas);
		orderMaterialSKUMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvOrderMaterialSKUToItemUI);
		uiModelNodeMapList.add(orderMaterialSKUMap);

		// UI Model Configure of node:[reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProductionOrderItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(ProductionOrderItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(ProductionOrderItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProductionOrderItem.NODENAME));

		Class<?>[] itemMaterialSKUConvToUIParas = {
				MaterialStockKeepUnit.class, ProductionOrderItemUIModel.class };
		
		List<UIModelNodeMapConfigure> itemMaterialSKUMapList = docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						ProductionOrderItem.NODENAME,
						ProductionOrderItemManager.METHOD_ConvItemMaterialSKUToUI, null, itemMaterialSKUConvToUIParas);
		itemMaterialSKUMapList.get(0).setLogicManager(productionOrderItemManager);
		uiModelNodeMapList.addAll(itemMaterialSKUMapList);
		

		// UI Model Configure of node:[ItemBillOfMaterialItem]
		UIModelNodeMapConfigure itemBillOfMaterialItemMap = new UIModelNodeMapConfigure();
		itemBillOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		itemBillOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		itemBillOfMaterialItemMap.setNodeInstID("ItemBillOfMaterialItem");
		itemBillOfMaterialItemMap
				.setBaseNodeInstID(ProductionOrderItem.NODENAME);
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
				BillOfMaterialItem.class, ProductionOrderItemUIModel.class };
		itemBillOfMaterialItemMap
				.setConvToUIMethodParas(itemBillOfMaterialItemConvToUIParas);
		itemBillOfMaterialItemMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvItemBillOfMaterialItemToUI);
		uiModelNodeMapList.add(itemBillOfMaterialItemMap);
		productionOrderItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionOrderItemExtensionUnion);
		return resultList;
	}

}
