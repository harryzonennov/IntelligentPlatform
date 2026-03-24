package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.CrossCopyInvolvePartyManager;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrossCopyInvolvePartyServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected CrossCopyInvolvePartyManager crossCopyInvolvePartyManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion crossCopyInvolvePartyExtensionUnion = new ServiceUIModelExtensionUnion();
		crossCopyInvolvePartyExtensionUnion
				.setNodeInstId(CrossCopyInvolveParty.NODENAME);
		crossCopyInvolvePartyExtensionUnion
				.setNodeName(CrossCopyInvolveParty.NODENAME);

		// UI Model Configure of node:[CrossCopyInvolveParty]
		UIModelNodeMapConfigure crossCopyInvolvePartyMap = new UIModelNodeMapConfigure();
		crossCopyInvolvePartyMap
				.setSeName(CrossCopyInvolveParty.SENAME);
		crossCopyInvolvePartyMap
				.setNodeName(CrossCopyInvolveParty.NODENAME);
		crossCopyInvolvePartyMap
				.setNodeInstID(CrossCopyInvolveParty.NODENAME);
		crossCopyInvolvePartyMap.setHostNodeFlag(true);
		Class<?>[] crossCopyInvolvePartyConvToUIParas = {
				CrossCopyInvolveParty.class,
				CrossCopyInvolvePartyUIModel.class };
		crossCopyInvolvePartyMap
				.setConvToUIMethodParas(crossCopyInvolvePartyConvToUIParas);
		crossCopyInvolvePartyMap.setLogicManager(crossCopyInvolvePartyManager);
		crossCopyInvolvePartyMap
				.setConvToUIMethod(CrossCopyInvolvePartyManager.METHOD_ConvCrossCopyInvolvePartyToUI);
		Class<?>[] CrossCopyInvolvePartyConvUIToParas = {
				CrossCopyInvolvePartyUIModel.class,
				CrossCopyInvolveParty.class };
		crossCopyInvolvePartyMap
				.setConvUIToMethodParas(CrossCopyInvolvePartyConvUIToParas);
		crossCopyInvolvePartyMap
				.setConvUIToMethod(CrossCopyInvolvePartyManager.METHOD_ConvUIToCrossCopyInvolveParty);
		uiModelNodeMapList.add(crossCopyInvolvePartyMap);

		UIModelNodeMapConfigure crossCopyDocConfigureMap = new UIModelNodeMapConfigure();
		crossCopyDocConfigureMap
				.setSeName(CrossCopyDocConfigure.SENAME);
		crossCopyDocConfigureMap
				.setNodeName(CrossCopyDocConfigure.NODENAME);
		crossCopyDocConfigureMap
				.setNodeInstID(CrossCopyDocConfigure.NODENAME);
		crossCopyDocConfigureMap.setBaseNodeInstID(CrossCopyInvolveParty.NODENAME);
		crossCopyDocConfigureMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		crossCopyDocConfigureMap.setHostNodeFlag(false);
		Class<?>[] crossCopyDocConfigureConvToUIParas = {
				CrossCopyDocConfigure.class,
				CrossCopyInvolvePartyUIModel.class };
		crossCopyDocConfigureMap
				.setConvToUIMethodParas(crossCopyDocConfigureConvToUIParas);
		crossCopyDocConfigureMap.setLogicManager(crossCopyInvolvePartyManager);
		crossCopyDocConfigureMap
				.setConvToUIMethod(CrossCopyInvolvePartyManager.METHOD_ConvCrossCopyDocConfigureToEventUI);
		uiModelNodeMapList.add(crossCopyDocConfigureMap);


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
				CrossCopyInvolvePartyUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(crossCopyInvolvePartyManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(CrossCopyInvolvePartyManager.METHOD_ConvHomeDocumentToInvolvePartyUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		crossCopyInvolvePartyExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(crossCopyInvolvePartyExtensionUnion);
		return resultList;
	}

}
