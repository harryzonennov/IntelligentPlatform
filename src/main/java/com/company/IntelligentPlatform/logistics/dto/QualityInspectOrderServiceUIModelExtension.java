package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.LogisticsFlowProxy;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class QualityInspectOrderServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected QualityInspectMatItemServiceUIModelExtension qualityInspectMatItemServiceUIModelExtension;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected LogisticsFlowProxy logisticsFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(qualityInspectMatItemServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				QualityInsOrderAttachment.SENAME,
				QualityInsOrderAttachment.NODENAME,
				QualityInsOrderAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				QualityInsOrderActionNode.SENAME,
				QualityInsOrderActionNode.NODENAME,
				QualityInsOrderActionNode.NODEINST_ACTION_STRATTEST,
				qualityInspectOrderManager, QualityInsOrderActionNode.DOC_ACTION_START_TEST
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				QualityInsOrderActionNode.SENAME,
				QualityInsOrderActionNode.NODENAME,
				QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE,
				qualityInspectOrderManager, QualityInsOrderActionNode.DOC_ACTION_TESTDONE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				QualityInsOrderActionNode.SENAME,
				QualityInsOrderActionNode.NODENAME,
				QualityInsOrderActionNode.NODEINST_ACTION_PROCESS_DONE,
				qualityInspectOrderManager, QualityInsOrderActionNode.DOC_ACTION_PROCESS_DONE
		)));

		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectOrderParty.SENAME,
				QualityInspectOrderParty.NODENAME,
				QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
				qualityInspectOrderManager,
				QualityInspectOrderParty.PARTY_ROLE_SUPPLIER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectOrderParty.SENAME,
				QualityInspectOrderParty.NODENAME,
				QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG,
				qualityInspectOrderManager,
				QualityInspectOrderParty.PARTY_ROLE_PURORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectOrderParty.SENAME,
				QualityInspectOrderParty.NODENAME,
				QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
				qualityInspectOrderManager,
				QualityInspectOrderParty.PARTY_ROLE_CUSTOMER,
				CorporateCustomer.class,
				IndividualCustomer.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectOrderParty.SENAME,
				QualityInspectOrderParty.NODENAME,
				QualityInspectOrderParty.PARTY_NODEINST_SOLD_ORG,
				qualityInspectOrderManager,
				QualityInspectOrderParty.PARTY_ROLE_SALESORG,
				Organization.class,
				Employee.class
		)));
		resultList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
				QualityInspectOrderParty.SENAME,
				QualityInspectOrderParty.NODENAME,
				QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG,
				qualityInspectOrderManager,
				QualityInspectOrderParty.PARTY_ROLE_PRODORG,
				Organization.class,
				Employee.class
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion qualityInspectOrderExtensionUnion = new ServiceUIModelExtensionUnion();
		qualityInspectOrderExtensionUnion
				.setNodeInstId(QualityInspectOrder.SENAME);
		qualityInspectOrderExtensionUnion
				.setNodeName(QualityInspectOrder.NODENAME);

		// UI Model Configure of node:[QualityInspectOrder]
		UIModelNodeMapConfigure qualityInspectOrderMap = new UIModelNodeMapConfigure();
		qualityInspectOrderMap.setSeName(QualityInspectOrder.SENAME);
		qualityInspectOrderMap.setNodeName(QualityInspectOrder.NODENAME);
		qualityInspectOrderMap.setNodeInstID(QualityInspectOrder.SENAME);
		qualityInspectOrderMap.setHostNodeFlag(true);
		Class<?>[] qualityInspectOrderConvToUIParas = {
				QualityInspectOrder.class, QualityInspectOrderUIModel.class };
		qualityInspectOrderMap
				.setConvToUIMethodParas(qualityInspectOrderConvToUIParas);
		qualityInspectOrderMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvQualityInspectOrderToUI);
		Class<?>[] QualityInspectOrderConvUIToParas = {
				QualityInspectOrderUIModel.class, QualityInspectOrder.class };
		qualityInspectOrderMap
				.setConvUIToMethodParas(QualityInspectOrderConvUIToParas);
		qualityInspectOrderMap
				.setConvUIToMethod(QualityInspectOrderManager.METHOD_ConvUIToQualityInspectOrder);
		uiModelNodeMapList.add(qualityInspectOrderMap);
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(QualityInspectOrder.SENAME));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevProfDocMapConfigureList(QualityInspectOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefPrevDocMapConfigureList(QualityInspectOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextProfDocMapConfigureList(QualityInspectOrder.SENAME));
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefNextDocMapConfigureList(QualityInspectOrder.SENAME));

		// UI Model Configure of node:[reserved document Order]
		UIModelNodeMapConfigure reservedDocumentMap = new UIModelNodeMapConfigure();
		reservedDocumentMap.setNodeInstID("reservedDocument");
		reservedDocumentMap
				.setGetSENodeCallback(rawSENode -> {
					QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) rawSENode;
					ServiceEntityManager refReservedDocumentManager = serviceDocumentComProxy
							.getDocumentManager(qualityInspectOrder
									.getReservedDocType());
					if (refReservedDocumentManager == null) {
						return null;
					}
					ServiceEntityNode reservedDocContent = null;
					try {
						reservedDocContent = refReservedDocumentManager
								.getEntityNodeByUUID(qualityInspectOrder
										.getReservedDocUUID(),
										ServiceEntityNode.NODENAME_ROOT,
										qualityInspectOrder.getClient());
						return reservedDocContent;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		reservedDocumentMap.setBaseNodeInstID(QualityInspectOrder.SENAME);
		reservedDocumentMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] reservedDocumentConvToUIParas = { ServiceEntityNode.class,
				QualityInspectOrderUIModel.class };
		reservedDocumentMap
				.setConvToUIMethodParas(reservedDocumentConvToUIParas);
		reservedDocumentMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvReservedDocumentToUI);
		uiModelNodeMapList.add(reservedDocumentMap);

		// UI Model Configure of node:[Reserved Order MatItem]
		UIModelNodeMapConfigure reservedMatItemMap = new UIModelNodeMapConfigure();
		reservedMatItemMap.setBaseNodeInstID(QualityInspectOrder.SENAME);
		reservedMatItemMap.setNodeInstID("reservedMatItem");
		reservedMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		reservedMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(qualityInspectOrder
									.getReservedDocType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentItemNode = null;
					try {
						String targetNodeName = serviceDocumentComProxy
								.getDocumentMaterialItemNodeName(qualityInspectOrder
										.getReservedDocType());
						documentItemNode = refDocumentManager
								.getEntityNodeByUUID(qualityInspectOrder
										.getReservedDocUUID(),
										targetNodeName, qualityInspectOrder
												.getClient());
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(reservedMatItemMap);

		// UI Model Configure of node:[Reserved Order from reservedMatItemMap]
		UIModelNodeMapConfigure reservedOrderFromMatItemMap = new UIModelNodeMapConfigure();
		reservedOrderFromMatItemMap.setNodeInstID("reservedOrderFromMat");
		reservedOrderFromMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ServiceEntityNode refMaterialItem = (ServiceEntityNode) rawSENode;
					ServiceEntityManager refDocumentManager = serviceEntityManagerFactoryInContext
							.getManagerBySEName(refMaterialItem
									.getServiceEntityName());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentContent = null;
					try {
						documentContent = refDocumentManager
								.getEntityNodeByUUID(
										refMaterialItem.getRootNodeUUID(),
										ServiceEntityNode.NODENAME_ROOT,
										refMaterialItem.getClient());
						return documentContent;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		reservedOrderFromMatItemMap.setBaseNodeInstID("reservedMatItem");
		reservedOrderFromMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		reservedOrderFromMatItemMap
				.setConvToUIMethodParas(reservedDocumentConvToUIParas);
		reservedOrderFromMatItemMap
				.setConvToUIMethod(QualityInspectOrderManager.METHOD_ConvReservedDocumentToUI);
		uiModelNodeMapList.add(reservedOrderFromMatItemMap);

		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				QualityInspectOrderUIModel.class };
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				QualityInspectOrderUIModel.class };
		uiModelNodeMapList.addAll(logisticsFlowProxy.getDefWarehouseMapConfigureList(new LogisticsFlowProxy.WarehouseNodeMapRequest(
				QualityInspectOrder.SENAME, qualityInspectOrderManager,
				QualityInspectOrderManager.METHOD_ConvWarehouseToUI,
				warehouseConvToUIParas,
				QualityInspectOrderManager.METHOD_ConvWarehouseAreaToUI, warehouseAreaConvToUIParas
		)));

		qualityInspectOrderExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(qualityInspectOrderExtensionUnion);
		return resultList;
	}

}
