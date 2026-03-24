package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderItemManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdOrderItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected RepairProdItemReqProposalServiceUIModelExtension repairProdItemReqProposalServiceUIModelExtension;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;
	
	@Autowired
	protected RepairProdOrderItemManager repairProdOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(repairProdItemReqProposalServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion repairProdOrderItemExtensionUnion = new ServiceUIModelExtensionUnion();
		repairProdOrderItemExtensionUnion
				.setNodeInstId(RepairProdOrderItem.NODENAME);
		repairProdOrderItemExtensionUnion
				.setNodeName(RepairProdOrderItem.NODENAME);

		// UI Model Configure of node:[RepairProdOrderItem]
		UIModelNodeMapConfigure repairProdOrderItemMap = new UIModelNodeMapConfigure();
		repairProdOrderItemMap.setSeName(RepairProdOrderItem.SENAME);
		repairProdOrderItemMap.setNodeName(RepairProdOrderItem.NODENAME);
		repairProdOrderItemMap.setNodeInstID(RepairProdOrderItem.NODENAME);
		repairProdOrderItemMap.setHostNodeFlag(true);
		repairProdOrderItemMap.setLogicManager(repairProdOrderItemManager);
		Class<?>[] repairProdOrderItemConvToUIParas = {
				RepairProdOrderItem.class, RepairProdOrderItemUIModel.class };
		repairProdOrderItemMap
				.setConvToUIMethodParas(repairProdOrderItemConvToUIParas);
		repairProdOrderItemMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvRepairProdOrderItemToUI);
		Class<?>[] RepairProdOrderItemConvUIToParas = {
				RepairProdOrderItemUIModel.class, RepairProdOrderItem.class };
		repairProdOrderItemMap
				.setConvUIToMethodParas(RepairProdOrderItemConvUIToParas);
		repairProdOrderItemMap
				.setConvUIToMethod(RepairProdOrderItemManager.METHOD_ConvUIToRepairProdOrderItem);
		uiModelNodeMapList.add(repairProdOrderItemMap);

		// UI Model Configure of node:[repairProdOrder]
		UIModelNodeMapConfigure repairProdOrderMap = new UIModelNodeMapConfigure();
		repairProdOrderMap.setSeName(RepairProdOrder.SENAME);
		repairProdOrderMap.setNodeName(RepairProdOrder.NODENAME);
		repairProdOrderMap.setNodeInstID(RepairProdOrder.SENAME);
		repairProdOrderMap.setLogicManager(repairProdOrderItemManager);
		repairProdOrderMap.setBaseNodeInstID(RepairProdOrderItem.NODENAME);
		repairProdOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] repairProdOrderConvToUIParas = { RepairProdOrder.class,
				RepairProdOrderItemUIModel.class };
		repairProdOrderMap.setConvToUIMethodParas(repairProdOrderConvToUIParas);
		repairProdOrderMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvRepairProdOrderToItemUI);
		uiModelNodeMapList.add(repairProdOrderMap);

		// UI Model Configure of node:[repairProdOrder]
		UIModelNodeMapConfigure orderMaterialSKUMap = new UIModelNodeMapConfigure();
		orderMaterialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		orderMaterialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		orderMaterialSKUMap.setNodeInstID("OrderMaterialStockKeepUnit");
		orderMaterialSKUMap.setBaseNodeInstID(RepairProdOrder.SENAME);
		orderMaterialSKUMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		orderMaterialSKUMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		orderMaterialSKUMap.setLogicManager(repairProdOrderItemManager);
		Class<?>[] orderMaterialSKUMapConvToUIParas = {
				MaterialStockKeepUnit.class, RepairProdOrderItemUIModel.class };
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
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvOrderMaterialSKUToItemUI);
		uiModelNodeMapList.add(orderMaterialSKUMap);

		// UI Model Configure of node:[reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(RepairProdOrderItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(RepairProdOrderItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(RepairProdOrderItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(RepairProdOrderItem.NODENAME));

		Class<?>[] itemMaterialSKUConvToUIParas = {
				MaterialStockKeepUnit.class, RepairProdOrderItemUIModel.class };
		
		List<UIModelNodeMapConfigure> itemMaterialSKUMapList = docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						RepairProdOrderItem.NODENAME,
						RepairProdOrderItemManager.METHOD_ConvItemMaterialSKUToUI, null, itemMaterialSKUConvToUIParas);
		itemMaterialSKUMapList.get(0).setLogicManager(repairProdOrderItemManager);
		uiModelNodeMapList.addAll(itemMaterialSKUMapList);
		

		// UI Model Configure of node:[ItemBillOfMaterialItem]
		UIModelNodeMapConfigure itemBillOfMaterialItemMap = new UIModelNodeMapConfigure();
		itemBillOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		itemBillOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		itemBillOfMaterialItemMap.setNodeInstID("ItemBillOfMaterialItem");
		itemBillOfMaterialItemMap
				.setBaseNodeInstID(RepairProdOrderItem.NODENAME);
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
				BillOfMaterialItem.class, RepairProdOrderItemUIModel.class };
		itemBillOfMaterialItemMap
				.setConvToUIMethodParas(itemBillOfMaterialItemConvToUIParas);
		itemBillOfMaterialItemMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvItemBillOfMaterialItemToUI);
		uiModelNodeMapList.add(itemBillOfMaterialItemMap);
		repairProdOrderItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(repairProdOrderItemExtensionUnion);
		return resultList;
	}

}
