package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

// TODO-LEGACY: import com.sun.jna.platform.win32.Netapi32Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.UserRoleManager;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.UserRole;

@Service
public class UserRoleServiceUIModelExtension extends ServiceUIModelExtension {
	
	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected UserRoleManager userRoleManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion userRoleExtensionUnion = new ServiceUIModelExtensionUnion();
		userRoleExtensionUnion.setNodeInstId(UserRole.NODENAME);
		userRoleExtensionUnion.setNodeName(UserRole.NODENAME);

		// UI Model Configure of node:[UserRole]
		UIModelNodeMapConfigure userRoleMap = new UIModelNodeMapConfigure();
		userRoleMap.setSeName(UserRole.SENAME);
		userRoleMap.setNodeName(UserRole.NODENAME);
		userRoleMap.setNodeInstID(UserRole.NODENAME);
		userRoleMap.setLogicManager(userRoleManager);
		userRoleMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] convUserRoleToUIParas = { UserRole.class, UserRoleUIModel.class };
		userRoleMap.setConvToUIMethodParas(convUserRoleToUIParas);
		userRoleMap.setConvToUIMethod(UserRoleManager.METHOD_convUserRoleToUserRoleUI);
		
		Class<?>[] convUIToUserRoleParas = { UserRoleUIModel.class,  UserRole.class };
		userRoleMap.setConvUIToMethodParas(convUIToUserRoleParas);
		userRoleMap.setConvUIToMethod(UserRoleManager.METHOD_convUserRoleUIToUserRole);
		userRoleMap.setHostNodeFlag(true);

		uiModelNodeMapList.add(userRoleMap);

		// UI Model Configure of node:[Role]
		UIModelNodeMapConfigure roleMap = new UIModelNodeMapConfigure();
		roleMap.setSeName(Role.SENAME);
		roleMap.setNodeName(Role.NODENAME);
		roleMap.setNodeInstID(Role.SENAME);
		roleMap.setBaseNodeInstID(UserRole.NODENAME);
		roleMap.setServiceEntityManager(roleManager);
		roleMap.setLogicManager(userRoleManager);
		roleMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] roleConvToUIParas = { Role.class, UserRoleUIModel.class };
		roleMap.setConvToUIMethodParas(roleConvToUIParas);
		roleMap.setConvToUIMethod(UserRoleManager.METHOD_convRoleToUserRoleUI);
		uiModelNodeMapList.add(roleMap);
		userRoleExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(userRoleExtensionUnion);
		return resultList;
	}

}
