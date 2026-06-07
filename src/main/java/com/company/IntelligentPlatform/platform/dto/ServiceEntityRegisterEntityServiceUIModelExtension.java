package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.platform.dto.ServiceEntityRegisterEntityUIModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityRegisterEntityManager;
import com.company.IntelligentPlatform.platform.model.ServiceEntityRegisterEntity;

@Service
public class ServiceEntityRegisterEntityServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceEntityRegisterEntityExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceEntityRegisterEntityExtensionUnion
				.setNodeInstId(ServiceEntityRegisterEntity.SENAME);
		serviceEntityRegisterEntityExtensionUnion
				.setNodeName(ServiceEntityRegisterEntity.NODENAME);

		// UI Model Configure of node:[ServiceEntityRegisterEntity]
		UIModelNodeMapConfigure serviceEntityRegisterEntityMap = new UIModelNodeMapConfigure();
		serviceEntityRegisterEntityMap
				.setSeName(ServiceEntityRegisterEntity.SENAME);
		serviceEntityRegisterEntityMap
				.setNodeName(ServiceEntityRegisterEntity.NODENAME);
		serviceEntityRegisterEntityMap
				.setNodeInstID(ServiceEntityRegisterEntity.SENAME);
		serviceEntityRegisterEntityMap.setHostNodeFlag(true);
		Class<?>[] serviceEntityRegisterEntityConvToUIParas = {
				ServiceEntityRegisterEntity.class,
				ServiceEntityRegisterEntityUIModel.class };
		serviceEntityRegisterEntityMap
				.setConvToUIMethodParas(serviceEntityRegisterEntityConvToUIParas);
		serviceEntityRegisterEntityMap
				.setConvToUIMethod(ServiceEntityRegisterEntityManager.METHOD_ConvServiceEntityRegisterEntityToUI);
		Class<?>[] ServiceEntityRegisterEntityConvUIToParas = {
				ServiceEntityRegisterEntityUIModel.class,
				ServiceEntityRegisterEntity.class };
		serviceEntityRegisterEntityMap
				.setConvUIToMethodParas(ServiceEntityRegisterEntityConvUIToParas);
		serviceEntityRegisterEntityMap
				.setConvUIToMethod(ServiceEntityRegisterEntityManager.METHOD_ConvUIToServiceEntityRegisterEntity);
		uiModelNodeMapList.add(serviceEntityRegisterEntityMap);
		serviceEntityRegisterEntityExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceEntityRegisterEntityExtensionUnion);
		return resultList;
	}

}
