package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CalendarTemplateItemUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class CalendarTemplateItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion calendarTemplateItemExtensionUnion = new ServiceUIModelExtensionUnion();
		calendarTemplateItemExtensionUnion
				.setNodeInstId(CalendarTemplateItem.NODENAME);
		calendarTemplateItemExtensionUnion
				.setNodeName(CalendarTemplateItem.NODENAME);

		// UI Model Configure of node:[CalendarTemplateItem]
		UIModelNodeMapConfigure calendarTemplateItemMap = new UIModelNodeMapConfigure();
		calendarTemplateItemMap.setSeName(CalendarTemplateItem.SENAME);
		calendarTemplateItemMap.setNodeName(CalendarTemplateItem.NODENAME);
		calendarTemplateItemMap.setNodeInstID(CalendarTemplateItem.NODENAME);
		calendarTemplateItemMap.setHostNodeFlag(true);
		Class<?>[] calendarTemplateItemConvToUIParas = {
				CalendarTemplateItem.class, CalendarTemplateItemUIModel.class };
		calendarTemplateItemMap
				.setConvToUIMethodParas(calendarTemplateItemConvToUIParas);
		calendarTemplateItemMap
				.setConvToUIMethod(CalendarTemplateManager.METHOD_ConvCalendarTemplateItemToUI);
		Class<?>[] CalendarTemplateItemConvUIToParas = {
				CalendarTemplateItemUIModel.class, CalendarTemplateItem.class };
		calendarTemplateItemMap
				.setConvUIToMethodParas(CalendarTemplateItemConvUIToParas);
		calendarTemplateItemMap
				.setConvUIToMethod(CalendarTemplateManager.METHOD_ConvUIToCalendarTemplateItem);
		uiModelNodeMapList.add(calendarTemplateItemMap);
		calendarTemplateItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(calendarTemplateItemExtensionUnion);
		return resultList;
	}

}
