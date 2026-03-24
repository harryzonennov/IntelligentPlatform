package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MaterialSKUExtendPropertyUIModel;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialSKUExtendProperty;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class MaterialSKUExtendPropertyServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialSKUExtendPropertyExtensionUnion = new ServiceUIModelExtensionUnion();
		materialSKUExtendPropertyExtensionUnion
				.setNodeInstId(MaterialSKUExtendProperty.NODENAME);
		materialSKUExtendPropertyExtensionUnion
				.setNodeName(MaterialSKUExtendProperty.NODENAME);

		// UI Model Configure of node:[MaterialSKUExtendProperty]
		UIModelNodeMapConfigure materialSKUExtendPropertyMap = new UIModelNodeMapConfigure();
		materialSKUExtendPropertyMap
				.setSeName(MaterialSKUExtendProperty.SENAME);
		materialSKUExtendPropertyMap
				.setNodeName(MaterialSKUExtendProperty.NODENAME);
		materialSKUExtendPropertyMap
				.setNodeInstID(MaterialSKUExtendProperty.NODENAME);
		materialSKUExtendPropertyMap.setHostNodeFlag(true);
		Class<?>[] materialSKUExtendPropertyConvToUIParas = {
				MaterialSKUExtendProperty.class,
				MaterialSKUExtendPropertyUIModel.class };
		materialSKUExtendPropertyMap
				.setConvToUIMethodParas(materialSKUExtendPropertyConvToUIParas);
		materialSKUExtendPropertyMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialSKUExtendPropertyToUI);
		Class<?>[] materialStockKeepUnitExtendPropertyConvUIToParas = {
				MaterialSKUExtendPropertyUIModel.class,
				MaterialSKUExtendProperty.class };
		materialSKUExtendPropertyMap
				.setConvUIToMethodParas(materialStockKeepUnitExtendPropertyConvUIToParas);
		materialSKUExtendPropertyMap
				.setConvUIToMethod(MaterialStockKeepUnitManager.METHOD_ConvUIToMaterialSKUExtendProperty);
		uiModelNodeMapList.add(materialSKUExtendPropertyMap);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap
				.setNodeInstID(MaterialSKUExtendProperty.NODENAME);
		standardMaterialUnitMap
				.setBaseNodeInstID(MaterialUnitReference.NODENAME);
		standardMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		
		List<SearchConfigConnectCondition> standardUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition standardUnitCondition0 = new SearchConfigConnectCondition();
		standardUnitCondition0.setSourceFieldName("refUnitUUID");
		standardUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		standardUnitConditionList.add(standardUnitCondition0);
		standardMaterialUnitMap.setConnectionConditions(standardUnitConditionList);

		Class<?>[] standardMaterialUnitConvToUIParas = {
				StandardMaterialUnit.class, MaterialSKUExtendPropertyUIModel.class };
		standardMaterialUnitMap
				.setConvToUIMethodParas(standardMaterialUnitConvToUIParas);
		standardMaterialUnitMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvStandardMaterialUnitToPropertyUI);
		uiModelNodeMapList.add(standardMaterialUnitMap);

		materialSKUExtendPropertyExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialSKUExtendPropertyExtensionUnion);
		return resultList;
	}

}
