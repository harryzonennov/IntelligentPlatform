package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class FinAccountAttachmentServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountAttachmentExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountAttachmentExtensionUnion.setNodeInstId(FinAccountAttachment.NODENAME);
		finAccountAttachmentExtensionUnion.setNodeName(FinAccountAttachment.NODENAME);

		// UI Model Configure of node:[FinAccountAttachment]
		UIModelNodeMapConfigure finAccountAttachmentMap = new UIModelNodeMapConfigure();
		finAccountAttachmentMap.setSeName(FinAccountAttachment.SENAME);
		finAccountAttachmentMap.setNodeName(FinAccountAttachment.NODENAME);
		finAccountAttachmentMap.setNodeInstID(FinAccountAttachment.NODENAME);
		finAccountAttachmentMap.setHostNodeFlag(true);
		Class<?>[] productionOrderAttachConvToUIParas = { FinAccountAttachment.class,
				FinAccountAttachmentUIModel.class };
		finAccountAttachmentMap.setConvToUIMethodParas(productionOrderAttachConvToUIParas);
		finAccountAttachmentMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountAttachmentToUI);
		uiModelNodeMapList.add(finAccountAttachmentMap);
		finAccountAttachmentExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountAttachmentExtensionUnion);
		return resultList;
	}

}
