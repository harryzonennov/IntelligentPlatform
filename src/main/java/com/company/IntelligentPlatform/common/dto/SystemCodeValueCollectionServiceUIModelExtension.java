package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemCodeValueCollectionUIModel;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

@Service
public class SystemCodeValueCollectionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemCodeValueUnionServiceUIModelExtension systemCodeValueUnionServiceUIModelExtension;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(systemCodeValueUnionServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemCodeValueCollectionExtensionUnion = new ServiceUIModelExtensionUnion();
		systemCodeValueCollectionExtensionUnion
				.setNodeInstId(SystemCodeValueCollection.SENAME);
		systemCodeValueCollectionExtensionUnion
				.setNodeName(SystemCodeValueCollection.NODENAME);

		// UI Model Configure of node:[SystemCodeValueCollection]
		UIModelNodeMapConfigure systemCodeValueCollectionMap = new UIModelNodeMapConfigure();
		systemCodeValueCollectionMap
				.setSeName(SystemCodeValueCollection.SENAME);
		systemCodeValueCollectionMap
				.setNodeName(SystemCodeValueCollection.NODENAME);
		systemCodeValueCollectionMap
				.setNodeInstID(SystemCodeValueCollection.SENAME);
		systemCodeValueCollectionMap.setHostNodeFlag(true);
		Class<?>[] systemCodeValueCollectionConvToUIParas = {
				SystemCodeValueCollection.class,
				SystemCodeValueCollectionUIModel.class };
		systemCodeValueCollectionMap
				.setConvToUIMethodParas(systemCodeValueCollectionConvToUIParas);
		systemCodeValueCollectionMap
				.setConvToUIMethod(SystemCodeValueCollectionManager.METHOD_ConvSystemCodeValueCollectionToUI);
		Class<?>[] SystemCodeValueCollectionConvUIToParas = {
				SystemCodeValueCollectionUIModel.class,
				SystemCodeValueCollection.class };
		systemCodeValueCollectionMap
				.setConvUIToMethodParas(SystemCodeValueCollectionConvUIToParas);
		systemCodeValueCollectionMap
				.setConvUIToMethod(SystemCodeValueCollectionManager.METHOD_ConvUIToSystemCodeValueCollection);
		uiModelNodeMapList.add(systemCodeValueCollectionMap);
		systemCodeValueCollectionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemCodeValueCollectionExtensionUnion);
		return resultList;
	}

}
