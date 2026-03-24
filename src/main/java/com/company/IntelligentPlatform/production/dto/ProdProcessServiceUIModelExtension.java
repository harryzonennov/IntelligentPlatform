package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdProcessUIModel;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
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
public class ProdProcessServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodProcessExtensionUnion = new ServiceUIModelExtensionUnion();
		prodProcessExtensionUnion.setNodeInstId(ProdProcess.SENAME);
		prodProcessExtensionUnion.setNodeName(ProdProcess.NODENAME);

		// UI Model Configure of node:[ProdProcess]
		UIModelNodeMapConfigure prodProcessMap = new UIModelNodeMapConfigure();
		prodProcessMap.setSeName(ProdProcess.SENAME);
		prodProcessMap.setNodeName(ProdProcess.NODENAME);
		prodProcessMap.setNodeInstID(ProdProcess.SENAME);
		prodProcessMap.setHostNodeFlag(true);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				ProdProcessUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(ProdProcessManager.METHOD_ConvProdProcessToUI);
		Class<?>[] ProdProcessConvUIToParas = { ProdProcessUIModel.class,
				ProdProcess.class };
		prodProcessMap.setConvUIToMethodParas(ProdProcessConvUIToParas);
		prodProcessMap
				.setConvUIToMethod(ProdProcessManager.METHOD_ConvUIToProdProcess);
		uiModelNodeMapList.add(prodProcessMap);

		// UI Model Configure of node:[ProdWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setBaseNodeInstID(ProdProcess.SENAME);
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
				ProdProcessUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(ProdProcessManager.METHOD_ConvProdWorkCenterToUI);
		uiModelNodeMapList.add(prodWorkCenterMap);
		prodProcessExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodProcessExtensionUnion);
		return resultList;
	}

}
