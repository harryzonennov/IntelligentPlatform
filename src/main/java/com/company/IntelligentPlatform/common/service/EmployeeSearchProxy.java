package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.EmployeeSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class EmployeeSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected EmployeeManager employeeManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return EmployeeSearchModel.class;
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
		// start node:[Employee->EmpLogonUserReference]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(Employee.class,
						EmpLogonUserReference.class);
		// search node [EmpLogonUserReference->LogonUser]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(LogonUser.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(EmpLogonUserReference.NODENAME).build());
		// search node3: [Employee->EmployeeOrgReference->Organization]
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildReference(EmployeeOrgReference.class, Organization.class, null, null,
				SearchModelConfigHelper.genBuilder().baseNodeInstId(Employee.SENAME).toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT)));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				EmployeeActionNode.class, EmployeeActionNode.NODEINST_ACTION_SUBMIT,
				EmployeeActionNode.DOC_ACTION_SUBMIT,
				null, Employee.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				EmployeeActionNode.class, EmployeeActionNode.NODEINST_ACTION_APPROVE,
				EmployeeActionNode.DOC_ACTION_APPROVE,
				null, Employee.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				EmployeeActionNode.class, EmployeeActionNode.NODEINST_ACTION_ACTIVE,
				EmployeeActionNode.DOC_ACTION_ACTIVE,
				null, Employee.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				EmployeeActionNode.class, EmployeeActionNode.NODEINST_ACTION_REINIT,
				EmployeeActionNode.DOC_ACTION_REINIT,
				null, Employee.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				EmployeeActionNode.class, EmployeeActionNode.NODEINST_ACTION_ARCHIVE,
				EmployeeActionNode.DOC_ACTION_ARCHIVE,
				null, Employee.SENAME
		));
		return searchNodeConfigList;
	}

}
