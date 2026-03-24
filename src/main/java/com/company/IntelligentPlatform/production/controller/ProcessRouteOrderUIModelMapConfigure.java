package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.common.controller.ISEUIConfigureMapping;
import com.company.IntelligentPlatform.common.controller.SEUIModelMapConfigure;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;


@ISEUIConfigureMapping(uiModelName = "com.company.IntelligentPlatform.production.dto.ProcessRouteOrderUIModel")
public class ProcessRouteOrderUIModelMapConfigure extends
		SEUIModelMapConfigure {

	@Override
	public void initConfigure() {

		UIModelNodeMapConfigure processRouteOrderUIModelMap = new UIModelNodeMapConfigure();
		processRouteOrderUIModelMap.setHostNodeFlag(true);
		processRouteOrderUIModelMap.setSeName(ProcessRouteOrder.SENAME);
		processRouteOrderUIModelMap
				.setNodeName(ProcessRouteOrder.NODENAME);
		processRouteOrderUIModelMap
				.setNodeInstID(ProcessRouteOrder.SENAME);
		processRouteOrderUIModelMap.setEditNodeFlag(true);
		this.uiModelNodeMapList.add(processRouteOrderUIModelMap);
		
		UIModelNodeMapConfigure processRouteProcessItemUIModelMap = new UIModelNodeMapConfigure();
		processRouteProcessItemUIModelMap.setHostNodeFlag(false);
		processRouteProcessItemUIModelMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemUIModelMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemUIModelMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemUIModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_ROOT);		
		processRouteProcessItemUIModelMap.setBaseNodeInstID(ProcessRouteOrder.SENAME);
		processRouteProcessItemUIModelMap.setEditNodeFlag(true);
		processRouteProcessItemUIModelMap.setListEmbededCategory(UIModelNodeMapConfigure.LIST_CATE_CHOOSE);
		processRouteProcessItemUIModelMap.setSubNodeUIModelCls(ProcessRouteProcessItemUIModel.class);
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
		prodProcessUIModelMap.setSubNodeUIModelCls(ProcessRouteProcessItemUIModel.class);
		this.uiModelNodeMapList.add(prodProcessUIModelMap);
		
		
	}

}
