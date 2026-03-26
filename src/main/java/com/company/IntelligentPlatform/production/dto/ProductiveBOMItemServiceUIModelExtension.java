package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ProductiveBOMItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected ProductiveBOMOrderManager productiveBOMOrderManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProductiveBOMItemManager productiveBOMItemManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(this);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productiveBOMItemExtensionUnion = new ServiceUIModelExtensionUnion();
		productiveBOMItemExtensionUnion
				.setNodeInstId(ProductiveBOMItem.NODENAME);
		productiveBOMItemExtensionUnion
				.setNodeName(ProductiveBOMItem.NODENAME);

		// UI Model Configure of node:[ProductiveBOMItem]
		UIModelNodeMapConfigure productiveBOMItemMap = new UIModelNodeMapConfigure();
		productiveBOMItemMap.setSeName(ProductiveBOMItem.SENAME);
		productiveBOMItemMap.setNodeName(ProductiveBOMItem.NODENAME);
		productiveBOMItemMap.setNodeInstID(ProductiveBOMItem.NODENAME);
		productiveBOMItemMap.setHostNodeFlag(true);
		Class<?>[] productiveBOMItemConvToUIParas = {
				ProductiveBOMItem.class, ProductiveBOMItemUIModel.class };
		productiveBOMItemMap
				.setConvToUIMethodParas(productiveBOMItemConvToUIParas);
		productiveBOMItemMap
				.setConvToUIMethod(ProductiveBOMItemManager.METHOD_ConvProductiveBOMItemToUI);
		productiveBOMItemMap.setLogicManager(productiveBOMItemManager);
		uiModelNodeMapList.add(productiveBOMItemMap);

		Class<?>[] itemMaterialSKUConvToUIParas = {
				MaterialStockKeepUnit.class, ProductiveBOMItemUIModel.class };

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						ProductiveBOMItem.NODENAME,
						ProductiveBOMItemManager.METHOD_ConvMaterialStockKeepUnitToUI,  productiveBOMItemManager,itemMaterialSKUConvToUIParas));

		// UI Model Configure of node:[ProcessRouteProcessItem]
		UIModelNodeMapConfigure processRouteProcessItemMap = new UIModelNodeMapConfigure();
		processRouteProcessItemMap.setSeName(ProcessRouteProcessItem.SENAME);
		processRouteProcessItemMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemMap
				.setBaseNodeInstID(ProductiveBOMItem.NODENAME);
		processRouteProcessItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processRouteProcessItemMap
				.setServiceEntityManager(processRouteOrderManager);
		List<SearchConfigConnectCondition> processRouteProcessItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition processRouteProcessItemCondition0 = new SearchConfigConnectCondition();
		processRouteProcessItemCondition0
				.setSourceFieldName("refRouteProcessItemUUID");
		processRouteProcessItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		processRouteProcessItemConditionList
				.add(processRouteProcessItemCondition0);
		processRouteProcessItemMap
				.setConnectionConditions(processRouteProcessItemConditionList);
		Class<?>[] processRouteProcessItemConvToUIParas = {
				ProcessRouteProcessItem.class, ProductiveBOMItemUIModel.class };
		processRouteProcessItemMap
				.setConvToUIMethodParas(processRouteProcessItemConvToUIParas);
		processRouteProcessItemMap.setLogicManager(productiveBOMItemManager);
		processRouteProcessItemMap
				.setConvToUIMethod(ProductiveBOMItemManager.METHOD_ConvProcessRouteProcessItemToUI);
		uiModelNodeMapList.add(processRouteProcessItemMap);

		// UI Model Configure of node:[ProdProcess]
		UIModelNodeMapConfigure prodProcessMap = new UIModelNodeMapConfigure();
		prodProcessMap.setSeName(ProdProcess.SENAME);
		prodProcessMap.setNodeName(ProdProcess.NODENAME);
		prodProcessMap.setNodeInstID(ProdProcess.SENAME);
		prodProcessMap.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		prodProcessMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		prodProcessMap.setServiceEntityManager(prodProcessManager);
		Class<?>[] prodProcessConvToUIParas = { ProdProcess.class,
				ProductiveBOMItemUIModel.class };
		prodProcessMap.setConvToUIMethodParas(prodProcessConvToUIParas);
		prodProcessMap
				.setConvToUIMethod(ProductiveBOMOrderManager.METHOD_ConvProdProcessToUI);
		uiModelNodeMapList.add(prodProcessMap);

		productiveBOMItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productiveBOMItemExtensionUnion);
		return resultList;
	}

}
