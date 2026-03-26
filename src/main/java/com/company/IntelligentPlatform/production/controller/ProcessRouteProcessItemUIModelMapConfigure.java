package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.dto.ProdProcessUIModel;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel")
public class ProcessRouteProcessItemUIModelMapConfigure extends
		SEUIModelMapConfigure {

	@Override
	public void initConfigure() {
		
		UIModelNodeMapConfigure processRouteProcessItemUIModelMap = new UIModelNodeMapConfigure();
		processRouteProcessItemUIModelMap.setHostNodeFlag(true);
		processRouteProcessItemUIModelMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemUIModelMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemUIModelMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);		
		processRouteProcessItemUIModelMap.setEditNodeFlag(true);		
		this.uiModelNodeMapList.add(processRouteProcessItemUIModelMap);
			
		UIModelNodeMapConfigure prodProcessUIModelMap = new UIModelNodeMapConfigure();
		prodProcessUIModelMap.setHostNodeFlag(false);
		prodProcessUIModelMap.setSeName(ProdProcess.SENAME);
		prodProcessUIModelMap
				.setNodeName(ProdProcess.NODENAME);
		prodProcessUIModelMap
				.setNodeInstID(ProdProcess.SENAME);
		prodProcessUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		prodProcessUIModelMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodProcessUIModelMap.setEditNodeFlag(false);
		prodProcessUIModelMap.setSubNodeUIModelCls(ProdProcessUIModel.class);
		this.uiModelNodeMapList.add(prodProcessUIModelMap);
		
		
	}

}
