package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.IndividualCustomerToFinSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class IndividualCustomerToFinSearchProxy extends ServiceSearchProxy {

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

	
	public List<BSearchNodeComConfigure> getSearchConfigure(){
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[account]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig0.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig0.setNodeInstID(IndividualCustomer.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(FinAccountObjectRef.SENAME);
		searchNodeConfig1.setNodeName(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setBaseNodeInstID(IndividualCustomer.SENAME);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_REFTO_TARGET);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(FinAccount.SENAME);
		searchNodeConfig2.setNodeName(FinAccount.NODENAME);
		searchNodeConfig2.setNodeInstID(FinAccount.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2.setBaseNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig2
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig2);
		return searchNodeConfigList;
	}

}
