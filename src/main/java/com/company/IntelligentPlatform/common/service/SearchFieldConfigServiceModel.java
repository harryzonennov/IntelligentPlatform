package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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
