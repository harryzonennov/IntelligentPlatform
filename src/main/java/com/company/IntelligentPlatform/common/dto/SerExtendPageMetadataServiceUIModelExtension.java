package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SerExtendPageMetadataManager;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingManager;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;

import java.util.ArrayList;
import java.util.List;

@Service
public class SerExtendPageMetadataServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected SerExtendPageMetadataManager serExtendPageMetadataManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serExtendPageMetadataExtensionUnion = new ServiceUIModelExtensionUnion();
		serExtendPageMetadataExtensionUnion.setNodeInstId(SerExtendPageMetadata.NODENAME);
		serExtendPageMetadataExtensionUnion.setNodeName(SerExtendPageMetadata.NODENAME);

		// UI Model Configure of node:[SerExtendPageMetadata]
		UIModelNodeMapConfigure serExtendPageMetadataMap = new UIModelNodeMapConfigure();
		serExtendPageMetadataMap.setSeName(SerExtendPageMetadata.SENAME);
		serExtendPageMetadataMap.setNodeName(SerExtendPageMetadata.NODENAME);
		serExtendPageMetadataMap.setNodeInstID(SerExtendPageMetadata.NODENAME);
		serExtendPageMetadataMap.setHostNodeFlag(true);
		Class<?>[] serExtendPageMetadataConvToUIParas = { SerExtendPageMetadata.class,
				SerExtendPageMetadataUIModel.class };
		serExtendPageMetadataMap.setLogicManager(serExtendPageMetadataManager);
		serExtendPageMetadataMap
				.setConvToUIMethodParas(serExtendPageMetadataConvToUIParas);
		serExtendPageMetadataMap
				.setConvToUIMethod(SerExtendPageMetadataManager.METHOD_ConvSerExtendPageMetadataToUI);
		Class<?>[] SerExtendPageMetadataConvUIToParas = {
				SerExtendPageMetadataUIModel.class, SerExtendPageMetadata.class };
		serExtendPageMetadataMap
				.setConvUIToMethodParas(SerExtendPageMetadataConvUIToParas);
		serExtendPageMetadataMap
				.setConvUIToMethod(SerExtendPageMetadataManager.METHOD_ConvUIToSerExtendPageMetadata);
		uiModelNodeMapList.add(serExtendPageMetadataMap);
		serExtendPageMetadataExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serExtendPageMetadataExtensionUnion);
		return resultList;
	}

}
