package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.QualityInspectPropertyItemManager;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItem;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitUIModel;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class QualityInspectPropertyItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected QualityInspectPropertyItemManager qualityInspectPropertyItemManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder qualityInspectPropertyItemBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(QualityInspectPropertyItem.class,
					QualityInspectPropertyItemUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder
							.convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectPropertyItemToUI)
							.convUIToMethod(QualityInspectPropertyItemManager.METHOD_ConvUIToQualityInspectPropertyItem).logicManager(qualityInspectPropertyItemManager));
			qualityInspectPropertyItemBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(QualityInspectMatItem.class,QualityInspectPropertyItemUIModel.class)
					.toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
			qualityInspectPropertyItemBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(QualityInspectOrder.class,QualityInspectPropertyItemUIModel.class)
							.logicManager(qualityInspectPropertyItemManager)
					.toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD).
					convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectOrderToPropertyUI).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
			qualityInspectPropertyItemBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(RegisteredProduct.class,QualityInspectPropertyItemUIModel.class)
					.logicManager(qualityInspectPropertyItemManager).serviceEntityManager(registeredProductManager).baseNodeInstId(QualityInspectMatItem.NODENAME)
					.addConnectionCondition("refMaterialSKUUUID").
					convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRefRegisteredProductToPropertyItemUI).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
			qualityInspectPropertyItemBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(RegisteredProductExtendProperty.class,QualityInspectPropertyItemUIModel.class)
					.logicManager(qualityInspectPropertyItemManager).serviceEntityManager(registeredProductManager)
					.addConnectionCondition("refPropertyUUID").baseNodeInstId(QualityInspectPropertyItem.NODENAME)
					.convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRegisteredProductPropertyItemToUI).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
			resultList.add(qualityInspectPropertyItemBuilder.build());
		} catch (ServiceEntityConfigureException ex) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion qualityInspectPropertyItemExtensionUnion = new ServiceUIModelExtensionUnion();
		qualityInspectPropertyItemExtensionUnion
				.setNodeInstId(QualityInspectPropertyItem.NODENAME);
		qualityInspectPropertyItemExtensionUnion
				.setNodeName(QualityInspectPropertyItem.NODENAME);

		// UI Model Configure of node:[QualityInspectPropertyItem]
		UIModelNodeMapConfigure qualityInspectPropertyItemMap = new UIModelNodeMapConfigure();
		qualityInspectPropertyItemMap
				.setSeName(QualityInspectPropertyItem.SENAME);
		qualityInspectPropertyItemMap
				.setNodeName(QualityInspectPropertyItem.NODENAME);
		qualityInspectPropertyItemMap
				.setNodeInstID(QualityInspectPropertyItem.NODENAME);
		qualityInspectPropertyItemMap.setHostNodeFlag(true);
		Class<?>[] qualityInspectPropertyItemConvToUIParas = {
				QualityInspectPropertyItem.class,
				QualityInspectPropertyItemUIModel.class };
		qualityInspectPropertyItemMap
				.setConvToUIMethodParas(qualityInspectPropertyItemConvToUIParas);
		qualityInspectPropertyItemMap
				.setConvToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectPropertyItemToUI);
		qualityInspectPropertyItemMap.setLogicManager(qualityInspectPropertyItemManager);
		Class<?>[] QualityInspectPropertyItemConvUIToParas = {
				QualityInspectPropertyItemUIModel.class,
				QualityInspectPropertyItem.class };
		qualityInspectPropertyItemMap
				.setConvUIToMethodParas(QualityInspectPropertyItemConvUIToParas);
		qualityInspectPropertyItemMap
				.setConvUIToMethod(QualityInspectPropertyItemManager.METHOD_ConvUIToQualityInspectPropertyItem);
		uiModelNodeMapList.add(qualityInspectPropertyItemMap);

		// UI Model Configure of node:[QualityInspectMatItem]
		UIModelNodeMapConfigure qualityInspectMatItemMap = new UIModelNodeMapConfigure();
		qualityInspectMatItemMap.setSeName(QualityInspectMatItem.SENAME);
		qualityInspectMatItemMap.setNodeName(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemMap.setNodeInstID(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemMap.setHostNodeFlag(false);
		qualityInspectMatItemMap
				.setBaseNodeInstID(QualityInspectPropertyItem.NODENAME);
		qualityInspectMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);

		uiModelNodeMapList.add(qualityInspectMatItemMap);

		// UI Model Configure of node:[QualityInspectMatItem]
		UIModelNodeMapConfigure qualityInspectOrderMap = new UIModelNodeMapConfigure();
		qualityInspectOrderMap.setSeName(QualityInspectOrder.SENAME);
		qualityInspectOrderMap.setNodeName(QualityInspectOrder.NODENAME);
		qualityInspectOrderMap.setNodeInstID(QualityInspectOrder.NODENAME);
		qualityInspectOrderMap.setHostNodeFlag(false);
		qualityInspectOrderMap
				.setBaseNodeInstID(QualityInspectPropertyItem.NODENAME);
		qualityInspectOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] qualityInspectPropertyOrderConvToUIParas = {
				QualityInspectOrder.class,
				QualityInspectPropertyItemUIModel.class };
		qualityInspectOrderMap
				.setConvToUIMethodParas(qualityInspectPropertyOrderConvToUIParas);
		qualityInspectOrderMap.setLogicManager(qualityInspectPropertyItemManager);
		qualityInspectOrderMap
				.setConvToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectOrderToPropertyUI);
		uiModelNodeMapList.add(qualityInspectOrderMap);

		// UI Model Configure of node:[RegisteredProduct]
		UIModelNodeMapConfigure refRegisteredProductMap = new UIModelNodeMapConfigure();
		refRegisteredProductMap.setSeName(RegisteredProduct.SENAME);
		refRegisteredProductMap.setNodeName(RegisteredProduct.NODENAME);
		refRegisteredProductMap.setNodeInstID(RegisteredProduct.SENAME);
		refRegisteredProductMap
				.setBaseNodeInstID(QualityInspectMatItem.NODENAME);
		refRegisteredProductMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		refRegisteredProductMap
				.setServiceEntityManager(registeredProductManager);
		List<SearchConfigConnectCondition> refRegisteredProductConditionList = new ArrayList<>();
		SearchConfigConnectCondition refRegisteredProductCondition0 = new SearchConfigConnectCondition();
		refRegisteredProductCondition0.setSourceFieldName("refMaterialSKUUUID");
		refRegisteredProductCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		refRegisteredProductConditionList.add(refRegisteredProductCondition0);
		refRegisteredProductMap
				.setConnectionConditions(refRegisteredProductConditionList);
		Class<?>[] refRegisteredProductConvToUIParas = {
				RegisteredProduct.class,
				QualityInspectPropertyItemUIModel.class };
		refRegisteredProductMap
				.setConvToUIMethodParas(refRegisteredProductConvToUIParas);
		refRegisteredProductMap.setLogicManager(qualityInspectPropertyItemManager);
		refRegisteredProductMap
				.setConvToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRefRegisteredProductToPropertyItemUI);
		uiModelNodeMapList.add(refRegisteredProductMap);

		// UI Model Configure of node:[RegisteredProductProperty]
		UIModelNodeMapConfigure registeredProductExtendPropertyMap = new UIModelNodeMapConfigure();
		registeredProductExtendPropertyMap
				.setSeName(RegisteredProductExtendProperty.SENAME);
		registeredProductExtendPropertyMap
				.setNodeName(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyMap
				.setNodeInstID(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyMap
				.setBaseNodeInstID(QualityInspectPropertyItem.NODENAME);
		registeredProductExtendPropertyMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		registeredProductExtendPropertyMap
				.setServiceEntityManager(registeredProductManager);
		List<SearchConfigConnectCondition> registeredProductExtendPropertyConditionList = new ArrayList<>();
		SearchConfigConnectCondition registeredProductExtendPropertyCondition0 = new SearchConfigConnectCondition();
		registeredProductExtendPropertyCondition0
				.setSourceFieldName("refPropertyUUID");
		registeredProductExtendPropertyCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		registeredProductExtendPropertyConditionList
				.add(registeredProductExtendPropertyCondition0);
		registeredProductExtendPropertyMap
				.setConnectionConditions(registeredProductExtendPropertyConditionList);
		Class<?>[] registeredProductExtendPropertyConvToUIParas = {
				RegisteredProductExtendProperty.class,
				QualityInspectPropertyItemUIModel.class };
		registeredProductExtendPropertyMap
				.setConvToUIMethodParas(registeredProductExtendPropertyConvToUIParas);
		registeredProductExtendPropertyMap.setLogicManager(qualityInspectPropertyItemManager);
		registeredProductExtendPropertyMap
				.setConvToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRegisteredProductPropertyItemToUI);
		uiModelNodeMapList.add(registeredProductExtendPropertyMap);

		qualityInspectPropertyItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(qualityInspectPropertyItemExtensionUnion);

		return resultList;
	}

}
