package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.CalendarTempWorkScheduleUIModel;
import com.company.IntelligentPlatform.platform.dto.CalendarTemplateItemUIModel;
import com.company.IntelligentPlatform.platform.dto.CalendarTemplateUIModel;
import com.company.IntelligentPlatform.platform.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.platform.model.CalendarTempWorkSchedule;
import com.company.IntelligentPlatform.platform.model.CalendarTemplate;
import com.company.IntelligentPlatform.platform.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class CalendarTemplateServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = CalendarTemplate.NODENAME, nodeInstId = CalendarTemplate.SENAME, convToUIMethod = CalendarTemplateManager.METHOD_ConvCalendarTemplateToUI, convUIToMethod = CalendarTemplateManager.METHOD_ConvUIToCalendarTemplate)
	protected CalendarTemplateUIModel calendarTemplateUIModel;

	@IServiceUIModuleFieldConfig(nodeName = CalendarTempWorkSchedule.NODENAME, nodeInstId = CalendarTempWorkSchedule.NODENAME, convToUIMethod = CalendarTemplateManager.METHOD_ConvCalendarTempWorkScheduleToUI, convUIToMethod = CalendarTemplateManager.METHOD_ConvUIToCalendarTempWorkSchedule)
	protected List<CalendarTempWorkScheduleUIModel> calendarTempWorkScheduleUIModelList = new ArrayList<CalendarTempWorkScheduleUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = CalendarTemplateItem.NODENAME, nodeInstId = CalendarTemplateItem.NODENAME, convToUIMethod = CalendarTemplateManager.METHOD_ConvCalendarTemplateItemToUI, convUIToMethod = CalendarTemplateManager.METHOD_ConvUIToCalendarTemplateItem)
	protected List<CalendarTemplateItemUIModel> calendarTemplateItemUIModelList = new ArrayList<CalendarTemplateItemUIModel>();

	public CalendarTemplateUIModel getCalendarTemplateUIModel() {
		return this.calendarTemplateUIModel;
	}

	public void setCalendarTemplateUIModel(
			CalendarTemplateUIModel calendarTemplateUIModel) {
		this.calendarTemplateUIModel = calendarTemplateUIModel;
	}

	public List<CalendarTempWorkScheduleUIModel> getCalendarTempWorkScheduleUIModelList() {
		return this.calendarTempWorkScheduleUIModelList;
	}

	public void setCalendarTempWorkScheduleUIModelList(
			List<CalendarTempWorkScheduleUIModel> calendarTempWorkScheduleUIModelList) {
		this.calendarTempWorkScheduleUIModelList = calendarTempWorkScheduleUIModelList;
	}

	public List<CalendarTemplateItemUIModel> getCalendarTemplateItemUIModelList() {
		return this.calendarTemplateItemUIModelList;
	}

	public void setCalendarTemplateItemUIModelList(
			List<CalendarTemplateItemUIModel> calendarTemplateItemUIModelList) {
		this.calendarTemplateItemUIModelList = calendarTemplateItemUIModelList;
	}

}
