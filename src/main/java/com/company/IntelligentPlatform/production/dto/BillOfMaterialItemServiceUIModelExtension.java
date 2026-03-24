package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class BillOfMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected BillOfMaterialItemManager billOfMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;
	
	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion billOfMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		billOfMaterialItemExtensionUnion
				.setNodeInstId(BillOfMaterialItem.NODENAME);
		billOfMaterialItemExtensionUnion
				.setNodeName(BillOfMaterialItem.NODENAME);

		// UI Model Configure of node:[BillOfMaterialItem]
		UIModelNodeMapConfigure billOfMaterialItemMap = new UIModelNodeMapConfigure();
		billOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		billOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setNodeInstID(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setHostNodeFlag(true);
		billOfMaterialItemMap.setLogicManager(billOfMaterialItemManager);
		Class<?>[] billOfMaterialItemConvToUIParas = {
				BillOfMaterialItem.class, BillOfMaterialItemUIModel.class };
		billOfMaterialItemMap
				.setConvToUIMethodParas(billOfMaterialItemConvToUIParas);
		billOfMaterialItemMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvBillOfMaterialItemToUI);
		Class<?>[] BillOfMaterialItemConvUIToParas = {
				BillOfMaterialItemUIModel.class, BillOfMaterialItem.class };
		billOfMaterialItemMap
				.setConvUIToMethodParas(BillOfMaterialItemConvUIToParas);
		billOfMaterialItemMap
				.setConvUIToMethod(BillOfMaterialItemManager.METHOD_ConvUIToBillOfMaterialItem);
		uiModelNodeMapList.add(billOfMaterialItemMap);

		// UI Model Configure of node:[SubBomOrder]
		UIModelNodeMapConfigure subBomOrderMap = new UIModelNodeMapConfigure();
		subBomOrderMap.setSeName(BillOfMaterialOrder.SENAME);
		subBomOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
		subBomOrderMap.setNodeInstID("SubBomOrder");
		subBomOrderMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		subBomOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		subBomOrderMap.setServiceEntityManager(billOfMaterialOrderManager);
		subBomOrderMap.setLogicManager(billOfMaterialItemManager);
		List<SearchConfigConnectCondition> subBomOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition subBomOrderCondition0 = new SearchConfigConnectCondition();
		subBomOrderCondition0.setSourceFieldName("refSubBOMUUID");
		subBomOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		subBomOrderConditionList.add(subBomOrderCondition0);
		subBomOrderMap.setConnectionConditions(subBomOrderConditionList);
		Class<?>[] subBomOrderConvToUIParas = { BillOfMaterialOrder.class,
				BillOfMaterialItemUIModel.class };
		subBomOrderMap.setConvToUIMethodParas(subBomOrderConvToUIParas);
		subBomOrderMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvSubBomOrderToItemUI);
		uiModelNodeMapList.add(subBomOrderMap);

		// UI Model Configure of node:[ItemMaterial]
		UIModelNodeMapConfigure itemMaterialMap = new UIModelNodeMapConfigure();
		itemMaterialMap.setSeName(MaterialStockKeepUnit.SENAME);
		itemMaterialMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		itemMaterialMap.setNodeInstID("ItemMaterial");
		itemMaterialMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		itemMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		itemMaterialMap.setServiceEntityManager(materialStockKeepUnitManager);
		itemMaterialMap.setLogicManager(billOfMaterialItemManager);
		List<SearchConfigConnectCondition> itemMaterialConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemMaterialCondition0 = new SearchConfigConnectCondition();
		itemMaterialCondition0.setSourceFieldName("refMaterialSKUUUID");
		itemMaterialCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemMaterialConditionList.add(itemMaterialCondition0);
		itemMaterialMap.setConnectionConditions(itemMaterialConditionList);
		Class<?>[] itemMaterialConvToUIParas = { MaterialStockKeepUnit.class,
				BillOfMaterialItemUIModel.class };
		itemMaterialMap.setConvToUIMethodParas(itemMaterialConvToUIParas);
		itemMaterialMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvItemMaterialToUI);
		uiModelNodeMapList.add(itemMaterialMap);
		
		// UI Model Configure of node:[prodWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(MaterialStockKeepUnit.SENAME);
		prodWorkCenterMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		prodWorkCenterMap.setLogicManager(billOfMaterialItemManager);
		prodWorkCenterMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodWorkCenterMap.setServiceEntityManager(prodWorkCenterManager);
		List<SearchConfigConnectCondition> prodWorkCenterConditionList = new ArrayList<>();
		SearchConfigConnectCondition prodWorkCenterCondition0 = new SearchConfigConnectCondition();
		prodWorkCenterCondition0.setSourceFieldName("refWocUUID");
		prodWorkCenterCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodWorkCenterConditionList.add(prodWorkCenterCondition0);
		prodWorkCenterMap.setConnectionConditions(prodWorkCenterConditionList);
		Class<?>[] prodWorkCenterConvToUIParas = { ProdWorkCenter.class,
				BillOfMaterialItemUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvProdWorkCenterToItemUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[ItemMaterial]
		UIModelNodeMapConfigure parentbillOfMaterialMap = new UIModelNodeMapConfigure();
		parentbillOfMaterialMap.setSeName(BillOfMaterialOrder.SENAME);
		parentbillOfMaterialMap.setNodeName(BillOfMaterialOrder.NODENAME);
		parentbillOfMaterialMap.setNodeInstID(BillOfMaterialOrder.SENAME);
		parentbillOfMaterialMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		parentbillOfMaterialMap.setLogicManager(billOfMaterialItemManager);
		parentbillOfMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] parentbillOfMaterialConvToUIParas = {
				BillOfMaterialOrder.class, BillOfMaterialItemUIModel.class };
		parentbillOfMaterialMap
				.setConvToUIMethodParas(parentbillOfMaterialConvToUIParas);
		parentbillOfMaterialMap
				.setConvToUIMethod(BillOfMaterialItemManager.MEMTHOD_ConvParentBOMOrderToUI);
		uiModelNodeMapList.add(parentbillOfMaterialMap);

		// UI Model Configure of node:[ProcessRouteProcessItem]
		UIModelNodeMapConfigure processRouteProcessItemMap = new UIModelNodeMapConfigure();
		processRouteProcessItemMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		processRouteProcessItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processRouteProcessItemMap
				.setServiceEntityManager(processRouteOrderManager);
		processRouteProcessItemMap.setLogicManager(billOfMaterialItemManager);
		List<SearchConfigConnectCondition> processRouteProcessItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition processRouteProcessItemCondition0 = new SearchConfigConnectCondition();
		processRouteProcessItemCondition0
				.setSourceFieldName("refRouteProcessItemUUID");
		processRouteProcessItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		processRouteProcessItemConditionList
				.add(processRouteProcessItemCondition0);
		processRouteProcessItemMap
				.setConnectionConditions(processRouteProcessItemConditionList);
		Class<?>[] processRouteProcessItemConvToUIParas = {
				ProcessRouteProcessItem.class, BillOfMaterialItemUIModel.class };
		processRouteProcessItemMap
				.setConvToUIMethodParas(processRouteProcessItemConvToUIParas);
		processRouteProcessItemMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvProcessRouteProcessItemToUI);
		uiModelNodeMapList.add(processRouteProcessItemMap);

		// UI Model Configure of node:[ProdProcess]
		UIModelNodeMapConfigure prodProcessMap = new UIModelNodeMapConfigure();
		prodProcessMap.setSeName(ProdProcess.SENAME);
		prodProcessMap.setNodeName(ProdProcess.NODENAME);
		prodProcessMap.setNodeInstID(ProdProcess.SENAME);
		prodProcessMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodProcessMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		prodProcessMap.setServiceEntityManager(prodProcessManager);
		prodProcessMap.setLogicManager(billOfMaterialItemManager);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				BillOfMaterialItemUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(BillOfMaterialItemManager.METHOD_ConvProdProcessToUI);
		uiModelNodeMapList.add(prodProcessMap);

		billOfMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(billOfMaterialItemExtensionUnion);
		return resultList;
	}

}
