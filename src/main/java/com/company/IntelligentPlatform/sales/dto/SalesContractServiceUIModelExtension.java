package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.service.SalesContractManager;
import com.company.IntelligentPlatform.sales.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class SalesContractServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesContractMaterialItemServiceUIModelExtension salesContractMaterialItemServiceUIModelExtension;

	@Autowired
	protected SalesContractManager salesContractManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(salesContractMaterialItemServiceUIModelExtension);
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesContractParty.SENAME,
				SalesContractParty.NODENAME,
				SalesContractParty.PARTY_NODEINST_SOLD_CUSTOMER,
				salesContractManager,
				SalesContractParty.ROLE_SOLD_TO_PARTY,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesContractParty.SENAME,
				SalesContractParty.NODENAME,
				SalesContractParty.PARTY_NODEINST_SOLD_ORG,
				salesContractManager,
				SalesContractParty.ROLE_SOLD_FROM_PARTY,
				Organization.class,
				Employee.class
		)));
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesContractAttachment.SENAME,
				SalesContractAttachment.NODENAME,
				SalesContractAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesContractActionNode.SENAME,
				SalesContractActionNode.NODENAME,
				SalesContractActionNode.NODEINST_ACTION_APPROVE,
				salesContractManager, SalesContractActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesContractActionNode.SENAME,
				SalesContractActionNode.NODENAME,
				SalesContractActionNode.NODEINST_ACTION_SUBMIT,
				salesContractManager, SalesContractActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesContractActionNode.SENAME,
				SalesContractActionNode.NODENAME,
				SalesContractActionNode.NODEINST_ACTION_INPLAN,
				salesContractManager, SalesContractActionNode.DOC_ACTION_INPLAN
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesContractActionNode.SENAME,
				SalesContractActionNode.NODENAME,
				SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE,
				salesContractManager, SalesContractActionNode.DOC_ACTION_DELIVERY_DONE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesContractActionNode.SENAME,
				SalesContractActionNode.NODENAME,
				SalesContractActionNode.NODEINST_ACTION_PROCESS_DONE,
				salesContractManager, SalesContractActionNode.DOC_ACTION_PROCESS_DONE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesContractExtensionUnion = new ServiceUIModelExtensionUnion();
		salesContractExtensionUnion.setNodeInstId(SalesContract.SENAME);
		salesContractExtensionUnion.setNodeName(SalesContract.NODENAME);

		// UI Model Configure of node:[SalesContract]
		UIModelNodeMapConfigure salesContractMap = new UIModelNodeMapConfigure();
		salesContractMap.setSeName(SalesContract.SENAME);
		salesContractMap.setNodeName(SalesContract.NODENAME);
		salesContractMap.setNodeInstID(SalesContract.SENAME);
		salesContractMap.setHostNodeFlag(true);
		Class<?>[] salesContractConvToUIParas = { SalesContract.class,
				SalesContractUIModel.class };
		salesContractMap.setConvToUIMethodParas(salesContractConvToUIParas);
		salesContractMap
				.setConvToUIMethod(SalesContractManager.METHOD_ConvSalesContractToUI);
		Class<?>[] SalesContractConvUIToParas = { SalesContractUIModel.class,
				SalesContract.class };
		salesContractMap.setConvUIToMethodParas(SalesContractConvUIToParas);
		salesContractMap
				.setConvUIToMethod(SalesContractManager.METHOD_ConvUIToSalesContract);
		uiModelNodeMapList.add(salesContractMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(SalesContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(SalesContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(SalesContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(SalesContract.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(SalesContract.SENAME));

		salesContractExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(salesContractExtensionUnion);
		return resultList;
	}

}