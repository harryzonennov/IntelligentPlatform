package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.model.*;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class OutboundDeliveryServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OutboundItemServiceUIModelExtension outboundItemServiceUIModelExtension;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(outboundItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				OutboundDeliveryAttachment.SENAME,
				OutboundDeliveryAttachment.NODENAME,
				OutboundDeliveryAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				OutboundDeliveryActionNode.SENAME,
				OutboundDeliveryActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				outboundDeliveryManager, OutboundDeliveryActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				OutboundDeliveryActionNode.SENAME,
				OutboundDeliveryActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE,
				outboundDeliveryManager, OutboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				OutboundDeliveryActionNode.SENAME,
				OutboundDeliveryActionNode.NODENAME,
				OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE,
				outboundDeliveryManager, OutboundDeliveryActionNode.DOC_ACTION_RECORD_DONE
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundDeliveryParty.SENAME,
				OutboundDeliveryParty.NODENAME,
				OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER,
				outboundDeliveryManager,
				OutboundDeliveryParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundDeliveryParty.SENAME,
				OutboundDeliveryParty.NODENAME,
				OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG,
				outboundDeliveryManager,
				OutboundDeliveryParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundDeliveryParty.SENAME,
				OutboundDeliveryParty.NODENAME,
				OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER,
				outboundDeliveryManager,
				OutboundDeliveryParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundDeliveryParty.SENAME,
				OutboundDeliveryParty.NODENAME,
				OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG,
				outboundDeliveryManager,
				WarehouseStoreParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				OutboundDeliveryParty.SENAME,
				OutboundDeliveryParty.NODENAME,
				OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG,
				outboundDeliveryManager,
				OutboundDeliveryParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion outboundIDeliveryExtensionUnion = new ServiceUIModelExtensionUnion();
		outboundIDeliveryExtensionUnion.setNodeInstId(OutboundDelivery.SENAME);
		outboundIDeliveryExtensionUnion.setNodeName(OutboundDelivery.NODENAME);

		// UI Model Configure of node:[OutboundDelivery]
		UIModelNodeMapConfigure outboundIDeliveryMap = new UIModelNodeMapConfigure();
		outboundIDeliveryMap.setSeName(OutboundDelivery.SENAME);
		outboundIDeliveryMap.setNodeName(OutboundDelivery.NODENAME);
		outboundIDeliveryMap.setNodeInstID(OutboundDelivery.SENAME);
		outboundIDeliveryMap.setHostNodeFlag(true);
		Class<?>[] outboundIDeliveryConvToUIParas = { OutboundDelivery.class,
				OutboundDeliveryUIModel.class };
		outboundIDeliveryMap
				.setConvToUIMethodParas(outboundIDeliveryConvToUIParas);
		outboundIDeliveryMap
				.setConvToUIMethod(OutboundDeliveryManager.METHOD_ConvOutboundDeliveryToUI);
		Class<?>[] OutboundDeliveryConvUIToParas = {
				OutboundDeliveryUIModel.class, OutboundDelivery.class };
		outboundIDeliveryMap
				.setConvUIToMethodParas(OutboundDeliveryConvUIToParas);
		outboundIDeliveryMap
				.setConvUIToMethod(OutboundDeliveryManager.METHOD_ConvUIToOutboundDelivery);
		uiModelNodeMapList.add(outboundIDeliveryMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(OutboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(OutboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(OutboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(OutboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(OutboundDelivery.SENAME));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				OutboundDeliveryUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				OutboundDeliveryUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				OutboundDelivery.SENAME, outboundDeliveryManager,
				OutboundDeliveryManager.METHOD_ConvWarehouseToUI,
				warehouseConvToUIParas, OutboundDeliveryManager.METHOD_ConvWarehouseAreaToUI,
				warehouseAreaConvToUIParas
		)));

		outboundIDeliveryExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(outboundIDeliveryExtensionUnion);
		return resultList;
	}
}
