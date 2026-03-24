package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ProcessRouteProcessItemServiceUIModelExtension extends
		ServiceUIModelExtension {

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
		ServiceUIModelExtensionUnion processRouteProcessItemExtensionUnion = new ServiceUIModelExtensionUnion();
		processRouteProcessItemExtensionUnion
				.setNodeInstId(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemExtensionUnion
				.setNodeName(ProcessRouteProcessItem.NODENAME);

		// UI Model Configure of node:[ProcessRouteProcessItem]
		UIModelNodeMapConfigure processRouteProcessItemMap = new UIModelNodeMapConfigure();
		processRouteProcessItemMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap.setHostNodeFlag(true);
		Class<?>[] processRouteProcessItemConvToUIParas = {
				ProcessRouteProcessItem.class,
				ProcessRouteProcessItemUIModel.class };
		processRouteProcessItemMap
				.setConvToUIMethodParas(processRouteProcessItemConvToUIParas);
		processRouteProcessItemMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvProcessRouteProcessItemToUI);
		Class<?>[] ProcessRouteProcessItemConvUIToParas = {
				ProcessRouteProcessItemUIModel.class,
				ProcessRouteProcessItem.class };
		processRouteProcessItemMap
				.setConvUIToMethodParas(ProcessRouteProcessItemConvUIToParas);
		processRouteProcessItemMap
				.setConvUIToMethod(ProcessRouteOrderManager.METHOD_ConvUIToProcessRouteProcessItem);
		uiModelNodeMapList.add(processRouteProcessItemMap);

		// UI Model Configure of node:[ProdWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodWorkCenterMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodWorkCenterMap.setServiceEntityManager(prodWorkCenterManager);
		List<SearchConfigConnectCondition> prodWorkCenterConditionList = new ArrayList<>();
		SearchConfigConnectCondition prodWorkCenterCondition0 = new SearchConfigConnectCondition();
		prodWorkCenterCondition0.setSourceFieldName("refWorkCenterUUID");
		prodWorkCenterCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodWorkCenterConditionList.add(prodWorkCenterCondition0);
		prodWorkCenterMap.setConnectionConditions(prodWorkCenterConditionList);
		Class<?>[] prodWorkCenterConvToUIParas = { ProdWorkCenter.class,
				ProcessRouteProcessItemUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvProdWorkCenterToUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[ProdProcess]
		UIModelNodeMapConfigure prodProcessMap = new UIModelNodeMapConfigure();
		prodProcessMap.setSeName(ProdProcess.SENAME);
		prodProcessMap.setNodeName(ProdProcess.NODENAME);
		prodProcessMap.setNodeInstID(ProdProcess.SENAME);
		prodProcessMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodProcessMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		prodProcessMap.setServiceEntityManager(prodProcessManager);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				ProcessRouteProcessItemUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvProdProcessToUI);
		uiModelNodeMapList.add(prodProcessMap);
		processRouteProcessItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(processRouteProcessItemExtensionUnion);

		// UI Model Configure of node:[prodRouteOrder]
		UIModelNodeMapConfigure prodRouteOrderMap = new UIModelNodeMapConfigure();
		prodRouteOrderMap.setSeName(ProcessRouteOrder.SENAME);
		prodRouteOrderMap.setNodeName(ProcessRouteOrder.NODENAME);
		prodRouteOrderMap.setNodeInstID(ProcessRouteOrder.SENAME);
		prodRouteOrderMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodRouteOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);

		Class<?>[] parentDocToProcessItemUIParas = { ProcessRouteOrder.class,
				ProcessRouteProcessItemUIModel.class };
		prodRouteOrderMap.setConvToUIMethodParas(parentDocToProcessItemUIParas);
		prodRouteOrderMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvParentDocToProcessItemUI);
		uiModelNodeMapList.add(prodRouteOrderMap);

		return resultList;
	}

}
