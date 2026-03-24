package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.FlowRouterSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;

import java.util.List;
import java.util.Map;

@Service
public class FlowRouterSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected FlowRouterManager flowRouterManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return FlowRouterSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return flowRouterManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[FlowRouter root node]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(FlowRouter.class,
						FlowRouterExtendClass.class);
		// search node [FlowRouterExtendClass->LogonUser]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(LogonUser.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refDirectAssigneeUUID").
				baseNodeInstId(FlowRouterExtendClass.NODENAME).build());
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(FlowRouter.class, null));
		return searchNodeConfigList;
	}

}
