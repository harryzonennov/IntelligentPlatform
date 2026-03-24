package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InventoryTransferItemManager;
import com.company.IntelligentPlatform.logistics.service.InventoryTransferOrderManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
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
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InventoryTransferItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected InventoryTransferItemManager inventoryTransferItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InventoryTransferItemAttachment.SENAME,
				InventoryTransferItemAttachment.NODENAME,
				InventoryTransferItemAttachment.NODENAME
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferItemParty.SENAME,
				InventoryTransferItemParty.NODENAME,
				InventoryTransferItemParty.PARTY_NODEINST_PUR_SUPPLIER,
				inventoryTransferOrderManager,
				InventoryTransferItemParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferItemParty.SENAME,
				InventoryTransferItemParty.NODENAME,
				InventoryTransferItemParty.PARTY_NODEINST_PUR_ORG,
				inventoryTransferOrderManager,
				InventoryTransferItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferItemParty.SENAME,
				InventoryTransferItemParty.NODENAME,
				InventoryTransferItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				inventoryTransferOrderManager,
				InventoryTransferItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferItemParty.SENAME,
				InventoryTransferItemParty.NODENAME,
				InventoryTransferItemParty.PARTY_NODEINST_SOLD_ORG,
				inventoryTransferOrderManager,
				InventoryTransferItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferItemParty.SENAME,
				InventoryTransferItemParty.NODENAME,
				InventoryTransferItemParty.PARTY_NODEINST_PROD_ORG,
				inventoryTransferOrderManager,
				InventoryTransferItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inventoryTransferItemExtensionUnion = new ServiceUIModelExtensionUnion();
		inventoryTransferItemExtensionUnion
				.setNodeInstId(InventoryTransferItem.NODENAME);
		inventoryTransferItemExtensionUnion
				.setNodeName(InventoryTransferItem.NODENAME);

		UIModelNodeMapConfigure inventoryTransferItemMap = new UIModelNodeMapConfigure();
		inventoryTransferItemMap
				.setSeName(InventoryTransferItem.SENAME);
		inventoryTransferItemMap
				.setNodeName(InventoryTransferItem.NODENAME);
		inventoryTransferItemMap
				.setNodeInstID(InventoryTransferItem.NODENAME);
		inventoryTransferItemMap.setHostNodeFlag(true);
		Class<?>[] inventoryTransferItemConvToUIParas = {
				InventoryTransferItem.class,
				InventoryTransferItemUIModel.class };
		inventoryTransferItemMap
				.setConvToUIMethodParas(inventoryTransferItemConvToUIParas);
		inventoryTransferItemMap.setLogicManager(inventoryTransferItemManager);
		inventoryTransferItemMap
				.setConvToUIMethod(InventoryTransferItemManager.METHOD_ConvInventoryTransferItemToUI);
		Class<?>[] inventoryTransferItemConvUIToParas = {
				InventoryTransferItemUIModel.class,
				InventoryTransferItem.class };
		inventoryTransferItemMap
				.setConvUIToMethodParas(inventoryTransferItemConvUIToParas);
		inventoryTransferItemMap
				.setConvUIToMethod(InventoryTransferItemManager.METHOD_ConvUIToInventoryTransferItem);
		uiModelNodeMapList.add(inventoryTransferItemMap);
		// uiModelNodeMapList.add(inventoryTransferOrderMap);
		// UI Model Configure of node:[InboundDelivery]
		Class<?>[] convParentDocToUIMethodParas = {InventoryTransferOrder.class, InventoryTransferItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(InventoryTransferItem.NODENAME,
				InventoryTransferItemManager.METHOD_ConvParentDocToItemUI, inventoryTransferItemManager,
				convParentDocToUIMethodParas));

		// UI Model Configure of node:[reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(InventoryTransferItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(InventoryTransferItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(InventoryTransferItem.NODENAME));
		uiModelNodeMapList
		.addAll(docFlowProxy
				.getDefCreateUpdateNodeMapConfigureList(InventoryTransferItem.NODENAME));
		// UI Model Configure of node:[OutboundItem]
		UIModelNodeMapConfigure outboundItemMap = new UIModelNodeMapConfigure();
		outboundItemMap.setSeName(OutboundItem.SENAME);
		outboundItemMap.setNodeName(OutboundItem.NODENAME);
		outboundItemMap.setNodeInstID(OutboundItem.NODENAME);
		outboundItemMap
				.setBaseNodeInstID(InventoryTransferItem.NODENAME);
		outboundItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outboundItemMap
				.setServiceEntityManager(outboundDeliveryManager);
		List<SearchConfigConnectCondition> outboundItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition outboundItemCondition0 = new SearchConfigConnectCondition();
		outboundItemCondition0
				.setSourceFieldName("refOutboundItemUUID");
		outboundItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outboundItemConditionList.add(outboundItemCondition0);
		outboundItemMap
				.setConnectionConditions(outboundItemConditionList);
		uiModelNodeMapList.add(outboundItemMap);

		// UI Model Configure of node:[out-bound-delivery]
		UIModelNodeMapConfigure outboundDeliveryMap = new UIModelNodeMapConfigure();
		outboundDeliveryMap.setSeName(OutboundDelivery.SENAME);
		outboundDeliveryMap.setNodeName(OutboundDelivery.NODENAME);
		outboundDeliveryMap.setNodeInstID(OutboundDelivery.SENAME);
		outboundDeliveryMap.setBaseNodeInstID(OutboundItem.NODENAME);
		outboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		outboundDeliveryMap.setServiceEntityManager(outboundDeliveryManager);

		Class<?>[] outboundDeliveryConvToUIParas = {
				OutboundDelivery.class, InventoryTransferItemUIModel.class };
		outboundDeliveryMap
				.setConvToUIMethodParas(outboundDeliveryConvToUIParas);

		outboundDeliveryMap.setLogicManager(inventoryTransferItemManager);
		outboundDeliveryMap
				.setConvToUIMethod(InventoryTransferItemManager.METHOD_ConvOutboundDeliveryToItemUI);
		uiModelNodeMapList.add(outboundDeliveryMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, InventoryTransferItemUIModel.class };
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								InventoryTransferItem.NODENAME,
								InventoryTransferItemManager.METHOD_ConvMaterialSKUToItemUI, inventoryTransferItemManager,
								materialStockKeepUnitConvToUIParas));

		// UI Model Configure of node:[InboundItem]
		UIModelNodeMapConfigure inboundItemMap = new UIModelNodeMapConfigure();
		inboundItemMap.setSeName(InboundItem.SENAME);
		inboundItemMap.setNodeName(InboundItem.NODENAME);
		inboundItemMap.setNodeInstID(InboundItem.NODENAME);
		inboundItemMap
				.setBaseNodeInstID(InventoryTransferItem.NODENAME);
		inboundItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		inboundItemMap.setServiceEntityManager(inboundDeliveryManager);
		List<SearchConfigConnectCondition> inboundItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition inboundItemCondition0 = new SearchConfigConnectCondition();
		inboundItemCondition0.setSourceFieldName("refInboundItemUUID");
		inboundItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		inboundItemConditionList.add(inboundItemCondition0);
		inboundItemMap
				.setConnectionConditions(inboundItemConditionList);
		uiModelNodeMapList.add(inboundItemMap);

		// UI Model Configure of node:[in-bound-delivery]
		UIModelNodeMapConfigure inboundDeliveryMap = new UIModelNodeMapConfigure();
		inboundDeliveryMap.setSeName(InboundDelivery.SENAME);
		inboundDeliveryMap.setNodeName(InboundDelivery.NODENAME);
		inboundDeliveryMap.setNodeInstID(InboundDelivery.SENAME);
		inboundDeliveryMap.setBaseNodeInstID(InboundItem.NODENAME);
		inboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		inboundDeliveryMap.setServiceEntityManager(inboundDeliveryManager);
		inboundDeliveryMap.setLogicManager(inventoryTransferItemManager);

		Class<?>[] InboundDeliveryConvToUIParas = { InboundDelivery.class,
				InventoryTransferItemUIModel.class };
		inboundDeliveryMap.setConvToUIMethodParas(InboundDeliveryConvToUIParas);
		inboundDeliveryMap
				.setConvToUIMethod(InventoryTransferItemManager.METHOD_ConvInboundDeliveryToItemUI);
		uiModelNodeMapList.add(inboundDeliveryMap);

		// UI Model Configure of node:[WarehouseStoreItem]
		UIModelNodeMapConfigure warehouseStoreItemMap = new UIModelNodeMapConfigure();
		warehouseStoreItemMap.setSeName(WarehouseStoreItem.SENAME);
		warehouseStoreItemMap.setNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setNodeInstID(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap
				.setBaseNodeInstID(InventoryTransferItem.NODENAME);
		warehouseStoreItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		warehouseStoreItemMap.setServiceEntityManager(warehouseStoreManager);
		List<SearchConfigConnectCondition> warehouseStoreItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition warehouseStoreItemCondition0 = new SearchConfigConnectCondition();
		warehouseStoreItemCondition0.setSourceFieldName("refStoreItemUUID");
		warehouseStoreItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		warehouseStoreItemConditionList.add(warehouseStoreItemCondition0);
		warehouseStoreItemMap
				.setConnectionConditions(warehouseStoreItemConditionList);
		Class<?>[] warehouseStoreItemConvToUIParas = {
				WarehouseStoreItem.class, InventoryTransferItemUIModel.class };
		warehouseStoreItemMap
				.setConvToUIMethodParas(warehouseStoreItemConvToUIParas);
		warehouseStoreItemMap.setLogicManager(inventoryTransferItemManager);
		warehouseStoreItemMap
				.setConvToUIMethod(InventoryTransferItemManager.METHOD_ConvWarehouseStoreItemToUI);
		uiModelNodeMapList.add(warehouseStoreItemMap);

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				InventoryTransferItemUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				InventoryTransferItemUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				"inbound",
				DocFlowProxy.getDefParentDocId(InventoryTransferItem.NODENAME), inventoryTransferItemManager,"refInboundWarehouseUUID", "refInboundWarehouseAreaUUID",
				InventoryTransferItemManager.METHOD_ConvInboundWarehouseToItemUI,
				warehouseConvToUIParas,
				InventoryTransferItemManager.METHOD_ConvInboundWarehouseAreaToItemUI, warehouseAreaConvToUIParas
		)));

		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				"outbound",
				DocFlowProxy.getDefParentDocId(InventoryTransferItem.NODENAME), inventoryTransferItemManager,"refWarehouseUUID", "refWarehouseAreaUUID",
				InventoryTransferItemManager.METHOD_ConvOutboundWarehouseToItemUI,
				warehouseConvToUIParas,
				InventoryTransferItemManager.METHOD_ConvOutboundWarehouseAreaToItemUI, warehouseAreaConvToUIParas
		)));


		inventoryTransferItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inventoryTransferItemExtensionUnion);
		return resultList;
	}

}
