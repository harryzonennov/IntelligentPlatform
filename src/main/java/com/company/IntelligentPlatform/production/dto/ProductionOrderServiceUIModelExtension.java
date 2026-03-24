package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProductionOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdOrderSupplyWarehouseServiceUIModelExtension prodOrderSupplyWarehouseServiceUIModelExtension;
	
	@Autowired
	protected ProdOrderTargetMatItemServiceUIModelExtension prodOrderTargetMatItemServiceUIModelExtension;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(productionOrderItemServiceUIModelExtension);
		resultList.add(prodOrderSupplyWarehouseServiceUIModelExtension);
		//resultList.add(productionOrderAttachmentServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ProductionOrderAttachment.SENAME,
				ProductionOrderAttachment.NODENAME,
				ProductionOrderAttachment.NODENAME
		)));
		resultList.add(prodOrderTargetMatItemServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				ProductionOrderActionNode.NODEINST_ACTION_SUBMIT,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				ProductionOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_REVOKE_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				ProductionOrderActionNode.NODEINST_ACTION_REJECT_APPROVE,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				ProductionOrderActionNode.NODEINST_ACTION_INPRODUCTION,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_INPRODUCTION
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionOrderActionNode.SENAME,
				ProductionOrderActionNode.NODENAME,
				ProductionOrderActionNode.NODEINST_ACTION_FINISHED,
				productionOrderManager, ProductionOrderActionNode.DOC_ACTION_FINISHED
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderParty.SENAME,
				ProductionOrderParty.NODENAME,
				ProductionOrderParty.PARTY_NODEINST_PUR_ORG,
				productionOrderManager,
				ProductionOrderParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderParty.SENAME,
				ProductionOrderParty.NODENAME,
				ProductionOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				productionOrderManager,
				ProductionOrderParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderParty.SENAME,
				ProductionOrderParty.NODENAME,
				ProductionOrderParty.PARTY_NODEINST_SOLD_ORG,
				productionOrderManager,
				ProductionOrderParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionOrderParty.SENAME,
				ProductionOrderParty.NODENAME,
				ProductionOrderParty.PARTY_NODEINST_PROD_ORG,
				productionOrderManager,
				ProductionOrderParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		productionOrderExtensionUnion.setNodeInstId(ProductionOrder.SENAME);
		productionOrderExtensionUnion.setNodeName(ProductionOrder.NODENAME);

		// UI Model Configure of node:[ProductionOrder]
		UIModelNodeMapConfigure productionOrderMap = new UIModelNodeMapConfigure();
		productionOrderMap.setSeName(ProductionOrder.SENAME);
		productionOrderMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderMap.setNodeInstID(ProductionOrder.SENAME);
		productionOrderMap.setHostNodeFlag(true);
		Class<?>[] productionOrderConvToUIParas = { ProductionOrder.class,
				ProductionOrderUIModel.class };
		productionOrderMap.setConvToUIMethodParas(productionOrderConvToUIParas);
		productionOrderMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvProductionOrderToUI);
		Class<?>[] ProductionOrderConvUIToParas = {
				ProductionOrderUIModel.class, ProductionOrder.class };
		productionOrderMap.setConvUIToMethodParas(ProductionOrderConvUIToParas);
		productionOrderMap
				.setConvUIToMethod(ProductionOrderManager.METHOD_ConvUIToProductionOrder);
		uiModelNodeMapList.add(productionOrderMap);
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(ProductionOrder.SENAME));

		// UI Model Configure of node:[OutBillOfMaterialOrder]
		UIModelNodeMapConfigure outBillOfMaterialOrderMap = new UIModelNodeMapConfigure();
		outBillOfMaterialOrderMap.setSeName(BillOfMaterialOrder.SENAME);
		outBillOfMaterialOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
		outBillOfMaterialOrderMap.setNodeInstID("OutBillOfMaterialOrder");
		outBillOfMaterialOrderMap.setBaseNodeInstID(ProductionOrder.SENAME);
		outBillOfMaterialOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outBillOfMaterialOrderMap
				.setServiceEntityManager(billOfMaterialOrderManager);
		List<SearchConfigConnectCondition> outBillOfMaterialOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition outBillOfMaterialOrderCondition0 = new SearchConfigConnectCondition();
		outBillOfMaterialOrderCondition0
				.setSourceFieldName("refBillOfMaterialUUID");
		outBillOfMaterialOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outBillOfMaterialOrderConditionList
				.add(outBillOfMaterialOrderCondition0);
		outBillOfMaterialOrderMap
				.setConnectionConditions(outBillOfMaterialOrderConditionList);
		Class<?>[] outBillOfMaterialOrderConvToUIParas = {
				BillOfMaterialOrder.class, ProductionOrderUIModel.class };
		outBillOfMaterialOrderMap
				.setConvToUIMethodParas(outBillOfMaterialOrderConvToUIParas);
		outBillOfMaterialOrderMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvOutBillOfMaterialOrderToUI);
		uiModelNodeMapList.add(outBillOfMaterialOrderMap);

		// UI Model Configure of node:[OutBillOfMaterialOrder]
		UIModelNodeMapConfigure outBillOfMaterialItemMap = new UIModelNodeMapConfigure();
		outBillOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		outBillOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		outBillOfMaterialItemMap.setNodeInstID("outBillOfMaterialItem");
		outBillOfMaterialItemMap.setBaseNodeInstID(ProductionOrder.SENAME);
		outBillOfMaterialItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outBillOfMaterialItemMap
				.setServiceEntityManager(billOfMaterialOrderManager);
		List<SearchConfigConnectCondition> outBillOfMaterialItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition outBillOfMaterialItemCondition0 = new SearchConfigConnectCondition();
		outBillOfMaterialItemCondition0
				.setSourceFieldName("refBillOfMaterialUUID");
		outBillOfMaterialItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outBillOfMaterialItemConditionList.add(outBillOfMaterialItemCondition0);
		outBillOfMaterialItemMap
				.setConnectionConditions(outBillOfMaterialItemConditionList);
		Class<?>[] outBillOfMaterialItemConvToUIParas = {
				BillOfMaterialItem.class, ProductionOrderUIModel.class };
		outBillOfMaterialItemMap
				.setConvToUIMethodParas(outBillOfMaterialItemConvToUIParas);
		outBillOfMaterialItemMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvOutBillOfMaterialItemToUI);
		uiModelNodeMapList.add(outBillOfMaterialItemMap);

		// UI Model Configure of node:[OutMaterialStockKeepUnit]
		UIModelNodeMapConfigure outMaterialStockKeepUnitMap = new UIModelNodeMapConfigure();
		outMaterialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		outMaterialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		outMaterialStockKeepUnitMap.setNodeInstID("OutMaterialStockKeepUnit");
		outMaterialStockKeepUnitMap.setBaseNodeInstID(ProductionOrder.SENAME);
		outMaterialStockKeepUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		outMaterialStockKeepUnitMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> outMaterialStockKeepUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition outMaterialStockKeepUnitCondition0 = new SearchConfigConnectCondition();
		outMaterialStockKeepUnitCondition0
				.setSourceFieldName("refMaterialSKUUUID");
		outMaterialStockKeepUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		outMaterialStockKeepUnitConditionList
				.add(outMaterialStockKeepUnitCondition0);
		outMaterialStockKeepUnitMap
				.setConnectionConditions(outMaterialStockKeepUnitConditionList);
		Class<?>[] outMaterialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, ProductionOrderUIModel.class };
		outMaterialStockKeepUnitMap
				.setConvToUIMethodParas(outMaterialStockKeepUnitConvToUIParas);
		outMaterialStockKeepUnitMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvOutMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(outMaterialStockKeepUnitMap);

		// UI Model Configure of node:[ProductinPlan]
		UIModelNodeMapConfigure productionPlanMap = new UIModelNodeMapConfigure();
		productionPlanMap.setSeName(ProductionPlan.SENAME);
		productionPlanMap.setNodeName(ProductionPlan.NODENAME);
		productionPlanMap.setNodeInstID(ProductionPlan.NODENAME);
		productionPlanMap.setBaseNodeInstID(ProductionOrder.SENAME);
		productionPlanMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		productionPlanMap.setServiceEntityManager(productionPlanManager);
		List<SearchConfigConnectCondition> productionPlanList = new ArrayList<>();
		SearchConfigConnectCondition productionPlanCondition0 = new SearchConfigConnectCondition();
		productionPlanCondition0.setSourceFieldName("refPlanUUID");
		productionPlanCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		productionPlanList.add(productionPlanCondition0);
		productionPlanMap.setConnectionConditions(productionPlanList);
		Class<?>[] productionPlanConvToUIParas = { ProductionPlan.class,
				ProductionOrderUIModel.class };
		productionPlanMap.setConvToUIMethodParas(productionPlanConvToUIParas);
		productionPlanMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvProductionPlanToUI);
		uiModelNodeMapList.add(productionPlanMap);

		// UI Model Configure of node:[ProdWorkcenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setBaseNodeInstID(ProductionOrder.SENAME);
		prodWorkCenterMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prodWorkCenterMap.setServiceEntityManager(prodWorkCenterManager);
		List<SearchConfigConnectCondition> prodWorkCenterList = new ArrayList<>();
		SearchConfigConnectCondition prodWorkCenterCondition0 = new SearchConfigConnectCondition();
		prodWorkCenterCondition0.setSourceFieldName("refWocUUID");
		prodWorkCenterCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		prodWorkCenterList.add(prodWorkCenterCondition0);
		prodWorkCenterMap.setConnectionConditions(prodWorkCenterList);
		Class<?>[] prodWorkCenterConvToUIParas = { ProdWorkCenter.class,
				ProductionOrderUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvProdWorkCenterToUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[reserved Order MatItem]
		UIModelNodeMapConfigure reservedMatItemMap = new UIModelNodeMapConfigure();
		reservedMatItemMap.setBaseNodeInstID(ProductionOrder.SENAME);
		reservedMatItemMap.setNodeInstID("reservedMatItem");
		reservedMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		reservedMatItemMap.setGetSENodeCallback(rawSENode -> {
			ProductionOrder productionOrder = (ProductionOrder) rawSENode;
			ServiceEntityManager refDocumentManager = serviceDocumentComProxy
					.getDocumentManager(productionOrder.getReservedDocType());
			if (refDocumentManager == null) {
				return null;
			}
			ServiceEntityNode documentItemNode = null;
			try {
				String targetNodeName = serviceDocumentComProxy
						.getDocumentMaterialItemNodeName(productionOrder
								.getReservedDocType());
				documentItemNode = refDocumentManager.getEntityNodeByKey(
						productionOrder.getReservedMatItemUUID(),
						IServiceEntityNodeFieldConstant.UUID, targetNodeName,
						productionOrder.getClient(), null);
				return documentItemNode;
			} catch (ServiceEntityConfigureException e) {
				return null;
			}
		});
		uiModelNodeMapList.add(reservedMatItemMap);

		UIModelNodeMapConfigure reservedDocMap = new UIModelNodeMapConfigure();
		reservedDocMap.setNodeInstID("reservedDoc");
		reservedDocMap
				.setGetSENodeCallback(rawSENode -> {
					ReferenceNode refMaterialItem = (ReferenceNode) rawSENode;
					ServiceEntityManager refDocumentManager = serviceEntityManagerFactoryInContext
							.getManagerBySEName(refMaterialItem
									.getServiceEntityName());
					if (refDocumentManager == null) {
						return null;
					}
					DocumentContent documentContent = null;
					try {
						documentContent = (DocumentContent) refDocumentManager
								.getEntityNodeByKey(
										refMaterialItem.getRootNodeUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										ServiceEntityNode.NODENAME_ROOT,
										refMaterialItem.getClient(), null);
						return documentContent;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		reservedDocMap.setBaseNodeInstID("reservedMatItem");
		reservedDocMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] researvedDocConvToUIParas = { DocumentContent.class,
				ProductionOrderUIModel.class };
		reservedDocMap.setConvToUIMethodParas(researvedDocConvToUIParas);
		reservedDocMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvReservedDocToOrderUI);
		uiModelNodeMapList.add(reservedDocMap);

		productionOrderExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionOrderExtensionUnion);
		return resultList;
	}

}
