package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;

@Service
public class ResFinAccountProcessCodeServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemResourceManager systemResourceManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();		
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion resFinAccountProcessCodeUnion = new ServiceUIModelExtensionUnion();
		resFinAccountProcessCodeUnion.setNodeInstId(ResFinAccountProcessCode.NODENAME);
		resFinAccountProcessCodeUnion.setNodeName(ResFinAccountProcessCode.NODENAME);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountProcessCodeMap = new UIModelNodeMapConfigure();
		resFinAccountProcessCodeMap.setSeName(ResFinAccountProcessCode.SENAME);
		resFinAccountProcessCodeMap.setNodeName(ResFinAccountProcessCode.NODENAME);
		resFinAccountProcessCodeMap.setNodeInstID(ResFinAccountProcessCode.NODENAME);
		resFinAccountProcessCodeMap.setHostNodeFlag(true);
		resFinAccountProcessCodeMap.setServiceEntityManager(systemResourceManager);
		Class<?>[] resFinAccountProcessCodeConvToUIParas = {
				ResFinAccountProcessCode.class, ResFinAccountProcessCodeUIModel.class };
		resFinAccountProcessCodeMap
				.setConvToUIMethodParas(resFinAccountProcessCodeConvToUIParas);
		resFinAccountProcessCodeMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinAccountProcessCodeToUI);
		Class<?>[] ResFinAccountProcessCodeConvUIToParas = {
				ResFinAccountProcessCodeUIModel.class, ResFinAccountProcessCode.class };
		resFinAccountProcessCodeMap
				.setConvUIToMethodParas(ResFinAccountProcessCodeConvUIToParas);
		resFinAccountProcessCodeMap
				.setConvUIToMethod(SystemResourceManager.METHOD_ConvUIToResFinAccountProcessCode);
		uiModelNodeMapList.add(resFinAccountProcessCodeMap);
		resFinAccountProcessCodeUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(resFinAccountProcessCodeUnion);
		return resultList;
	}

}
