package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
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
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProductionPlanServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProductionPlanItemServiceUIModelExtension productionPlanItemServiceUIModelExtension;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProdPlanSupplyWarehouseServiceUIModelExtension prodPlanSupplyWarehouseServiceUIModelExtension;
	
	@Autowired
	protected ProdPlanTargetMatItemServiceUIModelExtension prodPlanTargetMatItemServiceUIModelExtension;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(productionPlanItemServiceUIModelExtension);
		resultList.add(prodPlanSupplyWarehouseServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ProductionPlanAttachment.SENAME,
				ProductionPlanAttachment.NODENAME,
				ProductionPlanAttachment.NODENAME
		)));
		resultList.add(prodPlanTargetMatItemServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_COUNTAPPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				ProductionPlanActionNode.NODEINST_ACTION_SUBMIT,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				ProductionPlanActionNode.NODEINST_ACTION_REVOKE_SUBMIT,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_REVOKE_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				ProductionPlanActionNode.NODEINST_ACTION_REJECT_APPROVE,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_REJECT_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				ProductionPlanActionNode.NODEINST_ACTION_INPRODUCTION,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_INPRODUCTION
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				ProductionPlanActionNode.SENAME,
				ProductionPlanActionNode.NODENAME,
				ProductionPlanActionNode.NODEINST_ACTION_FINISHED,
				productionPlanManager, ProductionPlanActionNode.DOC_ACTION_FINISHED
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanParty.SENAME,
				ProductionPlanParty.NODENAME,
				ProductionPlanParty.PARTY_NODEINST_PUR_ORG,
				productionPlanManager,
				ProductionPlanParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanParty.SENAME,
				ProductionPlanParty.NODENAME,
				ProductionPlanParty.PARTY_NODEINST_SOLD_CUSTOMER,
				productionPlanManager,
				ProductionPlanParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanParty.SENAME,
				ProductionPlanParty.NODENAME,
				ProductionPlanParty.PARTY_NODEINST_SOLD_ORG,
				productionPlanManager,
				ProductionPlanParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				ProductionPlanParty.SENAME,
				ProductionPlanParty.NODENAME,
				ProductionPlanParty.PARTY_NODEINST_PROD_ORG,
				productionPlanManager,
				ProductionPlanParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionPlanExtensionUnion = new ServiceUIModelExtensionUnion();
		productionPlanExtensionUnion.setNodeInstId(ProductionPlan.SENAME);
		productionPlanExtensionUnion.setNodeName(ProductionPlan.NODENAME);

		// UI Model Configure of node:[ProductionPlan]
		UIModelNodeMapConfigure productionPlanMap = new UIModelNodeMapConfigure();
		productionPlanMap.setSeName(ProductionPlan.SENAME);
		productionPlanMap.setNodeName(ProductionPlan.NODENAME);
		productionPlanMap.setNodeInstID(ProductionPlan.SENAME);
		productionPlanMap.setHostNodeFlag(true);
		Class<?>[] productionPlanConvToUIParas = { ProductionPlan.class,
				ProductionPlanUIModel.class };
		productionPlanMap.setConvToUIMethodParas(productionPlanConvToUIParas);
		productionPlanMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvProductionPlanToUI);
		Class<?>[] ProductionPlanConvUIToParas = { ProductionPlanUIModel.class,
				ProductionPlan.class };
		productionPlanMap.setConvUIToMethodParas(ProductionPlanConvUIToParas);
		productionPlanMap
				.setConvUIToMethod(ProductionPlanManager.METHOD_ConvUIToProductionPlan);
		uiModelNodeMapList.add(productionPlanMap);
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(ProductionPlan.SENAME));

		// UI Model Configure of node:[OutBillOfMaterialOrder]
		UIModelNodeMapConfigure outBillOfMaterialOrderMap = new UIModelNodeMapConfigure();
		outBillOfMaterialOrderMap.setSeName(BillOfMaterialOrder.SENAME);
		outBillOfMaterialOrderMap.setNodeName(BillOfMaterialOrder.NODENAME);
		outBillOfMaterialOrderMap.setNodeInstID("OutBillOfMaterialOrder");
		outBillOfMaterialOrderMap.setBaseNodeInstID(ProductionPlan.SENAME);
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
				BillOfMaterialOrder.class, ProductionPlanUIModel.class };
		outBillOfMaterialOrderMap
				.setConvToUIMethodParas(outBillOfMaterialOrderConvToUIParas);
		outBillOfMaterialOrderMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvOutBillOfMaterialOrderToUI);
		uiModelNodeMapList.add(outBillOfMaterialOrderMap);

		// UI Model Configure of node:[approveBy]
		UIModelNodeMapConfigure approveByMap = new UIModelNodeMapConfigure();
		approveByMap.setSeName(LogonUser.SENAME);
		approveByMap.setNodeName(LogonUser.NODENAME);
		approveByMap.setNodeInstID("approveBy");
		approveByMap.setBaseNodeInstID(ProductionPlan.SENAME);
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
				ProductionPlanUIModel.class };
		approveByMap.setConvToUIMethodParas(approveByConvToUIParas);
		approveByMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvApproveByToUI);
		uiModelNodeMapList.add(approveByMap);

		// UI Model Configure of node:[countApproveBy]
		UIModelNodeMapConfigure countApproveByMap = new UIModelNodeMapConfigure();
		countApproveByMap.setSeName(LogonUser.SENAME);
		countApproveByMap.setNodeName(LogonUser.NODENAME);
		countApproveByMap.setNodeInstID("countApproveBy");
		countApproveByMap.setBaseNodeInstID(ProductionPlan.SENAME);
		countApproveByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		countApproveByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> countApproveByConditionList = new ArrayList<>();
		SearchConfigConnectCondition countApproveByCondition0 = new SearchConfigConnectCondition();
		countApproveByCondition0.setSourceFieldName("countApproveBy");
		countApproveByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		countApproveByConditionList.add(countApproveByCondition0);
		countApproveByMap.setConnectionConditions(countApproveByConditionList);
		Class<?>[] countApproveByConvToUIParas = { LogonUser.class,
				ProductionPlanUIModel.class };
		countApproveByMap.setConvToUIMethodParas(countApproveByConvToUIParas);
		countApproveByMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvCountApproveByToUI);
		uiModelNodeMapList.add(countApproveByMap);

		// UI Model Configure of node:[mainProductionOrder]
		UIModelNodeMapConfigure mainProductionOrderMap = new UIModelNodeMapConfigure();
		mainProductionOrderMap.setSeName(ProductionOrder.SENAME);
		mainProductionOrderMap.setNodeName(ProductionOrder.NODENAME);
		mainProductionOrderMap.setNodeInstID("mainProductionOrder");
		mainProductionOrderMap.setBaseNodeInstID(ProductionPlan.SENAME);
		mainProductionOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		mainProductionOrderMap.setServiceEntityManager(productionOrderManager);
		List<SearchConfigConnectCondition> mainProductionOrderConditionList = new ArrayList<>();
		SearchConfigConnectCondition mainProductionOrderCondition0 = new SearchConfigConnectCondition();
		mainProductionOrderCondition0
				.setSourceFieldName("refMainProdOrderUUID");
		mainProductionOrderCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		mainProductionOrderConditionList.add(mainProductionOrderCondition0);
		mainProductionOrderMap
				.setConnectionConditions(mainProductionOrderConditionList);
		Class<?>[] mainProductionOrderConvToUIParas = { ProductionOrder.class,
				ProductionPlanUIModel.class };
		mainProductionOrderMap
				.setConvToUIMethodParas(mainProductionOrderConvToUIParas);
		mainProductionOrderMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvMainProductionOrderToUI);
		uiModelNodeMapList.add(mainProductionOrderMap);

		// UI Model Configure of node:[OutMaterialStockKeepUnit]
		UIModelNodeMapConfigure outMaterialStockKeepUnitMap = new UIModelNodeMapConfigure();
		outMaterialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		outMaterialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		outMaterialStockKeepUnitMap.setNodeInstID("OutMaterialStockKeepUnit");
		outMaterialStockKeepUnitMap.setBaseNodeInstID(ProductionPlan.SENAME);
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
				MaterialStockKeepUnit.class, ProductionPlanUIModel.class };
		outMaterialStockKeepUnitMap
				.setConvToUIMethodParas(outMaterialStockKeepUnitConvToUIParas);
		outMaterialStockKeepUnitMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvOutMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(outMaterialStockKeepUnitMap);
		productionPlanExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionPlanExtensionUnion);
		return resultList;
	}

}
