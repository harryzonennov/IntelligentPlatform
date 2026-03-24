package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.UserRoleManager;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class LogonUserServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected LogonUserOrgServiceUIModelExtension logonUserOrgReferenceServiceUIModelExtension;

	@Autowired
	protected UserRoleServiceUIModelExtension userRoleServiceUIModelExtension;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(logonUserOrgReferenceServiceUIModelExtension);
		resultList.add(userRoleServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				LogonUserActionNode.SENAME,
				LogonUserActionNode.NODENAME,
				LogonUserActionNode.NODEINST_ACTION_ACTIVE,
				logonUserManager, LogonUserActionNode.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				LogonUserActionNode.SENAME,
				LogonUserActionNode.NODENAME,
				LogonUserActionNode.NODEINST_ACTION_REINIT,
				logonUserManager, LogonUserActionNode.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				LogonUserActionNode.SENAME,
				LogonUserActionNode.NODENAME,
				LogonUserActionNode.NODEINST_ACTION_ARCHIVE,
				logonUserManager, LogonUserActionNode.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				LogonUserActionNode.SENAME,
				LogonUserActionNode.NODENAME,
				LogonUserActionNode.NODEINST_ACTION_SUBMIT,
				logonUserManager, LogonUserActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				LogonUserActionNode.SENAME,
				LogonUserActionNode.NODENAME,
				LogonUserActionNode.NODEINST_ACTION_APPROVE,
				logonUserManager, LogonUserActionNode.DOC_ACTION_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion logonUserExtensionUnion = new ServiceUIModelExtensionUnion();
		logonUserExtensionUnion.setNodeInstId(LogonUser.SENAME);
		logonUserExtensionUnion.setNodeName(LogonUser.NODENAME);

		// UI Model Configure of node:[LogonUser]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.SENAME);
		logonUserMap.setHostNodeFlag(true);
		Class<?>[] logonUserConvToUIParas = { LogonUser.class,
				LogonUserUIModel.class };
		logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
		logonUserMap
				.setConvToUIMethod(LogonUserManager.METHOD_convLogonUserToUI);
		Class<?>[] LogonUserConvUIToParas = { LogonUserUIModel.class,
				LogonUser.class };
		logonUserMap.setConvUIToMethodParas(LogonUserConvUIToParas);
		logonUserMap
				.setConvUIToMethod(LogonUserManager.METHOD_convUIToLogonUser);
		uiModelNodeMapList.add(logonUserMap);

		UIModelNodeMapConfigure userOrgMap = new UIModelNodeMapConfigure();
		userOrgMap.setSeName(LogonUserOrgReference.SENAME);
		userOrgMap.setNodeName(LogonUserOrgReference.NODENAME);
		userOrgMap.setNodeInstID(LogonUserOrgReference.NODENAME);
		userOrgMap.setBaseNodeInstID(LogonUser.SENAME);
		userOrgMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		uiModelNodeMapList.add(userOrgMap);

		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setServiceEntityManager(organizationManager);
		organizationMap.setBaseNodeInstID(LogonUserOrgReference.NODENAME);
		organizationMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] organziationConvToUIParas = { Organization.class,
				LogonUserUIModel.class };
		organizationMap.setConvToUIMethodParas(organziationConvToUIParas);
		organizationMap
				.setConvToUIMethod(LogonUserManager.METHOD_convOrganizationToUI);
		uiModelNodeMapList.add(organizationMap);

		UIModelNodeMapConfigure userRoleMap = new UIModelNodeMapConfigure();
		userRoleMap.setSeName(UserRole.SENAME);
		userRoleMap.setNodeName(UserRole.NODENAME);
		userRoleMap.setNodeInstID(UserRole.NODENAME);
		userRoleMap.setBaseNodeInstID(LogonUser.SENAME);
		userRoleMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		uiModelNodeMapList.add(userRoleMap);

		UIModelNodeMapConfigure mainRoleMap = new UIModelNodeMapConfigure();
		mainRoleMap.setSeName(Role.SENAME);
		mainRoleMap.setNodeName(Role.NODENAME);
		mainRoleMap.setNodeInstID(Role.SENAME);
		mainRoleMap.setBaseNodeInstID(UserRole.NODENAME);
		mainRoleMap.setServiceEntityManager(roleManager);
		mainRoleMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] roleConvToUIParas = { Role.class,
				LogonUserUIModel.class };
		mainRoleMap.setConvToUIMethodParas(roleConvToUIParas);
		mainRoleMap
				.setConvToUIMethod(LogonUserManager.METHOD_convRoleToUI);
		uiModelNodeMapList.add(mainRoleMap);

		logonUserExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(logonUserExtensionUnion);
		return resultList;
	}

}
