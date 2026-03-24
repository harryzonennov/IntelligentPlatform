package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Organization;

@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProdProcessUIModel")
public class ProdProcessUIModelMapConfigure extends
		SEUIModelMapConfigure {

	@Override
	public void initConfigure() {

		UIModelNodeMapConfigure prodProcessUIModelMap = new UIModelNodeMapConfigure();
		prodProcessUIModelMap.setHostNodeFlag(true);
		prodProcessUIModelMap.setSeName(ProdProcess.SENAME);
		prodProcessUIModelMap
				.setNodeName(ProdProcess.NODENAME);
		prodProcessUIModelMap
				.setNodeInstID(ProdProcess.SENAME);
		prodProcessUIModelMap.setEditNodeFlag(true);
		this.uiModelNodeMapList.add(prodProcessUIModelMap);			
		
		UIModelNodeMapConfigure costCenterUIModelMap = new UIModelNodeMapConfigure();
		costCenterUIModelMap.setHostNodeFlag(false);
		costCenterUIModelMap.setSeName(Organization.SENAME);
		costCenterUIModelMap
				.setNodeName(Organization.NODENAME);
		costCenterUIModelMap
				.setNodeInstID(Organization.SENAME);
		costCenterUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		costCenterUIModelMap.setMapBaseFieldName("refCostCenterUUID");
		costCenterUIModelMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		costCenterUIModelMap.setBaseNodeInstID(ProductionResourceUnion.SENAME);
		costCenterUIModelMap.setEditNodeFlag(false);
		this.uiModelNodeMapList.add(costCenterUIModelMap);
		
	}

}
