package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;

@Service
public class SearchProxyConfigServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected SearchFieldConfigServiceUIModelExtension searchFieldConfigServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(searchFieldConfigServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion searchProxyConfigExtensionUnion = new ServiceUIModelExtensionUnion();
		searchProxyConfigExtensionUnion.setNodeInstId(SearchProxyConfig.SENAME);
		searchProxyConfigExtensionUnion.setNodeName(SearchProxyConfig.NODENAME);

		// UI Model Configure of node:[SearchProxyConfig]
		UIModelNodeMapConfigure searchProxyConfigMap = new UIModelNodeMapConfigure();
		searchProxyConfigMap.setSeName(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setNodeName(SearchProxyConfig.NODENAME);
		searchProxyConfigMap.setNodeInstID(SearchProxyConfig.SENAME);
		searchProxyConfigMap.setHostNodeFlag(true);
		Class<?>[] searchProxyConfigConvToUIParas = { SearchProxyConfig.class,
				SearchProxyConfigUIModel.class };
		searchProxyConfigMap
				.setConvToUIMethodParas(searchProxyConfigConvToUIParas);
		searchProxyConfigMap
				.setConvToUIMethod(SearchProxyConfigManager.METHOD_ConvSearchProxyConfigToUI);
		Class<?>[] SearchProxyConfigConvUIToParas = {
				SearchProxyConfigUIModel.class, SearchProxyConfig.class };
		searchProxyConfigMap
				.setConvUIToMethodParas(SearchProxyConfigConvUIToParas);
		searchProxyConfigMap
				.setConvUIToMethod(SearchProxyConfigManager.METHOD_ConvUIToSearchProxyConfig);
		uiModelNodeMapList.add(searchProxyConfigMap);
		searchProxyConfigExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(searchProxyConfigExtensionUnion);
		return resultList;
	}

}
