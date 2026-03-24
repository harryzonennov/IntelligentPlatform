package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.FinAccountTitleUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class FinAccountTitleServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountTitleExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountTitleExtensionUnion.setNodeInstId(FinAccountTitle.SENAME);
		finAccountTitleExtensionUnion.setNodeName(FinAccountTitle.NODENAME);

		// UI Model Configure of node:[FinAccountTitle]
		UIModelNodeMapConfigure finAccountTitleMap = new UIModelNodeMapConfigure();
		finAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		finAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		finAccountTitleMap.setNodeInstID(FinAccountTitle.SENAME);
		finAccountTitleMap.setHostNodeFlag(true);
		Class<?>[] finAccountTitleConvToUIParas = { FinAccountTitle.class,
				FinAccountTitleUIModel.class };
		finAccountTitleMap.setConvToUIMethodParas(finAccountTitleConvToUIParas);
		finAccountTitleMap
				.setConvToUIMethod(FinAccountTitleManager.METHOD_ConvFinAccountTitleToUI);
		Class<?>[] FinAccountTitleConvUIToParas = {
				FinAccountTitleUIModel.class, FinAccountTitle.class };
		finAccountTitleMap.setConvUIToMethodParas(FinAccountTitleConvUIToParas);
		finAccountTitleMap
				.setConvUIToMethod(FinAccountTitleManager.METHOD_ConvUIToFinAccountTitle);
		uiModelNodeMapList.add(finAccountTitleMap);

		// UI Model Configure of node:[ParentAccountTitle]
		UIModelNodeMapConfigure parentAccountTitleMap = new UIModelNodeMapConfigure();
		parentAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		parentAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		parentAccountTitleMap.setNodeInstID("ParentAccountTitle");
		parentAccountTitleMap.setBaseNodeInstID(FinAccountTitle.SENAME);
		parentAccountTitleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		parentAccountTitleMap.setServiceEntityManager(finAccountTitleManager);
		List<SearchConfigConnectCondition> parentAccountTitleConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentAccountTitleCondition0 = new SearchConfigConnectCondition();
		parentAccountTitleCondition0.setSourceFieldName("parentAccountTitleUUID");
		parentAccountTitleCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentAccountTitleConditionList.add(parentAccountTitleCondition0);
		parentAccountTitleMap
				.setConnectionConditions(parentAccountTitleConditionList);
		Class<?>[] parentAccountTitleConvToUIParas = { FinAccountTitle.class,
				FinAccountTitleUIModel.class };
		parentAccountTitleMap
				.setConvToUIMethodParas(parentAccountTitleConvToUIParas);
		parentAccountTitleMap
				.setConvToUIMethod(FinAccountTitleManager.METHOD_ConvParentFinAccountTitleToUI);
		uiModelNodeMapList.add(parentAccountTitleMap);

		// UI Model Configure of node:[RootAccountTitle]
		UIModelNodeMapConfigure rootAccountTitleMap = new UIModelNodeMapConfigure();
		rootAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		rootAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		rootAccountTitleMap.setNodeInstID("RootAccountTitle");
		rootAccountTitleMap.setBaseNodeInstID(FinAccountTitle.SENAME);
		rootAccountTitleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		rootAccountTitleMap.setServiceEntityManager(finAccountTitleManager);
		List<SearchConfigConnectCondition> rootAccountTitleConditionList = new ArrayList<>();
		SearchConfigConnectCondition rootAccountTitleCondition0 = new SearchConfigConnectCondition();
		rootAccountTitleCondition0.setSourceFieldName("rootAccountTitleUUID");
		rootAccountTitleCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		rootAccountTitleConditionList.add(rootAccountTitleCondition0);
		rootAccountTitleMap
				.setConnectionConditions(rootAccountTitleConditionList);
		Class<?>[] rootAccountTitleConvToUIParas = { FinAccountTitle.class,
				FinAccountTitleUIModel.class };
		rootAccountTitleMap
				.setConvToUIMethodParas(rootAccountTitleConvToUIParas);
		rootAccountTitleMap
				.setConvToUIMethod(FinAccountTitleManager.METHOD_ConvRootFinAccountTitleToUI);
		uiModelNodeMapList.add(rootAccountTitleMap);
		finAccountTitleExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountTitleExtensionUnion);
		return resultList;
	}

}
