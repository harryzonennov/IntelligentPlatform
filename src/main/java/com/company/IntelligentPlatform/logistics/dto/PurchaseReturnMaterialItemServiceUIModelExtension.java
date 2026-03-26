package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseReturnMaterialItemManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseReturnOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseReturnMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseReturnOrderManager purchaseReturnOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected PurchaseReturnMaterialItemManager purchaseReturnMaterialItemManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseReturnMaterialItemAttachment.SENAME,
				PurchaseReturnMaterialItemAttachment.NODENAME,
				PurchaseReturnMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseReturnMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseReturnMaterialItemExtensionUnion
				.setNodeInstId(PurchaseReturnMaterialItem.NODENAME);
		purchaseReturnMaterialItemExtensionUnion
				.setNodeName(PurchaseReturnMaterialItem.NODENAME);

		// UI Model Configure of node:[PurchaseReturnMaterialItem]
		UIModelNodeMapConfigure purchaseReturnMaterialItemMap = new UIModelNodeMapConfigure();
		purchaseReturnMaterialItemMap.setSeName(PurchaseReturnMaterialItem.SENAME);
		purchaseReturnMaterialItemMap.setNodeName(PurchaseReturnMaterialItem.NODENAME);
		purchaseReturnMaterialItemMap.setNodeInstID(PurchaseReturnMaterialItem.NODENAME);
		purchaseReturnMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] purchaseReturnMaterialItemConvToUIParas = {
				PurchaseReturnMaterialItem.class, PurchaseReturnMaterialItemUIModel.class };
		purchaseReturnMaterialItemMap
				.setConvToUIMethodParas(purchaseReturnMaterialItemConvToUIParas);
		purchaseReturnMaterialItemMap
				.setConvToUIMethod(PurchaseReturnMaterialItemManager.METHOD_ConvPurchaseReturnMaterialItemToUI);
		purchaseReturnMaterialItemMap.setLogicManager(purchaseReturnMaterialItemManager);
		Class<?>[] purchaseReturnMaterialItemConvUIToParas = {
				PurchaseReturnMaterialItemUIModel.class, PurchaseReturnMaterialItem.class };
		purchaseReturnMaterialItemMap
				.setConvUIToMethodParas(purchaseReturnMaterialItemConvUIToParas);
		purchaseReturnMaterialItemMap
				.setConvUIToMethod(PurchaseReturnMaterialItemManager.METHOD_ConvUIToPurchaseReturnMaterialItem);
		uiModelNodeMapList.add(purchaseReturnMaterialItemMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));
		Class<?>[] purchaseContractItemConvToItemUIParas = {PurchaseContractMaterialItem.class,
				PurchaseReturnMaterialItemUIModel.class};
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevProfNodeMapConfigureListFrame(PurchaseReturnMaterialItem.NODENAME,
								PurchaseReturnMaterialItemManager.METHOD_ConvPurchaseContractMatItemToItemUI, null,
								purchaseReturnMaterialItemManager, null, purchaseContractItemConvToItemUIParas, null));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextProfNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(PurchaseReturnMaterialItem.NODENAME));

		// UI Model Configure of node:[PurchaseReturnOrder]
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(PurchaseReturnMaterialItem.NODENAME));

		purchaseReturnMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		
		resultList.add(purchaseReturnMaterialItemExtensionUnion);
		return resultList;
	}

}
