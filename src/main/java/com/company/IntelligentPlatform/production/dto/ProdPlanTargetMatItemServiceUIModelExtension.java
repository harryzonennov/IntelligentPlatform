package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.service.ProdPlanTargetItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;

@Service
public class ProdPlanTargetMatItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected ProdPlanTargetItemManager prodPlanTargetItemManager;
	
	@Autowired
	protected ProdPlanTarSubItemServiceUIModelExtension prodPlanTarSubItemServiceUIModelExtension;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(prodPlanTarSubItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPlanTargetMatItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPlanTargetMatItemExtensionUnion
				.setNodeInstId(ProdPlanTargetMatItem.NODENAME);
		prodPlanTargetMatItemExtensionUnion
				.setNodeName(ProdPlanTargetMatItem.NODENAME);

		// UI Model Configure of node:[ProdPlanTargetMatItem]
		UIModelNodeMapConfigure prodPlanTargetMatItemMap = new UIModelNodeMapConfigure();
		prodPlanTargetMatItemMap.setSeName(ProdPlanTargetMatItem.SENAME);
		prodPlanTargetMatItemMap.setNodeName(ProdPlanTargetMatItem.NODENAME);
		prodPlanTargetMatItemMap
				.setNodeInstID(ProdPlanTargetMatItem.NODENAME);
		prodPlanTargetMatItemMap.setHostNodeFlag(true);
		prodPlanTargetMatItemMap.setLogicManager(prodPlanTargetItemManager);
		Class<?>[] prodPlanTargetMatItemConvToUIParas = {
				ProdPlanTargetMatItem.class,
				ProdPlanTargetMatItemUIModel.class };
		prodPlanTargetMatItemMap
				.setConvToUIMethodParas(prodPlanTargetMatItemConvToUIParas);
		prodPlanTargetMatItemMap
				.setConvToUIMethod(ProdPlanTargetItemManager.METHOD_ConvProdPlanTargetMatItemToUI);
		Class<?>[] ProdPlanTargetMatItemConvUIToParas = {
				ProdPlanTargetMatItemUIModel.class,
				ProdPlanTargetMatItem.class };
		prodPlanTargetMatItemMap
				.setConvUIToMethodParas(ProdPlanTargetMatItemConvUIToParas);
		prodPlanTargetMatItemMap
				.setConvUIToMethod(ProdPlanTargetItemManager.METHOD_ConvUIToProdPlanTargetMatItem);
		uiModelNodeMapList.add(prodPlanTargetMatItemMap);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setBaseNodeInstID(ProdPlanTargetMatItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] productionOrderConvToUIParas = {
				ProductionOrder.class, ProdPlanTargetMatItemUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProdPlanTargetItemManager.METHOD_ConvProductionPlanToTargetItemUI);
		uiModelNodeMapList.add(productionOrderMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdPlanTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdPlanTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdPlanTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdPlanTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdPlanTargetMatItem.NODENAME));

		prodPlanTargetMatItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPlanTargetMatItemExtensionUnion);
		return resultList;
	}

}
