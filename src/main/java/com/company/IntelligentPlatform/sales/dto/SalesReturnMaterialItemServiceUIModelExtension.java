package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.service.SalesReturnMaterialItemManager;
import com.company.IntelligentPlatform.sales.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.sales.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class SalesReturnMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesReturnOrderManager salesReturnOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected SalesReturnMaterialItemManager salesReturnMaterialItemManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesReturnMatItemAttachment.SENAME,
				SalesReturnMatItemAttachment.NODENAME,
				SalesReturnMatItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesReturnMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		salesReturnMaterialItemExtensionUnion
				.setNodeInstId(SalesReturnMaterialItem.NODENAME);
		salesReturnMaterialItemExtensionUnion
				.setNodeName(SalesReturnMaterialItem.NODENAME);

		// UI Model Configure of node:[SalesReturnMaterialItem]
		UIModelNodeMapConfigure salesReturnMaterialItemMap = new UIModelNodeMapConfigure();
		salesReturnMaterialItemMap.setSeName(SalesReturnMaterialItem.SENAME);
		salesReturnMaterialItemMap.setNodeName(SalesReturnMaterialItem.NODENAME);
		salesReturnMaterialItemMap.setNodeInstID(SalesReturnMaterialItem.NODENAME);
		salesReturnMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] salesReturnMaterialItemConvToUIParas = {
				SalesReturnMaterialItem.class, SalesReturnMaterialItemUIModel.class };
		salesReturnMaterialItemMap
				.setConvToUIMethodParas(salesReturnMaterialItemConvToUIParas);
		salesReturnMaterialItemMap
				.setConvToUIMethod(SalesReturnMaterialItemManager.METHOD_ConvSalesReturnMaterialItemToUI);
		salesReturnMaterialItemMap.setLogicManager(salesReturnMaterialItemManager);
		Class<?>[] salesReturnMaterialItemConvUIToParas = {
				SalesReturnMaterialItemUIModel.class, SalesReturnMaterialItem.class };
		salesReturnMaterialItemMap
				.setConvUIToMethodParas(salesReturnMaterialItemConvUIToParas);
		salesReturnMaterialItemMap
				.setConvUIToMethod(SalesReturnMaterialItemManager.METHOD_ConvUIToSalesReturnMaterialItem);
		uiModelNodeMapList.add(salesReturnMaterialItemMap);
		Class<?>[] salesContractItemConvToItemUIParas = {SalesContractMaterialItem.class,
				SalesReturnMaterialItemUIModel.class};

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevProfNodeMapConfigureListFrame(SalesReturnMaterialItem.NODENAME,
								SalesReturnMaterialItemManager.METHOD_ConvSalesContractMatItemToItemUI, null,
								salesReturnMaterialItemManager, null, salesContractItemConvToItemUIParas, null));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextProfNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(SalesReturnMaterialItem.NODENAME));
		salesReturnMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(SalesReturnMaterialItem.NODENAME));


		resultList.add(salesReturnMaterialItemExtensionUnion);
		return resultList;
	}

}