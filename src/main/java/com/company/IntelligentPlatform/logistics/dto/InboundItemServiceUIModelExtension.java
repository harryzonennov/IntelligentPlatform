package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InboundItemManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InboundItemServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected InboundItemManager inboundItemManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InboundItemAttachment.SENAME,
				InboundItemAttachment.NODENAME,
				InboundItemAttachment.NODENAME
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundItemParty.SENAME,
				InboundItemParty.NODENAME,
				InboundItemParty.PARTY_NODEINST_PUR_SUPPLIER,
				inboundDeliveryManager,
				InboundItemParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundItemParty.SENAME,
				InboundItemParty.NODENAME,
				InboundItemParty.PARTY_NODEINST_PUR_ORG,
				inboundDeliveryManager,
				InboundItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundItemParty.SENAME,
				InboundItemParty.NODENAME,
				InboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				inboundDeliveryManager,
				InboundItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundItemParty.SENAME,
				InboundItemParty.NODENAME,
				InboundItemParty.PARTY_NODEINST_SOLD_ORG,
				inboundDeliveryManager,
				InboundItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				InboundItemParty.SENAME,
				InboundItemParty.NODENAME,
				InboundItemParty.PARTY_NODEINST_PROD_ORG,
				inboundDeliveryManager,
				InboundItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inboundItemExtensionUnion = new ServiceUIModelExtensionUnion();
		inboundItemExtensionUnion
				.setNodeInstId(InboundItem.NODENAME);
		inboundItemExtensionUnion
				.setNodeName(InboundItem.NODENAME);

		// UI Model Configure of node:[InboundItem]
		UIModelNodeMapConfigure inboundItemMap = new UIModelNodeMapConfigure();
		inboundItemMap.setSeName(InboundItem.SENAME);
		inboundItemMap.setNodeName(InboundItem.NODENAME);
		inboundItemMap.setNodeInstID(InboundItem.NODENAME);
		inboundItemMap.setHostNodeFlag(true);
		Class<?>[] inboundItemConvToUIParas = {
				InboundItem.class, InboundItemUIModel.class };
		inboundItemMap
				.setConvToUIMethodParas(inboundItemConvToUIParas);
		inboundItemMap.setLogicManager(inboundItemManager);
		inboundItemMap
				.setConvToUIMethod(InboundItemManager.METHOD_ConvInboundItemToUI);
		Class<?>[] InboundItemConvUIToParas = {
				InboundItemUIModel.class, InboundItem.class };
		inboundItemMap
				.setConvUIToMethodParas(InboundItemConvUIToParas);
		inboundItemMap
				.setConvUIToMethod(InboundItemManager.METHOD_ConvUIToInboundItem);
		uiModelNodeMapList.add(inboundItemMap);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, InboundItemUIModel.class };
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								InboundItem.NODENAME,
								InboundItemManager.METHOD_ConvMaterialStockKeepUnitToUI, inboundItemManager,
								materialStockKeepUnitConvToUIParas));

		// UI Model Configure of node:[reserved Order MatItem]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(InboundItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(InboundItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfNodeMapConfigureList(InboundItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfNodeMapConfigureList(InboundItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(InboundItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefCreateUpdateNodeMapConfigureList(InboundItem.NODENAME));

		// UI Model Configure of node:[InboundDelivery]
		Class<?>[] convParentDocToUIMethodParas = {InboundDelivery.class, InboundItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(InboundItem.NODENAME,
				InboundItemManager.METHOD_ConvParentDocToItemUI, inboundItemManager,
				convParentDocToUIMethodParas));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				InboundItemUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				InboundItemUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				InboundDelivery.SENAME, inboundItemManager,
				InboundItemManager.METHOD_ConvWarehouseToItemUI,
				warehouseConvToUIParas, InboundItemManager.METHOD_ConvWarehouseAreaToItemUI,
				warehouseAreaConvToUIParas
		)));

		inboundItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inboundItemExtensionUnion);
		return resultList;
	}

}
