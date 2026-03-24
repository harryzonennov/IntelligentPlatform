package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.SystemResource;

@Service
public class ResFinSystemResourceServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ResourceAuthorizationServiceUIModelExtension resourceAuthorizationServiceUIModelExtension;
	
	@Autowired
	protected ResFinAccountSettingServiceUIModelExtension resFinAccountSettingServiceUIModelExtension;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(resourceAuthorizationServiceUIModelExtension);
		resultList.add(resFinAccountSettingServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion resFinSystemResourceExtensionUnion = new ServiceUIModelExtensionUnion();
		resFinSystemResourceExtensionUnion.setNodeInstId(SystemResource.SENAME);
		resFinSystemResourceExtensionUnion.setNodeName(SystemResource.NODENAME);

		// UI Model Configure of node:[SystemResource]
		UIModelNodeMapConfigure resFinSystemResourceMap = new UIModelNodeMapConfigure();
		resFinSystemResourceMap.setSeName(SystemResource.SENAME);
		resFinSystemResourceMap.setNodeName(SystemResource.NODENAME);
		resFinSystemResourceMap.setNodeInstID(SystemResource.SENAME);
		resFinSystemResourceMap.setHostNodeFlag(true);
		Class<?>[] resFinSystemResourceConvToUIParas = { SystemResource.class,
				ResFinSystemResourceUIModel.class };
		resFinSystemResourceMap.setConvToUIMethodParas(resFinSystemResourceConvToUIParas);
		resFinSystemResourceMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinSystemResourceToUI);
		Class<?>[] resFinSystemResourceConvUIToParas = { ResFinSystemResourceUIModel.class,
				SystemResource.class };
		resFinSystemResourceMap.setConvUIToMethodParas(resFinSystemResourceConvUIToParas);
		resFinSystemResourceMap
				.setConvUIToMethod(SystemResourceManager.METHOD_ConvUIToResFinSystemResource);
		uiModelNodeMapList.add(resFinSystemResourceMap);

		
		resFinSystemResourceExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(resFinSystemResourceExtensionUnion);
		return resultList;
	}

}
