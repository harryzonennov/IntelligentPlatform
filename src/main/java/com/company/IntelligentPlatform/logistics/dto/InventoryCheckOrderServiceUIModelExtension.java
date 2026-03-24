package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InventoryCheckOrderManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrderActionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InventoryCheckOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected InventoryCheckItemServiceUIModelExtension inventoryCheckItemServiceUIModelExtension;

	@Autowired
	protected InventoryCheckOrderManager inventoryCheckOrderManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(inventoryCheckItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InventoryCheckAttachment.SENAME,
				InventoryCheckAttachment.NODENAME,
				InventoryCheckAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_SUBMIT,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_REVOKE_SUBMIT,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_INPROCESS,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_START_CHECK
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InventoryCheckOrderActionNode.SENAME,
				InventoryCheckOrderActionNode.NODENAME,
				InventoryCheckOrderActionNode.NODEINST_ACTION_DELIVERY_DONE,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.DOC_ACTION_RECORD_DONE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inventoryCheckOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		inventoryCheckOrderExtensionUnion
				.setNodeInstId(InventoryCheckOrder.SENAME);
		inventoryCheckOrderExtensionUnion
				.setNodeName(InventoryCheckOrder.NODENAME);

		// UI Model Configure of node:[InventoryCheckOrder]
		UIModelNodeMapConfigure inventoryCheckOrderMap = new UIModelNodeMapConfigure();
		inventoryCheckOrderMap.setSeName(InventoryCheckOrder.SENAME);
		inventoryCheckOrderMap.setNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckOrderMap.setNodeInstID(InventoryCheckOrder.SENAME);
		inventoryCheckOrderMap.setHostNodeFlag(true);
		Class<?>[] inventoryCheckOrderConvToUIParas = {
				InventoryCheckOrder.class, InventoryCheckOrderUIModel.class };
		inventoryCheckOrderMap
				.setConvToUIMethodParas(inventoryCheckOrderConvToUIParas);
		inventoryCheckOrderMap
				.setConvToUIMethod(InventoryCheckOrderManager.METHOD_ConvInventoryCheckOrderToUI);
		Class<?>[] InventoryCheckOrderConvUIToParas = {
				InventoryCheckOrderUIModel.class, InventoryCheckOrder.class };
		inventoryCheckOrderMap
				.setConvUIToMethodParas(InventoryCheckOrderConvUIToParas);
		inventoryCheckOrderMap
				.setConvUIToMethod(InventoryCheckOrderManager.METHOD_ConvUIToInventoryCheckOrder);
		uiModelNodeMapList.add(inventoryCheckOrderMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(InventoryCheckOrder.SENAME));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				InventoryCheckOrderUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				InventoryCheckOrderUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				InventoryCheckOrder.SENAME, inventoryCheckOrderManager,
				InventoryCheckOrderManager.METHOD_ConvWarehouseToUI,
				warehouseConvToUIParas,
				InventoryCheckOrderManager.METHOD_ConvWarehouseAreaToUI, warehouseAreaConvToUIParas
		)));

		inventoryCheckOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inventoryCheckOrderExtensionUnion);
		return resultList;
	}

}
