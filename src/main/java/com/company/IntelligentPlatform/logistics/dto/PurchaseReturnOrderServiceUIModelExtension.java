package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseReturnOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;


@Service
public class PurchaseReturnOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseReturnMaterialItemServiceUIModelExtension purchaseReturnMaterialItemServiceUIModelExtension;

	@Autowired
	protected PurchaseReturnOrderManager purchaseReturnOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

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
		resultList.add(purchaseReturnMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseReturnOrderAttachment.SENAME,
				PurchaseReturnOrderAttachment.NODENAME,
				PurchaseReturnOrderAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseReturnOrderParty.SENAME,
				PurchaseReturnOrderParty.NODENAME,
				PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
				purchaseReturnOrderManager,
				PurchaseReturnOrderParty.ROLE_PARTYB,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseReturnOrderParty.SENAME,
				PurchaseReturnOrderParty.NODENAME,
				PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG,
				purchaseReturnOrderManager,
				PurchaseReturnOrderParty.ROLE_PARTYA,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseReturnOrderActionNode.SENAME,
				PurchaseReturnOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				purchaseReturnOrderManager, SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseReturnOrderActionNode.SENAME,
				PurchaseReturnOrderActionNode.NODENAME,
				PurchaseReturnOrderActionNode.NODEINST_ACTION_SUBMIT,
				purchaseReturnOrderManager, PurchaseReturnOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseReturnOrderActionNode.SENAME,
				PurchaseReturnOrderActionNode.NODENAME,
				PurchaseReturnOrderActionNode.NODEINST_ACTION_REJECT_APPROVE,
				purchaseReturnOrderManager, PurchaseReturnOrderActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseReturnOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseReturnOrderExtensionUnion.setNodeInstId(PurchaseReturnOrder.SENAME);
		purchaseReturnOrderExtensionUnion.setNodeName(PurchaseReturnOrder.NODENAME);

		UIModelNodeMapConfigure purchaseReturnOrderMap = new UIModelNodeMapConfigure();
		purchaseReturnOrderMap.setSeName(PurchaseReturnOrder.SENAME);
		purchaseReturnOrderMap.setNodeName(PurchaseReturnOrder.NODENAME);
		purchaseReturnOrderMap.setNodeInstID(PurchaseReturnOrder.SENAME);
		purchaseReturnOrderMap.setHostNodeFlag(true);
		Class<?>[] purchaseReturnOrderConvToUIParas = { PurchaseReturnOrder.class,
				PurchaseReturnOrderUIModel.class };
		purchaseReturnOrderMap
				.setConvToUIMethodParas(purchaseReturnOrderConvToUIParas);
		purchaseReturnOrderMap
				.setConvToUIMethod(PurchaseReturnOrderManager.METHOD_ConvPurchaseReturnOrderToUI);
		Class<?>[] purchaseReturnOrderConvUIToParas = {
				PurchaseReturnOrderUIModel.class, PurchaseReturnOrder.class };
		purchaseReturnOrderMap
				.setConvUIToMethodParas(purchaseReturnOrderConvUIToParas);
		purchaseReturnOrderMap
				.setConvUIToMethod(PurchaseReturnOrderManager.METHOD_ConvUIToPurchaseReturnOrder);
		uiModelNodeMapList.add(purchaseReturnOrderMap);


		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(PurchaseReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(PurchaseReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(PurchaseReturnOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(PurchaseReturnOrder.SENAME));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(PurchaseReturnOrder.SENAME));



		purchaseReturnOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(purchaseReturnOrderExtensionUnion);
		return resultList;
	}

}
