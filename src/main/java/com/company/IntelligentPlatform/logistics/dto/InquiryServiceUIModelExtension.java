package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InquiryManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.Inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
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
public class InquiryServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected InquiryMaterialItemServiceUIModelExtension inquiryMaterialItemServiceUIModelExtension;

	@Autowired
	protected InquiryManager inquiryManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;


	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(inquiryMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InquiryAttachment.SENAME,
				InquiryAttachment.NODENAME,
				InquiryAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InquiryParty.SENAME,
				InquiryParty.NODENAME,
				InquiryParty.PARTY_NODEINST_PUR_SUPPLIER,
				inquiryManager,
				InquiryParty.ROLE_PARTYB,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InquiryParty.SENAME,
				InquiryParty.NODENAME,
				InquiryParty.PARTY_NODEINST_PUR_ORG,
				inquiryManager,
				InquiryParty.ROLE_PARTYA,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InquiryActionNode.SENAME,
				InquiryActionNode.NODENAME,
				InquiryActionNode.NODEINST_ACTION_APPROVE,
				inquiryManager, SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InquiryActionNode.SENAME,
				InquiryActionNode.NODENAME,
				InquiryActionNode.NODEINST_ACTION_INPROCESS,
				inquiryManager, SystemDefDocActionCodeProxy.DOC_ACTION_INPROCESS
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				InquiryActionNode.SENAME,
				InquiryActionNode.NODENAME,
				InquiryActionNode.NODEINST_ACTION_SUBMIT,
				inquiryManager, InquiryActionNode.DOC_ACTION_SUBMIT
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder inquiryExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(Inquiry.class,
					InquiryUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(InquiryManager.METHOD_ConvInquiryToUI)
							.convUIToMethod(InquiryManager.METHOD_ConvUIToInquiry));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDocDefCreateUpdateNodeMapConfigureList(Inquiry.SENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevProfDocMapConfigureList(Inquiry.SENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevDocMapConfigureList(Inquiry.SENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextProfDocMapConfigureList(Inquiry.SENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextDocMapConfigureList(Inquiry.SENAME));
			resultList.add(inquiryExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}
	
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inquiryExtensionUnion = new ServiceUIModelExtensionUnion();
		inquiryExtensionUnion.setNodeInstId(Inquiry.SENAME);
		inquiryExtensionUnion.setNodeName(Inquiry.NODENAME);

		UIModelNodeMapConfigure inquiryMap = new UIModelNodeMapConfigure();
		inquiryMap.setSeName(Inquiry.SENAME);
		inquiryMap.setNodeName(Inquiry.NODENAME);
		inquiryMap.setNodeInstID(Inquiry.SENAME);
		inquiryMap.setHostNodeFlag(true);
		Class<?>[] inquiryConvToUIParas = { Inquiry.class, InquiryUIModel.class };
		inquiryMap.setConvToUIMethodParas(inquiryConvToUIParas);
		inquiryMap.setConvToUIMethod(InquiryManager.METHOD_ConvInquiryToUI);
		Class<?>[] InquiryConvUIToParas = { InquiryUIModel.class, Inquiry.class };
		inquiryMap.setConvUIToMethodParas(InquiryConvUIToParas);
		inquiryMap.setConvUIToMethod(InquiryManager.METHOD_ConvUIToInquiry);
		uiModelNodeMapList.add(inquiryMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(Inquiry.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(Inquiry.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(Inquiry.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(Inquiry.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(Inquiry.SENAME));

		inquiryExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inquiryExtensionUnion);
		return resultList;
	}

}
