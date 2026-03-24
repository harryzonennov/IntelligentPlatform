package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.RoleUIModel;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.model.Role;

@Service
public class RoleServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected RoleAuthorizationServiceUIModelExtension roleAuthorizationServiceUIModelExtension;
	
	@Autowired
	protected RoleMessageCategoryServiceUIModelExtension roleMessageCategoryServiceUIModelExtension;

	@Autowired
	protected RoleManager roleManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(roleAuthorizationServiceUIModelExtension);
		resultList.add(roleMessageCategoryServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion roleExtensionUnion = new ServiceUIModelExtensionUnion();
		roleExtensionUnion.setNodeInstId(Role.SENAME);
		roleExtensionUnion.setNodeName(Role.NODENAME);

		// UI Model Configure of node:[Role]
		UIModelNodeMapConfigure roleMap = new UIModelNodeMapConfigure();
		roleMap.setSeName(Role.SENAME);
		roleMap.setNodeName(Role.NODENAME);
		roleMap.setNodeInstID(Role.SENAME);
		roleMap.setHostNodeFlag(true);
		Class<?>[] roleConvToUIParas = { Role.class, RoleUIModel.class };
		roleMap.setConvToUIMethodParas(roleConvToUIParas);
		roleMap.setConvToUIMethod(RoleManager.METHOD_ConvRoleToUI);
		Class<?>[] RoleConvUIToParas = { RoleUIModel.class, Role.class };
		roleMap.setConvUIToMethodParas(RoleConvUIToParas);
		roleMap.setConvUIToMethod(RoleManager.METHOD_ConvUIToRole);
		uiModelNodeMapList.add(roleMap);

		roleExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(roleExtensionUnion);
		return resultList;
	}

}
