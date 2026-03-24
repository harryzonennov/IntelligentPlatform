package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;

@Service
public class SystemCodeValueUnionServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemCodeValueUnionExtensionUnion = new ServiceUIModelExtensionUnion();
		systemCodeValueUnionExtensionUnion
				.setNodeInstId(SystemCodeValueUnion.NODENAME);
		systemCodeValueUnionExtensionUnion
				.setNodeName(SystemCodeValueUnion.NODENAME);

		// UI Model Configure of node:[SystemCodeValueUnion]
		UIModelNodeMapConfigure systemCodeValueUnionMap = new UIModelNodeMapConfigure();
		systemCodeValueUnionMap.setSeName(SystemCodeValueUnion.SENAME);
		systemCodeValueUnionMap.setNodeName(SystemCodeValueUnion.NODENAME);
		systemCodeValueUnionMap.setNodeInstID(SystemCodeValueUnion.NODENAME);
		systemCodeValueUnionMap.setHostNodeFlag(true);
		Class<?>[] systemCodeValueUnionConvToUIParas = {
				SystemCodeValueUnion.class, SystemCodeValueUnionUIModel.class };
		systemCodeValueUnionMap
				.setConvToUIMethodParas(systemCodeValueUnionConvToUIParas);
		systemCodeValueUnionMap
				.setConvToUIMethod(SystemCodeValueCollectionManager.METHOD_ConvSystemCodeValueUnionToUI);
		Class<?>[] SystemCodeValueUnionConvUIToParas = {
				SystemCodeValueUnionUIModel.class, SystemCodeValueUnion.class };
		systemCodeValueUnionMap
				.setConvUIToMethodParas(SystemCodeValueUnionConvUIToParas);
		systemCodeValueUnionMap
				.setConvUIToMethod(SystemCodeValueCollectionManager.METHOD_ConvUIToSystemCodeValueUnion);
		uiModelNodeMapList.add(systemCodeValueUnionMap);
		systemCodeValueUnionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemCodeValueUnionExtensionUnion);
		return resultList;
	}

}
