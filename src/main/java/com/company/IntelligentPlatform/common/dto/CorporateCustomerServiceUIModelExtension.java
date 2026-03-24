package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CorporateCustomerActionNode;
import com.company.IntelligentPlatform.common.model.CorporateCustomerAttachment;
import com.company.IntelligentPlatform.common.model.CustomerParentOrgReference;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class CorporateCustomerServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected CorporateContactPersonServiceUIModelExtension corporateContactPersonServiceUIModelExtension;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(corporateContactPersonServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				CorporateCustomerAttachment.SENAME,
				CorporateCustomerAttachment.NODENAME,
				CorporateCustomerAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				CorporateCustomerActionNode.SENAME,
				CorporateCustomerActionNode.NODENAME,
				CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE,
				corporateCustomerManager, CorporateCustomerActionNode.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				CorporateCustomerActionNode.SENAME,
				CorporateCustomerActionNode.NODENAME,
				CorporateCustomerActionNode.NODEINST_ACTION_REINIT,
				corporateCustomerManager, CorporateCustomerActionNode.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				CorporateCustomerActionNode.SENAME,
				CorporateCustomerActionNode.NODENAME,
				CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE,
				corporateCustomerManager, CorporateCustomerActionNode.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				CorporateCustomerActionNode.SENAME,
				CorporateCustomerActionNode.NODENAME,
				CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT,
				corporateCustomerManager, CorporateCustomerActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				CorporateCustomerActionNode.SENAME,
				CorporateCustomerActionNode.NODENAME,
				CorporateCustomerActionNode.NODEINST_ACTION_APPROVE,
				corporateCustomerManager, CorporateCustomerActionNode.DOC_ACTION_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion corporateCustomerExtensionUnion = new ServiceUIModelExtensionUnion();
		corporateCustomerExtensionUnion.setNodeInstId(CorporateCustomer.SENAME);
		corporateCustomerExtensionUnion.setNodeName(CorporateCustomer.NODENAME);

		// UI Model Configure of node:[CorporateCustomer]
		UIModelNodeMapConfigure corporateCustomerMap = new UIModelNodeMapConfigure();
		corporateCustomerMap.setSeName(CorporateCustomer.SENAME);
		corporateCustomerMap.setNodeName(CorporateCustomer.NODENAME);
		corporateCustomerMap.setNodeInstID(CorporateCustomer.SENAME);
		corporateCustomerMap.setHostNodeFlag(true);
		Class<?>[] corporateCustomerConvToUIParas = { CorporateCustomer.class,
				CorporateCustomerUIModel.class };
		corporateCustomerMap
				.setConvToUIMethodParas(corporateCustomerConvToUIParas);
		corporateCustomerMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvCorporateCustomerToUI);
		Class<?>[] CorporateCustomerConvUIToParas = {
				CorporateCustomerUIModel.class, CorporateCustomer.class };
		corporateCustomerMap
				.setConvUIToMethodParas(CorporateCustomerConvUIToParas);
		corporateCustomerMap
				.setConvUIToMethod(CorporateCustomerManager.METHOD_ConvUIToCorporateCustomer);
		uiModelNodeMapList.add(corporateCustomerMap);

		// UI Model Configure of node:[CustomerParentOrgReference]
		UIModelNodeMapConfigure customerParentOrgReferenceMap = new UIModelNodeMapConfigure();
		customerParentOrgReferenceMap
				.setSeName(CustomerParentOrgReference.SENAME);
		customerParentOrgReferenceMap
				.setNodeName(CustomerParentOrgReference.NODENAME);
		customerParentOrgReferenceMap
				.setNodeInstID(CustomerParentOrgReference.NODENAME);
		customerParentOrgReferenceMap
				.setBaseNodeInstID(CorporateCustomer.SENAME);
		customerParentOrgReferenceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		customerParentOrgReferenceMap
				.setServiceEntityManager(corporateCustomerManager);
		Class<?>[] customerParentOrgReferenceConvToUIParas = {
				CustomerParentOrgReference.class,
				CorporateCustomerUIModel.class };
		customerParentOrgReferenceMap
				.setConvToUIMethodParas(customerParentOrgReferenceConvToUIParas);
		customerParentOrgReferenceMap
				.setConvToUIMethod(CorporateCustomerManager.METHOD_ConvCustomerParentOrgReferenceToUI);
		Class<?>[] CustomerParentOrgReferenceConvUIToParas = {
				CorporateCustomerUIModel.class,
				CustomerParentOrgReference.class };
		customerParentOrgReferenceMap
				.setConvUIToMethodParas(CustomerParentOrgReferenceConvUIToParas);
		customerParentOrgReferenceMap
				.setConvUIToMethod(CorporateCustomerManager.METHOD_ConvUIToCustomerParentOrgReference);
		uiModelNodeMapList.add(customerParentOrgReferenceMap);
		corporateCustomerExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(corporateCustomerExtensionUnion);
		return resultList;
	}

}
