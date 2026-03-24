package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CalendarTemplateUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class CalendarTemplateServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected CalendarTempWorkScheduleServiceUIModelExtension calendarTempWorkScheduleServiceUIModelExtension;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	@Autowired
	protected CalendarTemplateItemServiceUIModelExtension calendarTemplateItemServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(calendarTempWorkScheduleServiceUIModelExtension);
		resultList.add(calendarTemplateItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion calendarTemplateExtensionUnion = new ServiceUIModelExtensionUnion();
		calendarTemplateExtensionUnion.setNodeInstId(CalendarTemplate.SENAME);
		calendarTemplateExtensionUnion.setNodeName(CalendarTemplate.NODENAME);

		// UI Model Configure of node:[CalendarTemplate]
		UIModelNodeMapConfigure calendarTemplateMap = new UIModelNodeMapConfigure();
		calendarTemplateMap.setSeName(CalendarTemplate.SENAME);
		calendarTemplateMap.setNodeName(CalendarTemplate.NODENAME);
		calendarTemplateMap.setNodeInstID(CalendarTemplate.SENAME);
		calendarTemplateMap.setHostNodeFlag(true);
		Class<?>[] calendarTemplateConvToUIParas = { CalendarTemplate.class,
				CalendarTemplateUIModel.class };
		calendarTemplateMap
				.setConvToUIMethodParas(calendarTemplateConvToUIParas);
		calendarTemplateMap
				.setConvToUIMethod(CalendarTemplateManager.METHOD_ConvCalendarTemplateToUI);
		Class<?>[] CalendarTemplateConvUIToParas = {
				CalendarTemplateUIModel.class, CalendarTemplate.class };
		calendarTemplateMap
				.setConvUIToMethodParas(CalendarTemplateConvUIToParas);
		calendarTemplateMap
				.setConvUIToMethod(CalendarTemplateManager.METHOD_ConvUIToCalendarTemplate);
		uiModelNodeMapList.add(calendarTemplateMap);
		calendarTemplateExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(calendarTemplateExtensionUnion);
		return resultList;
	}

}
