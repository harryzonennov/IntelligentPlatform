package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

/**
 * reference to Individual Customer
 * @author Zhang,Hang
 *
 */
@Service
public class CorContactPersonServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
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
		UIModelNodeMapConfigure toParentModelNodeMapConfigure = new UIModelNodeMapConfigure();
		toParentModelNodeMapConfigure.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_TARGET);
		toParentModelNodeMapConfigure.setServiceEntityManager(corporateCustomerManager);
		toParentModelNodeMapConfigure.setNodeInstID(CorporateContactPerson.NODENAME);
		toParentModelNodeMapConfigure.setNodeName(CorporateContactPerson.NODENAME);
		toParentModelNodeMapConfigure.setSeName(CorporateContactPerson.SENAME);
		corporateContactPersonExtensionUnion.setToParentModelNodeMapConfigure(toParentModelNodeMapConfigure);

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
		corporateContactPersonMap.setServiceEntityManager(corporateCustomerManager);
		corporateContactPersonMap.setLogicManager(corporateCustomerManager);
		corporateContactPersonMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvCorporateContactPersonToUI);		
		uiModelNodeMapList.add(corporateContactPersonMap);

		// UI Model Configure of node:[CorporateCustomer]
		UIModelNodeMapConfigure corporateCustomerMap = new UIModelNodeMapConfigure();
		corporateCustomerMap.setSeName(CorporateCustomer.SENAME);
		corporateCustomerMap.setNodeName(CorporateCustomer.NODENAME);
		corporateCustomerMap.setNodeInstID(CorporateCustomer.SENAME);
		corporateCustomerMap.setServiceEntityManager(corporateCustomerManager);
		corporateCustomerMap.setBaseNodeInstID(CorporateContactPerson.NODENAME);
		corporateCustomerMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		corporateCustomerMap.setServiceEntityManager(corporateCustomerManager);
		corporateCustomerMap.setLogicManager(corporateCustomerManager);
		Class<?>[] contactPersonConvToUIParas = { CorporateCustomer.class,
				CorporateContactPersonUIModel.class };
		corporateCustomerMap.setConvToUIMethodParas(contactPersonConvToUIParas);
		corporateCustomerMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvCorporateCustomerToContactUI);		
		uiModelNodeMapList.add(corporateCustomerMap);
		
		corporateContactPersonExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(corporateContactPersonExtensionUnion);
		return resultList;
	}

}
