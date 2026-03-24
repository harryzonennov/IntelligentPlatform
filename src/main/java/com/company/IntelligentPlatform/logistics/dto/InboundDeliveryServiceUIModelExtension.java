package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.model.*;

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
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InboundDeliveryServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected InboundItemServiceUIModelExtension inboundItemServiceUIModelExtension;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

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
		resultList.add(inboundItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InboundDeliveryAttachment.SENAME,
				InboundDeliveryAttachment.NODENAME,
				InboundDeliveryAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InboundDeliveryActionNode.SENAME,
				InboundDeliveryActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				inboundDeliveryManager, InboundDeliveryActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InboundDeliveryActionNode.SENAME,
				InboundDeliveryActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				inboundDeliveryManager, InboundDeliveryActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InboundDeliveryActionNode.SENAME,
				InboundDeliveryActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE,
				inboundDeliveryManager, InboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InboundDeliveryActionNode.SENAME,
				InboundDeliveryActionNode.NODENAME,
				InboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE,
				inboundDeliveryManager, InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundDeliveryParty.SENAME,
				InboundDeliveryParty.NODENAME,
				InboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER,
				inboundDeliveryManager,
				InboundDeliveryParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundDeliveryParty.SENAME,
				InboundDeliveryParty.NODENAME,
				InboundDeliveryParty.PARTY_NODEINST_PUR_ORG,
				inboundDeliveryManager,
				InboundDeliveryParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundDeliveryParty.SENAME,
				InboundDeliveryParty.NODENAME,
				InboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER,
				inboundDeliveryManager,
				InboundDeliveryParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundDeliveryParty.SENAME,
				InboundDeliveryParty.NODENAME,
				InboundDeliveryParty.PARTY_NODEINST_SOLD_ORG,
				inboundDeliveryManager,
				InboundDeliveryParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundDeliveryParty.SENAME,
				InboundDeliveryParty.NODENAME,
				InboundDeliveryParty.PARTY_NODEINST_PROD_ORG,
				inboundDeliveryManager,
				InboundDeliveryParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inboundDeliveryExtensionUnion = new ServiceUIModelExtensionUnion();
		inboundDeliveryExtensionUnion.setNodeInstId(InboundDelivery.SENAME);
		inboundDeliveryExtensionUnion.setNodeName(InboundDelivery.NODENAME);

		// UI Model Configure of node:[InboundDelivery]
		UIModelNodeMapConfigure inboundDeliveryMap = new UIModelNodeMapConfigure();
		inboundDeliveryMap.setSeName(InboundDelivery.SENAME);
		inboundDeliveryMap.setNodeName(InboundDelivery.NODENAME);
		inboundDeliveryMap.setNodeInstID(InboundDelivery.SENAME);
		inboundDeliveryMap.setHostNodeFlag(true);
		Class<?>[] inboundDeliveryConvToUIParas = { InboundDelivery.class,
				InboundDeliveryUIModel.class };
		inboundDeliveryMap.setConvToUIMethodParas(inboundDeliveryConvToUIParas);
		inboundDeliveryMap
				.setConvToUIMethod(InboundDeliveryManager.METHOD_ConvInboundDeliveryToUI);
		Class<?>[] InboundDeliveryConvUIToParas = {
				InboundDeliveryUIModel.class, InboundDelivery.class };
		inboundDeliveryMap.setConvUIToMethodParas(InboundDeliveryConvUIToParas);
		inboundDeliveryMap
				.setConvUIToMethod(InboundDeliveryManager.METHOD_ConvUIToInboundDelivery);
		uiModelNodeMapList.add(inboundDeliveryMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(InboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(InboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(InboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(InboundDelivery.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(InboundDelivery.SENAME));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				InboundDeliveryUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				InboundDeliveryUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				InboundDelivery.SENAME, inboundDeliveryManager,
				InboundDeliveryManager.METHOD_ConvWarehouseToUI,
				warehouseConvToUIParas, InboundDeliveryManager.METHOD_ConvWarehouseAreaToUI,
				warehouseAreaConvToUIParas
		)));

		inboundDeliveryExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inboundDeliveryExtensionUnion);
		return resultList;
	}

}
