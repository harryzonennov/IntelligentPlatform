package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.platform.dto.SerExtendPageSectionUIModel;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.SerExtendPageSectionManager;
import com.company.IntelligentPlatform.platform.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.platform.model.SerExtendPageSection;

@Service
public class SerExtendPageSectionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSectionManager serExtendPageSectionManager;
	
	@Autowired
	protected SerExtendUIControlSetServiceUIModelExtension serExtendUIControlSetServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serExtendUIControlSetServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serExtendPageSectionExtensionUnion = new ServiceUIModelExtensionUnion();
		serExtendPageSectionExtensionUnion
				.setNodeInstId(SerExtendPageSection.NODENAME);
		serExtendPageSectionExtensionUnion
				.setNodeName(SerExtendPageSection.NODENAME);

		// UI Model Configure of node:[SerExtendPageSection]
		UIModelNodeMapConfigure serExtendPageSectionMap = new UIModelNodeMapConfigure();
		serExtendPageSectionMap.setSeName(SerExtendPageSection.SENAME);
		serExtendPageSectionMap.setNodeName(SerExtendPageSection.NODENAME);
		serExtendPageSectionMap.setNodeInstID(SerExtendPageSection.NODENAME);
		serExtendPageSectionMap.setHostNodeFlag(true);
		Class<?>[] serExtendPageSectionConvToUIParas = {
				SerExtendPageSection.class, SerExtendPageSectionUIModel.class };
		serExtendPageSectionMap.setLogicManager(serExtendPageSectionManager);
		serExtendPageSectionMap
				.setConvToUIMethodParas(serExtendPageSectionConvToUIParas);
		serExtendPageSectionMap
				.setConvToUIMethod(SerExtendPageSectionManager.METHOD_ConvSerExtendPageSectionToUI);
		Class<?>[] SerExtendPageSectionConvUIToParas = {
				SerExtendPageSectionUIModel.class, SerExtendPageSection.class };
		serExtendPageSectionMap
				.setConvUIToMethodParas(SerExtendPageSectionConvUIToParas);
		serExtendPageSectionMap
				.setConvUIToMethod(SerExtendPageSectionManager.METHOD_ConvUIToSerExtendPageSection);
		uiModelNodeMapList.add(serExtendPageSectionMap);

		serExtendPageSectionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serExtendPageSectionExtensionUnion);
		return resultList;
	}

}
