package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

@Service
public class CorporateContactPersonServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion corporateContactPersonExtensionUnion = new ServiceUIModelExtensionUnion();
		corporateContactPersonExtensionUnion
				.setNodeInstId(CorporateContactPerson.NODENAME);
		corporateContactPersonExtensionUnion
		.setNodeName(CorporateContactPerson.NODENAME);

		// UI Model Configure of node:[CorporateContactPerson]
		UIModelNodeMapConfigure corporateContactPersonMap = new UIModelNodeMapConfigure();
		corporateContactPersonMap.setSeName(CorporateContactPerson.SENAME);
		corporateContactPersonMap.setNodeName(CorporateContactPerson.NODENAME);
		corporateContactPersonMap
				.setNodeInstID(CorporateContactPerson.NODENAME);
		corporateContactPersonMap.setHostNodeFlag(true);
		Class<?>[] corporateContactPersonConvToUIParas = {
				CorporateContactPerson.class,
				CorporateContactPersonUIModel.class };
		corporateContactPersonMap
				.setConvToUIMethodParas(corporateContactPersonConvToUIParas);
		corporateContactPersonMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvCorporateContactPersonToUI);
		Class<?>[] CorporateContactPersonConvUIToParas = {
				CorporateContactPersonUIModel.class,
				CorporateContactPerson.class };
		corporateContactPersonMap
				.setConvUIToMethodParas(CorporateContactPersonConvUIToParas);
		corporateContactPersonMap
				.setConvUIToMethod(CorporateCustomerManager.METHOD_ConvUIToCorporateContactPerson);
		uiModelNodeMapList.add(corporateContactPersonMap);

		// UI Model Configure of node:[ContactPerson]
		UIModelNodeMapConfigure contactPersonMap = new UIModelNodeMapConfigure();
		contactPersonMap.setSeName(IndividualCustomer.SENAME);
		contactPersonMap.setNodeName(IndividualCustomer.NODENAME);
		contactPersonMap.setNodeInstID("ContactPerson");
		contactPersonMap.setBaseNodeInstID(CorporateContactPerson.NODENAME);
		contactPersonMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		contactPersonMap.setServiceEntityManager(individualCustomerManager);
		Class<?>[] contactPersonConvToUIParas = { IndividualCustomer.class,
				CorporateContactPersonUIModel.class };
		contactPersonMap.setConvToUIMethodParas(contactPersonConvToUIParas);
		contactPersonMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvContactPersonToUI);

		Class<?>[] contactPersonConvUIToParas = {
				CorporateContactPersonUIModel.class, IndividualCustomer.class };
		contactPersonMap.setConvUIToMethodParas(contactPersonConvUIToParas);
		contactPersonMap
				.setConvUIToMethod(CorporateCustomerManager.METHOD_ConvContactPersonUIToIndividualCustomer);
		uiModelNodeMapList.add(contactPersonMap);
		corporateContactPersonExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(corporateContactPersonExtensionUnion);
		return resultList;
	}

}
