package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogItemUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;

@Service
public class ServiceEntityLogItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceEntityLogItemExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceEntityLogItemExtensionUnion
				.setNodeInstId(ServiceEntityLogItem.NODENAME);
		serviceEntityLogItemExtensionUnion
				.setNodeName(ServiceEntityLogItem.NODENAME);

		// UI Model Configure of node:[ServiceEntityLogItem]
		UIModelNodeMapConfigure serviceEntityLogItemMap = new UIModelNodeMapConfigure();
		serviceEntityLogItemMap.setSeName(ServiceEntityLogItem.SENAME);
		serviceEntityLogItemMap.setNodeName(ServiceEntityLogItem.NODENAME);
		serviceEntityLogItemMap.setNodeInstID(ServiceEntityLogItem.NODENAME);
		serviceEntityLogItemMap.setHostNodeFlag(true);
		Class<?>[] serviceEntityLogItemConvToUIParas = {
				ServiceEntityLogItem.class, ServiceEntityLogItemUIModel.class };
		serviceEntityLogItemMap
				.setConvToUIMethodParas(serviceEntityLogItemConvToUIParas);
		serviceEntityLogItemMap
				.setConvToUIMethod(ServiceEntityLogModelManager.METHOD_ConvServiceEntityLogItemToUI);
		uiModelNodeMapList.add(serviceEntityLogItemMap);
		serviceEntityLogItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceEntityLogItemExtensionUnion);
		return resultList;
	}

}
