package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProdPlanTargetItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTarSubItem;

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
public class ProdPlanTarSubItemServiceUIModelExtension extends
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
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPlanTarSubItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPlanTarSubItemExtensionUnion
				.setNodeInstId(ProdPlanTarSubItem.NODENAME);
		prodPlanTarSubItemExtensionUnion
				.setNodeName(ProdPlanTarSubItem.NODENAME);

		// UI Model Configure of node:[ProdPlanTargetMatItem]
		UIModelNodeMapConfigure prodPlanTarSubItemMap = new UIModelNodeMapConfigure();
		prodPlanTarSubItemMap.setSeName(ProdPlanTarSubItem.SENAME);
		prodPlanTarSubItemMap.setNodeName(ProdPlanTarSubItem.NODENAME);
		prodPlanTarSubItemMap
				.setNodeInstID(ProdPlanTarSubItem.NODENAME);
		prodPlanTarSubItemMap.setHostNodeFlag(true);
		prodPlanTarSubItemMap.setLogicManager(prodPlanTargetItemManager);
		Class<?>[] prodPlanTarSubItemConvToUIParas = {
				ProdPlanTarSubItem.class,
				ProdPlanTarSubItemUIModel.class };
		prodPlanTarSubItemMap
				.setConvToUIMethodParas(prodPlanTarSubItemConvToUIParas);
		prodPlanTarSubItemMap
				.setConvToUIMethod(ProdPlanTargetItemManager.METHOD_ConvProdPlanTarSubItemToUI);
		Class<?>[] ProdPlanTarSubItemConvUIToParas = {
				ProdPlanTarSubItemUIModel.class,
				ProdPlanTarSubItem.class };
		prodPlanTarSubItemMap
				.setConvUIToMethodParas(ProdPlanTarSubItemConvUIToParas);
		prodPlanTarSubItemMap
				.setConvUIToMethod(ProdPlanTargetItemManager.METHOD_ConvUIToProdPlanTarSubItem);
		uiModelNodeMapList.add(prodPlanTarSubItemMap);

		// UI Model Configure of node:[billOfMaterialItem]
		UIModelNodeMapConfigure billOfMaterialItemMap = new UIModelNodeMapConfigure();
		billOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		billOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setNodeInstID(BillOfMaterialItem.NODENAME);
		billOfMaterialItemMap.setBaseNodeInstID(ProdPlanTarSubItem.NODENAME);
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
				BillOfMaterialItem.class, ProdPlanTarSubItemUIModel.class };
		billOfMaterialItemMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		billOfMaterialItemMap
				.setConvToUIMethod(ProdPlanTargetItemManager.METHOD_ConvBillOfOrderItemToUI);
		uiModelNodeMapList.add(billOfMaterialItemMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdPlanTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdPlanTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdPlanTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdPlanTarSubItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdPlanTarSubItem.NODENAME));

		prodPlanTarSubItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPlanTarSubItemExtensionUnion);
		return resultList;
	}

}
