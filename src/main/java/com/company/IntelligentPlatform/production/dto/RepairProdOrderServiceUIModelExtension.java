package com.company.IntelligentPlatform.production.dto;


import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.RepairProdOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected RepairProdOrderItemServiceUIModelExtension repairProdOrderItemServiceUIModelExtension;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected RepairProdSupplyWarehouseServiceUIModelExtension repairProdSupplyWarehouseServiceUIModelExtension;
	
	@Autowired
	protected RepairProdTargetMatItemServiceUIModelExtension repairProdTargetMatItemServiceUIModelExtension;

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
		resultList.add(repairProdOrderItemServiceUIModelExtension);
		resultList.add(repairProdSupplyWarehouseServiceUIModelExtension);
		//resultList.add(repairProdOrderAttachmentServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				RepairProdOrderAttachment.SENAME,
				RepairProdOrderAttachment.NODENAME,
				RepairProdOrderAttachment.NODENAME
		)));
		resultList.add(repairProdTargetMatItemServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				RepairProdOrderActionNode.NODEINST_ACTION_SUBMIT,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				RepairProdOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_REVOKE_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				RepairProdOrderActionNode.NODEINST_ACTION_REJECT_APPROVE,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				RepairProdOrderActionNode.NODEINST_ACTION_INPRODUCTION,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_INPRODUCTION
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				RepairProdOrderActionNode.SENAME,
				RepairProdOrderActionNode.NODENAME,
				RepairProdOrderActionNode.NODEINST_ACTION_FINISHED,
				repairProdOrderManager, RepairProdOrderActionNode.DOC_ACTION_FINISHED
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RepairProdOrderParty.SENAME,
				RepairProdOrderParty.NODENAME,
				RepairProdOrderParty.PARTY_NODEINST_PUR_ORG,
				repairProdOrderManager,
				RepairProdOrderParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RepairProdOrderParty.SENAME,
				RepairProdOrderParty.NODENAME,
				RepairProdOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				repairProdOrderManager,
				RepairProdOrderParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RepairProdOrderParty.SENAME,
				RepairProdOrderParty.NODENAME,
				RepairProdOrderParty.PARTY_NODEINST_SOLD_ORG,
				repairProdOrderManager,
				RepairProdOrderParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				RepairProdOrderParty.SENAME,
				RepairProdOrderParty.NODENAME,
				RepairProdOrderParty.PARTY_NODEINST_PROD_ORG,
				repairProdOrderManager,
				RepairProdOrderParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion repairProdOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		repairProdOrderExtensionUnion.setNodeInstId(RepairProdOrder.SENAME);
		repairProdOrderExtensionUnion.setNodeName(RepairProdOrder.NODENAME);

		// UI Model Configure of node:[RepairProdOrder]
		UIModelNodeMapConfigure repairProdOrderMap = new UIModelNodeMapConfigure();
		repairProdOrderMap.setSeName(RepairProdOrder.SENAME);
		repairProdOrderMap.setNodeName(RepairProdOrder.NODENAME);
		repairProdOrderMap.setNodeInstID(RepairProdOrder.SENAME);
		repairProdOrderMap.setHostNodeFlag(true);
		Class<?>[] repairProdOrderConvToUIParas = { RepairProdOrder.class,
				RepairProdOrderUIModel.class };
		repairProdOrderMap.setConvToUIMethodParas(repairProdOrderConvToUIParas);
		repairProdOrderMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvRepairProdOrderToUI);
		Class<?>[] RepairProdOrderConvUIToParas = {
				RepairProdOrderUIModel.class, RepairProdOrder.class };
		repairProdOrderMap.setConvUIToMethodParas(RepairProdOrderConvUIToParas);
		repairProdOrderMap
				.setConvUIToMethod(RepairProdOrderManager.METHOD_ConvUIToRepairProdOrder);
		uiModelNodeMapList.add(repairProdOrderMap);
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(RepairProdOrder.SENAME));

		// UI Model Configure of node:[OutBillOfMaterialOrder]
		UIModelNodeMapConfigure outBillOfMaterialOrderMap = new UIModelNodeMapConfigure();
		outBillOfMaterialOrderMap.setSeName(BillOfMaterialOrder.SENAME);
		outBillOfMaterialOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
		outBillOfMaterialOrderMap.setNodeInstID("OutBillOfMaterialOrder");
		outBillOfMaterialOrderMap.setBaseNodeInstID(RepairProdOrder.SENAME);
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
				BillOfMaterialOrder.class, RepairProdOrderUIModel.class };
		outBillOfMaterialOrderMap
				.setConvToUIMethodParas(outBillOfMaterialOrderConvToUIParas);
		outBillOfMaterialOrderMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvOutBillOfMaterialOrderToUI);
		uiModelNodeMapList.add(outBillOfMaterialOrderMap);

		// UI Model Configure of node:[OutBillOfMaterialOrder]
		UIModelNodeMapConfigure outBillOfMaterialItemMap = new UIModelNodeMapConfigure();
		outBillOfMaterialItemMap.setSeName(BillOfMaterialItem.SENAME);
		outBillOfMaterialItemMap.setNodeName(BillOfMaterialItem.NODENAME);
		outBillOfMaterialItemMap.setNodeInstID("outBillOfMaterialItem");
		outBillOfMaterialItemMap.setBaseNodeInstID(RepairProdOrder.SENAME);
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
				BillOfMaterialItem.class, RepairProdOrderUIModel.class };
		outBillOfMaterialItemMap
				.setConvToUIMethodParas(outBillOfMaterialItemConvToUIParas);
		outBillOfMaterialItemMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvOutBillOfMaterialItemToUI);
		uiModelNodeMapList.add(outBillOfMaterialItemMap);

		// UI Model Configure of node:[OutMaterialStockKeepUnit]
		UIModelNodeMapConfigure outMaterialStockKeepUnitMap = new UIModelNodeMapConfigure();
		outMaterialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		outMaterialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		outMaterialStockKeepUnitMap.setNodeInstID("OutMaterialStockKeepUnit");
		outMaterialStockKeepUnitMap.setBaseNodeInstID(RepairProdOrder.SENAME);
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
				MaterialStockKeepUnit.class, RepairProdOrderUIModel.class };
		outMaterialStockKeepUnitMap
				.setConvToUIMethodParas(outMaterialStockKeepUnitConvToUIParas);
		outMaterialStockKeepUnitMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvOutMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(outMaterialStockKeepUnitMap);

		// UI Model Configure of node:[ProductinPlan]
		UIModelNodeMapConfigure productionPlanMap = new UIModelNodeMapConfigure();
		productionPlanMap.setSeName(ProductionPlan.SENAME);
		productionPlanMap.setNodeName(ProductionPlan.NODENAME);
		productionPlanMap.setNodeInstID(ProductionPlan.NODENAME);
		productionPlanMap.setBaseNodeInstID(RepairProdOrder.SENAME);
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
				RepairProdOrderUIModel.class };
		productionPlanMap.setConvToUIMethodParas(productionPlanConvToUIParas);
		productionPlanMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvProductionPlanToUI);
		uiModelNodeMapList.add(productionPlanMap);

		// UI Model Configure of node:[ProdWorkcenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setBaseNodeInstID(RepairProdOrder.SENAME);
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
				RepairProdOrderUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvProdWorkCenterToUI);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[reserved Order MatItem]
		UIModelNodeMapConfigure reservedMatItemMap = new UIModelNodeMapConfigure();
		reservedMatItemMap.setBaseNodeInstID(RepairProdOrder.SENAME);
		reservedMatItemMap.setNodeInstID("reservedMatItem");
		reservedMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		reservedMatItemMap.setGetSENodeCallback(rawSENode -> {
			RepairProdOrder repairProdOrder = (RepairProdOrder) rawSENode;
			ServiceEntityManager refDocumentManager = serviceDocumentComProxy
					.getDocumentManager(repairProdOrder.getReservedDocType());
			if (refDocumentManager == null) {
				return null;
			}
			ServiceEntityNode documentItemNode = null;
			try {
				String targetNodeName = serviceDocumentComProxy
						.getDocumentMaterialItemNodeName(repairProdOrder
								.getReservedDocType());
				documentItemNode = refDocumentManager.getEntityNodeByKey(
						repairProdOrder.getReservedMatItemUUID(),
						IServiceEntityNodeFieldConstant.UUID, targetNodeName,
						repairProdOrder.getClient(), null);
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
				RepairProdOrderUIModel.class };
		reservedDocMap.setConvToUIMethodParas(researvedDocConvToUIParas);
		reservedDocMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvReservedDocToOrderUI);
		uiModelNodeMapList.add(reservedDocMap);

		repairProdOrderExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(repairProdOrderExtensionUnion);
		return resultList;
	}

}
