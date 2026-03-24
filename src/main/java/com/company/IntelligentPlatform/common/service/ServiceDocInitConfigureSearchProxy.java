package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceDocInitConfigureSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceDocInitConfigureSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected ServiceDocumentSettingManager serviceDocumentSettingManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceDocInitConfigureSearchModel.class;
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
		// start node:[ServiceDocInitConfigure]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceDocInitConfigure.class).startNodeFlag(true).build());
		// search node [ServiceDocInitConfigure->ServiceDocumentSetting]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceDocumentSetting.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).baseNodeInstId(ServiceDocInitConfigure.NODENAME).build());
		return searchNodeConfigList;
	}



}
