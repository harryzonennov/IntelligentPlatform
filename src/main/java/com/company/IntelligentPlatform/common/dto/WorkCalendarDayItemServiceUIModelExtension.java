package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.WorkCalendarDayItemUIModel;
import com.company.IntelligentPlatform.common.service.WorkCalendarManager;
import com.company.IntelligentPlatform.common.model.WorkCalendarDayItem;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class WorkCalendarDayItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion workCalendarDayItemExtensionUnion = new ServiceUIModelExtensionUnion();
		workCalendarDayItemExtensionUnion
				.setNodeInstId(WorkCalendarDayItem.NODENAME);
		workCalendarDayItemExtensionUnion
				.setNodeName(WorkCalendarDayItem.NODENAME);

		// UI Model Configure of node:[WorkCalendarDayItem]
		UIModelNodeMapConfigure workCalendarDayItemMap = new UIModelNodeMapConfigure();
		workCalendarDayItemMap.setSeName(WorkCalendarDayItem.SENAME);
		workCalendarDayItemMap.setNodeName(WorkCalendarDayItem.NODENAME);
		workCalendarDayItemMap.setNodeInstID(WorkCalendarDayItem.NODENAME);
		workCalendarDayItemMap.setHostNodeFlag(true);
		Class<?>[] workCalendarDayItemConvToUIParas = {
				WorkCalendarDayItem.class, WorkCalendarDayItemUIModel.class };
		workCalendarDayItemMap
				.setConvToUIMethodParas(workCalendarDayItemConvToUIParas);
		workCalendarDayItemMap
				.setConvToUIMethod(WorkCalendarManager.METHOD_ConvWorkCalendarDayItemToUI);
		Class<?>[] WorkCalendarDayItemConvUIToParas = {
				WorkCalendarDayItemUIModel.class, WorkCalendarDayItem.class };
		workCalendarDayItemMap
				.setConvUIToMethodParas(WorkCalendarDayItemConvUIToParas);
		workCalendarDayItemMap
				.setConvUIToMethod(WorkCalendarManager.METHOD_ConvUIToWorkCalendarDayItem);
		uiModelNodeMapList.add(workCalendarDayItemMap);
		workCalendarDayItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(workCalendarDayItemExtensionUnion);
		return resultList;
	}

}
