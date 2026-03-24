package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteProcessItemManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processRouteProcessItemListController")
@RequestMapping(value = "/processRouteProcessItem")
public class ProcessRouteProcessItemListController extends SEListController {

	public static final String AOID_RESOURCE = ProdWorkCenterEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ProcessRouteProcessItemManager processRouteProcessItemManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessRouteProcessItemUIModel.class.getResource("")
				.getPath();
		String resFileName = ProcessRouteProcessItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "ProcessRouteProcessItemList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "ProcessRouteProcessItemChooser";
		}
		return "ProcessRouteProcessItemList";
	}

	protected List<ProcessRouteProcessItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProcessRouteProcessItemUIModel> processRouteProcessItemList = new ArrayList<ProcessRouteProcessItemUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel = new ProcessRouteProcessItemUIModel();
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) rawNode;
			processRouteProcessItemManager.convComProcessItemToUI(
					processRouteProcessItem, processRouteProcessItemUIModel);
			processRouteProcessItemList.add(processRouteProcessItemUIModel);
		}
		return processRouteProcessItemList;
	}


}
