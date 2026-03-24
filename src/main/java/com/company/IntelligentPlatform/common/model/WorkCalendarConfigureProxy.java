package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.model.WorkCalendarDayItem;

/**
 * Configure Proxy CLASS FOR Service Entity [WorkCalendar]
 *
 * @author
 * @date Sun May 12 21:20:02 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class WorkCalendarConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of WorkCalendar [ROOT] node
		ServiceEntityConfigureMap workCalendarConfigureMap = new ServiceEntityConfigureMap();
		workCalendarConfigureMap.setParentNodeName(" ");
		workCalendarConfigureMap.setNodeName(WorkCalendar.NODENAME);
		workCalendarConfigureMap.setNodeType(WorkCalendar.class);
		workCalendarConfigureMap.setTableName(WorkCalendar.SENAME);
		workCalendarConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		workCalendarConfigureMap.addNodeFieldMap("year", int.class);
		workCalendarConfigureMap.addNodeFieldMap("status", int.class);
		workCalendarConfigureMap.addNodeFieldMap("defaultFlag", int.class);
		workCalendarConfigureMap.addNodeFieldMap("refTemplateUUID",
				java.lang.String.class);
		seConfigureMapList.add(workCalendarConfigureMap);
		// Init configuration of WorkCalendar [WorkCalendarDayItem] node
		ServiceEntityConfigureMap workCalendarDayItemConfigureMap = new ServiceEntityConfigureMap();
		workCalendarDayItemConfigureMap
				.setParentNodeName(WorkCalendar.NODENAME);
		workCalendarDayItemConfigureMap
				.setNodeName(WorkCalendarDayItem.NODENAME);
		workCalendarDayItemConfigureMap.setNodeType(WorkCalendarDayItem.class);
		workCalendarDayItemConfigureMap
				.setTableName(WorkCalendarDayItem.NODENAME);
		workCalendarDayItemConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		workCalendarDayItemConfigureMap.addNodeFieldMap("refDate",
				java.util.Date.class);
		workCalendarDayItemConfigureMap.addNodeFieldMap("dayStatus", int.class);
		workCalendarDayItemConfigureMap.addNodeFieldMap("vocationType",
				int.class);
		seConfigureMapList.add(workCalendarDayItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
