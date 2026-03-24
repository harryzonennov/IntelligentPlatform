package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceDocumentSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceDocumentSettingSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceDocumentSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return serviceExtensionSettingManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[serviceExtensionSetting->ServiceExtendFieldSetting]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceDocumentSetting.class).startNodeFlag(true).build());
		return searchNodeConfigList;
	}
}
