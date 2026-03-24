package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseRequestManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
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
public class PurchaseRequestServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseRequestMaterialItemServiceUIModelExtension purchaseRequestMaterialItemServiceUIModelExtension;

	@Autowired
	protected PurchaseRequestManager purchaseRequestManager;

	@Autowired
	protected LogonUserManager logonUserManager;

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
		resultList.add(purchaseRequestMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseRequestAttachment.SENAME,
				PurchaseRequestAttachment.NODENAME,
				PurchaseRequestAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseRequestParty.SENAME,
				PurchaseRequestParty.NODENAME,
				PurchaseRequestParty.PARTY_NODEINST_PUR_SUPPLIER,
				purchaseRequestManager,
				PurchaseRequestParty.ROLE_PARTYB,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseRequestParty.SENAME,
				PurchaseRequestParty.NODENAME,
				PurchaseRequestParty.PARTY_NODEINST_PUR_ORG,
				purchaseRequestManager,
				PurchaseRequestParty.ROLE_PARTYA,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseRequestActionNode.SENAME,
				PurchaseRequestActionNode.NODENAME,
				PurchaseRequestActionNode.NODEINST_ACTION_APPROVE,
				purchaseRequestManager, PurchaseRequestActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseRequestActionNode.SENAME,
				PurchaseRequestActionNode.NODENAME,
				PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS,
				purchaseRequestManager, PurchaseRequestActionNode.DOC_ACTION_INPROCESS
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseRequestActionNode.SENAME,
				PurchaseRequestActionNode.NODENAME,
				PurchaseRequestActionNode.NODEINST_ACTION_SUBMIT,
				purchaseRequestManager, PurchaseRequestActionNode.DOC_ACTION_SUBMIT
		)));
		return resultList;
	}
	
	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder purchaseRequestExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(PurchaseRequest.class,
					PurchaseRequestUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(PurchaseRequestManager.METHOD_ConvPurchaseRequestToUI)
							.convUIToMethod(PurchaseRequestManager.METHOD_ConvUIToPurchaseRequest));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDocDefCreateUpdateNodeMapConfigureList(PurchaseRequest.SENAME));
			resultList.add(purchaseRequestExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseRequestExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseRequestExtensionUnion.setNodeInstId(PurchaseRequest.SENAME);
		purchaseRequestExtensionUnion.setNodeName(PurchaseRequest.NODENAME);

		// UI Model Configure of node:[PurchaseRequest]
		UIModelNodeMapConfigure purchaseRequestMap = new UIModelNodeMapConfigure();
		purchaseRequestMap.setSeName(PurchaseRequest.SENAME);
		purchaseRequestMap.setNodeName(PurchaseRequest.NODENAME);
		purchaseRequestMap.setNodeInstID(PurchaseRequest.SENAME);
		purchaseRequestMap.setHostNodeFlag(true);
		Class<?>[] purchaseRequestConvToUIParas = { PurchaseRequest.class,
				PurchaseRequestUIModel.class };
		purchaseRequestMap
				.setConvToUIMethodParas(purchaseRequestConvToUIParas);
		purchaseRequestMap
				.setConvToUIMethod(PurchaseRequestManager.METHOD_ConvPurchaseRequestToUI);
		Class<?>[] purchaseRequestConvUIToParas = {
				PurchaseRequestUIModel.class, PurchaseRequest.class };
		purchaseRequestMap
				.setConvUIToMethodParas(purchaseRequestConvUIToParas);
		purchaseRequestMap
				.setConvUIToMethod(PurchaseRequestManager.METHOD_ConvUIToPurchaseRequest);
		uiModelNodeMapList.add(purchaseRequestMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(PurchaseRequest.SENAME));

		purchaseRequestExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(purchaseRequestExtensionUnion);
		return resultList;
	}

}
