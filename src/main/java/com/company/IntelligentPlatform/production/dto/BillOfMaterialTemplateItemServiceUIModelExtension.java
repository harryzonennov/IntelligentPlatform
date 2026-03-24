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
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillOfMaterialTemplateItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

	@Autowired
	protected BillOfMaterialTemplateItemManager billOfMaterialTemplateItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;
	
	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion billOfMaterialTemplateItemExtensionUnion = new ServiceUIModelExtensionUnion();
		billOfMaterialTemplateItemExtensionUnion
				.setNodeInstId(BillOfMaterialTemplateItem.NODENAME);
		billOfMaterialTemplateItemExtensionUnion
				.setNodeName(BillOfMaterialTemplateItem.NODENAME);

		// UI Model Configure of node:[BillOfMaterialTemplateItem]
		UIModelNodeMapConfigure billOfMaterialTemplateItemMap = new UIModelNodeMapConfigure();
		billOfMaterialTemplateItemMap.setSeName(BillOfMaterialTemplateItem.SENAME);
		billOfMaterialTemplateItemMap.setNodeName(BillOfMaterialTemplateItem.NODENAME);
		billOfMaterialTemplateItemMap.setNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		billOfMaterialTemplateItemMap.setHostNodeFlag(true);
		billOfMaterialTemplateItemMap.setLogicManager(billOfMaterialTemplateItemManager);
		Class<?>[] billOfMaterialTemplateItemConvToUIParas = {
				BillOfMaterialTemplateItem.class, BillOfMaterialTemplateItemUIModel.class };
		billOfMaterialTemplateItemMap
				.setConvToUIMethodParas(billOfMaterialTemplateItemConvToUIParas);
		billOfMaterialTemplateItemMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvBillOfMaterialTemplateItemToUI);
		Class<?>[] BillOfMaterialTemplateItemConvUIToParas = {
				BillOfMaterialTemplateItemUIModel.class, BillOfMaterialTemplateItem.class };
		billOfMaterialTemplateItemMap
				.setConvUIToMethodParas(BillOfMaterialTemplateItemConvUIToParas);
		billOfMaterialTemplateItemMap
				.setConvUIToMethod(BillOfMaterialTemplateItemManager.METHOD_ConvUIToBillOfMaterialTemplateItem);
		uiModelNodeMapList.add(billOfMaterialTemplateItemMap);

		// UI Model Configure of node:[SubBomOrder]
		UIModelNodeMapConfigure subBomOrderMap = new UIModelNodeMapConfigure();
		subBomOrderMap.setSeName(BillOfMaterialTemplate.SENAME);
		subBomOrderMap.setNodeName(BillOfMaterialTemplate.NODENAME);
		subBomOrderMap.setNodeInstID("SubBomOrder");
		subBomOrderMap.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		subBomOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		subBomOrderMap.setServiceEntityManager(billOfMaterialTemplateManager);
		subBomOrderMap.setLogicManager(billOfMaterialTemplateItemManager);
		List<SearchConfigConnectCondition> subBomOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition subBomOrderCondition0 = new SearchConfigConnectCondition();
		subBomOrderCondition0.setSourceFieldName("refSubBOMUUID");
		subBomOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		subBomOrderConditionList.add(subBomOrderCondition0);
		subBomOrderMap.setConnectionConditions(subBomOrderConditionList);
		Class<?>[] subBomOrderConvToUIParas = { BillOfMaterialTemplate.class,
				BillOfMaterialTemplateItemUIModel.class };
		subBomOrderMap.setConvToUIMethodParas(subBomOrderConvToUIParas);
		subBomOrderMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvSubBomTemplateToItemUI);
		uiModelNodeMapList.add(subBomOrderMap);

		// UI Model Configure of node:[ItemMaterial]
		UIModelNodeMapConfigure itemMaterialMap = new UIModelNodeMapConfigure();
		itemMaterialMap.setSeName(MaterialStockKeepUnit.SENAME);
		itemMaterialMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		itemMaterialMap.setNodeInstID("ItemMaterial");
		itemMaterialMap.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		itemMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		itemMaterialMap.setServiceEntityManager(materialStockKeepUnitManager);
		itemMaterialMap.setLogicManager(billOfMaterialTemplateItemManager);
		List<SearchConfigConnectCondition> itemMaterialConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemMaterialCondition0 = new SearchConfigConnectCondition();
		itemMaterialCondition0.setSourceFieldName("refMaterialSKUUUID");
		itemMaterialCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemMaterialConditionList.add(itemMaterialCondition0);
		itemMaterialMap.setConnectionConditions(itemMaterialConditionList);
		Class<?>[] itemMaterialConvToUIParas = { MaterialStockKeepUnit.class,
				BillOfMaterialTemplateItemUIModel.class };
		itemMaterialMap.setConvToUIMethodParas(itemMaterialConvToUIParas);
		itemMaterialMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvItemMaterialToUI);
		uiModelNodeMapList.add(itemMaterialMap);
		
		// UI Model Configure of node:[prodWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(MaterialStockKeepUnit.SENAME);
		prodWorkCenterMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		prodWorkCenterMap.setLogicManager(billOfMaterialTemplateItemManager);
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
				BillOfMaterialTemplateItemUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvProdWorkCenterToItemUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[ItemMaterial]
		UIModelNodeMapConfigure parentbillOfMaterialMap = new UIModelNodeMapConfigure();
		parentbillOfMaterialMap.setSeName(BillOfMaterialTemplate.SENAME);
		parentbillOfMaterialMap.setNodeName(BillOfMaterialTemplate.NODENAME);
		parentbillOfMaterialMap.setNodeInstID(BillOfMaterialTemplate.SENAME);
		parentbillOfMaterialMap.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		parentbillOfMaterialMap.setLogicManager(billOfMaterialTemplateItemManager);
		parentbillOfMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] parentbillOfMaterialConvToUIParas = {
				BillOfMaterialTemplate.class, BillOfMaterialTemplateItemUIModel.class };
		parentbillOfMaterialMap
				.setConvToUIMethodParas(parentbillOfMaterialConvToUIParas);
		parentbillOfMaterialMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.MEMTHOD_ConvParentBOMTemplateToUI);
		uiModelNodeMapList.add(parentbillOfMaterialMap);

		// UI Model Configure of node:[ProcessRouteProcessItem]
		UIModelNodeMapConfigure processRouteProcessItemMap = new UIModelNodeMapConfigure();
		processRouteProcessItemMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setBaseNodeInstID(BillOfMaterialTemplateItem.NODENAME);
		processRouteProcessItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processRouteProcessItemMap
				.setServiceEntityManager(processRouteOrderManager);
		processRouteProcessItemMap.setLogicManager(billOfMaterialTemplateItemManager);
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
				ProcessRouteProcessItem.class, BillOfMaterialTemplateItemUIModel.class };
		processRouteProcessItemMap
				.setConvToUIMethodParas(processRouteProcessItemConvToUIParas);
		processRouteProcessItemMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvProcessRouteProcessItemToUI);
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
		prodProcessMap.setLogicManager(billOfMaterialTemplateItemManager);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				BillOfMaterialTemplateItemUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvProdProcessToUI);
		uiModelNodeMapList.add(prodProcessMap);

		billOfMaterialTemplateItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(billOfMaterialTemplateItemExtensionUnion);
		return resultList;
	}

}
