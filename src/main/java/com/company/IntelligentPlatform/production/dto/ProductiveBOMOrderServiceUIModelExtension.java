package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProductiveBOMOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProductiveBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.model.ProductiveBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;

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
public class ProductiveBOMOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProductiveBOMItemServiceUIModelExtension productiveBOMItemServiceUIModelExtension;

	@Autowired
	protected ProductiveBOMOrderManager productiveBOMOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(productiveBOMItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productiveBOMOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		productiveBOMOrderExtensionUnion
				.setNodeInstId(ProductiveBOMOrder.SENAME);
		productiveBOMOrderExtensionUnion
				.setNodeName(ProductiveBOMOrder.NODENAME);

		// UI Model Configure of node:[ProductiveBOMOrder]
		UIModelNodeMapConfigure productiveBOMOrderMap = new UIModelNodeMapConfigure();
		productiveBOMOrderMap.setSeName(ProductiveBOMOrder.SENAME);
		productiveBOMOrderMap.setNodeName(ProductiveBOMOrder.NODENAME);
		productiveBOMOrderMap.setNodeInstID(ProductiveBOMOrder.SENAME);
		productiveBOMOrderMap.setHostNodeFlag(true);
		Class<?>[] productiveBOMOrderConvToUIParas = {
				ProductiveBOMOrder.class, ProductiveBOMOrderUIModel.class };
		productiveBOMOrderMap
				.setConvToUIMethodParas(productiveBOMOrderConvToUIParas);
		productiveBOMOrderMap
				.setConvToUIMethod(ProductiveBOMOrderManager.METHOD_ConvProductiveBOMOrderToUI);
		Class<?>[] ProductiveBOMOrderConvUIToParas = {
				ProductiveBOMOrderUIModel.class, ProductiveBOMOrder.class };
		productiveBOMOrderMap
				.setConvUIToMethodParas(ProductiveBOMOrderConvUIToParas);
		productiveBOMOrderMap
				.setConvUIToMethod(ProductiveBOMOrderManager.METHOD_ConvUIToProductiveBOMOrder);
		uiModelNodeMapList.add(productiveBOMOrderMap);

		// UI Model Configure of node:[TargetMaterial]
		UIModelNodeMapConfigure targetMaterialMap = new UIModelNodeMapConfigure();
		targetMaterialMap.setSeName(MaterialStockKeepUnit.SENAME);
		targetMaterialMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		targetMaterialMap.setNodeInstID("TargetMaterial");
		targetMaterialMap.setBaseNodeInstID(ProductiveBOMOrder.SENAME);
		targetMaterialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		targetMaterialMap.setServiceEntityManager(materialStockKeepUnitManager);

		List<SearchConfigConnectCondition> refMaterialConditionList = new ArrayList<>();
		SearchConfigConnectCondition refMaterialCondition0 = new SearchConfigConnectCondition();
		refMaterialCondition0.setSourceFieldName("refMaterialSKUUUID");
		refMaterialCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		refMaterialConditionList.add(refMaterialCondition0);
		targetMaterialMap.setConnectionConditions(refMaterialConditionList);

		Class<?>[] targetMaterialConvToUIParas = { MaterialStockKeepUnit.class,
				ProductiveBOMOrderUIModel.class };
		targetMaterialMap.setConvToUIMethodParas(targetMaterialConvToUIParas);
		targetMaterialMap
				.setConvToUIMethod(ProductiveBOMOrderManager.METHOD_ConvMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(targetMaterialMap);
		productiveBOMOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);

		// UI Model Configure of node:[Prod Process order]
		UIModelNodeMapConfigure processRouteOrderMap = new UIModelNodeMapConfigure();
		processRouteOrderMap.setSeName(ProcessRouteOrder.SENAME);
		processRouteOrderMap.setNodeName(ProcessRouteOrder.NODENAME);
		processRouteOrderMap.setNodeInstID(ProcessRouteOrder.SENAME);
		processRouteOrderMap.setBaseNodeInstID(ProductiveBOMOrder.SENAME);
		processRouteOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processRouteOrderMap.setServiceEntityManager(processRouteOrderManager);
		List<SearchConfigConnectCondition> processRouteConditionList = new ArrayList<>();
		SearchConfigConnectCondition processRouteCondition0 = new SearchConfigConnectCondition();
		processRouteCondition0.setSourceFieldName("refRouteOrderUUID");
		processRouteCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		processRouteConditionList.add(processRouteCondition0);
		processRouteOrderMap.setConnectionConditions(processRouteConditionList);
		Class<?>[] processRouteOrderConvToUIParas = { ProcessRouteOrder.class,
				ProductiveBOMOrderUIModel.class };
		processRouteOrderMap
				.setConvToUIMethodParas(processRouteOrderConvToUIParas);
		processRouteOrderMap
				.setConvToUIMethod(ProductiveBOMOrderManager.METHOD_ConvProcessRouteOrderToUI);
		uiModelNodeMapList.add(processRouteOrderMap);
		productiveBOMOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);

		resultList.add(productiveBOMOrderExtensionUnion);
		return resultList;
	}

}
