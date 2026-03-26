package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.service.RepairProdTargetMatItemManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderManager;
import com.company.IntelligentPlatform.production.model.RepairProdTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdTargetMatItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected RepairProdTargetMatItemManager repairProdTargetMatItemManager;
	
	@Autowired
	protected RepairProdTarSubItemServiceUIModelExtension repairProdTarSubItemServiceUIModelExtension;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(repairProdTarSubItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				RepairProdTargetItemAttachment.SENAME,
				RepairProdTargetItemAttachment.NODENAME,
				RepairProdTargetItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion repairProdTargetMatItemExtensionUnion = new ServiceUIModelExtensionUnion();
		repairProdTargetMatItemExtensionUnion
				.setNodeInstId(RepairProdTargetMatItem.NODENAME);
		repairProdTargetMatItemExtensionUnion
				.setNodeName(RepairProdTargetMatItem.NODENAME);

		// UI Model Configure of node:[RepairProdTargetMatItem]
		UIModelNodeMapConfigure repairProdTargetMatItemMap = new UIModelNodeMapConfigure();
		repairProdTargetMatItemMap.setSeName(RepairProdTargetMatItem.SENAME);
		repairProdTargetMatItemMap.setNodeName(RepairProdTargetMatItem.NODENAME);
		repairProdTargetMatItemMap
				.setNodeInstID(RepairProdTargetMatItem.NODENAME);
		repairProdTargetMatItemMap.setHostNodeFlag(true);
		repairProdTargetMatItemMap.setLogicManager(repairProdTargetMatItemManager);
		Class<?>[] repairProdTargetMatItemConvToUIParas = {
				RepairProdTargetMatItem.class,
				RepairProdTargetMatItemUIModel.class };
		repairProdTargetMatItemMap
				.setConvToUIMethodParas(repairProdTargetMatItemConvToUIParas);
		repairProdTargetMatItemMap
				.setConvToUIMethod(RepairProdTargetMatItemManager.METHOD_ConvRepairProdTargetMatItemToUI);
		Class<?>[] RepairProdTargetMatItemConvUIToParas = {
				RepairProdTargetMatItemUIModel.class,
				RepairProdTargetMatItem.class };
		repairProdTargetMatItemMap
				.setConvUIToMethodParas(RepairProdTargetMatItemConvUIToParas);
		repairProdTargetMatItemMap
				.setConvUIToMethod(RepairProdTargetMatItemManager.METHOD_ConvUIToRepairProdTargetMatItem);
		uiModelNodeMapList.add(repairProdTargetMatItemMap);

		// UI Model Configure of node:[RepairProdOrder]
		UIModelNodeMapConfigure repairProdOrderMap = new UIModelNodeMapConfigure();
		repairProdOrderMap.setSeName(RepairProdOrder.SENAME);
		repairProdOrderMap.setNodeName(RepairProdOrder.NODENAME);
		repairProdOrderMap.setNodeInstID(RepairProdOrder.SENAME);
		repairProdOrderMap.setBaseNodeInstID(RepairProdTargetMatItem.NODENAME);
		repairProdOrderMap.setLogicManager(repairProdTargetMatItemManager);
		repairProdOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
		Class<?>[] repairProdOrderConvToUIParas = {
				RepairProdOrder.class, RepairProdTargetMatItemUIModel.class };
		repairProdOrderMap.setConvToUIMethodParas(repairProdOrderConvToUIParas);
		repairProdOrderMap
				.setConvToUIMethod(RepairProdTargetMatItemManager.METHOD_ConvRepairProdOrderToTargetItemUI);
		uiModelNodeMapList.add(repairProdOrderMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(RepairProdTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(RepairProdTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(RepairProdTargetMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(RepairProdTargetMatItem.NODENAME));

		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, RepairProdTargetMatItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						RepairProdTargetMatItem.NODENAME,
						RepairProdTargetMatItemManager.METHOD_ConvMaterialSKUToTargetItemUI, repairProdTargetMatItemManager,
						materialStockKeepUnitConvToUIParas));

		repairProdTargetMatItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(repairProdTargetMatItemExtensionUnion);
		return resultList;
	}

}
