package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.WorkCalendarDayItemUIModel;
import com.company.IntelligentPlatform.common.dto.WorkCalendarUIModel;
import com.company.IntelligentPlatform.common.service.WorkCalendarManager;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.model.WorkCalendarDayItem;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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
