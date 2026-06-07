package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.AuthorizationObjectSearchModel;
import com.company.IntelligentPlatform.platform.service.SystemAuthorizationObjectSearchProxy;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.model.AuthorizationGroup;
import com.company.IntelligentPlatform.platform.model.AuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationObjectSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected SystemAuthorizationObjectSearchProxy systemAuthorizationObjectSearchProxy;

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
		return authorizationObjectManager.getAuthorizationResource();
	}

	public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		BSearchResponse authorizationSearchResponse = super.searchDocList(searchContext);
		BSearchResponse systemSearchResponse = systemAuthorizationObjectSearchProxy.searchDocList(searchContext);
		return ServiceSearchProxy.mergeSearchResponse(authorizationSearchResponse, systemSearchResponse);
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(
				new BSearchNodeComConfigureBuilder().modelClass(AuthorizationObject.class).startNodeFlag(true).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(AuthorizationGroup.class).mapFieldUUID("refAGUUID").
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).baseNodeInstId(AuthorizationObject.SENAME).build());
		return searchNodeConfigList;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

}
