package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.sales.service.*;
import com.company.IntelligentPlatform.sales.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;

@Service
public class SalesContractMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesContractManager salesContractManager;
	
	@Autowired
	protected SalesContractMaterialItemManager salesContractMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesContractMaterialItemAttachment.SENAME,
				SalesContractMaterialItemAttachment.NODENAME,
				SalesContractMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesContractMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		salesContractMaterialItemExtensionUnion
				.setNodeInstId(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemExtensionUnion
		.setNodeName(SalesContractMaterialItem.NODENAME);

		// UI Model Configure of node:[SalesContractMaterialItem]
		UIModelNodeMapConfigure salesContractMaterialItemMap = new UIModelNodeMapConfigure();
		salesContractMaterialItemMap
				.setSeName(SalesContractMaterialItem.SENAME);
		salesContractMaterialItemMap
				.setNodeName(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemMap
				.setNodeInstID(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] salesContractMaterialItemConvToUIParas = {
				SalesContractMaterialItem.class,
				SalesContractMaterialItemUIModel.class };
		salesContractMaterialItemMap
				.setConvToUIMethodParas(salesContractMaterialItemConvToUIParas);
		salesContractMaterialItemMap.setLogicManager(salesContractMaterialItemManager);
		salesContractMaterialItemMap
				.setConvToUIMethod(SalesContractMaterialItemManager.METHOD_ConvSalesContractMaterialItemToUI);
		Class<?>[] SalesContractMaterialItemConvUIToParas = {
				SalesContractMaterialItemUIModel.class,
				SalesContractMaterialItem.class };
		salesContractMaterialItemMap
				.setConvUIToMethodParas(SalesContractMaterialItemConvUIToParas);
		salesContractMaterialItemMap
				.setConvUIToMethod(SalesContractMaterialItemManager.METHOD_ConvUIToSalesContractMaterialItem);
		uiModelNodeMapList.add(salesContractMaterialItemMap);

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(SalesContractMaterialItem.NODENAME));

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(SalesContractMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefReservedNodeMapConfigureList(SalesContractMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefPrevNodeMapConfigureList(SalesContractMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefNextNodeMapConfigureList(SalesContractMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefCreateUpdateNodeMapConfigureList(SalesContractMaterialItem.NODENAME));

		// UI Model Configure of node:[SalesForcast]
		Class<?>[] convParentDocToUIMethodParas = {SalesContract.class, SalesContractMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(SalesContractMaterialItem.NODENAME,
				SalesContractMaterialItemManager.METHOD_ConvParentDocToItemUI, salesContractMaterialItemManager,
				convParentDocToUIMethodParas));

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(SalesContractMaterialItem.NODENAME));

		salesContractMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(salesContractMaterialItemExtensionUnion);
		return resultList;
	}

}