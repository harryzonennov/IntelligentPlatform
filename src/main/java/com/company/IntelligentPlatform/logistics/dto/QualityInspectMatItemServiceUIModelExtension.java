package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectMatItemManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class QualityInspectMatItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected QualityInspectMatItemManager qualityInspectMatItemManager;

	@Autowired
	protected QualityInspectPropertyItemServiceUIModelExtension qualityInspectPropertyItemServiceUIModelExtension;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(qualityInspectPropertyItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				QualityInsMatItemAttachment.SENAME,
				QualityInsMatItemAttachment.NODENAME,
				QualityInsMatItemAttachment.NODENAME
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectMatItemParty.SENAME,
				QualityInspectMatItemParty.NODENAME,
				QualityInspectMatItemParty.PARTY_NODEINST_PUR_SUPPLIER,
				qualityInspectOrderManager,
				QualityInspectMatItemParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectMatItemParty.SENAME,
				QualityInspectMatItemParty.NODENAME,
				QualityInspectMatItemParty.PARTY_NODEINST_PUR_ORG,
				qualityInspectOrderManager,
				QualityInspectMatItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectMatItemParty.SENAME,
				QualityInspectMatItemParty.NODENAME,
				QualityInspectMatItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				qualityInspectOrderManager,
				QualityInspectMatItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectMatItemParty.SENAME,
				QualityInspectMatItemParty.NODENAME,
				QualityInspectMatItemParty.PARTY_NODEINST_SOLD_ORG,
				qualityInspectOrderManager,
				QualityInspectMatItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectMatItemParty.SENAME,
				QualityInspectMatItemParty.NODENAME,
				QualityInspectMatItemParty.PARTY_NODEINST_PROD_ORG,
				qualityInspectOrderManager,
				QualityInspectMatItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion qualityInspectMatItemExtensionUnion = new ServiceUIModelExtensionUnion();
		qualityInspectMatItemExtensionUnion
				.setNodeInstId(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemExtensionUnion
				.setNodeName(QualityInspectMatItem.NODENAME);

		// UI Model Configure of node:[QualityInspectMatItem]
		UIModelNodeMapConfigure qualityInspectMatItemMap = new UIModelNodeMapConfigure();
		qualityInspectMatItemMap.setSeName(QualityInspectMatItem.SENAME);
		qualityInspectMatItemMap.setNodeName(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemMap.setNodeInstID(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemMap.setHostNodeFlag(true);
		Class<?>[] qualityInspectMatItemConvToUIParas = {
				QualityInspectMatItem.class, QualityInspectMatItemUIModel.class };
		qualityInspectMatItemMap
				.setConvToUIMethodParas(qualityInspectMatItemConvToUIParas);
		qualityInspectMatItemMap.setLogicManager(qualityInspectMatItemManager);
		qualityInspectMatItemMap
				.setConvToUIMethod(QualityInspectMatItemManager.METHOD_ConvQualityInspectMatItemToUI);
		Class<?>[] QualityInspectMatItemConvUIToParas = {
				QualityInspectMatItemUIModel.class, QualityInspectMatItem.class };
		qualityInspectMatItemMap
				.setConvUIToMethodParas(QualityInspectMatItemConvUIToParas);
		qualityInspectMatItemMap
				.setConvUIToMethod(QualityInspectMatItemManager.METHOD_ConvUIToQualityInspectMatItem);
		uiModelNodeMapList.add(qualityInspectMatItemMap);

		// UI Model Configure of node:[QualityInspectOrder]
		UIModelNodeMapConfigure qualityInspectOrderMap = new UIModelNodeMapConfigure();
		qualityInspectOrderMap.setSeName(QualityInspectOrder.SENAME);
		qualityInspectOrderMap.setNodeName(QualityInspectOrder.NODENAME);
		qualityInspectOrderMap.setNodeInstID(QualityInspectOrder.NODENAME);
		qualityInspectOrderMap.setHostNodeFlag(false);
		qualityInspectOrderMap
				.setBaseNodeInstID(QualityInspectMatItem.NODENAME);
		qualityInspectOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] qualityInspectOrderConvToUIParas = {
				QualityInspectOrder.class, QualityInspectMatItemUIModel.class };
		qualityInspectOrderMap
				.setConvToUIMethodParas(qualityInspectOrderConvToUIParas);
		qualityInspectOrderMap.setLogicManager(qualityInspectMatItemManager);
		qualityInspectOrderMap
				.setConvToUIMethod(QualityInspectMatItemManager.METHOD_ConvParentDocToItemUI);

		// UI Model Configure of node:[QualityInspectOrder]
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(QualityInspectMatItem.NODENAME));

		uiModelNodeMapList.add(qualityInspectOrderMap);

		// UI Model Configure of node:[Prev Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(QualityInspectMatItem.NODENAME));

		// UI Model Configure of node:[Reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(QualityInspectMatItem.NODENAME));

		// UI Model Configure of node:[ref Next MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(QualityInspectMatItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(QualityInspectMatItem.NODENAME));

		// UI Model Configure of node:[warehouseAreaMap]
		UIModelNodeMapConfigure warehouseAreaMap = new UIModelNodeMapConfigure();
		warehouseAreaMap.setSeName(WarehouseArea.SENAME);
		warehouseAreaMap.setNodeName(WarehouseArea.NODENAME);
		warehouseAreaMap.setNodeInstID(WarehouseArea.NODENAME);
		warehouseAreaMap.setHostNodeFlag(false);
		warehouseAreaMap.setBaseNodeInstID(QualityInspectMatItem.NODENAME);
		warehouseAreaMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> warehouseAraConditionList = new ArrayList<>();
		SearchConfigConnectCondition warehouseAreaCondition0 = new SearchConfigConnectCondition();
		warehouseAreaCondition0.setSourceFieldName("refWarehouseAreaUUID");
		warehouseAreaCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		warehouseAraConditionList.add(warehouseAreaCondition0);
		warehouseAreaMap.setConnectionConditions(warehouseAraConditionList);
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				QualityInspectMatItemUIModel.class };
		warehouseAreaMap.setServiceEntityManager(warehouseManager);
		warehouseAreaMap.setConvToUIMethodParas(warehouseAreaConvToUIParas);
		warehouseAreaMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvWarehouseAreaToItemUI);
		uiModelNodeMapList.add(warehouseAreaMap);

		// UI Model Configure of node:[refWasteWarehouse]
		UIModelNodeMapConfigure refWasteWarehouseMap = new UIModelNodeMapConfigure();
		refWasteWarehouseMap.setSeName(Warehouse.SENAME);
		refWasteWarehouseMap.setNodeName(Warehouse.NODENAME);
		refWasteWarehouseMap.setNodeInstID(Warehouse.SENAME);
		refWasteWarehouseMap.setHostNodeFlag(false);
		refWasteWarehouseMap.setBaseNodeInstID(QualityInspectMatItem.NODENAME);
		refWasteWarehouseMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> refWasteWarehouseConditionList = new ArrayList<>();
		SearchConfigConnectCondition refWasteWarehouseCondition0 = new SearchConfigConnectCondition();
		refWasteWarehouseCondition0.setSourceFieldName("refWasteWarehouseUUID");
		refWasteWarehouseCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		refWasteWarehouseConditionList.add(refWasteWarehouseCondition0);
		refWasteWarehouseMap
				.setConnectionConditions(refWasteWarehouseConditionList);
		Class<?>[] refWasteWarehouseConvToUIParas = { Warehouse.class,
				QualityInspectMatItemUIModel.class };
		refWasteWarehouseMap.setServiceEntityManager(warehouseManager);
		refWasteWarehouseMap
				.setConvToUIMethodParas(refWasteWarehouseConvToUIParas);
		refWasteWarehouseMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvRefWasteWarehouseToItemUI);
		uiModelNodeMapList.add(refWasteWarehouseMap);

		// UI Model Configure of node:[refWasteWareAreaMap]
		UIModelNodeMapConfigure refWasteWareAreaMap = new UIModelNodeMapConfigure();
		refWasteWareAreaMap.setSeName(WarehouseArea.SENAME);
		refWasteWareAreaMap.setNodeName(WarehouseArea.NODENAME);
		refWasteWareAreaMap.setNodeInstID("refWasteWareArea");
		refWasteWareAreaMap.setHostNodeFlag(false);
		refWasteWareAreaMap.setBaseNodeInstID(QualityInspectMatItem.NODENAME);
		refWasteWareAreaMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> wasteAraConditionList = new ArrayList<>();
		SearchConfigConnectCondition wasteAreaCondition0 = new SearchConfigConnectCondition();
		wasteAreaCondition0.setSourceFieldName("refWasteWareAreaUUID");
		wasteAreaCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		wasteAraConditionList.add(wasteAreaCondition0);
		refWasteWareAreaMap.setConnectionConditions(wasteAraConditionList);
		refWasteWareAreaMap.setServiceEntityManager(warehouseManager);
		refWasteWareAreaMap.setConvToUIMethodParas(warehouseAreaConvToUIParas);
		refWasteWareAreaMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvRefWasteWarehouseAreaToItemUI);
		uiModelNodeMapList.add(refWasteWareAreaMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								QualityInspectMatItem.NODENAME));

		qualityInspectMatItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(qualityInspectMatItemExtensionUnion);
		return resultList;
	}

}
