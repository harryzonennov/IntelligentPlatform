package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.CorporateCustomerToFinSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.platform.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@Service
public class CorporateSupplierToFinSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return CorporateCustomerToFinSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return corporateCustomerManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node:[account]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig0.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig0.setNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(FinAccountObjectRef.SENAME);
		searchNodeConfig1.setNodeName(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setBaseNodeInstID(CorporateCustomer.SENAME);
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
