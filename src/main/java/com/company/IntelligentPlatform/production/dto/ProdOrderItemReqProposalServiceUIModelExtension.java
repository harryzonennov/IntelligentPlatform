package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SimpleDocConfigurePara;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdOrderItemReqProposalServiceUIModelExtension extends
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
	protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(productionOrderItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodOrderItemReqProposalExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderItemReqProposalExtensionUnion
				.setNodeInstId(ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposalExtensionUnion
				.setNodeName(ProdOrderItemReqProposal.NODENAME);

		// UI Model Configure of node:[ProdOrderItemReqProposal]
		UIModelNodeMapConfigure prodOrderItemReqProposalMap = new UIModelNodeMapConfigure();
		prodOrderItemReqProposalMap.setSeName(ProdOrderItemReqProposal.SENAME);
		prodOrderItemReqProposalMap
				.setNodeName(ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposalMap
				.setNodeInstID(ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposalMap.setLogicManager(productionOrderItemManager);
		prodOrderItemReqProposalMap.setHostNodeFlag(true);
		Class<?>[] prodOrderItemReqProposalConvToUIParas = {
				ProdOrderItemReqProposal.class,
				ProdOrderItemReqProposalUIModel.class };
		prodOrderItemReqProposalMap
				.setConvToUIMethodParas(prodOrderItemReqProposalConvToUIParas);
		prodOrderItemReqProposalMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvProdOrderItemReqProposalToUI);
		Class<?>[] ProdOrderItemReqProposalConvUIToParas = {
				ProdOrderItemReqProposalUIModel.class,
				ProdOrderItemReqProposal.class };
		prodOrderItemReqProposalMap
				.setConvUIToMethodParas(ProdOrderItemReqProposalConvUIToParas);
		prodOrderItemReqProposalMap
				.setConvUIToMethod(ProductionOrderItemManager.METHOD_ConvUIToProdOrderItemReqProposal);
		uiModelNodeMapList.add(prodOrderItemReqProposalMap);

		UIModelNodeMapConfigure productionOrderItemMap = new UIModelNodeMapConfigure();
		productionOrderItemMap.setSeName(ProductionOrderItem.SENAME);
		productionOrderItemMap.setNodeName(ProductionOrderItem.NODENAME);
		productionOrderItemMap.setNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderItemMap.setLogicManager(productionOrderItemManager);
		productionOrderItemMap.setHostNodeFlag(false);
		productionOrderItemMap
				.setBaseNodeInstID(ProdOrderItemReqProposal.NODENAME);
		productionOrderItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionOrderItemConvToUIParas = {
				ProductionOrderItem.class,
				ProdOrderItemReqProposalUIModel.class };
		productionOrderItemMap
				.setConvToUIMethodParas(productionOrderItemConvToUIParas);
		productionOrderItemMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderItemToProposalUI);
		uiModelNodeMapList.add(productionOrderItemMap);

		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setLogicManager(productionOrderItemManager);
		productionOrderMap.setHostNodeFlag(false);
		productionOrderMap.setBaseNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProdOrderItemReqProposalUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderToProposalUI);
		uiModelNodeMapList.add(productionOrderMap);

		// UI Model Configure of node:[refPickingRefMaterialItemMap]
		UIModelNodeMapConfigure refPickingRefMaterialItemMap = new UIModelNodeMapConfigure();
		refPickingRefMaterialItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) rawSENode;
					ServiceEntityNode documentItemNode = null;
					try {
						documentItemNode = prodPickingOrderManager
								.getEntityNodeByKey(
										prodOrderItemReqProposal.getRefUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ProdPickingRefMaterialItem.NODENAME,
										prodOrderItemReqProposal.getClient(),
										null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		refPickingRefMaterialItemMap
				.setNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		refPickingRefMaterialItemMap
				.setBaseNodeInstID(ProdOrderItemReqProposal.NODENAME);
		refPickingRefMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);

		Class<?>[] refDocConvToUIParas = { ServiceEntityNode.class,
				ProdOrderItemReqProposalUIModel.class };
		refPickingRefMaterialItemMap.setLogicManager(productionOrderItemManager);
		refPickingRefMaterialItemMap
				.setConvToUIMethod(ProductionOrderItemManager.METHOD_ConvDocumentToItemReqProposalUI);
		refPickingRefMaterialItemMap
				.setConvToUIMethodParas(refDocConvToUIParas);
		// uiModelNodeMapList.add(refPickingRefMaterialItemMap);

		SimpleDocConfigurePara simpleDocConfigurePara = new SimpleDocConfigurePara(
				ProdOrderItemReqProposal.NODENAME,
				ProductionOrderItemManager.METHOD_ConvDocumentToItemReqProposalUI,
				refDocConvToUIParas, productionOrderItemManager);
		simpleDocConfigurePara
				.setDocMatItemGetCallback(rawSENode -> {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) rawSENode;
					int documentType = prodOrderItemReqProposal
							.getDocumentType();
					ServiceEntityNode specDocMatItemNode = docFlowProxy
							.getDefDocItemNode(documentType,
									prodOrderItemReqProposal.getRefUUID(),
									prodOrderItemReqProposal.getClient());
					return specDocMatItemNode;
				});

		simpleDocConfigurePara
				.setDocGetCallback(rawSENode -> {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) rawSENode;
					int documentType = prodOrderItemReqProposal
							.getDocumentType();
					ServiceEntityNode specDocMatItemNode = docFlowProxy
							.getDirectDocContentNode(documentType,
									prodOrderItemReqProposal.getRefUUID(),
									prodOrderItemReqProposal.getClient());
					return specDocMatItemNode;
				});

		uiModelNodeMapList.addAll(docFlowProxy
				.getSpecNodeMapConfigureList(simpleDocConfigurePara));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdOrderItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdOrderItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdOrderItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdOrderItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdOrderItemReqProposal.NODENAME));


		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				ProdOrderItemReqProposalUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				ProdOrderItemReqProposal.NODENAME, productionOrderItemManager,
				ProductionOrderItemManager.METHOD_ConvWarehouseToItemReqProposalUI,
				warehouseConvToUIParas,
				null, null
		)));

		prodOrderItemReqProposalExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderItemReqProposalExtensionUnion);
		return resultList;
	}

}
