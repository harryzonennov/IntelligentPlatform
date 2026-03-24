package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderItemManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderManager;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SimpleDocConfigurePara;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdItemReqProposalServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected RepairProdOrderItemServiceUIModelExtension repairProdOrderItemServiceUIModelExtension;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected RepairProdOrderItemManager repairProdOrderItemManager;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(repairProdOrderItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion repairProdItemReqProposalExtensionUnion = new ServiceUIModelExtensionUnion();
		repairProdItemReqProposalExtensionUnion
				.setNodeInstId(RepairProdItemReqProposal.NODENAME);
		repairProdItemReqProposalExtensionUnion
				.setNodeName(RepairProdItemReqProposal.NODENAME);

		// UI Model Configure of node:[RepairProdItemReqProposal]
		UIModelNodeMapConfigure repairProdItemReqProposalMap = new UIModelNodeMapConfigure();
		repairProdItemReqProposalMap.setSeName(RepairProdItemReqProposal.SENAME);
		repairProdItemReqProposalMap
				.setNodeName(RepairProdItemReqProposal.NODENAME);
		repairProdItemReqProposalMap
				.setNodeInstID(RepairProdItemReqProposal.NODENAME);
		repairProdItemReqProposalMap.setLogicManager(repairProdOrderItemManager);
		repairProdItemReqProposalMap.setHostNodeFlag(true);
		Class<?>[] repairProdItemReqProposalConvToUIParas = {
				RepairProdItemReqProposal.class,
				RepairProdItemReqProposalUIModel.class };
		repairProdItemReqProposalMap
				.setConvToUIMethodParas(repairProdItemReqProposalConvToUIParas);
		repairProdItemReqProposalMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvRepairProdItemReqProposalToUI);
		Class<?>[] RepairProdItemReqProposalConvUIToParas = {
				RepairProdItemReqProposalUIModel.class,
				RepairProdItemReqProposal.class };
		repairProdItemReqProposalMap
				.setConvUIToMethodParas(RepairProdItemReqProposalConvUIToParas);
		repairProdItemReqProposalMap
				.setConvUIToMethod(RepairProdOrderItemManager.METHOD_ConvUIToRepairProdItemReqProposal);
		uiModelNodeMapList.add(repairProdItemReqProposalMap);

		UIModelNodeMapConfigure repairProdOrderItemMap = new UIModelNodeMapConfigure();
		repairProdOrderItemMap.setSeName(RepairProdOrderItem.SENAME);
		repairProdOrderItemMap.setNodeName(RepairProdOrderItem.NODENAME);
		repairProdOrderItemMap.setNodeInstID(RepairProdOrderItem.NODENAME);
		repairProdOrderItemMap.setLogicManager(repairProdOrderItemManager);
		repairProdOrderItemMap.setHostNodeFlag(false);
		repairProdOrderItemMap
				.setBaseNodeInstID(RepairProdItemReqProposal.NODENAME);
		repairProdOrderItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] repairProdOrderItemConvToUIParas = {
				RepairProdOrderItem.class,
				RepairProdItemReqProposalUIModel.class };
		repairProdOrderItemMap
				.setConvToUIMethodParas(repairProdOrderItemConvToUIParas);
		repairProdOrderItemMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvRepairProdOrderItemToProposalUI);
		uiModelNodeMapList.add(repairProdOrderItemMap);

		UIModelNodeMapConfigure repairProdOrderMap = new UIModelNodeMapConfigure();
		repairProdOrderMap.setSeName(RepairProdOrder.SENAME);
		repairProdOrderMap.setNodeName(RepairProdOrder.NODENAME);
		repairProdOrderMap.setNodeInstID(RepairProdOrder.SENAME);
		repairProdOrderMap.setLogicManager(repairProdOrderItemManager);
		repairProdOrderMap.setHostNodeFlag(false);
		repairProdOrderMap.setBaseNodeInstID(RepairProdOrderItem.NODENAME);
		repairProdOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] repairProdOrderConvToUIParas = { RepairProdOrder.class,
				RepairProdItemReqProposalUIModel.class };
		repairProdOrderMap.setConvToUIMethodParas(repairProdOrderConvToUIParas);
		repairProdOrderMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvRepairProdOrderToProposalUI);
		uiModelNodeMapList.add(repairProdOrderMap);

		// UI Model Configure of node:[refPickingRefMaterialItemMap]
		UIModelNodeMapConfigure refPickingRefMaterialItemMap = new UIModelNodeMapConfigure();
		refPickingRefMaterialItemMap
				.setGetSENodeCallback(rawSENode -> {
					RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) rawSENode;
					ServiceEntityNode documentItemNode = null;
					try {
						documentItemNode = prodPickingOrderManager
								.getEntityNodeByKey(
										repairProdItemReqProposal.getRefUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ProdPickingRefMaterialItem.NODENAME,
										repairProdItemReqProposal.getClient(),
										null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		refPickingRefMaterialItemMap
				.setNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		refPickingRefMaterialItemMap
				.setBaseNodeInstID(RepairProdItemReqProposal.NODENAME);
		refPickingRefMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);

		Class<?>[] refDocConvToUIParas = { ServiceEntityNode.class,
				RepairProdItemReqProposalUIModel.class };
		refPickingRefMaterialItemMap.setLogicManager(repairProdOrderItemManager);
		refPickingRefMaterialItemMap
				.setConvToUIMethod(RepairProdOrderItemManager.METHOD_ConvDocumentToItemReqProposalUI);
		refPickingRefMaterialItemMap
				.setConvToUIMethodParas(refDocConvToUIParas);
		// uiModelNodeMapList.add(refPickingRefMaterialItemMap);

		SimpleDocConfigurePara simpleDocConfigurePara = new SimpleDocConfigurePara(
				RepairProdItemReqProposal.NODENAME,
				RepairProdOrderItemManager.METHOD_ConvDocumentToItemReqProposalUI,
				refDocConvToUIParas, repairProdOrderItemManager);
		simpleDocConfigurePara
				.setDocMatItemGetCallback(rawSENode -> {
					RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) rawSENode;
					int documentType = repairProdItemReqProposal
							.getDocumentType();
					ServiceEntityNode specDocMatItemNode = docFlowProxy
							.getDefDocItemNode(documentType,
									repairProdItemReqProposal.getRefUUID(),
									repairProdItemReqProposal.getClient());
					return specDocMatItemNode;
				});

		simpleDocConfigurePara
				.setDocGetCallback(rawSENode -> {
					RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) rawSENode;
					int documentType = repairProdItemReqProposal
							.getDocumentType();
					ServiceEntityNode specDocMatItemNode = docFlowProxy
							.getDirectDocContentNode(documentType,
									repairProdItemReqProposal.getRefUUID(),
									repairProdItemReqProposal.getClient());
					return specDocMatItemNode;
				});

		uiModelNodeMapList.addAll(docFlowProxy
				.getSpecNodeMapConfigureList(simpleDocConfigurePara));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(RepairProdItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(RepairProdItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(RepairProdItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(RepairProdItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(RepairProdItemReqProposal.NODENAME));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				ProdOrderItemReqProposalUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				RepairProdItemReqProposal.NODENAME, repairProdOrderItemManager,
				RepairProdOrderItemManager.METHOD_ConvWarehouseToItemReqProposalUI,
				warehouseConvToUIParas,
				null, null
		)));

		repairProdItemReqProposalExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(repairProdItemReqProposalExtensionUnion);
		return resultList;
	}

}
