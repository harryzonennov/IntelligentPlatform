package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.StandardMaterialUnitManager;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class StandardMaterialUnitServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion standardMaterialUnitExtensionUnion = new ServiceUIModelExtensionUnion();
		standardMaterialUnitExtensionUnion
				.setNodeInstId(StandardMaterialUnit.SENAME);
		standardMaterialUnitExtensionUnion
				.setNodeName(StandardMaterialUnit.NODENAME);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap.setNodeInstID(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setHostNodeFlag(true);
		Class<?>[] standardMaterialUnitConvToUIParas = {
				StandardMaterialUnit.class, StandardMaterialUnitUIModel.class };
		standardMaterialUnitMap
				.setConvToUIMethodParas(standardMaterialUnitConvToUIParas);
		standardMaterialUnitMap
				.setConvToUIMethod(StandardMaterialUnitManager.METHOD_ConvStandardMaterialUnitToUI);
		Class<?>[] StandardMaterialUnitConvUIToParas = {
				StandardMaterialUnitUIModel.class, StandardMaterialUnit.class };
		standardMaterialUnitMap
				.setConvUIToMethodParas(StandardMaterialUnitConvUIToParas);
		standardMaterialUnitMap
				.setConvUIToMethod(StandardMaterialUnitManager.METHOD_ConvUIToStandardMaterialUnit);
		uiModelNodeMapList.add(standardMaterialUnitMap);

		// UI Model Configure of node:[RefMaterialUnit]
		UIModelNodeMapConfigure refMaterialUnitMap = new UIModelNodeMapConfigure();
		refMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		refMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		refMaterialUnitMap.setNodeInstID("RefMaterialUnit");
		refMaterialUnitMap.setBaseNodeInstID(StandardMaterialUnit.SENAME);
		refMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		refMaterialUnitMap.setServiceEntityManager(standardMaterialUnitManager);
		List<SearchConfigConnectCondition> refMaterialUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition refMaterialUnitCondition0 = new SearchConfigConnectCondition();
		refMaterialUnitCondition0.setSourceFieldName("referUnitUUID");
		refMaterialUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		refMaterialUnitConditionList.add(refMaterialUnitCondition0);
		refMaterialUnitMap
				.setConnectionConditions(refMaterialUnitConditionList);
		Class<?>[] refMaterialUnitConvToUIParas = { StandardMaterialUnit.class,
				StandardMaterialUnitUIModel.class };
		refMaterialUnitMap.setConvToUIMethodParas(refMaterialUnitConvToUIParas);
		refMaterialUnitMap
				.setConvToUIMethod(StandardMaterialUnitManager.METHOD_ConvRefMaterialUnitToUI);
		uiModelNodeMapList.add(refMaterialUnitMap);
		standardMaterialUnitExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(standardMaterialUnitExtensionUnion);
		return resultList;
	}

}
