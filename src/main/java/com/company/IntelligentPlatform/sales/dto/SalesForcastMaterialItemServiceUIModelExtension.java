package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.service.SalesForcastMaterialItemManager;
import com.company.IntelligentPlatform.sales.service.SalesForcastManager;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesForcastMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SalesForcastManager salesForcastManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected SalesForcastMaterialItemManager salesForcastMaterialItemManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				SalesForcastMaterialItemAttachment.SENAME,
				SalesForcastMaterialItemAttachment.NODENAME,
				SalesForcastMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion salesForcastMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		salesForcastMaterialItemExtensionUnion
				.setNodeInstId(SalesForcastMaterialItem.NODENAME);
		salesForcastMaterialItemExtensionUnion
				.setNodeName(SalesForcastMaterialItem.NODENAME);

		// UI Model Configure of node:[SalesForcastMaterialItem]
		UIModelNodeMapConfigure salesForcastMaterialItemMap = new UIModelNodeMapConfigure();
		salesForcastMaterialItemMap.setSeName(SalesForcastMaterialItem.SENAME);
		salesForcastMaterialItemMap.setNodeName(SalesForcastMaterialItem.NODENAME);
		salesForcastMaterialItemMap.setNodeInstID(SalesForcastMaterialItem.NODENAME);
		salesForcastMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] salesForcastMaterialItemConvToUIParas = {
				SalesForcastMaterialItem.class, SalesForcastMaterialItemUIModel.class };
		salesForcastMaterialItemMap
				.setConvToUIMethodParas(salesForcastMaterialItemConvToUIParas);
		salesForcastMaterialItemMap
				.setConvToUIMethod(SalesForcastMaterialItemManager.METHOD_ConvSalesForcastMaterialItemToUI);
		salesForcastMaterialItemMap.setLogicManager(salesForcastMaterialItemManager);
		Class<?>[] salesForcastMaterialItemConvUIToParas = {
				SalesForcastMaterialItemUIModel.class, SalesForcastMaterialItem.class };
		salesForcastMaterialItemMap
				.setConvUIToMethodParas(salesForcastMaterialItemConvUIToParas);
		salesForcastMaterialItemMap
				.setConvUIToMethod(SalesForcastMaterialItemManager.METHOD_ConvUIToSalesForcastMaterialItem);
		uiModelNodeMapList.add(salesForcastMaterialItemMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(SalesForcastMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(SalesForcastMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(SalesForcastMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(SalesForcastMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(SalesForcastMaterialItem.NODENAME));
		// UI Model Configure of node:[SalesForcast]
		Class<?>[] convParentDocToUIMethodParas = {SalesForcast.class, SalesForcastMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(SalesForcastMaterialItem.NODENAME,
				SalesForcastMaterialItemManager.METHOD_ConvParentDocToItemUI, salesForcastMaterialItemManager, convParentDocToUIMethodParas));
		salesForcastMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		
		resultList.add(salesForcastMaterialItemExtensionUnion);
		return resultList;
	}

}