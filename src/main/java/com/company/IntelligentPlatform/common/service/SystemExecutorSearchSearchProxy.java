package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceDocumentSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;

import java.util.List;
import java.util.Map;

@Service
public class SystemExecutorSearchSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SystemExecutorSettingManager systemExecutorSettingManager;
	
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
		return systemExecutorSettingManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[SystemExecutorSetting->SystemCodeValueUnion]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(SystemExecutorSetting.class,
						SystemExecutorLog.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(AuthorizationObject.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refAOUUID").
				baseNodeInstId(SystemExecutorSetting.SENAME).build());
		return searchNodeConfigList;
	}
}
