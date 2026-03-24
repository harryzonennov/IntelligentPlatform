package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SerialNumberSettingUIModel;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;

@Service
public class SerialNumberSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serialNumberSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serialNumberSettingExtensionUnion
				.setNodeInstId(SerialNumberSetting.SENAME);
		serialNumberSettingExtensionUnion
				.setNodeName(SerialNumberSetting.NODENAME);

		// UI Model Configure of node:[SerialNumberSetting]
		UIModelNodeMapConfigure serialNumberSettingMap = new UIModelNodeMapConfigure();
		serialNumberSettingMap.setSeName(SerialNumberSetting.SENAME);
		serialNumberSettingMap.setNodeName(SerialNumberSetting.NODENAME);
		serialNumberSettingMap.setNodeInstID(SerialNumberSetting.SENAME);
		serialNumberSettingMap.setHostNodeFlag(true);
		Class<?>[] serialNumberSettingConvToUIParas = {
				SerialNumberSetting.class, SerialNumberSettingUIModel.class };
		serialNumberSettingMap
				.setConvToUIMethodParas(serialNumberSettingConvToUIParas);
		serialNumberSettingMap
				.setConvToUIMethod(SerialNumberSettingManager.METHOD_ConvSerialNumberSettingToUI);
		Class<?>[] SerialNumberSettingConvUIToParas = {
				SerialNumberSettingUIModel.class, SerialNumberSetting.class };
		serialNumberSettingMap
				.setConvUIToMethodParas(SerialNumberSettingConvUIToParas);
		serialNumberSettingMap
				.setConvUIToMethod(SerialNumberSettingManager.METHOD_ConvUIToSerialNumberSetting);
		uiModelNodeMapList.add(serialNumberSettingMap);
		serialNumberSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serialNumberSettingExtensionUnion);
		return resultList;
	}

}
