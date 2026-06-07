package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.SearchFieldConfig;
import com.company.IntelligentPlatform.platform.model.SearchProxyConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class SearchFieldConfigServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SearchFieldConfig.NODENAME, nodeInstId = SearchFieldConfig.NODENAME)
	protected SearchProxyConfig searchFieldConfig;

	public SearchProxyConfig getSearchFieldConfig() {
		return searchFieldConfig;
	}

	public void setSearchFieldConfig(SearchProxyConfig searchFieldConfig) {
		this.searchFieldConfig = searchFieldConfig;
	}
}
