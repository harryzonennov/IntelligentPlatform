package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.EmployeeSearchModel;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

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
