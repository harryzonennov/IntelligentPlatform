package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.EmployeeOrgManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.EmployeeOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class EmployeeOrgServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected EmployeeOrgManager employeeOrgManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion employeeOrgReferenceExtensionUnion = new ServiceUIModelExtensionUnion();
		employeeOrgReferenceExtensionUnion
				.setNodeInstId(EmployeeOrgReference.NODENAME);
		employeeOrgReferenceExtensionUnion
				.setNodeName(EmployeeOrgReference.NODENAME);

		// UI Model Configure of node:[EmployeeOrgReference]
		UIModelNodeMapConfigure employeeOrgReferenceMap = new UIModelNodeMapConfigure();
		employeeOrgReferenceMap.setSeName(EmployeeOrgReference.SENAME);
		employeeOrgReferenceMap.setNodeName(EmployeeOrgReference.NODENAME);
		employeeOrgReferenceMap.setNodeInstID(EmployeeOrgReference.NODENAME);
		Class<?>[] logonUserOrgConvToUIParas = { EmployeeOrgReference.class,
				EmployeeOrgUIModel.class };
		employeeOrgReferenceMap
				.setConvToUIMethodParas(logonUserOrgConvToUIParas);
		employeeOrgReferenceMap
				.setConvToUIMethod(EmployeeOrgManager.METHOD_ConvEmployeeOrgToEmployeeOrgUIModel);
		employeeOrgReferenceMap.setLogicManager(employeeOrgManager);
		Class<?>[] logonConvUIToLogonUserOrgParas = {
				EmployeeOrgUIModel.class, EmployeeOrgReference.class };
		employeeOrgReferenceMap
				.setConvUIToMethodParas(logonConvUIToLogonUserOrgParas);
		employeeOrgReferenceMap
				.setConvUIToMethod(EmployeeOrgManager.METHOD_ConvUIToEmployeeOrgReference);
		employeeOrgReferenceMap.setHostNodeFlag(true);
		uiModelNodeMapList.add(employeeOrgReferenceMap);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setBaseNodeInstID(EmployeeOrgReference.NODENAME);
		organizationMap.setServiceEntityManager(organizationManager);
		organizationMap.setLogicManager(employeeOrgManager);
		organizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] organizationConvToUIParas = { Organization.class,
				EmployeeOrgUIModel.class };
		organizationMap.setConvToUIMethodParas(organizationConvToUIParas);
		organizationMap
				.setConvToUIMethod(EmployeeOrgManager.METHOD_ConvOrganizationToEmployeeOrgUIModel);
		uiModelNodeMapList.add(organizationMap);

		employeeOrgReferenceExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(employeeOrgReferenceExtensionUnion);
		return resultList;
	}

}
