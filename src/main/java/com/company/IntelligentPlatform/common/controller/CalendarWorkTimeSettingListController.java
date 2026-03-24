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
import com.company.IntelligentPlatform.common.dto.CalendarWorkTimeSettingUIModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.CalendarSettingManager;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.CalendarWorkTimeSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "calendarWorkTimeSettingListController")
@RequestMapping(value = "/calendarWorkTimeSetting")
public class CalendarWorkTimeSettingListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ORGANIZATION;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected CalendarSettingManager calendarSettingManager;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = CalendarWorkTimeSettingUIModel.class.getResource("")
				.getPath();
		String resFileName = CalendarWorkTimeSetting.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "CalendarWorkTimeSettingList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "CalendarWorkTimeSettingChooser";
		}
		return "CalendarWorkTimeSettingList";
	}

	protected List<CalendarWorkTimeSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<CalendarWorkTimeSettingUIModel> calendarWorkTimeSettingList = new ArrayList<CalendarWorkTimeSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			CalendarWorkTimeSettingUIModel calendarWorkTimeSettingUIModel = new CalendarWorkTimeSettingUIModel();
			CalendarWorkTimeSetting calendarWorkTimeSetting = (CalendarWorkTimeSetting) rawNode;
			calendarSettingManager.convCalendarWorkTimeSettingToUI(
					calendarWorkTimeSetting, calendarWorkTimeSettingUIModel);
			calendarWorkTimeSettingList.add(calendarWorkTimeSettingUIModel);
		}
		return calendarWorkTimeSettingList;
	}

}
