package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.AuthorizationObjectSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.SystemAuthorizationObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SystemAuthorizationObjectSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SystemAuthorizationObjectManager systemAuthorizationObjectManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return AuthorizationObjectSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return systemAuthorizationObjectManager.getAuthorizationResource();
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(
				new BSearchNodeComConfigureBuilder().modelClass(SystemAuthorizationObject.class).startNodeFlag(true).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(AuthorizationGroup.class).mapFieldUUID("refAGUUID").
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).baseNodeInstId(SystemAuthorizationObject.SENAME).build());
		return searchNodeConfigList;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

}
