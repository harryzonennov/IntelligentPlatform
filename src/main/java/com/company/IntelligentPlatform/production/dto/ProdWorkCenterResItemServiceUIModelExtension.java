package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProductionResourceUnionManager;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class ProdWorkCenterResItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProductionResourceUnionManager productionResourceUnionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodWorkCenterResItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodWorkCenterResItemExtensionUnion
				.setNodeInstId(ProdWorkCenterResItem.NODENAME);
		prodWorkCenterResItemExtensionUnion
				.setNodeName(ProdWorkCenterResItem.NODENAME);

		// UI Model Configure of node:[ProdWorkCenterResItem]
		UIModelNodeMapConfigure prodWorkCenterResItemMap = new UIModelNodeMapConfigure();
		prodWorkCenterResItemMap.setSeName(ProdWorkCenterResItem.SENAME);
		prodWorkCenterResItemMap.setNodeName(ProdWorkCenterResItem.NODENAME);
		prodWorkCenterResItemMap.setNodeInstID(ProdWorkCenterResItem.NODENAME);
		prodWorkCenterResItemMap.setHostNodeFlag(true);
		Class<?>[] prodWorkCenterResItemConvToUIParas = {
				ProdWorkCenterResItem.class, ProdWorkCenterResItemUIModel.class };
		prodWorkCenterResItemMap
				.setConvToUIMethodParas(prodWorkCenterResItemConvToUIParas);
		prodWorkCenterResItemMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvProdWorkCenterResItemToUI);
		Class<?>[] ProdWorkCenterResItemConvUIToParas = {
				ProdWorkCenterResItemUIModel.class, ProdWorkCenterResItem.class };
		prodWorkCenterResItemMap
				.setConvUIToMethodParas(ProdWorkCenterResItemConvUIToParas);
		prodWorkCenterResItemMap
				.setConvUIToMethod(ProdWorkCenterManager.METHOD_ConvUIToProdWorkCenterResItem);
		uiModelNodeMapList.add(prodWorkCenterResItemMap);
		prodWorkCenterResItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodWorkCenterResItemExtensionUnion);

		// UI Model Configure of node:[ProductionResourceUnion]
		UIModelNodeMapConfigure productionResourceUnionMap = new UIModelNodeMapConfigure();
		productionResourceUnionMap.setSeName(ProductionResourceUnion.SENAME);
		productionResourceUnionMap
				.setNodeName(ProductionResourceUnion.NODENAME);
		productionResourceUnionMap
				.setNodeInstID(ProductionResourceUnion.SENAME);
		productionResourceUnionMap
				.setBaseNodeInstID(ProdWorkCenterResItem.NODENAME);
		productionResourceUnionMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		productionResourceUnionMap
				.setServiceEntityManager(productionResourceUnionManager);

		Class<?>[] productionResourceUnionConvToUIParas = {
				ProductionResourceUnion.class,
				ProdWorkCenterResItemUIModel.class };
		productionResourceUnionMap
				.setConvToUIMethodParas(productionResourceUnionConvToUIParas);
		productionResourceUnionMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvProductionResourceUnionToItemUI);
		uiModelNodeMapList.add(productionResourceUnionMap);

		// UI Model Configure of node:[ProductionResourceUnion]
		UIModelNodeMapConfigure parentWorkCenterMap = new UIModelNodeMapConfigure();
		parentWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		parentWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		parentWorkCenterMap.setNodeInstID(ProductionResourceUnion.SENAME);
		parentWorkCenterMap.setBaseNodeInstID(ProdWorkCenterResItem.NODENAME);
		parentWorkCenterMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);

		Class<?>[] parentWorkCenterToUIParas = { ProdWorkCenter.class,
				ProdWorkCenterResItemUIModel.class };
		parentWorkCenterMap.setConvToUIMethodParas(parentWorkCenterToUIParas);
		parentWorkCenterMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvParentWorkCenterToItemUI);
		uiModelNodeMapList.add(parentWorkCenterMap);

		return resultList;
	}

}
