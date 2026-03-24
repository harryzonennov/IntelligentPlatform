package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillOfMaterialItemUpdateLogServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected BillOfMaterialItemManager billOfMaterialItemUpdateLogManager;

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
		ServiceUIModelExtensionUnion billOfMaterialItemUpdateLogExtensionUnion = new ServiceUIModelExtensionUnion();
		billOfMaterialItemUpdateLogExtensionUnion
				.setNodeInstId(BillOfMaterialItemUpdateLog.NODENAME);
		billOfMaterialItemUpdateLogExtensionUnion
				.setNodeName(BillOfMaterialItemUpdateLog.NODENAME);

		// UI Model Configure of node:[BillOfMaterialItemUpdateLog]
		UIModelNodeMapConfigure billOfMaterialItemUpdateLogMap = new UIModelNodeMapConfigure();
		billOfMaterialItemUpdateLogMap.setSeName(BillOfMaterialItemUpdateLog.SENAME);
		billOfMaterialItemUpdateLogMap.setNodeName(BillOfMaterialItemUpdateLog.NODENAME);
		billOfMaterialItemUpdateLogMap.setNodeInstID(BillOfMaterialItemUpdateLog.NODENAME);
		billOfMaterialItemUpdateLogMap.setHostNodeFlag(true);
		billOfMaterialItemUpdateLogMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		Class<?>[] billOfMaterialItemUpdateLogConvToUIParas = {
				BillOfMaterialItem.class, BillOfMaterialItemUpdateLogUIModel.class };
		billOfMaterialItemUpdateLogMap
				.setConvToUIMethodParas(billOfMaterialItemUpdateLogConvToUIParas);
		billOfMaterialItemUpdateLogMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvBillOfMaterialItemUpdateLogToUI);
		uiModelNodeMapList.add(billOfMaterialItemUpdateLogMap);

		// UI Model Configure of node:[SubBomOrder]
		UIModelNodeMapConfigure subBomOrderMap = new UIModelNodeMapConfigure();
		subBomOrderMap.setSeName(BillOfMaterialOrder.SENAME);
		subBomOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
		subBomOrderMap.setNodeInstID("SubBomOrder");
		subBomOrderMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		subBomOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		subBomOrderMap.setServiceEntityManager(billOfMaterialOrderManager);
		subBomOrderMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		List<SearchConfigConnectCondition> subBomOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition subBomOrderCondition0 = new SearchConfigConnectCondition();
		subBomOrderCondition0.setSourceFieldName("refSubBOMUUID");
		subBomOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		subBomOrderConditionList.add(subBomOrderCondition0);
		subBomOrderMap.setConnectionConditions(subBomOrderConditionList);
		Class<?>[] subBomOrderConvToUIParas = { BillOfMaterialOrder.class,
				BillOfMaterialItemUpdateLogUIModel.class };
		subBomOrderMap.setConvToUIMethodParas(subBomOrderConvToUIParas);
		subBomOrderMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvSubBomOrderToItemUI);
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
		itemMaterialMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		List<SearchConfigConnectCondition> itemMaterialConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemMaterialCondition0 = new SearchConfigConnectCondition();
		itemMaterialCondition0.setSourceFieldName("refMaterialSKUUUID");
		itemMaterialCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemMaterialConditionList.add(itemMaterialCondition0);
		itemMaterialMap.setConnectionConditions(itemMaterialConditionList);
		Class<?>[] itemMaterialConvToUIParas = { MaterialStockKeepUnit.class,
				BillOfMaterialItemUpdateLogUIModel.class };
		itemMaterialMap.setConvToUIMethodParas(itemMaterialConvToUIParas);
		itemMaterialMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvItemMaterialToUI);
		uiModelNodeMapList.add(itemMaterialMap);

		// UI Model Configure of node:[prodWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(MaterialStockKeepUnit.SENAME);
		prodWorkCenterMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		prodWorkCenterMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		prodWorkCenterMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodWorkCenterMap.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> prodWorkCenterConditionList = new ArrayList<>();
		SearchConfigConnectCondition prodWorkCenterCondition0 = new SearchConfigConnectCondition();
		prodWorkCenterCondition0.setSourceFieldName("refWocUUID");
		prodWorkCenterCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodWorkCenterConditionList.add(prodWorkCenterCondition0);
		prodWorkCenterMap.setConnectionConditions(prodWorkCenterConditionList);
		Class<?>[] prodWorkCenterConvToUIParas = { ProdWorkCenter.class,
				BillOfMaterialItemUpdateLogUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvProdWorkCenterToItemUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[ItemMaterial]
		UIModelNodeMapConfigure parentbillOfMaterialMap = new UIModelNodeMapConfigure();
		parentbillOfMaterialMap.setSeName(BillOfMaterialOrder.SENAME);
		parentbillOfMaterialMap.setNodeName(BillOfMaterialOrder.NODENAME);
		parentbillOfMaterialMap.setNodeInstID(BillOfMaterialOrder.SENAME);
		parentbillOfMaterialMap.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
		parentbillOfMaterialMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		parentbillOfMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] parentbillOfMaterialConvToUIParas = {
				BillOfMaterialOrder.class, BillOfMaterialItemUpdateLogUIModel.class };
		parentbillOfMaterialMap
				.setConvToUIMethodParas(parentbillOfMaterialConvToUIParas);
		parentbillOfMaterialMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.MEMTHOD_ConvParentBOMOrderToUI);
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
		processRouteProcessItemMap.setLogicManager(billOfMaterialItemUpdateLogManager);
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
				ProcessRouteProcessItem.class, BillOfMaterialItemUpdateLogUIModel.class };
		processRouteProcessItemMap
				.setConvToUIMethodParas(processRouteProcessItemConvToUIParas);
		processRouteProcessItemMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvProcessRouteProcessItemToUI);
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
		prodProcessMap.setLogicManager(billOfMaterialItemUpdateLogManager);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				BillOfMaterialItemUpdateLogUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(BillOfMaterialItemUpdateLogManager.METHOD_ConvProdProcessToUI);
		uiModelNodeMapList.add(prodProcessMap);

		billOfMaterialItemUpdateLogExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(billOfMaterialItemUpdateLogExtensionUnion);
		return resultList;
	}

}
