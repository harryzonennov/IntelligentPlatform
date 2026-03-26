package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseStoreServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;
	
	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(warehouseStoreItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				WarehouseStoreAttachment.SENAME,
				WarehouseStoreAttachment.NODENAME,
				WarehouseStoreAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WarehouseStoreActionNode.SENAME,
				WarehouseStoreActionNode.NODENAME,
				WarehouseStoreActionNode.NODEINST_ACTION_ARCHIVE,
				warehouseStoreManager, WarehouseStoreActionNode.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WarehouseStoreActionNode.SENAME,
				WarehouseStoreActionNode.NODENAME,
				WarehouseStoreActionNode.NODEINST_ACTION_INSTOCK,
				warehouseStoreManager, WarehouseStoreActionNode.DOC_ACTION_INSTOCK
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreParty.SENAME,
				WarehouseStoreParty.NODENAME,
				WarehouseStoreParty.PARTY_NODEINST_PUR_SUPPLIER,
				warehouseStoreManager,
				WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreParty.SENAME,
				WarehouseStoreParty.NODENAME,
				WarehouseStoreParty.PARTY_NODEINST_PUR_ORG,
				warehouseStoreManager,
				WarehouseStoreParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreParty.SENAME,
				WarehouseStoreParty.NODENAME,
				WarehouseStoreParty.PARTY_NODEINST_SOLD_CUSTOMER,
				warehouseStoreManager,
				WarehouseStoreParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreParty.SENAME,
				WarehouseStoreParty.NODENAME,
				WarehouseStoreParty.PARTY_NODEINST_SOLD_ORG,
				warehouseStoreManager,
				WarehouseStoreParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreParty.SENAME,
				WarehouseStoreParty.NODENAME,
				WarehouseStoreParty.PARTY_NODEINST_PROD_ORG,
				warehouseStoreManager,
				WarehouseStoreParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseStoreExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseStoreExtensionUnion.setNodeInstId(WarehouseStore.SENAME);
		warehouseStoreExtensionUnion.setNodeName(WarehouseStore.NODENAME);

		// UI Model Configure of node:[WarehouseStore]
		UIModelNodeMapConfigure warehouseStoreMap = new UIModelNodeMapConfigure();
		warehouseStoreMap.setSeName(WarehouseStore.SENAME);
		warehouseStoreMap.setNodeName(WarehouseStore.NODENAME);
		warehouseStoreMap.setNodeInstID(WarehouseStore.SENAME);
		warehouseStoreMap.setHostNodeFlag(true);
		Class<?>[] warehouseStoreConvToUIParas = { WarehouseStore.class,
				WarehouseStoreUIModel.class };
		warehouseStoreMap
				.setConvToUIMethodParas(warehouseStoreConvToUIParas);
		warehouseStoreMap
				.setConvToUIMethod(WarehouseStoreManager.METHOD_ConvWarehouseStoreToUI);
		Class<?>[] warehouseStoreConvUIToParas = {
				WarehouseStoreUIModel.class, WarehouseStore.class };
		warehouseStoreMap
				.setConvUIToMethodParas(warehouseStoreConvUIToParas);
		warehouseStoreMap
				.setConvUIToMethod(WarehouseStoreManager.METHOD_ConvUIToWarehouseStore);
		uiModelNodeMapList.add(warehouseStoreMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(WarehouseStore.SENAME));

		// UI Model Configure of node:[InboundDelivery]
		UIModelNodeMapConfigure inboundDeliveryMap = new UIModelNodeMapConfigure();
		inboundDeliveryMap.setSeName(InboundDelivery.SENAME);
		inboundDeliveryMap.setNodeName(InboundDelivery.NODENAME);
		inboundDeliveryMap.setNodeInstID(InboundDelivery.SENAME);
		inboundDeliveryMap.setBaseNodeInstID(WarehouseStore.SENAME);
		inboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);

		SearchConfigConnectCondition inboundCondition0 = new SearchConfigConnectCondition("prevDocUUID", IServiceEntityNodeFieldConstant.UUID);
		inboundDeliveryMap.setConnectionConditions(ServiceCollectionsHelper.asList(inboundCondition0));

		inboundDeliveryMap.setLogicManager(warehouseStoreManager);
		inboundDeliveryMap.setServiceEntityManager(inboundDeliveryManager);
		Class<?>[] inboundDeliveryConvToUIParas = { InboundDelivery.class,
				WarehouseStoreUIModel.class };
		inboundDeliveryMap.setConvToUIMethodParas(inboundDeliveryConvToUIParas);
		inboundDeliveryMap
				.setConvToUIMethod(WarehouseStoreManager.METHOD_ConvInboundDeliveryToUI);
		uiModelNodeMapList.add(inboundDeliveryMap);

		UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
		warehouseMap.setSeName(Warehouse.SENAME);
		warehouseMap.setNodeName(Warehouse.NODENAME);
		warehouseMap.setNodeInstID(Warehouse.SENAME);
		warehouseMap.setBaseNodeInstID(WarehouseStore.SENAME);
		warehouseMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		warehouseMap.setServiceEntityManager(warehouseManager);
		SearchConfigConnectCondition warehouseCondition0 = new SearchConfigConnectCondition("refWarehouseUUID",
				IServiceEntityNodeFieldConstant.UUID);
		warehouseMap.setConnectionConditions(ServiceCollectionsHelper.asList(warehouseCondition0));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				WarehouseStoreUIModel.class };
		warehouseMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseMap
				.setConvToUIMethod(WarehouseStoreManager.METHOD_ConvWarehouseToUI);
		uiModelNodeMapList.add(warehouseMap);

		warehouseStoreExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(warehouseStoreExtensionUnion);
		return resultList;
	}

}
