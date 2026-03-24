package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SerExtendPageI18nManager;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingManager;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;

import java.util.ArrayList;
import java.util.List;

@Service
public class SerExtendPageI18nServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected SerExtendPageI18nManager serExtendPageI18nManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serExtendPageI18nExtensionUnion = new ServiceUIModelExtensionUnion();
		serExtendPageI18nExtensionUnion.setNodeInstId(SerExtendPageI18n.NODENAME);
		serExtendPageI18nExtensionUnion.setNodeName(SerExtendPageI18n.NODENAME);

		// UI Model Configure of node:[SerExtendPageI18n]
		UIModelNodeMapConfigure serExtendPageI18nMap = new UIModelNodeMapConfigure();
		serExtendPageI18nMap.setSeName(SerExtendPageI18n.SENAME);
		serExtendPageI18nMap.setNodeName(SerExtendPageI18n.NODENAME);
		serExtendPageI18nMap.setNodeInstID(SerExtendPageI18n.NODENAME);
		serExtendPageI18nMap.setHostNodeFlag(true);
		Class<?>[] serExtendPageI18nConvToUIParas = { SerExtendPageI18n.class,
				SerExtendPageI18nUIModel.class };
		serExtendPageI18nMap
				.setConvToUIMethodParas(serExtendPageI18nConvToUIParas);
		serExtendPageI18nMap.setLogicManager(serExtendPageI18nManager);
		serExtendPageI18nMap
				.setConvToUIMethod(SerExtendPageI18nManager.METHOD_ConvSerExtendPageI18nToUI);
		Class<?>[] SerExtendPageI18nConvUIToParas = {
				SerExtendPageI18nUIModel.class, SerExtendPageI18n.class };
		serExtendPageI18nMap
				.setConvUIToMethodParas(SerExtendPageI18nConvUIToParas);
		serExtendPageI18nMap
				.setConvUIToMethod(SerExtendPageI18nManager.METHOD_ConvUIToSerExtendPageI18n);
		uiModelNodeMapList.add(serExtendPageI18nMap);
		serExtendPageI18nExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serExtendPageI18nExtensionUnion);
		return resultList;
	}

}
