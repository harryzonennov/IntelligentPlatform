package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.dto.ProcessBOMItemUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMMaterialItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProcessBOMOrderUIModel")
public class ProcessBOMOrderUIModelMapConfigure extends
		SEUIModelMapConfigure {

	@Override
	public void initConfigure() {
		
		UIModelNodeMapConfigure processBOMOrderUIModelMap = new UIModelNodeMapConfigure();
		processBOMOrderUIModelMap.setHostNodeFlag(true);
		processBOMOrderUIModelMap.setSeName(ProcessBOMOrder.SENAME);
		processBOMOrderUIModelMap
				.setNodeName(ProcessBOMOrder.NODENAME);
		processBOMOrderUIModelMap
				.setNodeInstID(ProcessBOMOrder.SENAME);		
		processBOMOrderUIModelMap.setEditNodeFlag(true);		
		this.uiModelNodeMapList.add(processBOMOrderUIModelMap);
			
		UIModelNodeMapConfigure billOfMaterialUIModelMap = new UIModelNodeMapConfigure();
		billOfMaterialUIModelMap.setHostNodeFlag(false);
		billOfMaterialUIModelMap.setSeName(BillOfMaterialOrder.SENAME);
		billOfMaterialUIModelMap
				.setNodeName(BillOfMaterialOrder.NODENAME);
		billOfMaterialUIModelMap
				.setNodeInstID(BillOfMaterialOrder.SENAME);
		billOfMaterialUIModelMap.setSelectScenaFlag(true);
		billOfMaterialUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		billOfMaterialUIModelMap.setBaseNodeInstID(ProcessBOMOrder.SENAME);
		billOfMaterialUIModelMap.setSelectScenaFlag(true);
		billOfMaterialUIModelMap.setMapBaseFieldName("refBOMUUID");
		billOfMaterialUIModelMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		billOfMaterialUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(billOfMaterialUIModelMap);
		
		UIModelNodeMapConfigure materialSKUUIModelMap = new UIModelNodeMapConfigure();
		materialSKUUIModelMap.setHostNodeFlag(false);
		materialSKUUIModelMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialSKUUIModelMap
				.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUUIModelMap
				.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialSKUUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialSKUUIModelMap.setBaseNodeInstID(ProcessBOMOrder.SENAME);
		materialSKUUIModelMap.setSelectScenaFlag(true);
		materialSKUUIModelMap.setEditNodeFlag(false);
		materialSKUUIModelMap.setMapBaseFieldName("refMaterialSKUUUID");
		materialSKUUIModelMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);		
		this.uiModelNodeMapList.add(materialSKUUIModelMap);
		
		UIModelNodeMapConfigure processRouteOrderUIModelMap = new UIModelNodeMapConfigure();
		processRouteOrderUIModelMap.setHostNodeFlag(false);
		processRouteOrderUIModelMap.setSeName(ProcessRouteOrder.SENAME);
		processRouteOrderUIModelMap
				.setNodeName(ProcessRouteOrder.NODENAME);
		processRouteOrderUIModelMap
				.setNodeInstID(ProcessRouteOrder.SENAME);
		processRouteOrderUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processRouteOrderUIModelMap.setBaseNodeInstID(ProcessBOMOrder.SENAME);
		processRouteOrderUIModelMap.setSelectScenaFlag(true);
		processRouteOrderUIModelMap.setEditNodeFlag(false);
		processRouteOrderUIModelMap.setMapBaseFieldName("refProcessRouteUUID");
		processRouteOrderUIModelMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		this.uiModelNodeMapList.add(processRouteOrderUIModelMap);
		
		UIModelNodeMapConfigure subProcessBOMItemUIModelMap = new UIModelNodeMapConfigure();
		subProcessBOMItemUIModelMap.setHostNodeFlag(false);
		subProcessBOMItemUIModelMap.setSeName(ProcessBOMMaterialItem.SENAME);
		subProcessBOMItemUIModelMap
				.setNodeName(ProcessBOMMaterialItem.NODENAME);
		subProcessBOMItemUIModelMap
				.setNodeInstID(ProcessBOMMaterialItem.NODENAME);
		subProcessBOMItemUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		subProcessBOMItemUIModelMap.setBaseNodeInstID(ProcessBOMItem.SENAME);		
		subProcessBOMItemUIModelMap.setEditNodeFlag(true);
		subProcessBOMItemUIModelMap.setListEmbededCategory(UIModelNodeMapConfigure.LIST_CATE_EDIT);
		subProcessBOMItemUIModelMap.setSubNodeUIModelCls(ProcessBOMItemUIModel.class);
		this.uiModelNodeMapList.add(subProcessBOMItemUIModelMap);
		
	}

}
