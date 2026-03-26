package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.EmployeeToFinSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.EmployeeManager;
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
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class EmployeeToFinSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected EmployeeManager employeeManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return EmployeeToFinSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return employeeManager.getAuthorizationResource();
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
		searchNodeConfig0.setSeName(Employee.SENAME);
		searchNodeConfig0.setNodeName(Employee.NODENAME);
		searchNodeConfig0.setNodeInstID(Employee.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(FinAccountObjectRef.SENAME);
		searchNodeConfig1.setNodeName(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setBaseNodeInstID(Employee.SENAME);
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
