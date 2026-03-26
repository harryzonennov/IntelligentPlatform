package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemAttachment;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class WarehouseStoreItemServiceUIModelExtension extends ServiceUIModelExtension{

	@Autowired
	protected WarehouseStoreItemLogServiceUIModelExtension warehouseStoreItemLogServiceUIModelExtension;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				WarehouseStoreItemAttachment.SENAME,
				WarehouseStoreItemAttachment.NODENAME,
				WarehouseStoreItemAttachment.NODENAME
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreItemParty.SENAME,
				WarehouseStoreItemParty.NODENAME,
				WarehouseStoreItemParty.PARTY_NODEINST_PUR_SUPPLIER,
				warehouseStoreManager,
				WarehouseStoreItemParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreItemParty.SENAME,
				WarehouseStoreItemParty.NODENAME,
				WarehouseStoreItemParty.PARTY_NODEINST_PUR_ORG,
				warehouseStoreManager,
				WarehouseStoreItemParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreItemParty.SENAME,
				WarehouseStoreItemParty.NODENAME,
				WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
				warehouseStoreManager,
				WarehouseStoreItemParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreItemParty.SENAME,
				WarehouseStoreItemParty.NODENAME,
				WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG,
				warehouseStoreManager,
				WarehouseStoreItemParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				WarehouseStoreItemParty.SENAME,
				WarehouseStoreItemParty.NODENAME,
				WarehouseStoreItemParty.PARTY_NODEINST_PROD_ORG,
				warehouseStoreManager,
				WarehouseStoreItemParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(warehouseStoreItemLogServiceUIModelExtension);
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseStoreItemExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseStoreItemExtensionUnion
				.setNodeInstId(WarehouseStoreItem.NODENAME);
		warehouseStoreItemExtensionUnion
				.setNodeName(WarehouseStoreItem.NODENAME);

		// UI Model Configure of node:[WarehouseStoreItem]
		UIModelNodeMapConfigure warehouseStoreItemMap = new UIModelNodeMapConfigure();
		warehouseStoreItemMap.setSeName(WarehouseStoreItem.SENAME);
		warehouseStoreItemMap.setNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setNodeInstID(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setHostNodeFlag(true);
		Class<?>[] warehouseStoreItemConvToUIParas = {
				WarehouseStoreItem.class, WarehouseStoreItemUIModel.class, WarehouseStoreItemUIModel.ConvertMeta.class };
		warehouseStoreItemMap.setLogicManager(warehouseStoreItemManager);
		warehouseStoreItemMap
				.setConvToUIMethodParas(warehouseStoreItemConvToUIParas);
		warehouseStoreItemMap
				.setConvToUIMethod(WarehouseStoreItemManager.METHOD_ConvWarehouseStoreItemToUI);
		Class<?>[] WarehouseStoreItemConvUIToParas = {
				WarehouseStoreItemUIModel.class, WarehouseStoreItem.class };
		warehouseStoreItemMap
				.setConvUIToMethodParas(WarehouseStoreItemConvUIToParas);
		warehouseStoreItemMap
				.setConvUIToMethod(WarehouseStoreItemManager.METHOD_ConvUIToWarehouseStoreItem);
		uiModelNodeMapList.add(warehouseStoreItemMap);

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(WarehouseStoreItem.NODENAME));

		Class<?>[] inboundItemConvToUIParas = { InboundItem.class, WarehouseStoreItemUIModel.class };
		Class<?>[] inboundDeliveryConvToUIParas = { InboundDelivery.class,	WarehouseStoreItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy.getSpecNodeMapConfigureList(new DocFlowProxy.SimpleDocConfigurePara(
				IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, WarehouseStoreItem.NODENAME,
				IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID,
				InboundDelivery.SENAME, warehouseStoreItemManager,
				WarehouseStoreItemManager.METHOD_ConvInboundDeliveryToStoreItemUI,
				inboundDeliveryConvToUIParas, WarehouseStoreItemManager.METHOD_ConvInboundItemToStoreItemUI,
				inboundItemConvToUIParas
		)));

		Class<?>[] outboundItemConvToUIParas = { OutboundItem.class, WarehouseStoreItemUIModel.class };
		Class<?>[] outboundDeliveryConvToUIParas = { OutboundDelivery.class,	WarehouseStoreItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy.getSpecNodeMapConfigureList(new DocFlowProxy.SimpleDocConfigurePara(
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, WarehouseStoreItem.NODENAME,
				IServiceEntityNodeFieldConstant.UUID, "refStoreItemUUID",
				OutboundDelivery.SENAME, warehouseStoreItemManager,
				WarehouseStoreItemManager.METHOD_ConvOutboundDeliveryToStoreItemUI,
				outboundDeliveryConvToUIParas, WarehouseStoreItemManager.METHOD_ConvOutboundItemToStoreItemUI,
				outboundItemConvToUIParas
		)));

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(WarehouseStoreItem.NODENAME));

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				WarehouseStoreItemUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				WarehouseStoreItemUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				WarehouseStoreItem.NODENAME, warehouseStoreItemManager,
				WarehouseStoreItemManager.METHOD_ConvWarehouseToStoreItemUI,
				warehouseConvToUIParas, WarehouseStoreItemManager.METHOD_ConvWarehouseAreaToStoreItemUI,
				warehouseAreaConvToUIParas
		)));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								WarehouseStoreItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(WarehouseStoreItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevNodeMapConfigureList(WarehouseStoreItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextNodeMapConfigureList(WarehouseStoreItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefCreateUpdateNodeMapConfigureList(WarehouseStoreItem.NODENAME));

		warehouseStoreItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(warehouseStoreItemExtensionUnion);
		return resultList;
	}

}
