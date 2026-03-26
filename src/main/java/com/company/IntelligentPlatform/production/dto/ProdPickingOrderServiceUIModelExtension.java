package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdPickingOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdPickingOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected ProdPickingRefOrderItemServiceUIModelExtension prodPickingRefOrderItemServiceUIModelExtension;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;
	
	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(prodPickingRefOrderItemServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProdPickingOrderActionNode.SENAME,
				ProdPickingOrderActionNode.NODENAME,
				ProdPickingOrderActionNode.NODEINST_ACTION_APPROVE,
				prodPickingOrderManager, ProdPickingOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProdPickingOrderActionNode.SENAME,
				ProdPickingOrderActionNode.NODENAME,
				ProdPickingOrderActionNode.NODEINST_ACTION_INPROCESS,
				prodPickingOrderManager, ProdPickingOrderActionNode.DOC_ACTION_INPROCESS
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProdPickingOrderActionNode.SENAME,
				ProdPickingOrderActionNode.NODENAME,
				ProdPickingOrderActionNode.NODEINST_ACTION_DELIVERY_DONE,
				prodPickingOrderManager, ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();

		ServiceUIModelExtensionUnion prodPickingOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPickingOrderExtensionUnion.setNodeInstId(ProdPickingOrder.SENAME);
		prodPickingOrderExtensionUnion.setNodeName(ProdPickingOrder.NODENAME);

		// UI Model Configure of node:[ProdPickingOrder]
		UIModelNodeMapConfigure prodPickingOrderMap = new UIModelNodeMapConfigure();
		prodPickingOrderMap.setSeName(ProdPickingOrder.SENAME);
		prodPickingOrderMap.setNodeName(ProdPickingOrder.NODENAME);
		prodPickingOrderMap.setNodeInstID(ProdPickingOrder.SENAME);
		prodPickingOrderMap.setHostNodeFlag(true);
		Class<?>[] prodPickingOrderConvToUIParas = { ProdPickingOrder.class,
				ProdPickingOrderUIModel.class };
		prodPickingOrderMap
				.setConvToUIMethodParas(prodPickingOrderConvToUIParas);
		prodPickingOrderMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvProdPickingOrderToUI);
		Class<?>[] ProdPickingOrderConvUIToParas = {
				ProdPickingOrderUIModel.class, ProdPickingOrder.class };
		prodPickingOrderMap
				.setConvUIToMethodParas(ProdPickingOrderConvUIToParas);
		prodPickingOrderMap
				.setConvUIToMethod(ProdPickingOrderManager.METHOD_ConvUIToProdPickingOrder);
		uiModelNodeMapList.add(prodPickingOrderMap);

		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(ProdPickingOrder.SENAME));

		// UI Model Configure of node:[ProdPickingRefOrderItem]
		UIModelNodeMapConfigure prodPickingRefOrderItemMap = new UIModelNodeMapConfigure();
		prodPickingRefOrderItemMap.setSeName(ProdPickingRefOrderItem.SENAME);
		prodPickingRefOrderItemMap
				.setNodeName(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemMap
				.setNodeInstID(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemMap.setHostNodeFlag(false);
		prodPickingRefOrderItemMap.setBaseNodeInstID(ProdPickingOrder.SENAME);
		prodPickingRefOrderItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_ROOT);
		uiModelNodeMapList.add(prodPickingRefOrderItemMap);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setBaseNodeInstID(ProdPickingRefOrderItem.NODENAME);
		productionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productionOrderMap.setServiceEntityManager(productionOrderManager);
		List<SearchConfigConnectCondition> productionOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition productionOrderCondition0 = new SearchConfigConnectCondition();
		productionOrderCondition0.setSourceFieldName("refProdOrderUUID");
		productionOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		productionOrderConditionList.add(productionOrderCondition0);
		productionOrderMap
				.setConnectionConditions(productionOrderConditionList);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProdPickingOrderUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvProductionOrderToUI);
		uiModelNodeMapList.add(productionOrderMap);

		// UI Model Configure of node:[ApproveBy]
		UIModelNodeMapConfigure approveByMap = new UIModelNodeMapConfigure();
		approveByMap.setSeName(LogonUser.SENAME);
		approveByMap.setNodeName(LogonUser.NODENAME);
		approveByMap.setNodeInstID("ApproveBy");
		approveByMap.setBaseNodeInstID(ProdPickingOrder.SENAME);
		approveByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		approveByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> approveByConditionList = new ArrayList<>();
		SearchConfigConnectCondition approveByCondition0 = new SearchConfigConnectCondition();
		approveByCondition0.setSourceFieldName("approveBy");
		approveByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		approveByConditionList.add(approveByCondition0);
		approveByMap.setConnectionConditions(approveByConditionList);
		Class<?>[] approveByConvToUIParas = { LogonUser.class,
				ProdPickingOrderUIModel.class };
		approveByMap.setConvToUIMethodParas(approveByConvToUIParas);
		approveByMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvApproveByToOrder);
		uiModelNodeMapList.add(approveByMap);

		// UI Model Configure of node:[ProcessBy]
		UIModelNodeMapConfigure processByMap = new UIModelNodeMapConfigure();
		processByMap.setSeName(LogonUser.SENAME);
		processByMap.setNodeName(LogonUser.NODENAME);
		processByMap.setNodeInstID("ProcessBy");
		processByMap.setBaseNodeInstID(ProdPickingOrder.SENAME);
		processByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		processByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> processByConditionList = new ArrayList<>();
		SearchConfigConnectCondition processByCondition0 = new SearchConfigConnectCondition();
		processByCondition0.setSourceFieldName("processBy");
		processByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		processByConditionList.add(processByCondition0);
		processByMap.setConnectionConditions(processByConditionList);
		Class<?>[] processByConvToUIParas = { LogonUser.class,
				ProdPickingOrderUIModel.class };
		processByMap.setConvToUIMethodParas(processByConvToUIParas);
		processByMap
				.setConvToUIMethod(ProdPickingOrderManager.METHOD_ConvApproveByToOrder);
		uiModelNodeMapList.add(processByMap);

		prodPickingOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPickingOrderExtensionUnion);
		return resultList;
	}

}
