package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureUIModel;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;

@Service
public class ServiceDocConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceDocConfigureParaServiceUIModelExtension serviceDocConfigureParaServiceUIModelExtension;

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected ServiceDocConfigureParaGroupServiceUIModelExtension serviceDocConfigureParaGroupServiceUIModelExtension;

	@Autowired
	protected SearchProxyConfigManager searchProxyConfigManager;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceDocConfigureParaServiceUIModelExtension);
		resultList.add(serviceDocConfigureParaGroupServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocConfigureExtensionUnion
				.setNodeInstId(ServiceDocConfigure.SENAME);
		serviceDocConfigureExtensionUnion
				.setNodeName(ServiceDocConfigure.NODENAME);

		// UI Model Configure of node:[ServiceDocConfigure]
		UIModelNodeMapConfigure serviceDocConfigureMap = new UIModelNodeMapConfigure();
		serviceDocConfigureMap.setSeName(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureMap.setNodeInstID(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setHostNodeFlag(true);
		Class<?>[] serviceDocConfigureConvToUIParas = {
				ServiceDocConfigure.class, ServiceDocConfigureUIModel.class };
		serviceDocConfigureMap
				.setConvToUIMethodParas(serviceDocConfigureConvToUIParas);
		serviceDocConfigureMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureToUI);
		Class<?>[] ServiceDocConfigureConvUIToParas = {
				ServiceDocConfigureUIModel.class, ServiceDocConfigure.class };
		serviceDocConfigureMap
				.setConvUIToMethodParas(ServiceDocConfigureConvUIToParas);
		serviceDocConfigureMap
				.setConvUIToMethod(ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigure);
		uiModelNodeMapList.add(serviceDocConfigureMap);

		// UI Model Configure of node:[SearchProxyConfig]
		UIModelNodeMapConfigure searchProxyConfigMap = new UIModelNodeMapConfigure();
		searchProxyConfigMap.setSeName(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setNodeName(SearchProxyConfig.NODENAME);
		searchProxyConfigMap.setNodeInstID(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchProxyConfigMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		searchProxyConfigMap.setServiceEntityManager(searchProxyConfigManager);
		List<SearchConfigConnectCondition> searchProxyConfigConditionList = new ArrayList<>();
		SearchConfigConnectCondition searchProxyConfigCondition0 = new SearchConfigConnectCondition();
		searchProxyConfigCondition0.setSourceFieldName("refSearchProxyUUID");
		searchProxyConfigCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchProxyConfigConditionList.add(searchProxyConfigCondition0);
		searchProxyConfigMap
				.setConnectionConditions(searchProxyConfigConditionList);
		Class<?>[] searchProxyConfigConvToUIParas = { SearchProxyConfig.class,
				ServiceDocConfigureUIModel.class };
		searchProxyConfigMap
				.setConvToUIMethodParas(searchProxyConfigConvToUIParas);
		searchProxyConfigMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvSearchProxyConfigToUI);
		uiModelNodeMapList.add(searchProxyConfigMap);

		// UI Model Configure of node:[InputModule]
		UIModelNodeMapConfigure inputModuleMap = new UIModelNodeMapConfigure();
		inputModuleMap.setSeName(ServiceDocConsumerUnion.SENAME);
		inputModuleMap.setNodeName(ServiceDocConsumerUnion.NODENAME);
		inputModuleMap.setNodeInstID("InputModule");
		inputModuleMap.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		inputModuleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		inputModuleMap.setServiceEntityManager(serviceDocConsumerUnionManager);
		List<SearchConfigConnectCondition> inputModuleConditionList = new ArrayList<>();
		SearchConfigConnectCondition inputModuleCondition0 = new SearchConfigConnectCondition();
		inputModuleCondition0.setSourceFieldName("inputUnionUUID");
		inputModuleCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		inputModuleConditionList.add(inputModuleCondition0);		
		inputModuleMap.setConnectionConditions(inputModuleConditionList);
		Class<?>[] inputModuleConvToUIParas = { ServiceDocConsumerUnion.class,
				ServiceDocConfigureUIModel.class };
		inputModuleMap.setConvToUIMethodParas(inputModuleConvToUIParas);
		inputModuleMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvInputModuleToUI);
		uiModelNodeMapList.add(inputModuleMap);

		// UI Model Configure of node:[OutputModule]
		UIModelNodeMapConfigure outputModuleMap = new UIModelNodeMapConfigure();
		outputModuleMap.setSeName(ServiceDocConsumerUnion.SENAME);
		outputModuleMap.setNodeName(ServiceDocConsumerUnion.NODENAME);
		outputModuleMap.setNodeInstID("OutputModule");
		outputModuleMap.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		outputModuleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outputModuleMap.setServiceEntityManager(serviceDocConsumerUnionManager);
		List<SearchConfigConnectCondition> outputModuleConditionList = new ArrayList<>();
		SearchConfigConnectCondition outputModuleCondition0 = new SearchConfigConnectCondition();
		outputModuleCondition0.setSourceFieldName("inputUnionUUID");
		outputModuleCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outputModuleConditionList.add(outputModuleCondition0);
		outputModuleMap.setConnectionConditions(outputModuleConditionList);
		Class<?>[] outputModuleConvToUIParas = { ServiceDocConsumerUnion.class,
				ServiceDocConfigureUIModel.class };
		outputModuleMap.setConvToUIMethodParas(outputModuleConvToUIParas);
		outputModuleMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvOutputModuleToUI);
		uiModelNodeMapList.add(outputModuleMap);
		serviceDocConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocConfigureExtensionUnion);
		return resultList;
	}

}
