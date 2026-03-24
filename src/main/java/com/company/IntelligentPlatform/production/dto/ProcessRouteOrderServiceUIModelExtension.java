package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
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
public class ProcessRouteOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProcessRouteProcessItemServiceUIModelExtension processRouteProcessItemServiceUIModelExtension;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(processRouteProcessItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion processRouteOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		processRouteOrderExtensionUnion.setNodeInstId(ProcessRouteOrder.SENAME);
		processRouteOrderExtensionUnion.setNodeName(ProcessRouteOrder.SENAME);

		// UI Model Configure of node:[ProcessRouteOrder]
		UIModelNodeMapConfigure processRouteOrderMap = new UIModelNodeMapConfigure();
		processRouteOrderMap.setSeName(ProcessRouteOrder.SENAME);
		processRouteOrderMap.setNodeName(ProcessRouteOrder.NODENAME);
		processRouteOrderMap.setNodeInstID(ProcessRouteOrder.SENAME);
		processRouteOrderMap.setHostNodeFlag(true);
		Class<?>[] processRouteOrderConvToUIParas = { ProcessRouteOrder.class,
				ProcessRouteOrderUIModel.class };
		processRouteOrderMap
				.setConvToUIMethodParas(processRouteOrderConvToUIParas);
		processRouteOrderMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvProcessRouteOrderToUI);
		Class<?>[] ProcessRouteOrderConvUIToParas = {
				ProcessRouteOrderUIModel.class, ProcessRouteOrder.class };
		processRouteOrderMap
				.setConvUIToMethodParas(ProcessRouteOrderConvUIToParas);
		processRouteOrderMap
				.setConvUIToMethod(ProcessRouteOrderManager.METHOD_ConvUIToProcessRouteOrder);
		uiModelNodeMapList.add(processRouteOrderMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialStockKeepUnitMap = new UIModelNodeMapConfigure();
		materialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialStockKeepUnitMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setBaseNodeInstID(ProcessRouteOrder.SENAME);
		materialStockKeepUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialStockKeepUnitMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> materialStockKeepUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition materialStockKeepUnitCondition0 = new SearchConfigConnectCondition();
		materialStockKeepUnitCondition0
				.setSourceFieldName("refMaterialSKUUUID");
		materialStockKeepUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialStockKeepUnitConditionList.add(materialStockKeepUnitCondition0);
		materialStockKeepUnitMap
				.setConnectionConditions(materialStockKeepUnitConditionList);
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, ProcessRouteOrderUIModel.class };
		materialStockKeepUnitMap
				.setConvToUIMethodParas(materialStockKeepUnitConvToUIParas);
		materialStockKeepUnitMap
				.setConvToUIMethod(ProcessRouteOrderManager.METHOD_ConvMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(materialStockKeepUnitMap);
		processRouteOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(processRouteOrderExtensionUnion);
		return resultList;
	}

}
