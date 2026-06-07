package com.company.IntelligentPlatform.platform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureElementSearchModel;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureElementUIModel;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.platform.service.SystemConfigureElementManager;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.SystemConfigureElement;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureElementListController")
@RequestMapping(value = "/systemConfigureElement")
public class SystemConfigureElementListController extends SEListController {

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
	protected SystemConfigureElementManager systemConfigureElementManager;

	protected List<SystemConfigureElementUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<SystemConfigureElementUIModel> systemConfigureElementList = new ArrayList<SystemConfigureElementUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			SystemConfigureElementUIModel systemConfigureElementUIModel = new SystemConfigureElementUIModel();
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) rawNode;
			systemConfigureElementManager.convSystemConfigureElementToUI(
					systemConfigureElement, systemConfigureElementUIModel);
			systemConfigureElementList.add(systemConfigureElementUIModel);
		}
		return systemConfigureElementList;
	}

	protected List<ServiceEntityNode> searchInternal(
			SystemConfigureElementSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemConfigureElement]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemConfigureElement.SENAME);
		searchNodeConfig0.setNodeName(SystemConfigureElement.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemConfigureElement.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
