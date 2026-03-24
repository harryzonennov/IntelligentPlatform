package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.model.ProdJobMaterialItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProdJobMaterialItemUIModel")
public class ProdJobMaterialItemUIModelMapConfigure extends SEUIModelMapConfigure {

	@Override
	public void initConfigure() {

		// UI Model Configure of Host node:[prodJobMaterialItem]
		UIModelNodeMapConfigure prodJobMaterialItemUIModelMap = new UIModelNodeMapConfigure();
		prodJobMaterialItemUIModelMap.setHostNodeFlag(true);
		prodJobMaterialItemUIModelMap.setSeName(ProdJobMaterialItem.SENAME);
		prodJobMaterialItemUIModelMap.setNodeName(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemUIModelMap.setNodeInstID(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemUIModelMap.setEditNodeFlag(true);
		this.uiModelNodeMapList.add(prodJobMaterialItemUIModelMap);
		// UI Model Configure of Host node:[production order]
		

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
		refMaterialSKUUIModelMap.setBaseNodeInstID(ProdJobMaterialItem.NODENAME);
		refMaterialSKUUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(refMaterialSKUUIModelMap);

		
	}

}
