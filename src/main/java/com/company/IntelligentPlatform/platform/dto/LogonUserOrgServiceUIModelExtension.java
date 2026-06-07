package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.OrganizationManager;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.platform.service.LogonUserManager;
import com.company.IntelligentPlatform.platform.service.LogonUserOrgManager;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.platform.model.Organization;

@Service
public class LogonUserOrgServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected LogonUserOrgManager logonUserOrgManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion logonUserOrgReferenceExtensionUnion = new ServiceUIModelExtensionUnion();
		logonUserOrgReferenceExtensionUnion
				.setNodeInstId(LogonUserOrgReference.NODENAME);
		logonUserOrgReferenceExtensionUnion
				.setNodeName(LogonUserOrgReference.NODENAME);

		// UI Model Configure of node:[LogonUserOrgReference]
		UIModelNodeMapConfigure logonUserOrgReferenceMap = new UIModelNodeMapConfigure();
		logonUserOrgReferenceMap.setSeName(LogonUserOrgReference.SENAME);
		logonUserOrgReferenceMap.setNodeName(LogonUserOrgReference.NODENAME);
		logonUserOrgReferenceMap.setNodeInstID(LogonUserOrgReference.NODENAME);
		logonUserOrgReferenceMap.setLogicManager(logonUserOrgManager);
		Class<?>[] logonUserOrgConvToUIParas = { LogonUserOrgReference.class,
				LogonUserOrganizationUIModel.class };
		logonUserOrgReferenceMap
				.setConvToUIMethodParas(logonUserOrgConvToUIParas);
		logonUserOrgReferenceMap
				.setConvToUIMethod(LogonUserOrgManager.METHOD_convLogonUserOrgRefToUserOrgUIModel);
		Class<?>[] logonConvUIToLogonUserOrgParas = {
				LogonUserOrganizationUIModel.class, LogonUserOrgReference.class };
		logonUserOrgReferenceMap
				.setConvUIToMethodParas(logonConvUIToLogonUserOrgParas);
		logonUserOrgReferenceMap
				.setConvUIToMethod(LogonUserOrgManager.METHOD_convUserOrgUIModelToLogonUserOrgRef);
		logonUserOrgReferenceMap.setHostNodeFlag(true);
		uiModelNodeMapList.add(logonUserOrgReferenceMap);

		// UI Model Configure of node:[LogonUserOrgReference]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.NODENAME);
		logonUserMap.setLogicManager(logonUserOrgManager);
		logonUserMap.setBaseNodeInstID(LogonUserOrgReference.NODENAME);
		logonUserMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] logonUserToOrgMap = { LogonUser.class,
				LogonUserOrganizationUIModel.class };
		logonUserMap
				.setConvToUIMethodParas(logonUserToOrgMap);
		logonUserMap
				.setConvToUIMethod(LogonUserOrgManager.METHOD_convLogonUserToUserOrgUIModel);
		logonUserMap.setHostNodeFlag(false);
		uiModelNodeMapList.add(logonUserMap);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setBaseNodeInstID(LogonUserOrgReference.NODENAME);
		organizationMap.setServiceEntityManager(organizationManager);
		organizationMap.setLogicManager(logonUserOrgManager);
		organizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] organizationConvToUIParas = { Organization.class,
				LogonUserOrganizationUIModel.class };
		organizationMap.setConvToUIMethodParas(organizationConvToUIParas);
		organizationMap
				.setConvToUIMethod(LogonUserOrgManager.METHOD_convOrgToUserOrgUIModel);
		uiModelNodeMapList.add(organizationMap);

		logonUserOrgReferenceExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(logonUserOrgReferenceExtensionUnion);
		return resultList;
	}

}
