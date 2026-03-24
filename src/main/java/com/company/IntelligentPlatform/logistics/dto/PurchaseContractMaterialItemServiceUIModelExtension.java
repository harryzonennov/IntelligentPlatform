package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractMaterialItemManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class PurchaseContractMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected PurchaseContractMaterialItemManager purchaseContractMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				PurchaseContractMaterialItemAttachment.SENAME,
				PurchaseContractMaterialItemAttachment.NODENAME,
				PurchaseContractMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder purchaseContractExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(PurchaseContractMaterialItem.class,
					PurchaseContractMaterialItemUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(
									PurchaseContractMaterialItemManager.METHOD_ConvPurchaseContractMaterialItemToUI).logicManager(purchaseContractMaterialItemManager)
							.convUIToMethod(PurchaseContractMaterialItemManager.METHOD_ConvUIToPurchaseContractMaterialItem));
			Class<?>[] convParentDocToUIMethodParas = {PurchaseContract.class, PurchaseContractMaterialItemUIModel.class};
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefParentDocMapConfigureList(PurchaseContractMaterialItem.NODENAME,
					PurchaseContractMaterialItemManager.METHOD_ConvParentDocToItemUI,
					purchaseContractMaterialItemManager, convParentDocToUIMethodParas));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefMaterialNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefPrevNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefNextNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefNextProfNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefPrevProfNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefReservedNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			purchaseContractExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefCreateUpdateNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
			resultList.add(purchaseContractExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion purchaseContractMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		purchaseContractMaterialItemExtensionUnion
				.setNodeInstId(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemExtensionUnion
				.setNodeName(PurchaseContractMaterialItem.NODENAME);

		// UI Model Configure of node:[PurchaseContractMaterialItem]
		UIModelNodeMapConfigure purchaseContractMaterialItemMap = new UIModelNodeMapConfigure();
		purchaseContractMaterialItemMap
				.setSeName(PurchaseContractMaterialItem.SENAME);
		purchaseContractMaterialItemMap
				.setNodeName(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemMap
				.setNodeInstID(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] purchaseContractMaterialItemConvToUIParas = {
				PurchaseContractMaterialItem.class,
				PurchaseContractMaterialItemUIModel.class };
		purchaseContractMaterialItemMap
				.setConvToUIMethodParas(purchaseContractMaterialItemConvToUIParas);
		purchaseContractMaterialItemMap.setLogicManager(purchaseContractMaterialItemManager);
		purchaseContractMaterialItemMap
				.setConvToUIMethod(PurchaseContractMaterialItemManager.METHOD_ConvPurchaseContractMaterialItemToUI);
		Class<?>[] PurchaseContractMaterialItemConvUIToParas = {
				PurchaseContractMaterialItemUIModel.class,
				PurchaseContractMaterialItem.class };
		purchaseContractMaterialItemMap
				.setConvUIToMethodParas(PurchaseContractMaterialItemConvUIToParas);
		purchaseContractMaterialItemMap
				.setConvUIToMethod(PurchaseContractMaterialItemManager.METHOD_ConvUIToPurchaseContractMaterialItem);
		uiModelNodeMapList.add(purchaseContractMaterialItemMap);

		// UI Model Configure of node:[PurchaseContract]
		Class<?>[] convParentDocToUIMethodParas = {PurchaseContract.class, PurchaseContractMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(PurchaseContractMaterialItem.NODENAME,
				PurchaseContractMaterialItemManager.METHOD_ConvParentDocToItemUI, purchaseContractMaterialItemManager,
				convParentDocToUIMethodParas));

		// UI Model Configure of node:[MaterialStockKeepUnit]
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class,
				PurchaseContractMaterialItemUIModel.class };
//		uiModelNodeMapList
//				.addAll(docFlowProxy
//						.getDefMaterialNodeMapConfigureList(
//								PurchaseContractMaterialItem.NODENAME,
//								PurchaseContractMaterialItemManager.METHOD_ConvMaterialStockKeepUnitToUI, purchaseContractMaterialItemManager,
//								materialStockKeepUnitConvToUIParas));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevProfNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextProfNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));
		uiModelNodeMapList
		.addAll(docFlowProxy
				.getDefCreateUpdateNodeMapConfigureList(PurchaseContractMaterialItem.NODENAME));

		purchaseContractMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(purchaseContractMaterialItemExtensionUnion);
		return resultList;
	}

}
