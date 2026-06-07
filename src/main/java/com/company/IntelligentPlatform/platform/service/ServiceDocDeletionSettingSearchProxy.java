package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.ServiceDocDeletionSettingSearchModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.platform.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceDocDeletionSettingSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected ServiceDocumentSettingManager serviceDocumentSettingManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceDocDeletionSettingSearchModel.class;
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
		// start node:[ServiceDocDeletionSettingIdId]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceDocDeletionSetting.class).startNodeFlag(true).build());
		// search node [ServiceDocDeletionSettingIdId->ServiceDocumentSetting]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceDocumentSetting.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).baseNodeInstId(ServiceDocDeletionSetting.NODENAME).build());
		return searchNodeConfigList;
	}

}
