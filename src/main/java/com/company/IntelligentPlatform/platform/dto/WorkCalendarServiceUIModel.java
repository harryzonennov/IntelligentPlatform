package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.WorkCalendarDayItemUIModel;
import com.company.IntelligentPlatform.platform.dto.WorkCalendarUIModel;
import com.company.IntelligentPlatform.platform.service.WorkCalendarManager;
import com.company.IntelligentPlatform.platform.model.WorkCalendar;
import com.company.IntelligentPlatform.platform.model.WorkCalendarDayItem;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class WorkCalendarServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WorkCalendar.NODENAME, nodeInstId = WorkCalendar.SENAME, convToUIMethod = WorkCalendarManager.METHOD_ConvWorkCalendarToUI, convUIToMethod = WorkCalendarManager.METHOD_ConvUIToWorkCalendar)
	protected WorkCalendarUIModel workCalendarUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WorkCalendarDayItem.NODENAME, nodeInstId = WorkCalendarDayItem.NODENAME, convToUIMethod = WorkCalendarManager.METHOD_ConvWorkCalendarDayItemToUI, convUIToMethod = WorkCalendarManager.METHOD_ConvUIToWorkCalendarDayItem)
	protected List<WorkCalendarDayItemUIModel> workCalendarDayItemUIModelList = new ArrayList<WorkCalendarDayItemUIModel>();

	public WorkCalendarUIModel getWorkCalendarUIModel() {
		return this.workCalendarUIModel;
	}

	public void setWorkCalendarUIModel(WorkCalendarUIModel workCalendarUIModel) {
		this.workCalendarUIModel = workCalendarUIModel;
	}

	public List<WorkCalendarDayItemUIModel> getWorkCalendarDayItemUIModelList() {
		return this.workCalendarDayItemUIModelList;
	}

	public void setWorkCalendarDayItemUIModelList(
			List<WorkCalendarDayItemUIModel> workCalendarDayItemUIModelList) {
		this.workCalendarDayItemUIModelList = workCalendarDayItemUIModelList;
	}

}
