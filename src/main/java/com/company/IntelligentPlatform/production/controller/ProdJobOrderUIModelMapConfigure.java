package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.dto.ProdJobMaterialItemUIModel;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdJobMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdJobOrder;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProdJobOrderUIModel")
public class ProdJobOrderUIModelMapConfigure extends SEUIModelMapConfigure {

	@Override
	public void initConfigure() {

		// UI Model Configure of Host node:[prodJobOrder]
		UIModelNodeMapConfigure prodJobOrderUIModelMap = new UIModelNodeMapConfigure();
		prodJobOrderUIModelMap.setHostNodeFlag(true);
		prodJobOrderUIModelMap.setSeName(ProdJobOrder.SENAME);
		prodJobOrderUIModelMap.setNodeName(ProdJobOrder.NODENAME);
		prodJobOrderUIModelMap.setNodeInstID(ProdJobOrder.SENAME);
		prodJobOrderUIModelMap.setEditNodeFlag(true);
		this.uiModelNodeMapList.add(prodJobOrderUIModelMap);
		// UI Model Configure of Host node:[production order]
		UIModelNodeMapConfigure productionOrderUIModelMap = new UIModelNodeMapConfigure();
		productionOrderUIModelMap.setHostNodeFlag(false);
		productionOrderUIModelMap.setSeName(ProductionOrder.SENAME);
		productionOrderUIModelMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderUIModelMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productionOrderUIModelMap
				.setMapBaseFieldName("refProdRouteProcessItemUUID");
		productionOrderUIModelMap
				.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		productionOrderUIModelMap.setBaseNodeInstID(ProdJobOrder.SENAME);
		productionOrderUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(productionOrderUIModelMap);

		// UI Model Configure of Host node:[process item]
		UIModelNodeMapConfigure prodRouteProcessItemUIModelMap = new UIModelNodeMapConfigure();
		prodRouteProcessItemUIModelMap.setHostNodeFlag(false);
		prodRouteProcessItemUIModelMap
				.setSeName(ProcessRouteProcessItem.SENAME);
		prodRouteProcessItemUIModelMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		prodRouteProcessItemUIModelMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodRouteProcessItemUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodRouteProcessItemUIModelMap
				.setMapBaseFieldName("refProductionOrderUUID");
		prodRouteProcessItemUIModelMap
				.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodRouteProcessItemUIModelMap.setBaseNodeInstID(ProdJobOrder.SENAME);
		prodRouteProcessItemUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(prodRouteProcessItemUIModelMap);

		// UI Model Configure of Host node:[process item]
		UIModelNodeMapConfigure prodRouteOrderUIModelMap = new UIModelNodeMapConfigure();
		prodRouteOrderUIModelMap.setHostNodeFlag(false);
		prodRouteOrderUIModelMap.setSeName(ProcessRouteOrder.SENAME);
		prodRouteOrderUIModelMap.setNodeName(ProcessRouteOrder.NODENAME);
		prodRouteOrderUIModelMap.setNodeInstID(ProcessRouteOrder.SENAME);
		prodRouteOrderUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		prodRouteOrderUIModelMap
				.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodRouteOrderUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(prodRouteOrderUIModelMap);

		// UI Model Configure of Host node:[prod process]
		UIModelNodeMapConfigure prodProcessUIModelMap = new UIModelNodeMapConfigure();
		prodProcessUIModelMap.setHostNodeFlag(false);
		prodProcessUIModelMap.setSeName(ProdProcess.SENAME);
		prodProcessUIModelMap.setNodeName(ProdProcess.NODENAME);
		prodProcessUIModelMap.setNodeInstID(ProdProcess.SENAME);
		prodProcessUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodProcessUIModelMap.setMapBaseFieldName("refProductionOrderUUID");
		prodProcessUIModelMap
				.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodProcessUIModelMap
				.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodProcessUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(prodProcessUIModelMap);

		// UI Model Configure of Host node:[main material]
		UIModelNodeMapConfigure refMaterialSKUUIModelMap = new UIModelNodeMapConfigure();
		refMaterialSKUUIModelMap.setHostNodeFlag(false);
		refMaterialSKUUIModelMap.setSeName(MaterialStockKeepUnit.SENAME);
		refMaterialSKUUIModelMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		refMaterialSKUUIModelMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		refMaterialSKUUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		refMaterialSKUUIModelMap.setMapBaseFieldName("refMaterialSKUUUID");
		refMaterialSKUUIModelMap
				.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		refMaterialSKUUIModelMap.setBaseNodeInstID(ProductionOrder.SENAME);
		refMaterialSKUUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(refMaterialSKUUIModelMap);

		// UI Model Configure of Host node:[work center]
		UIModelNodeMapConfigure refWorkCenterUIModelMap = new UIModelNodeMapConfigure();
		refWorkCenterUIModelMap.setHostNodeFlag(false);
		refWorkCenterUIModelMap.setSeName(ProdWorkCenter.SENAME);
		refWorkCenterUIModelMap.setNodeName(ProdWorkCenter.NODENAME);
		refWorkCenterUIModelMap.setNodeInstID(ProdWorkCenter.SENAME);
		refWorkCenterUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		refWorkCenterUIModelMap.setMapBaseFieldName("refWorkCenterUUID");
		refWorkCenterUIModelMap
				.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		refMaterialSKUUIModelMap.setBaseNodeInstID(ProdJobOrder.SENAME);
		refMaterialSKUUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(refWorkCenterUIModelMap);

		// UI Model Configure of Host node:[materialItem]
		UIModelNodeMapConfigure prodJobMaterialItemUIModelMap = new UIModelNodeMapConfigure();
		prodJobMaterialItemUIModelMap.setHostNodeFlag(false);
		prodJobMaterialItemUIModelMap.setSeName(ProdJobMaterialItem.SENAME);
		prodJobMaterialItemUIModelMap.setNodeName(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemUIModelMap
				.setNodeInstID(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_ROOT);
		prodJobMaterialItemUIModelMap
				.setListEmbededCategory(UIModelNodeMapConfigure.LIST_CATE_EDIT);
		prodJobMaterialItemUIModelMap.setBaseNodeInstID(ProdJobOrder.SENAME);
		prodJobMaterialItemUIModelMap.setEditNodeFlag(true);
		prodJobMaterialItemUIModelMap
				.setSubNodeUIModelCls(ProdJobMaterialItemUIModel.class);
		this.uiModelNodeMapList.add(prodJobMaterialItemUIModelMap);
	}

}
