package com.company.IntelligentPlatform.finance.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccountLog;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class FinAccountLogServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountLogExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountLogExtensionUnion.setNodeInstId(FinAccountLog.NODENAME);
		finAccountLogExtensionUnion.setNodeName(FinAccountLog.NODENAME);

		// UI Model Configure of node:[FinAccountLog]
		UIModelNodeMapConfigure finAccountLogMap = new UIModelNodeMapConfigure();
		finAccountLogMap.setSeName(FinAccountLog.SENAME);
		finAccountLogMap.setNodeName(FinAccountLog.NODENAME);
		finAccountLogMap.setNodeInstID(FinAccountLog.NODENAME);
		finAccountLogMap.setHostNodeFlag(true);
		Class<?>[] finAccountLogConvToUIParas = { FinAccountLog.class,
				FinAccountLogUIModel.class };
		finAccountLogMap.setConvToUIMethodParas(finAccountLogConvToUIParas);
		finAccountLogMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountLogToUI);
		Class<?>[] FinAccountLogConvUIToParas = { FinAccountLogUIModel.class,
				FinAccountLog.class };
		finAccountLogMap.setConvUIToMethodParas(FinAccountLogConvUIToParas);
		finAccountLogMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccountLog);
		uiModelNodeMapList.add(finAccountLogMap);
		finAccountLogExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountLogExtensionUnion);
		return resultList;
	}

}
