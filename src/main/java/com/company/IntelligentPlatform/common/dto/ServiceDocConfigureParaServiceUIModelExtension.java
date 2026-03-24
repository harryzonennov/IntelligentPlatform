package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

@Service
public class ServiceDocConfigureParaServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocConfigureParaExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocConfigureParaExtensionUnion
				.setNodeInstId(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureParaExtensionUnion
				.setNodeName(ServiceDocConfigurePara.NODENAME);

		// UI Model Configure of node:[ServiceDocConfigurePara]
		UIModelNodeMapConfigure serviceDocConfigureParaMap = new UIModelNodeMapConfigure();
		serviceDocConfigureParaMap.setSeName(ServiceDocConfigurePara.SENAME);
		serviceDocConfigureParaMap
				.setNodeName(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureParaMap
				.setNodeInstID(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureParaMap.setHostNodeFlag(true);
		Class<?>[] serviceDocConfigureParaConvToUIParas = {
				ServiceDocConfigurePara.class,
				ServiceDocConfigureParaUIModel.class };
		serviceDocConfigureParaMap
				.setConvToUIMethodParas(serviceDocConfigureParaConvToUIParas);
		serviceDocConfigureParaMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureParaToUI);
		Class<?>[] ServiceDocConfigureParaConvUIToParas = {
				ServiceDocConfigureParaUIModel.class,
				ServiceDocConfigurePara.class };
		serviceDocConfigureParaMap
				.setConvUIToMethodParas(ServiceDocConfigureParaConvUIToParas);
		serviceDocConfigureParaMap
				.setConvUIToMethod(ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigurePara);
		uiModelNodeMapList.add(serviceDocConfigureParaMap);

		UIModelNodeMapConfigure serviceDocConfigureMap = new UIModelNodeMapConfigure();
		serviceDocConfigureMap.setSeName(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureMap.setNodeInstID(ServiceDocConfigure.SENAME);
		serviceDocConfigureMap.setBaseNodeInstID(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] serviceDocConfigureConvToUIParas = {
				ServiceDocConfigure.class, ServiceDocConfigureParaUIModel.class };
		serviceDocConfigureMap
				.setConvToUIMethodParas(serviceDocConfigureConvToUIParas);
		serviceDocConfigureMap
				.setConvToUIMethod(ServiceDocConfigureManager.METHOD_ConvConfigureToParaUI);
		uiModelNodeMapList.add(serviceDocConfigureMap);

		serviceDocConfigureParaExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocConfigureParaExtensionUnion);
		return resultList;
	}

}
