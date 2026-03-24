package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.OrganizationFunctionManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.OrganizationBarcodeBasicSettingManager;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.OrganizationAttachment;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class OrganizationServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OrganizationBarcodeBasicSettingManager organizationBarcodeBasicSettingManager;

	@Autowired
	protected OrganizationFunctionManager organizationFunctionManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				OrganizationAttachment.SENAME,
				OrganizationAttachment.NODENAME,
				OrganizationAttachment.NODENAME
		)));
        return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion organizationExtensionUnion = new ServiceUIModelExtensionUnion();
		organizationExtensionUnion.setNodeInstId(Organization.SENAME);
		organizationExtensionUnion.setNodeName(Organization.NODENAME);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setHostNodeFlag(true);
		Class<?>[] organizationConvToUIParas = { Organization.class,
				OrganizationUIModel.class };
		organizationMap.setConvToUIMethodParas(organizationConvToUIParas);
		organizationMap
				.setConvToUIMethod(OrganizationManager.METHOD_ConvOrganizationToUI);
		Class<?>[] OrganizationConvUIToParas = { OrganizationUIModel.class,
				Organization.class };
		organizationMap.setConvUIToMethodParas(OrganizationConvUIToParas);
		organizationMap
				.setConvUIToMethod(OrganizationManager.METHOD_ConvUIToOrganization);
		uiModelNodeMapList.add(organizationMap);

		// UI Model Configure of node:[ParentOrganization]
		UIModelNodeMapConfigure parentOrganizationMap = new UIModelNodeMapConfigure();
		parentOrganizationMap.setSeName(Organization.SENAME);
		parentOrganizationMap.setNodeName(Organization.NODENAME);
		parentOrganizationMap.setNodeInstID("ParentOrganization");
		parentOrganizationMap.setBaseNodeInstID(Organization.SENAME);
		parentOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		parentOrganizationMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> parentOrgConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentOrgCondition0 = new SearchConfigConnectCondition();
		parentOrgCondition0.setSourceFieldName("parentOrganizationUUID");
		parentOrgCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentOrgConditionList.add(parentOrgCondition0);
		parentOrganizationMap.setConnectionConditions(parentOrgConditionList);
		Class<?>[] parentOrgConvToUIParas = { Organization.class,
				OrganizationUIModel.class };
		parentOrganizationMap.setConvToUIMethodParas(parentOrgConvToUIParas);
		parentOrganizationMap
				.setConvToUIMethod(OrganizationManager.METHOD_ConvParentOrganizationToUI);
		uiModelNodeMapList.add(parentOrganizationMap);

		// UI Model Configure of node:[Accountant]
		UIModelNodeMapConfigure accountantMap = new UIModelNodeMapConfigure();
		accountantMap.setSeName(Employee.SENAME);
		accountantMap.setNodeName(Employee.NODENAME);
		accountantMap.setNodeInstID("Accountant");
		accountantMap.setBaseNodeInstID(Organization.SENAME);
		accountantMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		accountantMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> accountantConditionList = new ArrayList<>();
		SearchConfigConnectCondition accountantCondition0 = new SearchConfigConnectCondition();
		accountantCondition0.setSourceFieldName("refAccountantUUID");
		accountantCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		accountantConditionList.add(accountantCondition0);
		accountantMap.setConnectionConditions(accountantConditionList);
		Class<?>[] accountantConvToUIParas = { Employee.class,
				OrganizationUIModel.class };
		accountantMap.setConvToUIMethodParas(accountantConvToUIParas);
		accountantMap
				.setConvToUIMethod(OrganizationManager.METHOD_ConvAccountantToUI);
		uiModelNodeMapList.add(accountantMap);

		// UI Model Configure of node:[MainContact]
		UIModelNodeMapConfigure mainContactMap = new UIModelNodeMapConfigure();
		mainContactMap.setSeName(Employee.SENAME);
		mainContactMap.setNodeName(Employee.NODENAME);
		mainContactMap.setNodeInstID("MainContact");
		mainContactMap.setBaseNodeInstID(Organization.SENAME);
		mainContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		mainContactMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> mainContactConditionList = new ArrayList<>();
		SearchConfigConnectCondition mainContactCondition0 = new SearchConfigConnectCondition();
		mainContactCondition0.setSourceFieldName("mainContactUUID");
		mainContactCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		mainContactConditionList.add(mainContactCondition0);
		mainContactMap.setConnectionConditions(mainContactConditionList);
		Class<?>[] mainContactConvToUIParas = { Employee.class,
				OrganizationUIModel.class };
		mainContactMap.setConvToUIMethodParas(mainContactConvToUIParas);
		mainContactMap
				.setConvToUIMethod(OrganizationManager.METHOD_ConvMainContactToUI);
		uiModelNodeMapList.add(mainContactMap);

		// UI Model Configure of node:[Employee]
		UIModelNodeMapConfigure employeeMap = new UIModelNodeMapConfigure();
		employeeMap.setSeName(Employee.SENAME);
		employeeMap.setNodeName(Employee.NODENAME);
		employeeMap.setNodeInstID(Employee.SENAME);
		employeeMap.setBaseNodeInstID(Organization.SENAME);
		employeeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		employeeMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> employeeConditionList = new ArrayList<>();
		SearchConfigConnectCondition employeeCondition0 = new SearchConfigConnectCondition();
		employeeCondition0.setSourceFieldName("refCashierUUID");
		employeeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		employeeConditionList.add(employeeCondition0);
		employeeMap.setConnectionConditions(employeeConditionList);
		Class<?>[] employeeConvToUIParas = { Employee.class,
				OrganizationUIModel.class };
		employeeMap.setConvToUIMethodParas(employeeConvToUIParas);
		employeeMap
				.setConvToUIMethod(OrganizationManager.METHOD_ConvCashierToUI);
		uiModelNodeMapList.add(employeeMap);
		organizationExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(organizationExtensionUnion);
		return resultList;
	}

}
