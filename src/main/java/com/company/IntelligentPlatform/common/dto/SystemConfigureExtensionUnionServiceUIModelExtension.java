package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemConfigureExtensionUnionUIModel;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureExtensionUnionManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Service
public class SystemConfigureExtensionUnionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected SystemConfigureExtensionUnionManager systemConfigureExtensionUnionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureExtensionUnionExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureExtensionUnionExtensionUnion
				.setNodeInstId(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureExtensionUnionExtensionUnion
				.setNodeName(SystemConfigureExtensionUnion.NODENAME);

		// UI Model Configure of node:[SystemConfigureExtensionUnion]
		UIModelNodeMapConfigure systemConfigureExtensionUnionMap = new UIModelNodeMapConfigure();
		systemConfigureExtensionUnionMap
				.setSeName(SystemConfigureExtensionUnion.SENAME);
		systemConfigureExtensionUnionMap
				.setNodeName(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureExtensionUnionMap
				.setNodeInstID(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureExtensionUnionMap.setHostNodeFlag(true);
		Class<?>[] systemConfigureExtensionUnionConvToUIParas = {
				SystemConfigureExtensionUnion.class,
				SystemConfigureExtensionUnionUIModel.class };
		systemConfigureExtensionUnionMap
				.setConvToUIMethodParas(systemConfigureExtensionUnionConvToUIParas);
		systemConfigureExtensionUnionMap.setLogicManager(systemConfigureExtensionUnionManager);
		systemConfigureExtensionUnionMap
				.setConvToUIMethod(SystemConfigureExtensionUnionManager.METHOD_ConvSystemConfigureExtensionUnionToUI);
		Class<?>[] systemConfigureExtensionUnionConvUIToParas = {
				SystemConfigureExtensionUnionUIModel.class,
				SystemConfigureExtensionUnion.class };
		systemConfigureExtensionUnionMap
				.setConvUIToMethodParas(systemConfigureExtensionUnionConvUIToParas);
		systemConfigureExtensionUnionMap
				.setConvUIToMethod(SystemConfigureExtensionUnionManager.METHOD_ConvUIToSystemConfigureExtensionUnion);
		uiModelNodeMapList.add(systemConfigureExtensionUnionMap);
		systemConfigureExtensionUnionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureExtensionUnionExtensionUnion);

		// UI Model Configure of node:[systemCodeValue]
		UIModelNodeMapConfigure systemCodeValueCollection = new UIModelNodeMapConfigure();
		systemCodeValueCollection.setSeName(SystemCodeValueCollection.SENAME);
		systemCodeValueCollection
				.setNodeName(SystemCodeValueCollection.NODENAME);
		systemCodeValueCollection
				.setNodeInstID(SystemCodeValueCollection.SENAME);
		systemCodeValueCollection.setHostNodeFlag(false);
		systemCodeValueCollection
				.setBaseNodeInstID(SystemConfigureExtensionUnion.NODENAME);
		systemCodeValueCollection
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] systemCodeValueConvToUIParas = {
				SystemCodeValueCollection.class,
				SystemConfigureExtensionUnionUIModel.class };
		systemCodeValueCollection
				.setConvToUIMethodParas(systemCodeValueConvToUIParas);
		systemCodeValueCollection.setLogicManager(systemConfigureExtensionUnionManager);
		systemCodeValueCollection
				.setConvToUIMethod(SystemConfigureExtensionUnionManager.METHOD_ConvSystemCodeValueCollectionToUI);
		List<SearchConfigConnectCondition> systemCodeValueConditionList = new ArrayList<>();
		SearchConfigConnectCondition systemCodeValueCondition0 = new SearchConfigConnectCondition();
		systemCodeValueCondition0.setSourceFieldName("refCodeValueUUID");
		systemCodeValueCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		systemCodeValueConditionList.add(systemCodeValueCondition0);
		systemCodeValueCollection
				.setConnectionConditions(systemCodeValueConditionList);
		systemCodeValueCollection
				.setServiceEntityManager(systemCodeValueCollectionManager);
		uiModelNodeMapList.add(systemCodeValueCollection);

		// UI Model Configure of node:[SystemConfigureElement]
		UIModelNodeMapConfigure systemConfigureElementMap = new UIModelNodeMapConfigure();
		systemConfigureElementMap.setSeName(SystemConfigureElement.SENAME);
		systemConfigureElementMap.setNodeName(SystemConfigureElement.NODENAME);
		systemConfigureElementMap
				.setNodeInstID(SystemConfigureElement.NODENAME);
		systemConfigureElementMap.setHostNodeFlag(false);
		systemConfigureElementMap
				.setBaseNodeInstID(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureElementMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		systemConfigureElementMap.setLogicManager(systemConfigureExtensionUnionManager);
		Class<?>[] systemConfigureElementConvToUIParas = {
				SystemConfigureElement.class,
				SystemConfigureExtensionUnionUIModel.class };
		systemConfigureElementMap
				.setConvToUIMethodParas(systemConfigureElementConvToUIParas);
		systemConfigureElementMap
				.setConvToUIMethod(SystemConfigureExtensionUnionManager.METHOD_ConvElementToExtensionUnionUI);
		uiModelNodeMapList.add(systemConfigureElementMap);

		// UI Model Configure of node:[SystemConfigureResource]
		UIModelNodeMapConfigure systemConfigureResourceMap = new UIModelNodeMapConfigure();
		systemConfigureResourceMap.setSeName(SystemConfigureResource.SENAME);
		systemConfigureResourceMap
				.setNodeName(SystemConfigureResource.NODENAME);
		systemConfigureResourceMap
				.setNodeInstID(SystemConfigureResource.NODENAME);
		systemConfigureResourceMap.setHostNodeFlag(false);
		systemConfigureResourceMap
				.setBaseNodeInstID(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureResourceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] systemConfigureResourceConvToUIParas = {
				SystemConfigureResource.class,
				SystemConfigureExtensionUnionUIModel.class };
		systemConfigureResourceMap.setLogicManager(systemConfigureExtensionUnionManager);
		systemConfigureResourceMap
				.setConvToUIMethodParas(systemConfigureResourceConvToUIParas);
		systemConfigureResourceMap
				.setConvToUIMethod(SystemConfigureExtensionUnionManager.METHOD_ConvResourceToExtensionUnionUI);
		uiModelNodeMapList.add(systemConfigureResourceMap);

		return resultList;
	}

}
