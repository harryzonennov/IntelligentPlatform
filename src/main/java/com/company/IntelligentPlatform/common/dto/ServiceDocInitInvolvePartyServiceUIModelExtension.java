package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDocInitInvolvePartyServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected ServiceDocInitConfigureManager serviceDocInitConfigureManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocServiceDocInitInvolvePartyExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocServiceDocInitInvolvePartyExtensionUnion
				.setNodeInstId(ServiceDocInitInvolveParty.NODENAME);
		serviceDocServiceDocInitInvolvePartyExtensionUnion
				.setNodeName(ServiceDocInitInvolveParty.NODENAME);

		// UI Model Configure of node:[ServiceDocInitInvolveParty]
		UIModelNodeMapConfigure serviceDocServiceDocInitInvolvePartyMap = new UIModelNodeMapConfigure();
		serviceDocServiceDocInitInvolvePartyMap
				.setSeName(ServiceDocInitInvolveParty.SENAME);
		serviceDocServiceDocInitInvolvePartyMap
				.setNodeName(ServiceDocInitInvolveParty.NODENAME);
		serviceDocServiceDocInitInvolvePartyMap
				.setNodeInstID(ServiceDocInitInvolveParty.NODENAME);
		serviceDocServiceDocInitInvolvePartyMap.setHostNodeFlag(true);
		Class<?>[] serviceDocServiceDocInitInvolvePartyConvToUIParas = {
				ServiceDocInitInvolveParty.class,
				ServiceDocInitInvolvePartyUIModel.class };
		serviceDocServiceDocInitInvolvePartyMap
				.setConvToUIMethodParas(serviceDocServiceDocInitInvolvePartyConvToUIParas);
		serviceDocServiceDocInitInvolvePartyMap.setLogicManager(serviceDocInitConfigureManager);
		serviceDocServiceDocInitInvolvePartyMap
				.setConvToUIMethod(ServiceDocInitConfigureManager.METHOD_ConvServiceDocInitConfigureToUI);
		Class<?>[] ServiceDocInitInvolvePartyConvUIToParas = {
				ServiceDocInitInvolvePartyUIModel.class,
				ServiceDocInitInvolveParty.class };
		serviceDocServiceDocInitInvolvePartyMap
				.setConvUIToMethodParas(ServiceDocInitInvolvePartyConvUIToParas);
		serviceDocServiceDocInitInvolvePartyMap
				.setConvUIToMethod(ServiceDocInitConfigureManager.METHOD_ConvUIToServiceDocInitConfigure);
		uiModelNodeMapList.add(serviceDocServiceDocInitInvolvePartyMap);

		serviceDocServiceDocInitInvolvePartyExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocServiceDocInitInvolvePartyExtensionUnion);
		return resultList;
	}

}
