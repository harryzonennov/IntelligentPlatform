package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.RoleAuthorizationObjectSearchModel;
import com.company.IntelligentPlatform.common.dto.RoleSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;

import java.util.List;
import java.util.Map;

@Service
public class RoleSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected RoleManager roleManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return RoleSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return RoleAuthorizationObjectSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return roleManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

    @Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(Role.class,
						RoleAuthorization.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(AuthorizationObject.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).
				baseNodeInstId(RoleAuthorization.NODENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(RoleSubAuthorization.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
				baseNodeInstId(RoleAuthorization.NODENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(AuthorizationObject.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
				baseNodeInstId(RoleAuthorization.NODENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(RoleMessageCategory.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
				baseNodeInstId(Role.SENAME).build());
		return searchNodeConfigList;
	}
}
