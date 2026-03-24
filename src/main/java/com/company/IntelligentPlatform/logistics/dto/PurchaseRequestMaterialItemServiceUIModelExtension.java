package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseRequestManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestMaterialItemManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseRequestMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseRequestManager purchaseRequestManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected PurchaseRequestMaterialItemManager purchaseRequestMaterialItemManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseRequestMaterialItemAttachment.SENAME,
				PurchaseRequestMaterialItemAttachment.NODENAME,
				PurchaseRequestMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder purchaseRequestExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(PurchaseRequestMaterialItem.class,
					PurchaseRequestMaterialItemUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(
							PurchaseRequestMaterialItemManager.METHOD_ConvPurchaseRequestMaterialItemToUI).logicManager(purchaseRequestMaterialItemManager)
							.convUIToMethod(PurchaseRequestMaterialItemManager.METHOD_ConvUIToPurchaseRequestMaterialItem));
			Class<?>[] convParentDocToUIMethodParas = {PurchaseRequest.class, PurchaseRequestMaterialItemUIModel.class};
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefParentDocMapConfigureList(PurchaseRequestMaterialItem.NODENAME,
					PurchaseRequestMaterialItemManager.METHOD_ConvParentDocToItemUI,
					purchaseRequestMaterialItemManager, convParentDocToUIMethodParas));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefMaterialNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefPrevNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefNextNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefReservedNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
			purchaseRequestExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefCreateUpdateNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
			resultList.add(purchaseRequestExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseRequestMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseRequestMaterialItemExtensionUnion
				.setNodeInstId(PurchaseRequestMaterialItem.NODENAME);
		purchaseRequestMaterialItemExtensionUnion
				.setNodeName(PurchaseRequestMaterialItem.NODENAME);

		// UI Model Configure of node:[PurchaseRequestMaterialItem]
		UIModelNodeMapConfigure purchaseRequestMaterialItemMap = new UIModelNodeMapConfigure();
		purchaseRequestMaterialItemMap.setSeName(PurchaseRequestMaterialItem.SENAME);
		purchaseRequestMaterialItemMap.setNodeName(PurchaseRequestMaterialItem.NODENAME);
		purchaseRequestMaterialItemMap.setNodeInstID(PurchaseRequestMaterialItem.NODENAME);
		purchaseRequestMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] purchaseRequestMaterialItemConvToUIParas = {
				PurchaseRequestMaterialItem.class, PurchaseRequestMaterialItemUIModel.class };
		purchaseRequestMaterialItemMap
				.setConvToUIMethodParas(purchaseRequestMaterialItemConvToUIParas);
		purchaseRequestMaterialItemMap
				.setConvToUIMethod(PurchaseRequestMaterialItemManager.METHOD_ConvPurchaseRequestMaterialItemToUI);
		purchaseRequestMaterialItemMap.setLogicManager(purchaseRequestMaterialItemManager);
		Class<?>[] purchaseRequestMaterialItemConvUIToParas = {
				PurchaseRequestMaterialItemUIModel.class, PurchaseRequestMaterialItem.class };
		purchaseRequestMaterialItemMap
				.setConvUIToMethodParas(purchaseRequestMaterialItemConvUIToParas);
		purchaseRequestMaterialItemMap
				.setConvUIToMethod(PurchaseRequestMaterialItemManager.METHOD_ConvUIToPurchaseRequestMaterialItem);
		uiModelNodeMapList.add(purchaseRequestMaterialItemMap);

		Class<?>[] convParentDocToUIMethodParas = {PurchaseRequest.class, PurchaseRequestMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(PurchaseRequestMaterialItem.NODENAME,
				PurchaseRequestMaterialItemManager.METHOD_ConvParentDocToItemUI,
				purchaseRequestMaterialItemManager, convParentDocToUIMethodParas));

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(PurchaseRequestMaterialItem.NODENAME));
		purchaseRequestMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);

		resultList.add(purchaseRequestMaterialItemExtensionUnion);
		return resultList;
	}

}
