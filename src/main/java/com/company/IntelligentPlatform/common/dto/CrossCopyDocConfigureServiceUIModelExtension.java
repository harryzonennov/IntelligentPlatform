package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.CrossCopyDocConfigureManager;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrossCopyDocConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected CrossCopyDocConfigureManager crossCopyDocConfigureManager;

	@Autowired
	protected CrossCopyInvolvePartyServiceUIModelExtension crossCopyInvolvePartyServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(crossCopyInvolvePartyServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion crossCopyDocConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		crossCopyDocConfigureExtensionUnion
				.setNodeInstId(CrossCopyDocConfigure.NODENAME);
		crossCopyDocConfigureExtensionUnion
				.setNodeName(CrossCopyDocConfigure.NODENAME);

		// UI Model Configure of node:[CrossCopyDocConfigure]
		UIModelNodeMapConfigure crossCopyDocConfigureMap = new UIModelNodeMapConfigure();
		crossCopyDocConfigureMap
				.setSeName(CrossCopyDocConfigure.SENAME);
		crossCopyDocConfigureMap
				.setNodeName(CrossCopyDocConfigure.NODENAME);
		crossCopyDocConfigureMap
				.setNodeInstID(CrossCopyDocConfigure.NODENAME);
		crossCopyDocConfigureMap.setHostNodeFlag(true);
		Class<?>[] crossCopyDocConfigureConvToUIParas = {
				CrossCopyDocConfigure.class,
				CrossCopyDocConfigureUIModel.class };
		crossCopyDocConfigureMap
				.setConvToUIMethodParas(crossCopyDocConfigureConvToUIParas);
		crossCopyDocConfigureMap.setLogicManager(crossCopyDocConfigureManager);
		crossCopyDocConfigureMap
				.setConvToUIMethod(CrossCopyDocConfigureManager.METHOD_ConvCrossCopyDocConfigureToUI);
		Class<?>[] CrossCopyDocConfigureConvUIToParas = {
				CrossCopyDocConfigureUIModel.class,
				CrossCopyDocConfigure.class };
		crossCopyDocConfigureMap
				.setConvUIToMethodParas(CrossCopyDocConfigureConvUIToParas);
		crossCopyDocConfigureMap
				.setConvUIToMethod(CrossCopyDocConfigureManager.METHOD_ConvUIToCrossCopyDocConfigure);
		uiModelNodeMapList.add(crossCopyDocConfigureMap);
		crossCopyDocConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(crossCopyDocConfigureExtensionUnion);


		UIModelNodeMapConfigure serviceDocumentSettingMap = new UIModelNodeMapConfigure();
		serviceDocumentSettingMap
				.setSeName(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap
				.setNodeName(ServiceDocumentSetting.NODENAME);
		serviceDocumentSettingMap
				.setNodeInstID(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setBaseNodeInstID(CrossCopyDocConfigure.NODENAME);
		serviceDocumentSettingMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceDocumentSettingMap.setHostNodeFlag(false);
		Class<?>[] serviceDocumentSettingConvToUIParas = {
				ServiceDocumentSetting.class,
				CrossCopyDocConfigureUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(crossCopyDocConfigureManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(CrossCopyDocConfigureManager.METHOD_ConvHomeDocumentToConfigureUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		crossCopyDocConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(crossCopyDocConfigureExtensionUnion);
		return resultList;
	}

}
