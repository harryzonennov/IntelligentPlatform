package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.model.CalendarTempWorkSchedule;

/**
 * Configure Proxy CLASS FOR Service Entity [CalendarTemplate]
 *
 * @author
 * @date Sun May 12 21:18:04 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CalendarTemplateConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of CalendarTemplate [ROOT] node
		ServiceEntityConfigureMap calendarTemplateConfigureMap = new ServiceEntityConfigureMap();
		calendarTemplateConfigureMap.setParentNodeName(" ");
		calendarTemplateConfigureMap.setNodeName(CalendarTemplate.NODENAME);
		calendarTemplateConfigureMap.setNodeType(CalendarTemplate.class);
		calendarTemplateConfigureMap.setTableName(CalendarTemplate.SENAME);
		calendarTemplateConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		calendarTemplateConfigureMap.addNodeFieldMap("year", int.class);
		calendarTemplateConfigureMap.addNodeFieldMap("status", int.class);
		seConfigureMapList.add(calendarTemplateConfigureMap);
		// Init configuration of CalendarTemplate [CalendarTemplateItem] node
		ServiceEntityConfigureMap calendarTemplateItemConfigureMap = new ServiceEntityConfigureMap();
		calendarTemplateItemConfigureMap
				.setParentNodeName(CalendarTemplate.NODENAME);
		calendarTemplateItemConfigureMap
				.setNodeName(CalendarTemplateItem.NODENAME);
		calendarTemplateItemConfigureMap
				.setNodeType(CalendarTemplateItem.class);
		calendarTemplateItemConfigureMap
				.setTableName(CalendarTemplateItem.NODENAME);
		calendarTemplateItemConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		calendarTemplateItemConfigureMap.addNodeFieldMap("periodType",
				int.class);
		calendarTemplateItemConfigureMap.addNodeFieldMap("startDay", int.class);
		calendarTemplateItemConfigureMap.addNodeFieldMap("startMonth",
				int.class);
		calendarTemplateItemConfigureMap.addNodeFieldMap("lastDays", int.class);
		calendarTemplateItemConfigureMap
				.addNodeFieldMap("dayStatus", int.class);
		calendarTemplateItemConfigureMap.addNodeFieldMap("vocationType",
				int.class);
		seConfigureMapList.add(calendarTemplateItemConfigureMap);
		// Init configuration of CalendarTemplate [CalendarTempWorkSchedule]
		// node
		ServiceEntityConfigureMap calendarTempWorkScheduleConfigureMap = new ServiceEntityConfigureMap();
		calendarTempWorkScheduleConfigureMap
				.setParentNodeName(CalendarTemplate.NODENAME);
		calendarTempWorkScheduleConfigureMap
				.setNodeName(CalendarTempWorkSchedule.NODENAME);
		calendarTempWorkScheduleConfigureMap
				.setNodeType(CalendarTempWorkSchedule.class);
		calendarTempWorkScheduleConfigureMap
				.setTableName(CalendarTempWorkSchedule.NODENAME);
		calendarTempWorkScheduleConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		calendarTempWorkScheduleConfigureMap.addNodeFieldMap("startTime",
				java.sql.Time.class);
		calendarTempWorkScheduleConfigureMap.addNodeFieldMap("endTime",
				java.sql.Time.class);
		seConfigureMapList.add(calendarTempWorkScheduleConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
