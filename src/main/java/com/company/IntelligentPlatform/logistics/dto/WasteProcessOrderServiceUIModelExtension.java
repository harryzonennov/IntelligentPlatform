package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.WasteProcessOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
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
public class WasteProcessOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WasteProcessMaterialItemServiceUIModelExtension wasteProcessMaterialItemServiceUIModelExtension;

	@Autowired
	protected WasteProcessOrderManager wasteProcessOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

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
		resultList.add(wasteProcessMaterialItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				WasteProcessOrderAttachment.SENAME,
				WasteProcessOrderAttachment.NODENAME,
				WasteProcessOrderAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WasteProcessOrderActionNode.SENAME,
				WasteProcessOrderActionNode.NODENAME,
				WasteProcessOrderActionNode.NODEINST_ACTION_APPROVE,
				wasteProcessOrderManager, WasteProcessOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WasteProcessOrderActionNode.SENAME,
				WasteProcessOrderActionNode.NODENAME,
				WasteProcessOrderActionNode.NODEINST_ACTION_SUBMIT,
				wasteProcessOrderManager, WasteProcessOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WasteProcessOrderActionNode.SENAME,
				WasteProcessOrderActionNode.NODENAME,
				WasteProcessOrderActionNode.NODEINST_ACTION_REJECT_APPROVE,
				wasteProcessOrderManager, WasteProcessOrderActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WasteProcessOrderActionNode.SENAME,
				WasteProcessOrderActionNode.NODENAME,
				WasteProcessOrderActionNode.NODEINST_ACTION_DELIVERY_DONE,
				wasteProcessOrderManager, WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				WasteProcessOrderActionNode.SENAME,
				WasteProcessOrderActionNode.NODENAME,
				WasteProcessOrderActionNode.NODEINST_ACTION_PROCESS_DONE,
				wasteProcessOrderManager, WasteProcessOrderActionNode.DOC_ACTION_PROCESS_DONE
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WasteProcessOrderParty.SENAME,
				WasteProcessOrderParty.NODENAME,
				WasteProcessOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				wasteProcessOrderManager,
				WasteProcessOrderParty.ROLE_SOLD_TO_PARTY,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WasteProcessOrderParty.SENAME,
				WasteProcessOrderParty.NODENAME,
				WasteProcessOrderParty.PARTY_NODEINST_SOLD_ORG,
				wasteProcessOrderManager,
				WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion wasteProcessOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		wasteProcessOrderExtensionUnion.setNodeInstId(WasteProcessOrder.SENAME);
		wasteProcessOrderExtensionUnion.setNodeName(WasteProcessOrder.NODENAME);

		// UI Model Configure of node:[WasteProcessOrder]
		UIModelNodeMapConfigure wasteProcessOrderMap = new UIModelNodeMapConfigure();
		wasteProcessOrderMap.setSeName(WasteProcessOrder.SENAME);
		wasteProcessOrderMap.setNodeName(WasteProcessOrder.NODENAME);
		wasteProcessOrderMap.setNodeInstID(WasteProcessOrder.SENAME);
		wasteProcessOrderMap.setHostNodeFlag(true);
		Class<?>[] wasteProcessOrderConvToUIParas = { WasteProcessOrder.class,
				WasteProcessOrderUIModel.class };
		wasteProcessOrderMap.setConvToUIMethodParas(wasteProcessOrderConvToUIParas);
		wasteProcessOrderMap
				.setConvToUIMethod(WasteProcessOrderManager.METHOD_ConvWasteProcessOrderToUI);
		Class<?>[] WasteProcessOrderConvUIToParas = { WasteProcessOrderUIModel.class,
				WasteProcessOrder.class };
		wasteProcessOrderMap.setConvUIToMethodParas(WasteProcessOrderConvUIToParas);
		wasteProcessOrderMap
				.setConvUIToMethod(WasteProcessOrderManager.METHOD_ConvUIToWasteProcessOrder);
		uiModelNodeMapList.add(wasteProcessOrderMap);
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(WasteProcessOrder.SENAME));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(WasteProcessOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(WasteProcessOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(WasteProcessOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(WasteProcessOrder.SENAME));

		wasteProcessOrderExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(wasteProcessOrderExtensionUnion);
		return resultList;
	}

}
