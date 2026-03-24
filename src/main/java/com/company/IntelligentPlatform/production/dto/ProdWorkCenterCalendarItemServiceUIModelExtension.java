package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdWorkCenterCalendarItemUIModel;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class ProdWorkCenterCalendarItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodWorkCenterCalendarItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodWorkCenterCalendarItemExtensionUnion
				.setNodeInstId(ProdWorkCenterCalendarItem.NODENAME);
		prodWorkCenterCalendarItemExtensionUnion
				.setNodeName(ProdWorkCenterCalendarItem.NODENAME);

		// UI Model Configure of node:[ProdWorkCenterCalendarItem]
		UIModelNodeMapConfigure prodWorkCenterCalendarItemMap = new UIModelNodeMapConfigure();
		prodWorkCenterCalendarItemMap
				.setSeName(ProdWorkCenterCalendarItem.SENAME);
		prodWorkCenterCalendarItemMap
				.setNodeName(ProdWorkCenterCalendarItem.NODENAME);
		prodWorkCenterCalendarItemMap
				.setNodeInstID(ProdWorkCenterCalendarItem.NODENAME);
		prodWorkCenterCalendarItemMap.setHostNodeFlag(true);
		Class<?>[] prodWorkCenterCalendarItemConvToUIParas = {
				ProdWorkCenterCalendarItem.class,
				ProdWorkCenterCalendarItemUIModel.class };
		prodWorkCenterCalendarItemMap
				.setConvToUIMethodParas(prodWorkCenterCalendarItemConvToUIParas);
		prodWorkCenterCalendarItemMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvProdWorkCenterCalendarItemToUI);
		Class<?>[] ProdWorkCenterCalendarItemConvUIToParas = {
				ProdWorkCenterCalendarItemUIModel.class,
				ProdWorkCenterCalendarItem.class };
		prodWorkCenterCalendarItemMap
				.setConvUIToMethodParas(ProdWorkCenterCalendarItemConvUIToParas);
		prodWorkCenterCalendarItemMap
				.setConvUIToMethod(ProdWorkCenterManager.METHOD_ConvUIToProdWorkCenterCalendarItem);
		uiModelNodeMapList.add(prodWorkCenterCalendarItemMap);
		prodWorkCenterCalendarItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodWorkCenterCalendarItemExtensionUnion);
		return resultList;
	}

}
