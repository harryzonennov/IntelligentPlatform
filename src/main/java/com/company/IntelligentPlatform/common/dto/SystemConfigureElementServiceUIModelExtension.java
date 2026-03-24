package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementUIModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureElementManager;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Service
public class SystemConfigureElementServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemConfigureUIFieldServiceUIModelExtension systemConfigureUIFieldServiceUIModelExtension;

	@Autowired
	protected SystemConfigureExtensionUnionServiceUIModelExtension systemConfigureExtensionUnionServiceUIModelExtension;

	@Autowired
	protected SubSystemConfigureElementServiceUIModelExtension subSystemConfigureElementServiceUIModelExtension;

	@Autowired
	protected SystemConfigureElementManager systemConfigureElementManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(systemConfigureUIFieldServiceUIModelExtension);
		resultList.add(systemConfigureExtensionUnionServiceUIModelExtension);
		resultList.add(subSystemConfigureElementServiceUIModelExtension);
		resultList.add(this);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureElementExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureElementExtensionUnion
				.setNodeInstId(SystemConfigureElement.NODENAME);
		systemConfigureElementExtensionUnion
				.setNodeName(SystemConfigureElement.NODENAME);

		// UI Model Configure of node:[SystemConfigureElement]
		UIModelNodeMapConfigure systemConfigureElementMap = new UIModelNodeMapConfigure();
		systemConfigureElementMap.setSeName(SystemConfigureElement.SENAME);
		systemConfigureElementMap.setNodeName(SystemConfigureElement.NODENAME);
		systemConfigureElementMap
				.setNodeInstID(SystemConfigureElement.NODENAME);
		systemConfigureElementMap.setHostNodeFlag(true);
		Class<?>[] systemConfigureElementConvToUIParas = {
				SystemConfigureElement.class,
				SystemConfigureElementUIModel.class };
		systemConfigureElementMap
				.setConvToUIMethodParas(systemConfigureElementConvToUIParas);
		systemConfigureElementMap.setLogicManager(systemConfigureElementManager);
		systemConfigureElementMap
				.setConvToUIMethod(SystemConfigureElementManager.METHOD_ConvSystemConfigureElementToUI);
		Class<?>[] SystemConfigureElementConvUIToParas = {
				SystemConfigureElementUIModel.class,
				SystemConfigureElement.class };
		systemConfigureElementMap
				.setConvUIToMethodParas(SystemConfigureElementConvUIToParas);
		systemConfigureElementMap
				.setConvUIToMethod(SystemConfigureElementManager.METHOD_ConvUIToSystemConfigureElement);
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
				.setBaseNodeInstID(SystemConfigureElement.NODENAME);
		systemConfigureResourceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] systemConfigureResourceConvToUIParas = {
				SystemConfigureResource.class,
				SystemConfigureElementUIModel.class };
		systemConfigureResourceMap
				.setConvToUIMethodParas(systemConfigureResourceConvToUIParas);
		systemConfigureResourceMap.setLogicManager(systemConfigureElementManager);
		systemConfigureResourceMap
				.setConvToUIMethod(SystemConfigureElementManager.METHOD_ConvResourceToElementUI);
		uiModelNodeMapList.add(systemConfigureResourceMap);

		systemConfigureElementExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureElementExtensionUnion);
		return resultList;
	}

}
