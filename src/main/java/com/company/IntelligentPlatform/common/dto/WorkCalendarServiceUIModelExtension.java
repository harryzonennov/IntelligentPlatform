package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.WorkCalendarUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.service.WorkCalendarManager;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class WorkCalendarServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WorkCalendarDayItemServiceUIModelExtension workCalendarDayItemServiceUIModelExtension;

	@Autowired
	protected WorkCalendarManager workCalendarManager;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(workCalendarDayItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion workCalendarExtensionUnion = new ServiceUIModelExtensionUnion();
		workCalendarExtensionUnion.setNodeInstId(WorkCalendar.SENAME);
		workCalendarExtensionUnion.setNodeName(WorkCalendar.NODENAME);

		// UI Model Configure of node:[WorkCalendar]
		UIModelNodeMapConfigure workCalendarMap = new UIModelNodeMapConfigure();
		workCalendarMap.setSeName(WorkCalendar.SENAME);
		workCalendarMap.setNodeName(WorkCalendar.NODENAME);
		workCalendarMap.setNodeInstID(WorkCalendar.SENAME);
		workCalendarMap.setHostNodeFlag(true);
		Class<?>[] workCalendarConvToUIParas = { WorkCalendar.class,
				WorkCalendarUIModel.class };
		workCalendarMap.setConvToUIMethodParas(workCalendarConvToUIParas);
		workCalendarMap
				.setConvToUIMethod(WorkCalendarManager.METHOD_ConvWorkCalendarToUI);
		Class<?>[] WorkCalendarConvUIToParas = { WorkCalendarUIModel.class,
				WorkCalendar.class };
		workCalendarMap.setConvUIToMethodParas(WorkCalendarConvUIToParas);
		workCalendarMap
				.setConvUIToMethod(WorkCalendarManager.METHOD_ConvUIToWorkCalendar);
		uiModelNodeMapList.add(workCalendarMap);

		// UI Model Configure of node:[CalendarTemplate]
		UIModelNodeMapConfigure calendarTemplateMap = new UIModelNodeMapConfigure();
		calendarTemplateMap.setSeName(CalendarTemplate.SENAME);
		calendarTemplateMap.setNodeName(CalendarTemplate.NODENAME);
		calendarTemplateMap.setNodeInstID(CalendarTemplate.SENAME);
		calendarTemplateMap.setBaseNodeInstID(WorkCalendar.SENAME);
		calendarTemplateMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		calendarTemplateMap.setServiceEntityManager(calendarTemplateManager);
		Class<?>[] calendarTemplateConvToUIParas = { CalendarTemplate.class,
				WorkCalendarUIModel.class };
		calendarTemplateMap
				.setConvToUIMethodParas(calendarTemplateConvToUIParas);
		List<SearchConfigConnectCondition> calendarTemplateConditionList = new ArrayList<>();
		SearchConfigConnectCondition calendarTemplateCondition0 = new SearchConfigConnectCondition();
		calendarTemplateCondition0
				.setSourceFieldName("refTemplateUUID");
		calendarTemplateCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		calendarTemplateConditionList.add(calendarTemplateCondition0);
		calendarTemplateMap
				.setConnectionConditions(calendarTemplateConditionList);
		calendarTemplateMap
				.setConvToUIMethod(WorkCalendarManager.METHOD_ConvCalendarTemplateToUI);
		uiModelNodeMapList.add(calendarTemplateMap);
		workCalendarExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(workCalendarExtensionUnion);
		return resultList;
	}

}
