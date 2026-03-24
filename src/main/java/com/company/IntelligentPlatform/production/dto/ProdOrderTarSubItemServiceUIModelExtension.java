package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProdOrderTargetMatItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ProdOrderTarSubItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodOrderTarSubItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderTarSubItemExtensionUnion
				.setNodeInstId(ProdOrderTarSubItem.NODENAME);
		prodOrderTarSubItemExtensionUnion
				.setNodeName(ProdOrderTarSubItem.NODENAME);

		// UI Model Configure of node:[ProdOrderTargetMatItem]
		UIModelNodeMapConfigure prodOrderTarSubItemMap = new UIModelNodeMapConfigure();
		prodOrderTarSubItemMap.setSeName(ProdOrderTarSubItem.SENAME);
		prodOrderTarSubItemMap.setNodeName(ProdOrderTarSubItem.NODENAME);
		prodOrderTarSubItemMap
				.setNodeInstID(ProdOrderTarSubItem.NODENAME);
		prodOrderTarSubItemMap.setHostNodeFlag(true);
		prodOrderTarSubItemMap.setLogicManager(prodOrderTargetMatItemManager);
		Class<?>[] prodOrderTarSubItemConvToUIParas = {
				ProdOrderTarSubItem.class,
				ProdOrderTarSubItemUIModel.class };
		prodOrderTarSubItemMap
				.setConvToUIMethodParas(prodOrderTarSubItemConvToUIParas);
		prodOrderTarSubItemMap
				.setConvToUIMethod(ProdOrderTargetMatItemManager.METHOD_ConvProdOrderTarSubItemToUI);
		Class<?>[] ProdOrderTarSubItemConvUIToParas = {
				ProdOrderTarSubItemUIModel.class,
				ProdOrderTarSubItem.class };
		prodOrderTarSubItemMap
				.setConvUIToMethodParas(ProdOrderTarSubItemConvUIToParas);
		prodOrderTarSubItemMap
				.setConvUIToMethod(ProdOrderTargetMatItemManager.METHOD_ConvUIToProdOrderTarSubItem);
		uiModelNodeMapList.add(prodOrderTarSubItemMap);

		// UI Model Configure of node:[billOfMaterialItem]
		UIModelNodeMapConfigure billOfMaterialItemMap = new UIModelNodeMapConfigure();
		billOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		billOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setNodeInstID(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setBaseNodeInstID(ProdOrderTarSubItem.NODENAME);
		billOfMaterialItemMap.setLogicManager(prodOrderTargetMatItemManager);
		billOfMaterialItemMap.setServiceEntityManager(billOfMaterialOrderManager);
		billOfMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		
		List<SearchConfigConnectCondition> billOfMaterialItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition billOfMaterialItemCondition0 = new SearchConfigConnectCondition();
		billOfMaterialItemCondition0.setSourceFieldName("refBOMItemUUID");
		billOfMaterialItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		billOfMaterialItemConditionList.add(billOfMaterialItemCondition0);
		billOfMaterialItemMap.setConnectionConditions(billOfMaterialItemConditionList);
		Class<?>[] productionOrderConvToUIParas = {
				BillOfMaterialItem.class, ProdOrderTarSubItemUIModel.class };
		billOfMaterialItemMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		billOfMaterialItemMap
				.setConvToUIMethod(ProdOrderTargetMatItemManager.METHOD_ConvBillOfOrderItemToUI);
		uiModelNodeMapList.add(billOfMaterialItemMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdOrderTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdOrderTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdOrderTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdOrderTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdOrderTarSubItem.NODENAME));

		prodOrderTarSubItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderTarSubItemExtensionUnion);
		return resultList;
	}

}
