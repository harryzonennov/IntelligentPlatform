package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderReportAttachment;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class ProdOrderReportAttachmentServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionOrderAttachmentExtensionUnion = new ServiceUIModelExtensionUnion();
		productionOrderAttachmentExtensionUnion.setNodeInstId(ProdOrderReportAttachment.NODENAME);
		productionOrderAttachmentExtensionUnion.setNodeName(ProdOrderReportAttachment.NODENAME);

		// UI Model Configure of node:[ProdOrderReportAttachment]
		UIModelNodeMapConfigure productionOrderAttachmentMap = new UIModelNodeMapConfigure();
		productionOrderAttachmentMap.setSeName(ProdOrderReportAttachment.SENAME);
		productionOrderAttachmentMap.setNodeName(ProdOrderReportAttachment.NODENAME);
		productionOrderAttachmentMap.setNodeInstID(ProdOrderReportAttachment.NODENAME);
		productionOrderAttachmentMap.setHostNodeFlag(true);
		Class<?>[] productionOrderAttachConvToUIParas = { ProdOrderReportAttachment.class,
				ProdOrderReportAttachmentUIModel.class };
		productionOrderAttachmentMap.setConvToUIMethodParas(productionOrderAttachConvToUIParas);
//		productionOrderAttachmentMap
//				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvProdOrderReportAttachmentToUI);
		uiModelNodeMapList.add(productionOrderAttachmentMap);
		productionOrderAttachmentExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionOrderAttachmentExtensionUnion);
		return resultList;
	}

}
