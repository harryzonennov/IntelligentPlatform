package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;

@Component
public class SearchProxyConfigServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SearchProxyConfig.NODENAME, nodeInstId = SearchProxyConfig.SENAME)
	protected SearchProxyConfigUIModel searchProxyConfigUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SearchFieldConfig.NODENAME, nodeInstId = SearchFieldConfig.NODENAME)
	protected List<SearchFieldConfigUIModel> searchFieldConfigUIModelList = new ArrayList<SearchFieldConfigUIModel>();

	public SearchProxyConfigUIModel getSearchProxyConfigUIModel() {
		return this.searchProxyConfigUIModel;
	}

	public void setSearchProxyConfigUIModel(
			SearchProxyConfigUIModel searchProxyConfigUIModel) {
		this.searchProxyConfigUIModel = searchProxyConfigUIModel;
	}

	public List<SearchFieldConfigUIModel> getSearchFieldConfigUIModelList() {
		return this.searchFieldConfigUIModelList;
	}

	public void setSearchFieldConfigUIModelList(
			List<SearchFieldConfigUIModel> searchFieldConfigUIModelList) {
		this.searchFieldConfigUIModelList = searchFieldConfigUIModelList;
	}

}
