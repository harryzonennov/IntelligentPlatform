package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.SearchProxyConfigSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchProxyConfigSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected SearchProxyConfigManager searchProxyConfigManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return SearchProxyConfigSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.SystemResource;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[SearchProxyConfig]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[MaterialType]
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(SearchProxyConfig.class).startNodeFlag(true).build());
		return searchNodeConfigList;
	}

}
