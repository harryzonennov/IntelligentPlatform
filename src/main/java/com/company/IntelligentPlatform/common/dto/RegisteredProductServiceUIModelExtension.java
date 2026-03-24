package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeConfigPreCondition;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.MaterialActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.InvolvePartyTemplate;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;

@Service
public class RegisteredProductServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected RegisteredProductExtendPropertyServiceUIModelExtension registeredProductExtendPropertyServiceUIModelExtension;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected MaterialActionNodeProxy materialActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(registeredProductExtendPropertyServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				RegisteredProductAttachment.SENAME,
				RegisteredProductAttachment.NODENAME,
				RegisteredProductAttachment.NODENAME
		)));
		resultList.add(materialActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RegisteredProductActionLog.SENAME,
				RegisteredProductActionLog.NODENAME,
				RegisteredProductActionLog.NODEINST_ACTION_ACTIVE,
				registeredProductManager, RegisteredProductActionLog.DOC_ACTION_ACTIVE
		)));
		resultList.add(materialActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RegisteredProductActionLog.SENAME,
				RegisteredProductActionLog.NODENAME,
				RegisteredProductActionLog.NODEINST_ACTION_INPROCESS,
				registeredProductManager, RegisteredProductActionLog.DOC_ACTION_INPROCESS
		)));
		resultList.add(materialActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RegisteredProductActionLog.SENAME,
				RegisteredProductActionLog.NODENAME,
				RegisteredProductActionLog.NODEINST_ACTION_ARCHIVE,
				registeredProductManager, RegisteredProductActionLog.DOC_ACTION_ARCHIVE
		)));
		resultList.add(materialActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RegisteredProductActionLog.SENAME,
				RegisteredProductActionLog.NODENAME,
				RegisteredProductActionLog.NODEINST_ACTION_DELETE,
				registeredProductManager, RegisteredProductActionLog.DOC_ACTION_DELETE
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_SALESBY,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_SALESBY,
				Organization.class, Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_SALESTO,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_SALESTO,
				CorporateCustomer.class, IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_PURCHASEBY,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_PURCHASEBY,
				Organization.class, Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_PURCHASEFROM,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_PURCHASEFROM,
				CorporateCustomer.class, IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_PRODUCTBY,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_PRODUCTBY,
				Organization.class, Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RegisteredProductInvolveParty.SENAME,
				RegisteredProductInvolveParty.NODENAME,
				RegisteredProductInvolveParty.NODEINST_SUPPORTBY,
				registeredProductManager,
				RegisteredProductInvolveParty.ROLE_ID_SUPPORTBY,
				Organization.class, Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion registeredProductExtensionUnion = new ServiceUIModelExtensionUnion();
		registeredProductExtensionUnion.setNodeInstId(RegisteredProduct.SENAME);
		registeredProductExtensionUnion.setNodeName(RegisteredProduct.NODENAME);

		// UI Model Configure of node:[RegisteredProduct]
		UIModelNodeMapConfigure registeredProductMap = new UIModelNodeMapConfigure();
		registeredProductMap.setSeName(RegisteredProduct.SENAME);
		registeredProductMap.setNodeName(RegisteredProduct.NODENAME);
		registeredProductMap.setNodeInstID(RegisteredProduct.SENAME);
		registeredProductMap.setHostNodeFlag(true);
		Class<?>[] registeredProductConvToUIParas = { RegisteredProduct.class,
				RegisteredProductUIModel.class, List.class};
		registeredProductMap
				.setConvToUIMethodParas(registeredProductConvToUIParas);
		registeredProductMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvRegisteredProductToUI);
		Class<?>[] RegisteredProductConvUIToParas = {
				RegisteredProductUIModel.class, RegisteredProduct.class };
		registeredProductMap
				.setConvUIToMethodParas(RegisteredProductConvUIToParas);
		registeredProductMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToRegisteredProduct);
		uiModelNodeMapList.add(registeredProductMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialSKUMap = new UIModelNodeMapConfigure();
		materialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialSKUMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		materialSKUMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialSKUMap.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> materialSKUConditions = new ArrayList<>();
		SearchConfigConnectCondition materialSKUConditionCondition0 = new SearchConfigConnectCondition();
		materialSKUConditionCondition0.setSourceFieldName("refMaterialSKUUUID");
		materialSKUConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialSKUConditions.add(materialSKUConditionCondition0);
		materialSKUMap.setConnectionConditions(materialSKUConditions);
		Class<?>[] materialSKUConvToUIParas = { MaterialStockKeepUnit.class,
				RegisteredProductUIModel.class };
		materialSKUMap.setConvToUIMethodParas(materialSKUConvToUIParas);
		materialSKUMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(materialSKUMap);

		// UI Model Configure of node:[PurchaseOrgParty]
		UIModelNodeMapConfigure purchaseOrgPartyMap = new UIModelNodeMapConfigure();
		purchaseOrgPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		purchaseOrgPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		purchaseOrgPartyMap.setNodeInstID("PurchaseOrgParty");
		purchaseOrgPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		purchaseOrgPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		purchaseOrgPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> purchaseOrgPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition purchaseOrgPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		purchaseOrgPartyPreCondtion0.setFieldName("partyRole");
		purchaseOrgPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_PURORG);
		purchaseOrgPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		purchaseOrgPartyPreConditions.add(purchaseOrgPartyPreCondtion0);
		purchaseOrgPartyMap.setPreConditions(purchaseOrgPartyPreConditions);
		Class<?>[] purchaseOrgPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		purchaseOrgPartyMap
				.setConvToUIMethodParas(purchaseOrgPartyConvToUIParas);
		purchaseOrgPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvPurchaseOrgPartyToUI);
		Class<?>[] PurchaseOrgPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		purchaseOrgPartyMap
				.setConvUIToMethodParas(PurchaseOrgPartyConvUIToParas);
		purchaseOrgPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToPurchaseOrgParty);
		uiModelNodeMapList.add(purchaseOrgPartyMap);

		// UI Model Configure of node:[PurchaseOrgContact]
		UIModelNodeMapConfigure purchaseOrgContactMap = new UIModelNodeMapConfigure();
		purchaseOrgContactMap.setSeName(Employee.SENAME);
		purchaseOrgContactMap.setNodeName(Employee.NODENAME);
		purchaseOrgContactMap.setNodeInstID("PurchaseOrgContact");
		purchaseOrgContactMap.setBaseNodeInstID("PurchaseOrgParty");
		purchaseOrgContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> purchaseOrgContactConditions = new ArrayList<>();
		SearchConfigConnectCondition purchaseOrgContactConditionCondition0 = new SearchConfigConnectCondition();
		purchaseOrgContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		purchaseOrgContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		purchaseOrgContactConditions.add(purchaseOrgContactConditionCondition0);
		purchaseOrgContactMap
				.setConnectionConditions(purchaseOrgContactConditions);
		purchaseOrgContactMap.setServiceEntityManager(employeeManager);
		Class<?>[] purchaseOrgContactConvToUIParas = { Employee.class,
				RegisteredProductUIModel.class };
		purchaseOrgContactMap
				.setConvToUIMethodParas(purchaseOrgContactConvToUIParas);
		purchaseOrgContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvPurchaseOrgContactToUI);
		uiModelNodeMapList.add(purchaseOrgContactMap);

		// UI Model Configure of node:[PurchaseOrganization]
		UIModelNodeMapConfigure purchaseOrganizationMap = new UIModelNodeMapConfigure();
		purchaseOrganizationMap.setSeName(Organization.SENAME);
		purchaseOrganizationMap.setNodeName(Organization.NODENAME);
		purchaseOrganizationMap.setNodeInstID("PurchaseOrganization");
		purchaseOrganizationMap.setBaseNodeInstID("PurchaseOrgParty");
		purchaseOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		purchaseOrganizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] purchaseOrganizationConvToUIParas = { Organization.class,
				RegisteredProductUIModel.class };
		purchaseOrganizationMap
				.setConvToUIMethodParas(purchaseOrganizationConvToUIParas);
		purchaseOrganizationMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvPurchaseOrganizationToUI);
		uiModelNodeMapList.add(purchaseOrganizationMap);

		// UI Model Configure of node:[supportOrgParty]
		UIModelNodeMapConfigure salesOrgPartyMap = new UIModelNodeMapConfigure();
		salesOrgPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		salesOrgPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		salesOrgPartyMap.setNodeInstID("SalesOrgParty");
		salesOrgPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		salesOrgPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		salesOrgPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> salesOrgPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition salesOrgPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		salesOrgPartyPreCondtion0.setFieldName("partyRole");
		salesOrgPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_SALESORG);
		salesOrgPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		salesOrgPartyPreConditions.add(salesOrgPartyPreCondtion0);
		salesOrgPartyMap.setPreConditions(salesOrgPartyPreConditions);
		Class<?>[] salesOrgPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		salesOrgPartyMap.setConvToUIMethodParas(salesOrgPartyConvToUIParas);
		salesOrgPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSalesOrgPartyToUI);
		Class<?>[] SalesOrgPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		salesOrgPartyMap.setConvUIToMethodParas(SalesOrgPartyConvUIToParas);
		salesOrgPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToSalesOrgParty);
		uiModelNodeMapList.add(salesOrgPartyMap);

		// UI Model Configure of node:[SalesOrganization]
		UIModelNodeMapConfigure salesOrganizationMap = new UIModelNodeMapConfigure();
		salesOrganizationMap.setSeName(Organization.SENAME);
		salesOrganizationMap.setNodeName(Organization.NODENAME);
		salesOrganizationMap.setNodeInstID("SalesOrganization");
		salesOrganizationMap.setBaseNodeInstID("SalesOrgParty");
		salesOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		salesOrganizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] salesOrganizationConvToUIParas = { Organization.class,
				RegisteredProductUIModel.class };
		salesOrganizationMap
				.setConvToUIMethodParas(salesOrganizationConvToUIParas);
		salesOrganizationMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSalesOrganizationToUI);
		uiModelNodeMapList.add(salesOrganizationMap);

		// UI Model Configure of node:[SalesOrgContact]
		UIModelNodeMapConfigure salesOrgContactMap = new UIModelNodeMapConfigure();
		salesOrgContactMap.setSeName(Employee.SENAME);
		salesOrgContactMap.setNodeName(Employee.NODENAME);
		salesOrgContactMap.setNodeInstID("SalesOrgContact");
		salesOrgContactMap.setBaseNodeInstID("SalesOrgParty");
		salesOrgContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		salesOrgContactMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> salesOrgContactConditions = new ArrayList<>();
		SearchConfigConnectCondition salesOrgContactConditionCondition0 = new SearchConfigConnectCondition();
		salesOrgContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		salesOrgContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		salesOrgContactConditions.add(salesOrgContactConditionCondition0);
		salesOrgContactMap.setConnectionConditions(salesOrgContactConditions);
		Class<?>[] salesOrgContactConvToUIParas = { Employee.class,
				RegisteredProductUIModel.class };
		salesOrgContactMap.setConvToUIMethodParas(salesOrgContactConvToUIParas);
		salesOrgContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSalesOrgContactToUI);
		uiModelNodeMapList.add(salesOrgContactMap);

		// UI Model Configure of node:[ProductOrgParty]
		UIModelNodeMapConfigure productOrgPartyMap = new UIModelNodeMapConfigure();
		productOrgPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		productOrgPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		productOrgPartyMap.setNodeInstID("ProductOrgParty");
		productOrgPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		productOrgPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		productOrgPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> productOrgPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition productOrgPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		productOrgPartyPreCondtion0.setFieldName("partyRole");
		productOrgPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_PRODORG);
		productOrgPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		productOrgPartyPreConditions.add(productOrgPartyPreCondtion0);
		productOrgPartyMap.setPreConditions(productOrgPartyPreConditions);
		Class<?>[] productOrgPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		productOrgPartyMap.setConvToUIMethodParas(productOrgPartyConvToUIParas);
		productOrgPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvProductOrgPartyToUI);
		Class<?>[] productOrgPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		productOrgPartyMap.setConvUIToMethodParas(productOrgPartyConvUIToParas);
		productOrgPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToProductOrgParty);
		uiModelNodeMapList.add(productOrgPartyMap);

		// UI Model Configure of node:[ProductOrganization]
		UIModelNodeMapConfigure productOrganizationMap = new UIModelNodeMapConfigure();
		productOrganizationMap.setSeName(Organization.SENAME);
		productOrganizationMap.setNodeName(Organization.NODENAME);
		productOrganizationMap.setNodeInstID("ProductOrganization");
		productOrganizationMap.setBaseNodeInstID("ProductOrgParty");
		productOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		productOrganizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] productOrganizationConvToUIParas = { Organization.class,
				RegisteredProductUIModel.class };
		productOrganizationMap
				.setConvToUIMethodParas(productOrganizationConvToUIParas);
		productOrganizationMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvProductOrganizationToUI);
		uiModelNodeMapList.add(productOrganizationMap);

		// UI Model Configure of node:[ProductOrgContact]
		UIModelNodeMapConfigure productOrgContactMap = new UIModelNodeMapConfigure();
		productOrgContactMap.setSeName(Employee.SENAME);
		productOrgContactMap.setNodeName(Employee.NODENAME);
		productOrgContactMap.setNodeInstID("ProductOrgContact");
		productOrgContactMap.setBaseNodeInstID("ProductOrgParty");
		productOrgContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productOrgContactMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> productOrgContactConditions = new ArrayList<>();
		SearchConfigConnectCondition productOrgContactConditionCondition0 = new SearchConfigConnectCondition();
		productOrgContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		productOrgContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		productOrgContactConditions.add(productOrgContactConditionCondition0);
		productOrgContactMap
				.setConnectionConditions(productOrgContactConditions);
		Class<?>[] productOrgContactConvToUIParas = { Employee.class,
				RegisteredProductUIModel.class };
		productOrgContactMap
				.setConvToUIMethodParas(productOrgContactConvToUIParas);
		productOrgContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvProductOrgContactToUI);
		uiModelNodeMapList.add(productOrgContactMap);

		// UI Model Configure of node:[SupportOrgParty]
		UIModelNodeMapConfigure supportOrgPartyMap = new UIModelNodeMapConfigure();
		supportOrgPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		supportOrgPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		supportOrgPartyMap.setNodeInstID("SupportOrgParty");
		supportOrgPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		supportOrgPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		supportOrgPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> supportOrgPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition supportOrgPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		supportOrgPartyPreCondtion0.setFieldName("partyRole");
		supportOrgPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_SALESORG);
		supportOrgPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		supportOrgPartyPreConditions.add(supportOrgPartyPreCondtion0);
		supportOrgPartyMap.setPreConditions(supportOrgPartyPreConditions);
		Class<?>[] supportOrgPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		supportOrgPartyMap.setConvToUIMethodParas(supportOrgPartyConvToUIParas);
		supportOrgPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSupportOrgPartyToUI);
		Class<?>[] SupportOrgPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		supportOrgPartyMap.setConvUIToMethodParas(SupportOrgPartyConvUIToParas);
		supportOrgPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToSupportOrgParty);
		uiModelNodeMapList.add(supportOrgPartyMap);

		// UI Model Configure of node:[SupportOrganization]
		UIModelNodeMapConfigure supportOrganizationMap = new UIModelNodeMapConfigure();
		supportOrganizationMap.setSeName(Organization.SENAME);
		supportOrganizationMap.setNodeName(Organization.NODENAME);
		supportOrganizationMap.setNodeInstID("SupportOrganization");
		supportOrganizationMap.setBaseNodeInstID("SupportOrgParty");
		supportOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		supportOrganizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] supportOrganizationConvToUIParas = { Organization.class,
				RegisteredProductUIModel.class };
		supportOrganizationMap
				.setConvToUIMethodParas(supportOrganizationConvToUIParas);
		supportOrganizationMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSupportOrganizationToUI);
		uiModelNodeMapList.add(supportOrganizationMap);

		// UI Model Configure of node:[SupportOrgContact]
		UIModelNodeMapConfigure supportOrgContactMap = new UIModelNodeMapConfigure();
		supportOrgContactMap.setSeName(Employee.SENAME);
		supportOrgContactMap.setNodeName(Employee.NODENAME);
		supportOrgContactMap.setNodeInstID("SupportOrgContact");
		supportOrgContactMap.setBaseNodeInstID("SupportOrgParty");
		supportOrgContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		supportOrgContactMap.setServiceEntityManager(employeeManager);
		List<SearchConfigConnectCondition> supportOrgContactConditions = new ArrayList<>();
		SearchConfigConnectCondition supportOrgContactConditionCondition0 = new SearchConfigConnectCondition();
		supportOrgContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		supportOrgContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		supportOrgContactConditions.add(supportOrgContactConditionCondition0);
		supportOrgContactMap
				.setConnectionConditions(supportOrgContactConditions);
		Class<?>[] supportOrgContactConvToUIParas = { Employee.class,
				RegisteredProductUIModel.class };
		supportOrgContactMap
				.setConvToUIMethodParas(supportOrgContactConvToUIParas);
		supportOrgContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSupportOrgContactToUI);
		uiModelNodeMapList.add(supportOrgContactMap);

		// UI Model Configure of node:[CustomerParty]
		UIModelNodeMapConfigure customerPartyMap = new UIModelNodeMapConfigure();
		customerPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		customerPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		customerPartyMap.setNodeInstID("CustomerParty");
		customerPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		customerPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		customerPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> customerPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition customerPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		customerPartyPreCondtion0.setFieldName("partyRole");
		customerPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_CUSTOMER);
		customerPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		customerPartyPreConditions.add(customerPartyPreCondtion0);
		customerPartyMap.setPreConditions(customerPartyPreConditions);
		Class<?>[] customerPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		customerPartyMap.setConvToUIMethodParas(customerPartyConvToUIParas);
		customerPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvCustomerPartyToUI);
		Class<?>[] CustomerPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		customerPartyMap.setConvUIToMethodParas(CustomerPartyConvUIToParas);
		customerPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToCustomerParty);
		uiModelNodeMapList.add(customerPartyMap);

		// UI Model Configure of node:[CorporateCustomer]
		UIModelNodeMapConfigure corporateCustomerMap = new UIModelNodeMapConfigure();
		corporateCustomerMap.setSeName(CorporateCustomer.SENAME);
		corporateCustomerMap.setNodeName(CorporateCustomer.NODENAME);
		corporateCustomerMap.setNodeInstID(CorporateCustomer.SENAME);
		corporateCustomerMap.setBaseNodeInstID("CustomerParty");
		corporateCustomerMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		corporateCustomerMap.setServiceEntityManager(corporateCustomerManager);
		Class<?>[] corporateCustomerConvToUIParas = { CorporateCustomer.class,
				RegisteredProductUIModel.class };
		corporateCustomerMap
				.setConvToUIMethodParas(corporateCustomerConvToUIParas);
		corporateCustomerMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvCorporateCustomerToUI);
		uiModelNodeMapList.add(corporateCustomerMap);

		// UI Model Configure of node:[CustomerContact]
		UIModelNodeMapConfigure customerContactMap = new UIModelNodeMapConfigure();
		customerContactMap.setSeName(IndividualCustomer.SENAME);
		customerContactMap.setNodeName(IndividualCustomer.NODENAME);
		customerContactMap.setNodeInstID("CustomerContact");
		customerContactMap.setBaseNodeInstID("CustomerParty");
		customerContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		customerContactMap.setServiceEntityManager(individualCustomerManager);
		SearchConfigConnectCondition customerContactConditionCondition0 = new SearchConfigConnectCondition();
		customerContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		customerContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		List<SearchConfigConnectCondition> customerContactConditions = new ArrayList<>();
		customerContactConditions.add(customerContactConditionCondition0);
		customerContactMap.setConnectionConditions(customerContactConditions);
		Class<?>[] customerContactConvToUIParas = { IndividualCustomer.class,
				RegisteredProductUIModel.class };
		customerContactMap.setConvToUIMethodParas(customerContactConvToUIParas);
		customerContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvCustomerContactToUI);
		uiModelNodeMapList.add(customerContactMap);

		// UI Model Configure of node:[SupplierParty]
		UIModelNodeMapConfigure supplierPartyMap = new UIModelNodeMapConfigure();
		supplierPartyMap.setSeName(RegisteredProductInvolveParty.SENAME);
		supplierPartyMap.setNodeName(RegisteredProductInvolveParty.NODENAME);
		supplierPartyMap.setNodeInstID("SupplierParty");
		supplierPartyMap.setBaseNodeInstID(RegisteredProduct.SENAME);
		supplierPartyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		supplierPartyMap.setServiceEntityManager(registeredProductManager);
		List<UIModelNodeConfigPreCondition> supplierPartyPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition supplierPartyPreCondtion0 = new UIModelNodeConfigPreCondition();
		supplierPartyPreCondtion0.setFieldName("partyRole");
		supplierPartyPreCondtion0
				.setFieldValue(InvolvePartyTemplate.PARTY_ROLE_SUPPLIER);
		supplierPartyPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		supplierPartyPreConditions.add(supplierPartyPreCondtion0);
		supplierPartyMap.setPreConditions(supplierPartyPreConditions);
		Class<?>[] supplierPartyConvToUIParas = {
				RegisteredProductInvolveParty.class,
				RegisteredProductUIModel.class };
		supplierPartyMap.setConvToUIMethodParas(supplierPartyConvToUIParas);
		supplierPartyMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSupplierPartyToUI);
		Class<?>[] SupplierPartyConvUIToParas = {
				RegisteredProductUIModel.class,
				RegisteredProductInvolveParty.class };
		supplierPartyMap.setConvUIToMethodParas(SupplierPartyConvUIToParas);
		supplierPartyMap
				.setConvUIToMethod(RegisteredProductManager.METHOD_ConvUIToSupplierParty);
		uiModelNodeMapList.add(supplierPartyMap);

		// UI Model Configure of node:[CorporateSupplier]
		UIModelNodeMapConfigure corporateSupplierMap = new UIModelNodeMapConfigure();
		corporateSupplierMap.setSeName(CorporateCustomer.SENAME);
		corporateSupplierMap.setNodeName(CorporateCustomer.NODENAME);
		corporateSupplierMap.setNodeInstID("CorporateSupplier");
		corporateSupplierMap.setBaseNodeInstID("SupplierParty");
		corporateSupplierMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		corporateSupplierMap.setServiceEntityManager(corporateCustomerManager);
		List<UIModelNodeConfigPreCondition> corporateSupplierPreConditions = new ArrayList<>();
		UIModelNodeConfigPreCondition corporateSupplierPreCondtion0 = new UIModelNodeConfigPreCondition();
		corporateSupplierPreCondtion0.setFieldName("accountType");
		corporateSupplierPreCondtion0
				.setFieldValue(Account.ACCOUNTTYPE_SUPPLIER);
		corporateSupplierPreCondtion0
				.setOperator(SEFieldSearchConfig.OPERATOR_EQUAL);
		corporateSupplierPreConditions.add(corporateSupplierPreCondtion0);
		corporateSupplierMap.setPreConditions(corporateSupplierPreConditions);
		Class<?>[] corporateSupplierConvToUIParas = { CorporateCustomer.class,
				RegisteredProductUIModel.class };
		corporateSupplierMap
				.setConvToUIMethodParas(corporateSupplierConvToUIParas);
		corporateSupplierMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvCorporateSupplierToUI);
		uiModelNodeMapList.add(corporateSupplierMap);

		// UI Model Configure of node:[SupplierContact]
		UIModelNodeMapConfigure supplierContactMap = new UIModelNodeMapConfigure();
		supplierContactMap.setSeName(IndividualCustomer.SENAME);
		supplierContactMap.setNodeName(IndividualCustomer.NODENAME);
		supplierContactMap.setNodeInstID("SupplierContact");
		supplierContactMap.setBaseNodeInstID("SupplierParty");
		supplierContactMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		SearchConfigConnectCondition supplierContactConditionCondition0 = new SearchConfigConnectCondition();
		supplierContactConditionCondition0
				.setSourceFieldName("refPartyContactUUID");
		supplierContactConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		List<SearchConfigConnectCondition> supplierContactConditions = new ArrayList<>();
		supplierContactConditions.add(supplierContactConditionCondition0);
		supplierContactMap.setConnectionConditions(supplierContactConditions);
		supplierContactMap.setServiceEntityManager(individualCustomerManager);
		Class<?>[] supplierContactConvToUIParas = { IndividualCustomer.class,
				RegisteredProductUIModel.class };
		supplierContactMap.setConvToUIMethodParas(supplierContactConvToUIParas);
		supplierContactMap
				.setConvToUIMethod(RegisteredProductManager.METHOD_ConvSupplierContactToUI);
		uiModelNodeMapList.add(supplierContactMap);
		registeredProductExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(registeredProductExtensionUnion);
		return resultList;
	}

}
