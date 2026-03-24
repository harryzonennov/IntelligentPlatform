package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [CalendarSetting]
 *
 * @author
 * @date Sun May 29 21:24:41 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CalendarSettingConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of CalendarSetting [ROOT] node
		ServiceEntityConfigureMap calendarSettingConfigureMap = new ServiceEntityConfigureMap();
		calendarSettingConfigureMap.setParentNodeName(" ");
		calendarSettingConfigureMap.setNodeName(CalendarSetting.NODENAME);
		calendarSettingConfigureMap.setNodeType(CalendarSetting.class);
		calendarSettingConfigureMap.setTableName(CalendarSetting.SENAME);
		calendarSettingConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		calendarSettingConfigureMap.addNodeFieldMap("freeMondayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeTuesdayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeWednesdayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeThursdayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeFridayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeSaturdayFlag",
				boolean.class);
		calendarSettingConfigureMap.addNodeFieldMap("freeSundayFlag",
				boolean.class);
		seConfigureMapList.add(calendarSettingConfigureMap);
		// Init configuration of CalendarSetting [CalendarWorkTimeSetting] node
		ServiceEntityConfigureMap calendarWorkTimeSettingConfigureMap = new ServiceEntityConfigureMap();
		calendarWorkTimeSettingConfigureMap
				.setParentNodeName(CalendarSetting.NODENAME);
		calendarWorkTimeSettingConfigureMap
				.setNodeName(CalendarWorkTimeSetting.NODENAME);
		calendarWorkTimeSettingConfigureMap
				.setNodeType(CalendarWorkTimeSetting.class);
		calendarWorkTimeSettingConfigureMap
				.setTableName(CalendarWorkTimeSetting.NODENAME);
		calendarWorkTimeSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("dailyShift",
				int.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("startDate1",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("endDate1",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("startDate2",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("endDate2",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("startDate3",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("endDate3",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("startDate4",
				java.util.Date.class);
		calendarWorkTimeSettingConfigureMap.addNodeFieldMap("endDate4",
				java.util.Date.class);
		seConfigureMapList.add(calendarWorkTimeSettingConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
