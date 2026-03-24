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
import com.company.IntelligentPlatform.common.service.SystemConfigureElementServiceModel;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;

@Service
public class SubSystemConfigureElementServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemConfigureElementManager systemConfigureElementManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(this);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureElementExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureElementExtensionUnion
				.setNodeInstId(SystemConfigureElementServiceModel.NODEID_SUB_SYSTEMCONFIGUREELEMENT);
		systemConfigureElementExtensionUnion
				.setNodeName(SystemConfigureElement.NODENAME);

		// UI Model Configure of node:[SystemConfigureElement]
		UIModelNodeMapConfigure systemConfigureElementMap = new UIModelNodeMapConfigure();
		systemConfigureElementMap.setSeName(SystemConfigureElement.SENAME);
		systemConfigureElementMap.setNodeName(SystemConfigureElement.NODENAME);
		systemConfigureElementMap
				.setNodeInstID(SystemConfigureElement.NODENAME);
		systemConfigureElementMap.setHostNodeFlag(true);
		systemConfigureElementMap.setLogicManager(systemConfigureElementManager);
		Class<?>[] systemConfigureElementConvToUIParas = {
				SystemConfigureElement.class,
				SystemConfigureElementUIModel.class };
		systemConfigureElementMap
				.setConvToUIMethodParas(systemConfigureElementConvToUIParas);
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

		systemConfigureElementExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureElementExtensionUnion);
		return resultList;
	}

}
