package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.service.ProductionPlanItemManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderItemManager;
import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;

import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdPlanItemReqProposalServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProductionPlanItemServiceUIModelExtension productionPlanItemServiceUIModelExtension;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;
	
	@Autowired
	protected ProductionPlanItemManager productionPlanItemManager;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(productionPlanItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPlanItemReqProposalExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPlanItemReqProposalExtensionUnion
				.setNodeInstId(ProdPlanItemReqProposal.NODENAME);
		prodPlanItemReqProposalExtensionUnion
				.setNodeName(ProdPlanItemReqProposal.NODENAME);

		// UI Model Configure of node:[ProdPlanItemReqProposal]
		UIModelNodeMapConfigure prodPlanItemReqProposalMap = new UIModelNodeMapConfigure();
		prodPlanItemReqProposalMap.setSeName(ProdPlanItemReqProposal.SENAME);
		prodPlanItemReqProposalMap
				.setNodeName(ProdPlanItemReqProposal.NODENAME);
		prodPlanItemReqProposalMap
				.setNodeInstID(ProdPlanItemReqProposal.NODENAME);
		prodPlanItemReqProposalMap.setHostNodeFlag(true);
		prodPlanItemReqProposalMap.setLogicManager(productionPlanItemManager);
		Class<?>[] prodPlanItemReqProposalConvToUIParas = {
				ProdPlanItemReqProposal.class,
				ProdPlanItemReqProposalUIModel.class };
		prodPlanItemReqProposalMap
				.setConvToUIMethodParas(prodPlanItemReqProposalConvToUIParas);
		prodPlanItemReqProposalMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvProdPlanItemReqProposalToUI);
		Class<?>[] ProdPlanItemReqProposalConvUIToParas = {
				ProdPlanItemReqProposalUIModel.class,
				ProdPlanItemReqProposal.class };
		prodPlanItemReqProposalMap
				.setConvUIToMethodParas(ProdPlanItemReqProposalConvUIToParas);
		prodPlanItemReqProposalMap
				.setConvUIToMethod(ProductionPlanItemManager.METHOD_ConvUIToProdPlanItemReqProposal);
		uiModelNodeMapList.add(prodPlanItemReqProposalMap);
		
		UIModelNodeMapConfigure productionPlanItemMap = new UIModelNodeMapConfigure();
		productionPlanItemMap.setSeName(ProductionPlanItem.SENAME);
		productionPlanItemMap
				.setNodeName(ProductionPlanItem.NODENAME);
		productionPlanItemMap
				.setNodeInstID(ProductionPlanItem.NODENAME);
		productionPlanItemMap.setLogicManager(productionPlanItemManager);
		productionPlanItemMap.setHostNodeFlag(false);
		productionPlanItemMap.setBaseNodeInstID(ProdPlanItemReqProposal.NODENAME);
		productionPlanItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionPlanItemConvToUIParas = {
				ProductionPlanItem.class,
				ProdPlanItemReqProposalUIModel.class };
		productionPlanItemMap
				.setConvToUIMethodParas(productionPlanItemConvToUIParas);
		productionPlanItemMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvProductionPlanItemToProposalUI);		
		uiModelNodeMapList.add(productionPlanItemMap);
		

		UIModelNodeMapConfigure productionPlanMap = new UIModelNodeMapConfigure();
		productionPlanMap.setSeName(ProductionPlan.SENAME);
		productionPlanMap
				.setNodeName(ProductionPlan.NODENAME);
		productionPlanMap
				.setNodeInstID(ProductionPlan.SENAME);
		productionPlanMap.setLogicManager(productionPlanItemManager);
		productionPlanMap.setHostNodeFlag(false);
		productionPlanMap.setBaseNodeInstID(ProductionPlanItem.NODENAME);
		productionPlanMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] productionPlanConvToUIParas = {
				ProductionPlan.class,
				ProdPlanItemReqProposalUIModel.class };
		productionPlanMap
				.setConvToUIMethodParas(productionPlanConvToUIParas);
		productionPlanMap
				.setConvToUIMethod(ProductionPlanItemManager.METHOD_ConvProductionPlanToProposalUI);		
		uiModelNodeMapList.add(productionPlanMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdPlanItemReqProposal.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(ProdPlanItemReqProposal.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(ProdPlanItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdPlanItemReqProposal.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdPlanItemReqProposal.NODENAME));


		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				ProdPlanItemReqProposalUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				ProdPlanItemReqProposal.NODENAME, productionPlanManager,
				ProductionPlanManager.METHOD_ConvWarehouseToItemReqProposalUI,
				warehouseConvToUIParas,
				null, null
		)));


		// TODO replace this method implementation in framework as well as production order manager
		// UI Model Configure of node:[refDocumentMap]
		UIModelNodeMapConfigure refDocumentMap = new UIModelNodeMapConfigure();
		refDocumentMap
				.setGetSENodeCallback(rawSENode -> {
					ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(prodPlanItemReqProposal
									.getDocumentType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentContent = null;
					try {
						documentContent = refDocumentManager
								.getEntityNodeByKey(
										prodPlanItemReqProposal.getRefUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ServiceEntityNode.NODENAME_ROOT,
										prodPlanItemReqProposal.getClient(),
										null);
						return documentContent;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		refDocumentMap.setNodeInstID("refDocument");
		refDocumentMap.setBaseNodeInstID(ProdPlanItemReqProposal.NODENAME);
		refDocumentMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);

		Class<?>[] refDocConvToUIParas = { ServiceEntityNode.class,
				ProdPlanItemReqProposalUIModel.class };
		refDocumentMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvDocumentToItemReqProposalUI);
		refDocumentMap.setConvToUIMethodParas(refDocConvToUIParas);
		uiModelNodeMapList.add(refDocumentMap);

		// UI Model Configure of node:[Ref Doc MatItem]
		UIModelNodeMapConfigure refMatItemMap = new UIModelNodeMapConfigure();
		refMatItemMap.setBaseNodeInstID(ProdPlanItemReqProposal.NODENAME);
		refMatItemMap.setNodeInstID("refDocMatItem");
		refMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		refMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(prodPlanItemReqProposal
									.getDocumentType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentItemNode = null;
					try {
						String targetNodeName = serviceDocumentComProxy
								.getDocumentMaterialItemNodeName(prodPlanItemReqProposal
										.getDocumentType());
						documentItemNode = refDocumentManager
								.getEntityNodeByKey(
										prodPlanItemReqProposal.getRefUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										targetNodeName,
										prodPlanItemReqProposal.getClient(),
										null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(refMatItemMap);

		// UI Model Configure of node:[Previous Order from prevMaterialItemMap]
		UIModelNodeMapConfigure refDocFromMatItemMap = new UIModelNodeMapConfigure();
		refDocFromMatItemMap.setNodeInstID("refDocFromMat");
		refDocFromMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ServiceEntityManager refDocumentManager = serviceEntityManagerFactoryInContext
							.getManagerBySEName(rawSENode
									.getServiceEntityName());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentContent = null;
					try {
						documentContent = refDocumentManager
								.getEntityNodeByKey(
										rawSENode.getRootNodeUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ServiceEntityNode.NODENAME_ROOT,
										rawSENode.getClient(), null);
						return documentContent;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		refDocFromMatItemMap.setBaseNodeInstID("refDocMatItem");
		refDocFromMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		refDocFromMatItemMap.setConvToUIMethodParas(refDocConvToUIParas);
		refDocFromMatItemMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvDocumentToItemReqProposalUI);
		uiModelNodeMapList.add(refDocFromMatItemMap);

		prodPlanItemReqProposalExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPlanItemReqProposalExtensionUnion);
		return resultList;
	}

}
