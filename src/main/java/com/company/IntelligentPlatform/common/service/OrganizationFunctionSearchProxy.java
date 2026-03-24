package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.SearchContext;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationFunctionSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected OrganizationFunctionManager organizationFunctionManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return null;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return organizationFunctionManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[individual customer-root]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(OrganizationFunction.SENAME);
		searchNodeConfig0.setNodeName(OrganizationFunction.NODENAME);
		searchNodeConfig0.setNodeInstID(OrganizationFunction.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		return searchNodeConfigList;
	}

}
