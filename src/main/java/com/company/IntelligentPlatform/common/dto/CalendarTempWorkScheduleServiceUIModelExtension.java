package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CalendarTempWorkScheduleUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.model.CalendarTempWorkSchedule;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class CalendarTempWorkScheduleServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion calendarTempWorkScheduleExtensionUnion = new ServiceUIModelExtensionUnion();
		calendarTempWorkScheduleExtensionUnion
				.setNodeInstId(CalendarTempWorkSchedule.NODENAME);
		calendarTempWorkScheduleExtensionUnion
				.setNodeName(CalendarTempWorkSchedule.NODENAME);

		// UI Model Configure of node:[CalendarTempWorkSchedule]
		UIModelNodeMapConfigure calendarTempWorkScheduleMap = new UIModelNodeMapConfigure();
		calendarTempWorkScheduleMap.setSeName(CalendarTempWorkSchedule.SENAME);
		calendarTempWorkScheduleMap
				.setNodeName(CalendarTempWorkSchedule.NODENAME);
		calendarTempWorkScheduleMap
				.setNodeInstID(CalendarTempWorkSchedule.NODENAME);
		calendarTempWorkScheduleMap.setHostNodeFlag(true);
		Class<?>[] calendarTempWorkScheduleConvToUIParas = {
				CalendarTempWorkSchedule.class,
				CalendarTempWorkScheduleUIModel.class };
		calendarTempWorkScheduleMap
				.setConvToUIMethodParas(calendarTempWorkScheduleConvToUIParas);
		calendarTempWorkScheduleMap
				.setConvToUIMethod(CalendarTemplateManager.METHOD_ConvCalendarTempWorkScheduleToUI);
		Class<?>[] CalendarTempWorkScheduleConvUIToParas = {
				CalendarTempWorkScheduleUIModel.class,
				CalendarTempWorkSchedule.class };
		calendarTempWorkScheduleMap
				.setConvUIToMethodParas(CalendarTempWorkScheduleConvUIToParas);
		calendarTempWorkScheduleMap
				.setConvUIToMethod(CalendarTemplateManager.METHOD_ConvUIToCalendarTempWorkSchedule);
		uiModelNodeMapList.add(calendarTempWorkScheduleMap);
		calendarTempWorkScheduleExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(calendarTempWorkScheduleExtensionUnion);
		return resultList;
	}

}
