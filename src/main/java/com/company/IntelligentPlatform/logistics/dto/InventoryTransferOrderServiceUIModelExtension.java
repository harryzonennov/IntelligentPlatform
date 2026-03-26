package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InventoryTransferOrderManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InventoryTransferOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected InventoryTransferItemServiceUIModelExtension inventoryTransferItemServiceUIModelExtension;

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(inventoryTransferItemServiceUIModelExtension);

		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InventoryTransferOrderAttachment.SENAME,
				InventoryTransferOrderAttachment.NODENAME,
				InventoryTransferOrderAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_SUBMIT,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_REVOKE_SUBMIT,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryTransferOrderActionNode.SENAME,
				InventoryTransferOrderActionNode.NODENAME,
				InventoryTransferOrderActionNode.NODEINST_ACTION_DELIVERY_DONE,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferOrderParty.SENAME,
				InventoryTransferOrderParty.NODENAME,
				InventoryTransferOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
				inboundDeliveryManager,
				InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferOrderParty.SENAME,
				InventoryTransferOrderParty.NODENAME,
				InventoryTransferOrderParty.PARTY_NODEINST_PUR_ORG,
				inboundDeliveryManager,
				InventoryTransferOrderParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferOrderParty.SENAME,
				InventoryTransferOrderParty.NODENAME,
				InventoryTransferOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				inboundDeliveryManager,
				InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferOrderParty.SENAME,
				InventoryTransferOrderParty.NODENAME,
				InventoryTransferOrderParty.PARTY_NODEINST_SOLD_ORG,
				inboundDeliveryManager,
				InventoryTransferOrderParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InventoryTransferOrderParty.SENAME,
				InventoryTransferOrderParty.NODENAME,
				InventoryTransferOrderParty.PARTY_NODEINST_PROD_ORG,
				inboundDeliveryManager,
				InventoryTransferOrderParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inventoryTransferOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		inventoryTransferOrderExtensionUnion
				.setNodeInstId(InventoryTransferOrder.SENAME);
		inventoryTransferOrderExtensionUnion
				.setNodeName(InventoryTransferOrder.NODENAME);

		// UI Model Configure of node:[InventoryTransferOrder]
		UIModelNodeMapConfigure inventoryTransferOrderMap = new UIModelNodeMapConfigure();
		inventoryTransferOrderMap.setSeName(InventoryTransferOrder.SENAME);
		inventoryTransferOrderMap.setNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferOrderMap.setNodeInstID(InventoryTransferOrder.SENAME);
		inventoryTransferOrderMap.setHostNodeFlag(true);
		Class<?>[] inventoryTransferOrderConvToUIParas = {
				InventoryTransferOrder.class,
				InventoryTransferOrderUIModel.class };
		inventoryTransferOrderMap
				.setConvToUIMethodParas(inventoryTransferOrderConvToUIParas);
		inventoryTransferOrderMap
				.setConvToUIMethod(InventoryTransferOrderManager.METHOD_ConvInventoryTransferOrderToUI);
		Class<?>[] InventoryTransferOrderConvUIToParas = {
				InventoryTransferOrderUIModel.class,
				InventoryTransferOrder.class };
		inventoryTransferOrderMap
				.setConvUIToMethodParas(InventoryTransferOrderConvUIToParas);
		inventoryTransferOrderMap
				.setConvUIToMethod(InventoryTransferOrderManager.METHOD_ConvUIToInventoryTransferOrder);
		uiModelNodeMapList.add(inventoryTransferOrderMap);

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				InventoryCheckOrderUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				InventoryCheckOrderUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				"inbound",
				InboundDelivery.SENAME, inventoryTransferOrderManager,"refInboundWarehouseUUID", "refInboundWarehouseAreaUUID",
				InventoryTransferOrderManager.METHOD_ConvInboundWarehouseToUI,
				warehouseConvToUIParas,
				InventoryTransferOrderManager.METHOD_ConvInboundWarehouseAreaToUI, warehouseAreaConvToUIParas
		)));

		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				"outbound",
				InboundDelivery.SENAME, inventoryTransferOrderManager,"refWarehouseUUID", "refWarehouseAreaUUID",
				InventoryTransferOrderManager.METHOD_ConvOutboundWarehouseToUI,
				warehouseConvToUIParas,
				InventoryTransferOrderManager.METHOD_ConvOutboundWarehouseAreaToUI, warehouseAreaConvToUIParas
		)));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(InventoryTransferOrder.SENAME));

		// UI Model Configure of node:[OutboundDelivery]
		UIModelNodeMapConfigure outboundDeliveryMap = new UIModelNodeMapConfigure();
		outboundDeliveryMap.setSeName(OutboundDelivery.SENAME);
		outboundDeliveryMap.setNodeName(OutboundDelivery.NODENAME);
		outboundDeliveryMap.setNodeInstID(OutboundDelivery.SENAME);
		outboundDeliveryMap.setBaseNodeInstID(InventoryTransferOrder.SENAME);
		outboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outboundDeliveryMap.setServiceEntityManager(outboundDeliveryManager);
		List<SearchConfigConnectCondition> outboundDeliveryConditionList = new ArrayList<>();
		SearchConfigConnectCondition outboundDeliveryCondition0 = new SearchConfigConnectCondition();
		outboundDeliveryCondition0
				.setSourceFieldName("refOutboundDeliveryUUID");
		outboundDeliveryCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outboundDeliveryConditionList.add(outboundDeliveryCondition0);
		outboundDeliveryMap
				.setConnectionConditions(outboundDeliveryConditionList);
		Class<?>[] outboundDeliveryConvToUIParas = { OutboundDelivery.class,
				InventoryTransferOrderUIModel.class };
		outboundDeliveryMap
				.setConvToUIMethodParas(outboundDeliveryConvToUIParas);
		outboundDeliveryMap
				.setConvToUIMethod(InventoryTransferOrderManager.METHOD_ConvOutboundDeliveryToUI);
		uiModelNodeMapList.add(outboundDeliveryMap);

		// UI Model Configure of node:[InboundDelivery]
		UIModelNodeMapConfigure inboundDeliveryMap = new UIModelNodeMapConfigure();
		inboundDeliveryMap.setSeName(InboundDelivery.SENAME);
		inboundDeliveryMap.setNodeName(InboundDelivery.NODENAME);
		inboundDeliveryMap.setNodeInstID(InboundDelivery.SENAME);
		inboundDeliveryMap.setBaseNodeInstID(InventoryTransferOrder.SENAME);
		inboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		inboundDeliveryMap.setServiceEntityManager(inboundDeliveryManager);
		List<SearchConfigConnectCondition> inboundDeliveryConditionList = new ArrayList<>();
		SearchConfigConnectCondition inboundDeliveryCondition0 = new SearchConfigConnectCondition();
		inboundDeliveryCondition0.setSourceFieldName("refInboundDeliveryUUID");
		inboundDeliveryCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		inboundDeliveryConditionList.add(inboundDeliveryCondition0);
		inboundDeliveryMap
				.setConnectionConditions(inboundDeliveryConditionList);
		Class<?>[] inboundDeliveryConvToUIParas = { InboundDelivery.class,
				InventoryTransferOrderUIModel.class };
		inboundDeliveryMap.setConvToUIMethodParas(inboundDeliveryConvToUIParas);
		inboundDeliveryMap
				.setConvToUIMethod(InventoryTransferOrderManager.METHOD_ConvInboundDeliveryToUI);
		uiModelNodeMapList.add(inboundDeliveryMap);

		inventoryTransferOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inventoryTransferOrderExtensionUnion);
		return resultList;
	}

}
