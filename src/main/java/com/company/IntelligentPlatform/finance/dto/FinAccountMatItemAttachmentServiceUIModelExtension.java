package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class FinAccountMatItemAttachmentServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountMatItemAttachmentExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountMatItemAttachmentExtensionUnion.setNodeInstId(FinAccountMatItemAttachment.NODENAME);
		finAccountMatItemAttachmentExtensionUnion.setNodeName(FinAccountMatItemAttachment.NODENAME);

		// UI Model Configure of node:[FinAccountMatItemAttachment]
		UIModelNodeMapConfigure finAccountMatItemAttachmentMap = new UIModelNodeMapConfigure();
		finAccountMatItemAttachmentMap.setSeName(FinAccountMatItemAttachment.SENAME);
		finAccountMatItemAttachmentMap.setNodeName(FinAccountMatItemAttachment.NODENAME);
		finAccountMatItemAttachmentMap.setNodeInstID(FinAccountMatItemAttachment.NODENAME);
		finAccountMatItemAttachmentMap.setHostNodeFlag(true);
		Class<?>[] finAccountMatItemAttachmentConvToUIParas = { FinAccountMatItemAttachment.class,
				FinAccountMatItemAttachmentUIModel.class };
		finAccountMatItemAttachmentMap.setConvToUIMethodParas(finAccountMatItemAttachmentConvToUIParas);
		finAccountMatItemAttachmentMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountMatItemAttachmentToUI);
		uiModelNodeMapList.add(finAccountMatItemAttachmentMap);
		finAccountMatItemAttachmentExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountMatItemAttachmentExtensionUnion);
		return resultList;
	}

}
