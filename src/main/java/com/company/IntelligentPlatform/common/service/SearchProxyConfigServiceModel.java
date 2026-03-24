package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SearchProxyConfigServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SearchProxyConfig.NODENAME, nodeInstId = SearchProxyConfig.SENAME)
	protected SearchProxyConfig searchProxyConfig;

	@IServiceModuleFieldConfig(nodeName = SearchFieldConfig.NODENAME, nodeInstId = SearchFieldConfig.NODENAME)
	protected List<ServiceEntityNode> searchFieldConfigList = new ArrayList<>();

	public SearchProxyConfig getSearchProxyConfig() {
		return this.searchProxyConfig;
	}

	public void setSearchProxyConfig(SearchProxyConfig searchProxyConfig) {
		this.searchProxyConfig = searchProxyConfig;
	}

	public List<ServiceEntityNode> getSearchFieldConfigList() {
		return this.searchFieldConfigList;
	}

	public void setSearchFieldConfigList(
			List<ServiceEntityNode> searchFieldConfigList) {
		this.searchFieldConfigList = searchFieldConfigList;
	}

}
