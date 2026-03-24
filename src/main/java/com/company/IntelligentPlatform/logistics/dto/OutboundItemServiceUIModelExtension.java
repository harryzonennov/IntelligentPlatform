package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundItemManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class OutboundItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundItemManager outboundItemManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				OutboundItemAttachment.SENAME,
				OutboundItemAttachment.NODENAME,
				OutboundItemAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundItemParty.SENAME,
				OutboundItemParty.NODENAME,
				OutboundItemParty.PARTY_NODEINST_PUR_SUPPLIER,
				outboundDeliveryManager,
				OutboundItemParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundItemParty.SENAME,
				OutboundItemParty.NODENAME,
				OutboundItemParty.PARTY_NODEINST_PUR_ORG,
				outboundDeliveryManager,
				OutboundItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundItemParty.SENAME,
				OutboundItemParty.NODENAME,
				OutboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				outboundDeliveryManager,
				OutboundItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundItemParty.SENAME,
				OutboundItemParty.NODENAME,
				OutboundItemParty.PARTY_NODEINST_SOLD_ORG,
				outboundDeliveryManager,
				OutboundItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundItemParty.SENAME,
				OutboundItemParty.NODENAME,
				OutboundItemParty.PARTY_NODEINST_PROD_ORG,
				outboundDeliveryManager,
				OutboundItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion outboundItemExtensionUnion = new ServiceUIModelExtensionUnion();
		outboundItemExtensionUnion
				.setNodeInstId(OutboundItem.NODENAME);
		outboundItemExtensionUnion
				.setNodeName(OutboundItem.NODENAME);

		// UI Model Configure of node:[OutboundItem]
		UIModelNodeMapConfigure outboundItemMap = new UIModelNodeMapConfigure();
		outboundItemMap.setSeName(OutboundItem.SENAME);
		outboundItemMap.setNodeName(OutboundItem.NODENAME);
		outboundItemMap.setNodeInstID(OutboundItem.NODENAME);
		outboundItemMap.setHostNodeFlag(true);
		Class<?>[] outboundItemConvToUIParas = {
				OutboundItem.class, OutboundItemUIModel.class };
		outboundItemMap
				.setConvToUIMethodParas(outboundItemConvToUIParas);
		outboundItemMap.setLogicManager(outboundItemManager);
		outboundItemMap
				.setConvToUIMethod(OutboundItemManager.METHOD_ConvOutboundItemToUI);
		Class<?>[] OutboundItemConvUIToParas = {
				OutboundItemUIModel.class, OutboundItem.class };
		outboundItemMap
				.setConvUIToMethodParas(OutboundItemConvUIToParas);
		outboundItemMap
				.setConvUIToMethod(OutboundItemManager.METHOD_ConvUIToOutboundItem);
		uiModelNodeMapList.add(outboundItemMap);

		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, OutboundItemUIModel.class };
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								OutboundItem.NODENAME,
								OutboundItemManager.METHOD_ConvMaterialStockKeepUnitToUI,outboundItemManager,
								materialStockKeepUnitConvToUIParas));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(OutboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(OutboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevProfNodeMapConfigureList(OutboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(OutboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextProfNodeMapConfigureList(OutboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(OutboundItem.NODENAME));

		UIModelNodeMapConfigure warehouseStoreItemMap = new UIModelNodeMapConfigure();
		warehouseStoreItemMap.setSeName(WarehouseStoreItem.SENAME);
		warehouseStoreItemMap.setNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setNodeInstID(WarehouseStoreItem.SENAME);
		warehouseStoreItemMap.setBaseNodeInstID(OutboundItem.NODENAME);
		warehouseStoreItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> warehouseStoreConditionList = new ArrayList<>();
		SearchConfigConnectCondition warehouseStoreCondition0 = new SearchConfigConnectCondition();
		warehouseStoreCondition0.setSourceFieldName("refStoreItemUUID");
		warehouseStoreCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		warehouseStoreConditionList.add(warehouseStoreCondition0);
		warehouseStoreItemMap.setConnectionConditions(warehouseStoreConditionList);
		Class<?>[] warehouseStoreItemConvToUIParas = { WarehouseStoreItem.class,
				OutboundItemUIModel.class };
		warehouseStoreItemMap
				.setConvToUIMethodParas(warehouseStoreItemConvToUIParas);
		warehouseStoreItemMap.setServiceEntityManager(warehouseStoreManager);
		warehouseStoreItemMap.setLogicManager(outboundItemManager);
		warehouseStoreItemMap
				.setConvToUIMethod(OutboundItemManager.METHOD_ConvWarehouseItemToItemUI);
		uiModelNodeMapList.add(warehouseStoreItemMap);

		Class<?>[] outboundDeliveryConvToUIParas = { OutboundDelivery.class,
				OutboundItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(OutboundItem.NODENAME,
				OutboundItemManager.METHOD_ConvParentDocToItemUI, outboundItemManager,
				outboundDeliveryConvToUIParas));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				OutboundItemUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				OutboundItemUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				OutboundDelivery.SENAME, outboundItemManager,
				OutboundItemManager.METHOD_ConvWarehouseToItemUI,
				warehouseConvToUIParas, OutboundItemManager.METHOD_ConvWarehouseAreaToItemUI,
				warehouseAreaConvToUIParas
		)));

		outboundItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(outboundItemExtensionUnion);
		return resultList;
	}
}
