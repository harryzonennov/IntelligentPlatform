package com.company.IntelligentPlatform.platform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureResourceSearchModel;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureResourceUIModel;
import com.company.IntelligentPlatform.platform.controller.SEListController;
// TODO-DAO: import platform.foundation.DAO.PageSplitHelper;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.INavigationElementConstants;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SearchNodeMapping;
import com.company.IntelligentPlatform.platform.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.platform.service.SystemConfigureResourceManager;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.SystemConfigureElement;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureResourceListController")
@RequestMapping(value = "/systemConfigureResource")
public class SystemConfigureResourceListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemConfigureCategory;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	protected List<SystemConfigureResourceUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<SystemConfigureResourceUIModel> systemConfigureResourceList = new ArrayList<SystemConfigureResourceUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			SystemConfigureResourceUIModel systemConfigureResourceUIModel = new SystemConfigureResourceUIModel();
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) rawNode;
			systemConfigureResourceManager.convSystemConfigureResourceToUI(
					systemConfigureResource, systemConfigureResourceUIModel);
			systemConfigureResourceList.add(systemConfigureResourceUIModel);
		}
		return systemConfigureResourceList;
	}
	

	protected List<ServiceEntityNode> searchInternal(
			SystemConfigureResourceSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemConfigureResource]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemConfigureResource.SENAME);
		searchNodeConfig0.setNodeName(SystemConfigureResource.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemConfigureResource.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[systemConfigureElement]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(SystemConfigureElement.SENAME);
		searchNodeConfig1.setNodeName(SystemConfigureElement.NODENAME);
		searchNodeConfig1.setNodeInstID(SystemConfigureElement.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig1.setBaseNodeInstID(SystemConfigureResource.NODENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
