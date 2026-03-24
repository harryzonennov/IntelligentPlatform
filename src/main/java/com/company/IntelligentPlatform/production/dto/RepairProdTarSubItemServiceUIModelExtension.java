package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.RepairProdTargetMatItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.RepairProdTarSubItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdTarSubItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected RepairProdTargetMatItemManager repairProdTargetMatItemManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion repairProdTarSubItemExtensionUnion = new ServiceUIModelExtensionUnion();
		repairProdTarSubItemExtensionUnion
				.setNodeInstId(RepairProdTarSubItem.NODENAME);
		repairProdTarSubItemExtensionUnion
				.setNodeName(RepairProdTarSubItem.NODENAME);

		// UI Model Configure of node:[RepairProdTargetMatItem]
		UIModelNodeMapConfigure repairProdTarSubItemMap = new UIModelNodeMapConfigure();
		repairProdTarSubItemMap.setSeName(RepairProdTarSubItem.SENAME);
		repairProdTarSubItemMap.setNodeName(RepairProdTarSubItem.NODENAME);
		repairProdTarSubItemMap
				.setNodeInstID(RepairProdTarSubItem.NODENAME);
		repairProdTarSubItemMap.setHostNodeFlag(true);
		repairProdTarSubItemMap.setLogicManager(repairProdTargetMatItemManager);
		Class<?>[] repairProdTarSubItemConvToUIParas = {
				RepairProdTarSubItem.class,
				RepairProdTarSubItemUIModel.class };
		repairProdTarSubItemMap
				.setConvToUIMethodParas(repairProdTarSubItemConvToUIParas);
		repairProdTarSubItemMap
				.setConvToUIMethod(RepairProdTargetMatItemManager.METHOD_ConvRepairProdTarSubItemToUI);
		Class<?>[] RepairProdTarSubItemConvUIToParas = {
				RepairProdTarSubItemUIModel.class,
				RepairProdTarSubItem.class };
		repairProdTarSubItemMap
				.setConvUIToMethodParas(RepairProdTarSubItemConvUIToParas);
		repairProdTarSubItemMap
				.setConvUIToMethod(RepairProdTargetMatItemManager.METHOD_ConvUIToRepairProdTarSubItem);
		uiModelNodeMapList.add(repairProdTarSubItemMap);

		// UI Model Configure of node:[billOfMaterialItem]
		UIModelNodeMapConfigure billOfMaterialItemMap = new UIModelNodeMapConfigure();
		billOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		billOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setNodeInstID(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setBaseNodeInstID(RepairProdTarSubItem.NODENAME);
		billOfMaterialItemMap.setLogicManager(repairProdTargetMatItemManager);
		billOfMaterialItemMap.setServiceEntityManager(billOfMaterialOrderManager);
		billOfMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		
		List<SearchConfigConnectCondition> billOfMaterialItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition billOfMaterialItemCondition0 = new SearchConfigConnectCondition();
		billOfMaterialItemCondition0.setSourceFieldName("refBOMItemUUID");
		billOfMaterialItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		billOfMaterialItemConditionList.add(billOfMaterialItemCondition0);
		billOfMaterialItemMap.setConnectionConditions(billOfMaterialItemConditionList);
		Class<?>[] productionOrderConvToUIParas = {
				BillOfMaterialItem.class, RepairProdTarSubItemUIModel.class };
		billOfMaterialItemMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		billOfMaterialItemMap
				.setConvToUIMethod(RepairProdTargetMatItemManager.METHOD_ConvBillOfOrderItemToUI);
		uiModelNodeMapList.add(billOfMaterialItemMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(RepairProdTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(RepairProdTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(RepairProdTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(RepairProdTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(RepairProdTarSubItem.NODENAME));

		repairProdTarSubItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(repairProdTarSubItemExtensionUnion);
		return resultList;
	}

}
