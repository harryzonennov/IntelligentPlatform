package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.CalendarSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.CalendarSettingUIModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.CalendarSettingManager;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.CalendarSetting;
import com.company.IntelligentPlatform.common.model.CalendarWorkTimeSetting;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "calendarSettingListController")
@RequestMapping(value = "/calendarSetting")
public class CalendarSettingListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ORGANIZATION;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected CalendarSettingManager calendarSettingManager;



	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = CalendarSettingUIModel.class.getResource("").getPath();
		String resFileName = CalendarSetting.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected List<CalendarSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<CalendarSettingUIModel> calendarSettingList = new ArrayList<CalendarSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			CalendarSettingUIModel calendarSettingUIModel = new CalendarSettingUIModel();
			CalendarSetting calendarSetting = (CalendarSetting) rawNode;
			calendarSettingManager.convCalendarSettingToUI(calendarSetting,
					calendarSettingUIModel);
			calendarSettingList.add(calendarSettingUIModel);
		}
		return calendarSettingList;
	}


	protected List<ServiceEntityNode> searchInternal(
			CalendarSettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[calendarSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(CalendarSetting.SENAME);
		searchNodeConfig0.setNodeName(CalendarSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(CalendarSetting.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[calendarWorkTimeSetting]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(CalendarWorkTimeSetting.SENAME);
		searchNodeConfig1.setNodeName(CalendarWorkTimeSetting.NODENAME);
		searchNodeConfig1.setNodeInstID(CalendarWorkTimeSetting.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT);
		searchNodeConfig1.setBaseNodeInstID(CalendarSetting.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}


}
