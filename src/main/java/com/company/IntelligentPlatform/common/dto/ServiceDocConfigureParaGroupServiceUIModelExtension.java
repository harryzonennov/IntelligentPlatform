package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaGroupUIModel;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;

@Service
public class ServiceDocConfigureParaGroupServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocConfigureParaGroupExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocConfigureParaGroupExtensionUnion
				.setNodeInstId(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureParaGroupExtensionUnion
				.setNodeName(ServiceDocConfigureParaGroup.NODENAME);

		// UI Model Configure of node:[ServiceDocConfigureParaGroup]
		UIModelNodeMapConfigure serviceDocConfigureParaGroupMap = new UIModelNodeMapConfigure();
		serviceDocConfigureParaGroupMap
				.setSeName(ServiceDocConfigureParaGroup.SENAME);
		serviceDocConfigureParaGroupMap
				.setNodeName(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureParaGroupMap
				.setNodeInstID(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureParaGroupMap.setHostNodeFlag(true);
		Class<?>[] serviceDocConfigureParaGroupConvToUIParas = {
				ServiceDocConfigureParaGroup.class,
				ServiceDocConfigureParaGroupUIModel.class };
		serviceDocConfigureParaGroupMap
				.setConvToUIMethodParas(serviceDocConfigureParaGroupConvToUIParas);
		serviceDocConfigureParaGroupMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureParaGroupToUI);
		Class<?>[] ServiceDocConfigureParaGroupConvUIToParas = {
				ServiceDocConfigureParaGroupUIModel.class,
				ServiceDocConfigureParaGroup.class };
		serviceDocConfigureParaGroupMap
				.setConvUIToMethodParas(ServiceDocConfigureParaGroupConvUIToParas);
		serviceDocConfigureParaGroupMap
				.setConvUIToMethod(ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigureParaGroup);
		uiModelNodeMapList.add(serviceDocConfigureParaGroupMap);		

		UIModelNodeMapConfigure parentGroupfigureMap = new UIModelNodeMapConfigure();
		parentGroupfigureMap.setSeName(ServiceDocConfigureParaGroup.SENAME);
		parentGroupfigureMap.setNodeName(ServiceDocConfigureParaGroup.NODENAME);
		parentGroupfigureMap.setNodeInstID("parentGroup");
		parentGroupfigureMap.setBaseNodeInstID(ServiceDocConfigureParaGroup.NODENAME);
		parentGroupfigureMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] parentGroupConvToUIParas = { ServiceDocConfigureParaGroup.class,
				ServiceDocConfigureParaGroupUIModel.class };
		List<SearchConfigConnectCondition> parentGroupConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentGroupCondition0 = new SearchConfigConnectCondition();
		parentGroupCondition0.setSourceFieldName("refParentGroupUUID");
		parentGroupCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentGroupConditionList.add(parentGroupCondition0);		
		parentGroupfigureMap.setConnectionConditions(parentGroupConditionList);
		parentGroupfigureMap.setConvToUIMethodParas(parentGroupConvToUIParas);
		parentGroupfigureMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvParntGroupToUI);
		uiModelNodeMapList.add(parentGroupfigureMap);

		UIModelNodeMapConfigure serviceDocConfigureMap = new UIModelNodeMapConfigure();
		serviceDocConfigureMap.setSeName(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureMap.setNodeInstID(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setBaseNodeInstID(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] serviceDocConfigureConvToUIParas = { ServiceDocConfigure.class,
				ServiceDocConfigureParaGroupUIModel.class };
		serviceDocConfigureMap.setConvToUIMethodParas(serviceDocConfigureConvToUIParas);
		serviceDocConfigureMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvConfigureToParaGroupUI);
		uiModelNodeMapList.add(serviceDocConfigureMap);
		
		serviceDocConfigureParaGroupExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocConfigureParaGroupExtensionUnion);
		return resultList;
	}

}
