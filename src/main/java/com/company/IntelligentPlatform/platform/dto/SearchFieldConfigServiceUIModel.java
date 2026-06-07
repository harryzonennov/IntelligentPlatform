package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SearchFieldConfig;

@Component
public class SearchFieldConfigServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SearchFieldConfig.NODENAME, nodeInstId = SearchFieldConfig.NODENAME)
	protected SearchFieldConfigUIModel searchProxyConfigUIModel;

	public SearchFieldConfigUIModel getSearchFieldConfigUIModel() {
		return searchProxyConfigUIModel;
	}

	public void setSearchFieldConfigUIModel(SearchFieldConfigUIModel searchProxyConfigUIModel) {
		this.searchProxyConfigUIModel = searchProxyConfigUIModel;
	}
}
