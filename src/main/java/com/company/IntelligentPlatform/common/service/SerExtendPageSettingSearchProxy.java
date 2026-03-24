package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SerExtendPageSettingSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return SerExtendPageSettingSearchModel.class;
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
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext)  throws SearchConfigureException {
		// start node:[serviceExtensionSetting->ServiceExtendFieldSetting]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(SerExtendPageSetting.class).startNodeFlag(true).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceExtensionSetting.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).baseNodeInstId(SerExtendPageSetting.NODENAME).build());
		return searchNodeConfigList;
	}
}
