package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.HostCompanySearchModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.HostCompanyManager;
import com.company.IntelligentPlatform.platform.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SearchContext;
import com.company.IntelligentPlatform.platform.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.platform.model.HostCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HostCompanySearchProxy extends ServiceSearchProxy{

	@Autowired
	protected HostCompanyManager hostCompanyManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return HostCompanySearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return hostCompanyManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[root]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(HostCompany.SENAME);
		searchNodeConfig0.setNodeName(HostCompany.NODENAME);
		searchNodeConfig0.setNodeInstID(HostCompany.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		return searchNodeConfigList;
	}
	
}
