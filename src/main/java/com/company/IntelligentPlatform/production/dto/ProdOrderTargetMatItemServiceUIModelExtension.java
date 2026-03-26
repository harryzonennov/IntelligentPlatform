package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProdOrderTargetMatItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;

@Service
public class ProdOrderTargetMatItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;
	
	@Autowired
	protected ProdOrderTarSubItemServiceUIModelExtension prodOrderTarSubItemServiceUIModelExtension;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(prodOrderTarSubItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ProdOrderTargetItemAttachment.SENAME,
				ProdOrderTargetItemAttachment.NODENAME,
				ProdOrderTargetItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodOrderTargetMatItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderTargetMatItemExtensionUnion
				.setNodeInstId(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetMatItemExtensionUnion
				.setNodeName(ProdOrderTargetMatItem.NODENAME);

		// UI Model Configure of node:[ProdOrderTargetMatItem]
		UIModelNodeMapConfigure prodOrderTargetMatItemMap = new UIModelNodeMapConfigure();
		prodOrderTargetMatItemMap.setSeName(ProdOrderTargetMatItem.SENAME);
		prodOrderTargetMatItemMap.setNodeName(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetMatItemMap
				.setNodeInstID(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetMatItemMap.setHostNodeFlag(true);
		prodOrderTargetMatItemMap.setLogicManager(prodOrderTargetMatItemManager);
		Class<?>[] prodOrderTargetMatItemConvToUIParas = {
				ProdOrderTargetMatItem.class,
				ProdOrderTargetMatItemUIModel.class };
		prodOrderTargetMatItemMap
				.setConvToUIMethodParas(prodOrderTargetMatItemConvToUIParas);
		prodOrderTargetMatItemMap
				.setConvToUIMethod(ProdOrderTargetMatItemManager.METHOD_ConvProdOrderTargetMatItemToUI);
		Class<?>[] ProdOrderTargetMatItemConvUIToParas = {
				ProdOrderTargetMatItemUIModel.class,
				ProdOrderTargetMatItem.class };
		prodOrderTargetMatItemMap
				.setConvUIToMethodParas(ProdOrderTargetMatItemConvUIToParas);
		prodOrderTargetMatItemMap
				.setConvUIToMethod(ProdOrderTargetMatItemManager.METHOD_ConvUIToProdOrderTargetMatItem);
		uiModelNodeMapList.add(prodOrderTargetMatItemMap);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setBaseNodeInstID(ProdOrderTargetMatItem.NODENAME);
		productionOrderMap.setLogicManager(prodOrderTargetMatItemManager);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] productionOrderConvToUIParas = {
				ProductionOrder.class, ProdOrderTargetMatItemUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProdOrderTargetMatItemManager.METHOD_ConvProductionOrderToTargetItemUI);
		uiModelNodeMapList.add(productionOrderMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdOrderTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdOrderTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdOrderTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdOrderTargetMatItem.NODENAME));

		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, ProdOrderTargetMatItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						ProdOrderTargetMatItem.NODENAME,
						ProdOrderTargetMatItemManager.METHOD_ConvMaterialSKUToTargetItemUI, prodOrderTargetMatItemManager,
						materialStockKeepUnitConvToUIParas));

		prodOrderTargetMatItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderTargetMatItemExtensionUnion);
		return resultList;
	}

}
