package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.*;
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
public class PurchaseContractServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseContractMaterialItemServiceUIModelExtension purchaseContractMaterialItemServiceUIModelExtension;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(purchaseContractMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseContractAttachment.SENAME,
				PurchaseContractAttachment.NODENAME,
				PurchaseContractAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseContractParty.SENAME,
				PurchaseContractParty.NODENAME,
				PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER,
				purchaseContractManager,
				PurchaseContractParty.ROLE_PARTYB,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				PurchaseContractParty.SENAME,
				PurchaseContractParty.NODENAME,
				PurchaseContractParty.PARTY_NODEINST_PUR_ORG,
				purchaseContractManager,
				PurchaseContractParty.ROLE_PARTYA,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseContractActionNode.SENAME,
				PurchaseContractActionNode.NODENAME,
				PurchaseContractActionNode.NODEINST_ACTION_APPROVE,
				purchaseContractManager, SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseContractActionNode.SENAME,
				PurchaseContractActionNode.NODENAME,
				PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE,
				purchaseContractManager, SystemDefDocActionCodeProxy.DOC_ACTION_DELIVERY_DONE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseContractActionNode.SENAME,
				PurchaseContractActionNode.NODENAME,
				PurchaseContractActionNode.NODEINST_ACTION_SUBMIT,
				purchaseContractManager, PurchaseContractActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				PurchaseContractActionNode.SENAME,
				PurchaseContractActionNode.NODENAME,
				PurchaseContractActionNode.NODEINST_ACTION_REJECT_APPROVE,
				purchaseContractManager, PurchaseContractActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder purchaseContractExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(PurchaseContract.class,
					PurchaseContractUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(PurchaseContractManager.METHOD_ConvPurchaseContractToUI)
							.convUIToMethod(PurchaseContractManager.METHOD_ConvUIToPurchaseContract));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDocDefCreateUpdateNodeMapConfigureList(PurchaseContract.SENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevProfDocMapConfigureList(PurchaseContract.SENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevDocMapConfigureList(PurchaseContract.SENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextProfDocMapConfigureList(PurchaseContract.SENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextDocMapConfigureList(PurchaseContract.SENAME));
			resultList.add(purchaseContractExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseContractExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseContractExtensionUnion.setNodeInstId(PurchaseContract.SENAME);
		purchaseContractExtensionUnion.setNodeName(PurchaseContract.NODENAME);

		// UI Model Configure of node:[PurchaseContract]
		UIModelNodeMapConfigure purchaseContractMap = new UIModelNodeMapConfigure();
		purchaseContractMap.setSeName(PurchaseContract.SENAME);
		purchaseContractMap.setNodeName(PurchaseContract.NODENAME);
		purchaseContractMap.setNodeInstID(PurchaseContract.SENAME);
		purchaseContractMap.setHostNodeFlag(true);
		Class<?>[] purchaseContractConvToUIParas = { PurchaseContract.class,
				PurchaseContractUIModel.class };
		purchaseContractMap
				.setConvToUIMethodParas(purchaseContractConvToUIParas);
		purchaseContractMap
				.setConvToUIMethod(PurchaseContractManager.METHOD_ConvPurchaseContractToUI);
		Class<?>[] PurchaseContractConvUIToParas = {
				PurchaseContractUIModel.class, PurchaseContract.class };
		purchaseContractMap
				.setConvUIToMethodParas(PurchaseContractConvUIToParas);
		purchaseContractMap
				.setConvUIToMethod(PurchaseContractManager.METHOD_ConvUIToPurchaseContract);
		uiModelNodeMapList.add(purchaseContractMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(PurchaseContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(PurchaseContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(PurchaseContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(PurchaseContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(PurchaseContract.SENAME));


		purchaseContractExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(purchaseContractExtensionUnion);
		return resultList;
	}

}
