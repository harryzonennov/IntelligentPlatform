package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdPickingRefOrderItemUIModel;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ProdPickingRefOrderItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(prodPickingRefMaterialItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPickingRefOrderItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPickingRefOrderItemExtensionUnion
				.setNodeInstId(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemExtensionUnion
				.setNodeName(ProdPickingRefOrderItem.NODENAME);

		// UI Model Configure of node:[ProdPickingRefOrderItem]
		UIModelNodeMapConfigure prodPickingRefOrderItemMap = new UIModelNodeMapConfigure();
		prodPickingRefOrderItemMap.setSeName(ProdPickingRefOrderItem.SENAME);
		prodPickingRefOrderItemMap
				.setNodeName(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemMap
				.setNodeInstID(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemMap.setHostNodeFlag(true);
		Class<?>[] prodPickingRefOrderItemConvToUIParas = {
				ProdPickingRefOrderItem.class,
				ProdPickingRefOrderItemUIModel.class };
		prodPickingRefOrderItemMap
				.setConvToUIMethodParas(prodPickingRefOrderItemConvToUIParas);
		prodPickingRefOrderItemMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvProdPickingRefOrderItemToUI);
		Class<?>[] ProdPickingRefOrderItemConvUIToParas = {
				ProdPickingRefOrderItemUIModel.class,
				ProdPickingRefOrderItem.class };
		prodPickingRefOrderItemMap
				.setConvUIToMethodParas(ProdPickingRefOrderItemConvUIToParas);
		prodPickingRefOrderItemMap
				.setConvUIToMethod(ProdPickingOrderManager.METHOD_ConvUIToProdPickingRefOrderItem);
		uiModelNodeMapList.add(prodPickingRefOrderItemMap);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productionOrderMap.setServiceEntityManager(productionOrderManager);
		List<SearchConfigConnectCondition> productionOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition productionOrderCondition0 = new SearchConfigConnectCondition();
		productionOrderCondition0.setSourceFieldName("refProdOrderUUID");
		productionOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		productionOrderConditionList.add(productionOrderCondition0);
		productionOrderMap
				.setConnectionConditions(productionOrderConditionList);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProdPickingRefOrderItemUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvProductionOrderToUI);
		uiModelNodeMapList.add(productionOrderMap);

		// UI Model Configure of node:[OrderMaterialStockKeepUnit]
		UIModelNodeMapConfigure orderMaterialStockKeepUnitMap = new UIModelNodeMapConfigure();
		orderMaterialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		orderMaterialStockKeepUnitMap
				.setNodeName(MaterialStockKeepUnit.NODENAME);
		orderMaterialStockKeepUnitMap
				.setNodeInstID("OrderMaterialStockKeepUnit");
		orderMaterialStockKeepUnitMap
				.setBaseNodeInstID(ProductionOrder.SENAME);
		orderMaterialStockKeepUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		orderMaterialStockKeepUnitMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> orderMaterialStockKeepUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition orderMaterialStockKeepUnitCondition0 = new SearchConfigConnectCondition();
		orderMaterialStockKeepUnitCondition0
				.setSourceFieldName("refMaterialSKUUUID");
		orderMaterialStockKeepUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		orderMaterialStockKeepUnitConditionList
				.add(orderMaterialStockKeepUnitCondition0);
		orderMaterialStockKeepUnitMap
				.setConnectionConditions(orderMaterialStockKeepUnitConditionList);
		Class<?>[] orderMaterialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class,
				ProdPickingRefOrderItemUIModel.class };
		orderMaterialStockKeepUnitMap
				.setConvToUIMethodParas(orderMaterialStockKeepUnitConvToUIParas);
		orderMaterialStockKeepUnitMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvOrderMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(orderMaterialStockKeepUnitMap);
		prodPickingRefOrderItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPickingRefOrderItemExtensionUnion);
		return resultList;
	}

}
