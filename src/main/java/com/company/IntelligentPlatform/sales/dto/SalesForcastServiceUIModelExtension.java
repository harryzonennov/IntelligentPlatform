package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.service.SalesForcastManager;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
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
public class SalesForcastServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesForcastMaterialItemServiceUIModelExtension salesForcastMaterialItemServiceUIModelExtension;

	@Autowired
	protected SalesForcastManager salesForcastManager;

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
		resultList.add(salesForcastMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesForcastAttachment.SENAME,
				SalesForcastAttachment.NODENAME,
				SalesForcastAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesForcast.SENAME,
				SalesForcastParty.NODENAME,
				SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER,
				salesForcastManager,
				SalesForcastParty.ROLE_SOLD_TO_PARTY,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				SalesForcastParty.SENAME,
				SalesForcastParty.NODENAME,
				SalesForcastParty.PARTY_NODEINST_SOLD_ORG,
				salesForcastManager,
				SalesForcastParty.ROLE_SOLD_FROM_PARTY,
				Organization.class,
				Employee.class
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesForcastActionNode.SENAME,
				SalesForcastActionNode.NODENAME,
				SalesForcastActionNode.NODEINST_ACTION_APPROVE,
				salesForcastManager, SalesForcastActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				SalesForcastActionNode.SENAME,
				SalesForcastActionNode.NODENAME,
				SalesForcastActionNode.NODEINST_ACTION_SUBMIT,
				salesForcastManager, SalesForcastActionNode.DOC_ACTION_SUBMIT
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesForcastExtensionUnion = new ServiceUIModelExtensionUnion();
		salesForcastExtensionUnion.setNodeInstId(SalesForcast.SENAME);
		salesForcastExtensionUnion.setNodeName(SalesForcast.NODENAME);

		// UI Model Configure of node:[SalesForcast]
		UIModelNodeMapConfigure salesForcastMap = new UIModelNodeMapConfigure();
		salesForcastMap.setSeName(SalesForcast.SENAME);
		salesForcastMap.setNodeName(SalesForcast.NODENAME);
		salesForcastMap.setNodeInstID(SalesForcast.SENAME);
		salesForcastMap.setHostNodeFlag(true);
		Class<?>[] salesForcastConvToUIParas = { SalesForcast.class,
				SalesForcastUIModel.class };
		salesForcastMap
				.setConvToUIMethodParas(salesForcastConvToUIParas);
		salesForcastMap
				.setConvToUIMethod(SalesForcastManager.METHOD_ConvSalesForcastToUI);
		Class<?>[] salesForcastConvUIToParas = {
				SalesForcastUIModel.class, SalesForcast.class };
		salesForcastMap
				.setConvUIToMethodParas(salesForcastConvUIToParas);
		salesForcastMap
				.setConvUIToMethod(SalesForcastManager.METHOD_ConvUIToSalesForcast);
		uiModelNodeMapList.add(salesForcastMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(SalesForcast.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(SalesForcast.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(SalesForcast.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(SalesForcast.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(SalesContract.SENAME));

		salesForcastExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(salesForcastExtensionUnion);
		return resultList;
	}

}