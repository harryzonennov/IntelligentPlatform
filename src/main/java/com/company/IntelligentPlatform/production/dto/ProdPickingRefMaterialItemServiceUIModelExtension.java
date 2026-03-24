package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.ProdPickingRefMaterialItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdPickingRefMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPickingRefMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPickingRefMaterialItemExtensionUnion
				.setNodeInstId(ProdPickingRefMaterialItem.NODENAME);
		prodPickingRefMaterialItemExtensionUnion
				.setNodeName(ProdPickingRefMaterialItem.NODENAME);

		// UI Model Configure of node:[ProdPickingRefMaterialItem]
		UIModelNodeMapConfigure prodPickingRefMaterialItemMap = new UIModelNodeMapConfigure();
		prodPickingRefMaterialItemMap
				.setSeName(ProdPickingRefMaterialItem.SENAME);
		prodPickingRefMaterialItemMap
				.setNodeName(ProdPickingRefMaterialItem.NODENAME);
		prodPickingRefMaterialItemMap
				.setNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		prodPickingRefMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] prodPickingRefMaterialItemConvToUIParas = {
				ProdPickingRefMaterialItem.class,
				ProdPickingRefMaterialItemUIModel.class };
		prodPickingRefMaterialItemMap.setLogicManager(prodPickingRefMaterialItemManager);
		prodPickingRefMaterialItemMap
				.setConvToUIMethodParas(prodPickingRefMaterialItemConvToUIParas);
		prodPickingRefMaterialItemMap
				.setConvToUIMethod(ProdPickingRefMaterialItemManager.METHOD_ConvProdPickingRefMaterialItemToUI);
		Class<?>[] ProdPickingRefMaterialItemConvUIToParas = {
				ProdPickingRefMaterialItemUIModel.class,
				ProdPickingRefMaterialItem.class };
		prodPickingRefMaterialItemMap
				.setConvUIToMethodParas(ProdPickingRefMaterialItemConvUIToParas);
		prodPickingRefMaterialItemMap
				.setConvUIToMethod(ProdPickingRefMaterialItemManager.METHOD_ConvUIToProdPickingRefMaterialItem);
		uiModelNodeMapList.add(prodPickingRefMaterialItemMap);

		// UI Model Configure of node:[ProdPickingOrder]
		UIModelNodeMapConfigure prodPickingOrderMap = new UIModelNodeMapConfigure();
		prodPickingOrderMap.setSeName(ProdPickingOrder.SENAME);
		prodPickingOrderMap.setNodeName(ProdPickingOrder.NODENAME);
		prodPickingOrderMap.setNodeInstID(ProdPickingOrder.SENAME);
		prodPickingOrderMap
				.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		prodPickingOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);

		Class<?>[] prodPickingOrderConvToUIParas = { ProdPickingOrder.class,
				ProdPickingRefMaterialItemUIModel.class };
		prodPickingOrderMap.setLogicManager(prodPickingRefMaterialItemManager);
		prodPickingOrderMap
				.setConvToUIMethodParas(prodPickingOrderConvToUIParas);
		prodPickingOrderMap
				.setConvToUIMethod(ProdPickingRefMaterialItemManager.METHOD_ConvPickingOrderToItemUI);
		uiModelNodeMapList.add(prodPickingOrderMap);

		// UI Model Configure of node:[ProductionOrderItem]
		UIModelNodeMapConfigure productionOrderItemMap = new UIModelNodeMapConfigure();
		productionOrderItemMap.setSeName(ProductionOrderItem.SENAME);
		productionOrderItemMap.setNodeName(ProductionOrderItem.NODENAME);
		productionOrderItemMap.setNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderItemMap
				.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		productionOrderItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productionOrderItemMap.setServiceEntityManager(productionOrderManager);
		List<SearchConfigConnectCondition> productionOrderItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition productionOrderItemCondition0 = new SearchConfigConnectCondition();
		productionOrderItemCondition0
				.setSourceFieldName("refProdOrderItemUUID");
		productionOrderItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		productionOrderItemConditionList.add(productionOrderItemCondition0);
		productionOrderItemMap
				.setConnectionConditions(productionOrderItemConditionList);
		uiModelNodeMapList.add(productionOrderItemMap);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setBaseNodeInstID(ProductionOrderItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		productionOrderMap.setServiceEntityManager(productionOrderManager);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProdPickingRefMaterialItemUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap.setLogicManager(prodPickingRefMaterialItemManager);
		productionOrderMap
				.setConvToUIMethod(ProdPickingRefMaterialItemManager.METHOD_ConvProductionOrderToItemUI);
		uiModelNodeMapList.add(productionOrderMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(ProdPickingRefMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(ProdPickingRefMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(ProdPickingRefMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(ProdPickingRefMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(ProdPickingRefMaterialItem.NODENAME));


		// UI Model Configure of node:[OutboundItem]
		UIModelNodeMapConfigure outboundItemMap = new UIModelNodeMapConfigure();
		outboundItemMap.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		outboundItemMap.setNodeInstID(OutboundItem.NODENAME);
		outboundItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		outboundItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) rawSENode;
					try {
						OutboundItem outboundItem = (OutboundItem) outboundDeliveryManager
								.getEntityNodeByKey(prodPickingRefMaterialItem
										.getRefOutboundItemUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										OutboundItem.NODENAME,
										prodPickingRefMaterialItem.getClient(),
										null);
						return outboundItem;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(outboundItemMap);

		// UI Model Configure of node:[OutboundDelivery]
		UIModelNodeMapConfigure outboundDeliveryMap = new UIModelNodeMapConfigure();
		outboundDeliveryMap.setNodeInstID(OutboundDelivery.SENAME);
		outboundDeliveryMap
				.setGetSENodeCallback(rawSENode -> {
					OutboundItem outboundItem = (OutboundItem) rawSENode;
					try {
						OutboundDelivery outboundDelivery = (OutboundDelivery) outboundDeliveryManager.getEntityNodeByKey(
								outboundItem.getRootNodeUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ServiceEntityNode.NODENAME_ROOT,
								outboundItem.getClient(), null);
						return outboundDelivery;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		outboundDeliveryMap.setBaseNodeInstID(OutboundItem.NODENAME);
		outboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] outboundConvToUIParas = { OutboundDelivery.class,
				ProdPickingRefMaterialItemUIModel.class };
		outboundDeliveryMap.setConvToUIMethodParas(outboundConvToUIParas);
		outboundDeliveryMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvOutboundToMatItemUI);
		uiModelNodeMapList.add(outboundDeliveryMap);

		// UI Model Configure of node:[InboundItem]
		UIModelNodeMapConfigure inboundItemMap = new UIModelNodeMapConfigure();
		inboundItemMap.setBaseNodeInstID(ProdPickingRefMaterialItem.NODENAME);
		inboundItemMap.setNodeInstID(InboundItem.NODENAME);
		inboundItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		inboundItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) rawSENode;
					try {
						InboundItem inboundItem = (InboundItem) inboundDeliveryManager
								.getEntityNodeByKey(prodPickingRefMaterialItem
										.getRefInboundItemUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										InboundItem.NODENAME,
										prodPickingRefMaterialItem.getClient(),
										null);
						return inboundItem;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(inboundItemMap);

		// UI Model Configure of node:[InboundDelivery]
		UIModelNodeMapConfigure inboundDeliveryMap = new UIModelNodeMapConfigure();
		inboundDeliveryMap.setNodeInstID(InboundDelivery.SENAME);
		inboundDeliveryMap
				.setGetSENodeCallback(rawSENode -> {
					InboundItem inboundItem = (InboundItem) rawSENode;
					try {
						InboundDelivery inboundDelivery = (InboundDelivery) inboundDeliveryManager
								.getEntityNodeByKey(
										inboundItem.getRootNodeUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ServiceEntityNode.NODENAME_ROOT,
										inboundItem.getClient(), null);
						return inboundDelivery;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		inboundDeliveryMap.setBaseNodeInstID(InboundItem.NODENAME);
		inboundDeliveryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] inboundConvToUIParas = { InboundDelivery.class,
				ProdPickingRefMaterialItemUIModel.class };
		inboundDeliveryMap.setConvToUIMethodParas(inboundConvToUIParas);
		inboundDeliveryMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvInboundToMatItemUI);
		uiModelNodeMapList.add(inboundDeliveryMap);

		prodPickingRefMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPickingRefMaterialItemExtensionUnion);
		return resultList;
	}

}
