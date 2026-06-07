package com.company.IntelligentPlatform.salesDistribution.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.service.SalesContractManager;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.salesDistribution.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.platform.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.platform.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.platform.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.platform.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.platform.service.DocFlowProxy;
import com.company.IntelligentPlatform.platform.service.LogonUserManager;
import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import com.company.IntelligentPlatform.platform.model.Employee;
import com.company.IntelligentPlatform.platform.model.IndividualCustomer;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import com.company.IntelligentPlatform.platform.model.Organization;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@Service
public class SalesReturnOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesReturnMaterialItemServiceUIModelExtension salesReturnMaterialItemServiceUIModelExtension;

	@Autowired
	protected SalesReturnOrderManager salesReturnOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected SalesContractManager salesContractManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(salesReturnMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesReturnOrderAttachment.SENAME,
				SalesReturnOrderAttachment.NODENAME,
				SalesReturnOrderAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesReturnOrderParty.SENAME,
				SalesReturnOrderParty.NODENAME,
				SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				salesReturnOrderManager,
				SalesReturnOrderParty.ROLE_SOLD_TO_PARTY,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesReturnOrderParty.SENAME,
				SalesReturnOrderParty.NODENAME,
				SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG,
				salesReturnOrderManager,
				SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesReturnOrderActionNode.SENAME,
				SalesReturnOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				salesReturnOrderManager, SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesReturnOrderActionNode.SENAME,
				SalesReturnOrderActionNode.NODENAME,
				SalesReturnOrderActionNode.NODEINST_ACTION_SUBMIT,
				salesReturnOrderManager, SalesReturnOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesReturnOrderActionNode.SENAME,
				SalesReturnOrderActionNode.NODENAME,
				SalesReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE,
				salesReturnOrderManager, SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesReturnOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		salesReturnOrderExtensionUnion.setNodeInstId(SalesReturnOrder.SENAME);
		salesReturnOrderExtensionUnion.setNodeName(SalesReturnOrder.NODENAME);

		// UI Model Configure of node:[SalesReturnOrder]
		UIModelNodeMapConfigure salesReturnOrderMap = new UIModelNodeMapConfigure();
		salesReturnOrderMap.setSeName(SalesReturnOrder.SENAME);
		salesReturnOrderMap.setNodeName(SalesReturnOrder.NODENAME);
		salesReturnOrderMap.setNodeInstID(SalesReturnOrder.SENAME);
		salesReturnOrderMap.setHostNodeFlag(true);
		Class<?>[] salesReturnOrderConvToUIParas = { SalesReturnOrder.class,
				SalesReturnOrderUIModel.class };
		salesReturnOrderMap
				.setConvToUIMethodParas(salesReturnOrderConvToUIParas);
		salesReturnOrderMap
				.setConvToUIMethod(SalesReturnOrderManager.METHOD_ConvSalesReturnOrderToUI);
		Class<?>[] salesReturnOrderConvUIToParas = {
				SalesReturnOrderUIModel.class, SalesReturnOrder.class };
		salesReturnOrderMap
				.setConvUIToMethodParas(salesReturnOrderConvUIToParas);
		salesReturnOrderMap
				.setConvUIToMethod(SalesReturnOrderManager.METHOD_ConvUIToSalesReturnOrder);
		uiModelNodeMapList.add(salesReturnOrderMap);
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(SalesReturnOrder.SENAME));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(SalesReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(SalesReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(SalesReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(SalesReturnOrder.SENAME));

		salesReturnOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(salesReturnOrderExtensionUnion);
		return resultList;
	}

}