package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.RegisteredProductExtendPropertyManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class RegisteredProductExtendPropertyServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected RegisteredProductExtendPropertyManager registeredProductExtendPropertyManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				RegisteredProductExtendPropertyAttachment.SENAME,
				RegisteredProductExtendPropertyAttachment.NODENAME,
				RegisteredProductExtendPropertyAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion registeredProductExtendPropertyExtensionUnion = new ServiceUIModelExtensionUnion();
		registeredProductExtendPropertyExtensionUnion
				.setNodeInstId(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyExtensionUnion
				.setNodeName(RegisteredProductExtendProperty.NODENAME);

		// UI Model Configure of node:[RegisteredProductExtendProperty]
		UIModelNodeMapConfigure registeredProductExtendPropertyMap = new UIModelNodeMapConfigure();
		registeredProductExtendPropertyMap
				.setSeName(RegisteredProductExtendProperty.SENAME);
		registeredProductExtendPropertyMap
				.setNodeName(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyMap
				.setNodeInstID(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyMap.setHostNodeFlag(true);
		Class<?>[] registeredProductExtendPropertyConvToUIParas = {
				RegisteredProductExtendProperty.class,
				RegisteredProductExtendPropertyUIModel.class };
		registeredProductExtendPropertyMap
				.setConvToUIMethodParas(registeredProductExtendPropertyConvToUIParas);
		registeredProductExtendPropertyMap.setLogicManager(registeredProductExtendPropertyManager);
		registeredProductExtendPropertyMap
				.setConvToUIMethod(RegisteredProductExtendPropertyManager.METHOD_ConvRegisteredProductExtendPropertyToUI);
		Class<?>[] RegisteredProductExtendPropertyConvUIToParas = {
				RegisteredProductExtendPropertyUIModel.class,
				RegisteredProductExtendProperty.class };
		registeredProductExtendPropertyMap
				.setConvUIToMethodParas(RegisteredProductExtendPropertyConvUIToParas);
		registeredProductExtendPropertyMap
				.setConvUIToMethod(RegisteredProductExtendPropertyManager.METHOD_ConvUIToRegisteredProductExtendProperty);
		uiModelNodeMapList.add(registeredProductExtendPropertyMap);

		// UI Model Configure of node:[RegisteredProduct]
		UIModelNodeMapConfigure registeredProductMap = new UIModelNodeMapConfigure();
		registeredProductMap.setSeName(RegisteredProduct.SENAME);
		registeredProductMap.setNodeName(RegisteredProduct.NODENAME);
		registeredProductMap.setNodeInstID(RegisteredProduct.SENAME);
		registeredProductMap.setHostNodeFlag(false);
		registeredProductMap.setLogicManager(registeredProductExtendPropertyManager);
		registeredProductMap
				.setBaseNodeInstID(RegisteredProductExtendProperty.NODENAME);
		registeredProductMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] registeredProductConvToUIParas = { RegisteredProduct.class,
				RegisteredProductExtendPropertyUIModel.class };
		registeredProductMap
				.setConvToUIMethodParas(registeredProductConvToUIParas);
		registeredProductMap
				.setConvToUIMethod(RegisteredProductExtendPropertyManager.METHOD_ConvRegisteredProductToPropertyUI);

		uiModelNodeMapList.add(registeredProductMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure refMaterialSKUMap = new UIModelNodeMapConfigure();
		refMaterialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		refMaterialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		refMaterialSKUMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		refMaterialSKUMap.setHostNodeFlag(false);
		refMaterialSKUMap
				.setBaseNodeInstID(RegisteredProduct.SENAME);
		refMaterialSKUMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		List<SearchConfigConnectCondition> materialSKUConditions = new ArrayList<>();
		SearchConfigConnectCondition materialSKUConditionCondition0 = new SearchConfigConnectCondition();
		materialSKUConditionCondition0.setSourceFieldName("refMaterialSKUUUID");
		materialSKUConditionCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialSKUConditions.add(materialSKUConditionCondition0);
		refMaterialSKUMap.setConnectionConditions(materialSKUConditions);
		Class<?>[] registeredMaterialSKUParas = { MaterialStockKeepUnit.class,
				RegisteredProductExtendPropertyUIModel.class };
		refMaterialSKUMap.setLogicManager(registeredProductExtendPropertyManager);
		refMaterialSKUMap
				.setConvToUIMethodParas(registeredMaterialSKUParas);
		refMaterialSKUMap
				.setConvToUIMethod(RegisteredProductExtendPropertyManager.METHOD_ConvRefMaterialSKUToPropertyUI);

		uiModelNodeMapList.add(refMaterialSKUMap);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap
				.setNodeInstID(RegisteredProductExtendProperty.NODENAME);
		standardMaterialUnitMap
				.setBaseNodeInstID(RegisteredProductExtendProperty.NODENAME);
		standardMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);

		List<SearchConfigConnectCondition> standardUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition standardUnitCondition0 = new SearchConfigConnectCondition();
		standardUnitCondition0.setSourceFieldName("refUnitUUID");
		standardUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		standardUnitConditionList.add(standardUnitCondition0);
		standardMaterialUnitMap
				.setConnectionConditions(standardUnitConditionList);

		Class<?>[] standardMaterialUnitConvToUIParas = {
				StandardMaterialUnit.class,
				RegisteredProductExtendPropertyUIModel.class };
		standardMaterialUnitMap.setLogicManager(registeredProductExtendPropertyManager);
		standardMaterialUnitMap
				.setConvToUIMethodParas(standardMaterialUnitConvToUIParas);
		standardMaterialUnitMap
				.setConvToUIMethod(RegisteredProductExtendPropertyManager.METHOD_ConvStandardMaterialUnitToPropertyUI);
		uiModelNodeMapList.add(standardMaterialUnitMap);

		registeredProductExtendPropertyExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(registeredProductExtendPropertyExtensionUnion);
		return resultList;
	}

}
