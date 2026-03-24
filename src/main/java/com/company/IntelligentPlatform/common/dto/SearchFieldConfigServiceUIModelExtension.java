package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SearchFieldConfigUIModel;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;

@Service
public class SearchFieldConfigServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion searchFieldConfigExtensionUnion = new ServiceUIModelExtensionUnion();
		searchFieldConfigExtensionUnion
				.setNodeInstId(SearchFieldConfig.NODENAME);
		searchFieldConfigExtensionUnion.setNodeName(SearchFieldConfig.NODENAME);

		// UI Model Configure of node:[SearchFieldConfig]
		UIModelNodeMapConfigure searchFieldConfigMap = new UIModelNodeMapConfigure();
		searchFieldConfigMap.setSeName(SearchFieldConfig.SENAME);
		searchFieldConfigMap.setNodeName(SearchFieldConfig.NODENAME);
		searchFieldConfigMap.setNodeInstID(SearchFieldConfig.NODENAME);
		searchFieldConfigMap.setHostNodeFlag(true);
		Class<?>[] searchFieldConfigConvToUIParas = { SearchFieldConfig.class,
				SearchFieldConfigUIModel.class };
		searchFieldConfigMap
				.setConvToUIMethodParas(searchFieldConfigConvToUIParas);
		searchFieldConfigMap
				.setConvToUIMethod(SearchProxyConfigManager.METHOD_ConvSearchFieldConfigToUI);
		Class<?>[] SearchFieldConfigConvUIToParas = {
				SearchFieldConfigUIModel.class, SearchFieldConfig.class };
		searchFieldConfigMap
				.setConvUIToMethodParas(SearchFieldConfigConvUIToParas);
		searchFieldConfigMap
				.setConvUIToMethod(SearchProxyConfigManager.METHOD_ConvUIToSearchFieldConfig);
		uiModelNodeMapList.add(searchFieldConfigMap);
		
		UIModelNodeMapConfigure searchProxyConfigMap = new UIModelNodeMapConfigure();
		searchProxyConfigMap.setSeName(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setNodeName(SearchProxyConfig.NODENAME);
		searchProxyConfigMap.setNodeInstID(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setBaseNodeInstID(SearchFieldConfig.NODENAME);
		searchProxyConfigMap.setHostNodeFlag(false);
		searchProxyConfigMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] searchProxyConfigConvToUIParas = { SearchProxyConfig.class,
				SearchFieldConfigUIModel.class };
		searchProxyConfigMap
				.setConvToUIMethodParas(searchProxyConfigConvToUIParas);
		searchProxyConfigMap
				.setConvToUIMethod(SearchProxyConfigManager.METHOD_ConvProxyToFieldUI);
		Class<?>[] SearchProxyConfigConvToUIParas = {
				SearchProxyConfig.class, SearchFieldConfigUIModel.class };
		searchProxyConfigMap
				.setConvUIToMethodParas(SearchProxyConfigConvToUIParas);		
		uiModelNodeMapList.add(searchProxyConfigMap);
		
		searchFieldConfigExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(searchFieldConfigExtensionUnion);
		return resultList;
	}

}
