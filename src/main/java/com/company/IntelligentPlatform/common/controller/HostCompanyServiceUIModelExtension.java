package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.HostCompanyUIModel;
import com.company.IntelligentPlatform.common.service.HostCompanyManager;
import com.company.IntelligentPlatform.common.model.HostCompany;

@Service
public class HostCompanyServiceUIModelExtension extends ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion hostCompanyExtensionUnion = new ServiceUIModelExtensionUnion();
		hostCompanyExtensionUnion.setNodeInstId(HostCompany.SENAME);
		hostCompanyExtensionUnion.setNodeName(HostCompany.NODENAME);

		// UI Model Configure of node:[HostCompany]
		UIModelNodeMapConfigure hostCompanyMap = new UIModelNodeMapConfigure();
		hostCompanyMap.setSeName(HostCompany.SENAME);
		hostCompanyMap.setNodeName(HostCompany.NODENAME);
		hostCompanyMap.setNodeInstID(HostCompany.SENAME);
		hostCompanyMap.setHostNodeFlag(true);
		Class<?>[] hostCompanyConvToUIParas = { HostCompany.class,
				HostCompanyUIModel.class };
		hostCompanyMap.setConvToUIMethodParas(hostCompanyConvToUIParas);
		hostCompanyMap
				.setConvToUIMethod(HostCompanyManager.METHOD_ConvHostCompanyToUI);
		Class<?>[] HostCompanyConvUIToParas = { HostCompanyUIModel.class,
				HostCompany.class };
		hostCompanyMap.setConvUIToMethodParas(HostCompanyConvUIToParas);
		hostCompanyMap
				.setConvUIToMethod(HostCompanyManager.METHOD_ConvUIToHostCompany);
		uiModelNodeMapList.add(hostCompanyMap);
		hostCompanyExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(hostCompanyExtensionUnion);
		return resultList;
	}

}
