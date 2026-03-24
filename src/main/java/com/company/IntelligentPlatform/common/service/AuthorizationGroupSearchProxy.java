package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.AuthorizationGroupSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationGroupSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return AuthorizationGroupSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return authorizationGroupManager.getAuthorizationResource();
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(
				new BSearchNodeComConfigureBuilder().modelClass(AuthorizationGroup.class).startNodeFlag(true).build());
		return searchNodeConfigList;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

}
