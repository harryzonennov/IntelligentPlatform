package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.dto.EmployeeSearchModel;
import com.company.IntelligentPlatform.platform.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.IndividualCustomer;
import com.company.IntelligentPlatform.platform.model.City;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@Service
public class IndividualCustomerSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

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
		return individualCustomerManager.getAuthorizationResource();
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
		searchNodeConfig0.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig0.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig0.setNodeInstID(IndividualCustomer.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// [individual customer-root]->[city-root]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(City.SENAME);
		searchNodeConfig1.setNodeName(City.NODENAME);
		searchNodeConfig1.setNodeInstID(City.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1.setBaseNodeInstID(IndividualCustomer.SENAME);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_OTHERS);
		searchNodeConfig1.setMapBaseFieldName("cityName");
		searchNodeConfig1
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.NAME);
		searchNodeConfigList.add(searchNodeConfig1);
		return searchNodeConfigList;
	}

}
