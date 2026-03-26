package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderServiceModel;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItem;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdTargetItemToCrossQuaInspectProxy {

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	/**
	 * Main entrance to create direct quality inspect order & in-bound items
	 * based on production target and material item list
	 * 
	 * @param repairProdOrder
	 * @param allRepairProdTargetMatItemList
	 * @param repairProdTargetMatItemList
	 * 
	 * @param refMaterialSKUList
	 *            : online reference material SKU list, should be quality check
	 *            [OFF] materials
	 * @param genRequest
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceModuleProxyException
	 */
	public void createQualityInspectOrderBatch(
			RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> allRepairProdTargetMatItemList,
			List<ServiceEntityNode> repairProdTargetMatItemList,
			List<ServiceEntityNode> refMaterialSKUList,
			DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo) throws ServiceEntityConfigureException,
            SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException, DocActionException {
		/*
		 * [Step1] found the existed quality check data: check order and existed
		 * reference check material item
		 */
		if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
			return;
		}
		if (ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
			return;
		}
		List<ServiceEntityNode> qualityInspectOrderList = getExistQualityInspectOrderForCreationBatch(
				repairProdOrder, allRepairProdTargetMatItemList);
		/*
		 * [1.5] Logic build quality inspect order
		 */
		QualityInspectOrder qualityInspectOrder;
		boolean newModelFlag = false;
		if (!ServiceCollectionsHelper.checkNullList(qualityInspectOrderList)) {
			qualityInspectOrder = (QualityInspectOrder) qualityInspectOrderList
					.get(0);
		} else {
			newModelFlag = true;
			// If can not find, then create new quality inspect order
			qualityInspectOrder = (QualityInspectOrder) qualityInspectOrderManager
					.newRootEntityNode(repairProdOrder.getClient());
			qualityInspectOrder
					.setCategory(QualityInspectOrder.CATEGORY_PRODUCTION);
			// and initial convert production target information to this new
			// quality inspect order
			if (genRequest != null) {
				if (ServiceEntityStringHelper.checkNullString(genRequest
						.getRefWarehouseUUID())) {
					// In case no warehouse
					Warehouse defWarehouse = warehouseManager
							.getDefaultWarehouse(logonInfo);
					if (defWarehouse != null) {
						genRequest.setRefWarehouseUUID(defWarehouse.getUuid());
					}
				}
				qualityInspectOrder.setRefWarehouseUUID(genRequest
						.getRefWarehouseUUID());
				qualityInspectOrder.setRefWarehouseAreaUUID(genRequest
						.getRefWarehouseAreaUUID());
			}
			initCopyRepairProdOrderToQualityInspectOrder(repairProdOrder,
					qualityInspectOrder);
		}
		/*
		 * [Step2] Batch creation of in-bound item list from production target
		 * material item list
		 */
		List<ServiceEntityNode> qualityInspectMatItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : repairProdTargetMatItemList) {
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
			MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper
					.filterSENodeOnline(repairProdTargetMatItem
							.getRefMaterialSKUUUID(), refMaterialSKUList);
			MaterialStockKeepUnit templateMaterial = materialStockKeepUnitManager
					.getRefTemplateMaterialSKU(refMaterialSKU);
			// Quality inspect flag only for template material
			if (templateMaterial.getQualityInspectFlag() == StandardSwitchProxy.SWITCH_OFF) {
				continue;
			}
			if (!ServiceEntityStringHelper
					.checkNullString(repairProdTargetMatItem
							.getNextDocMatItemUUID())) {
				continue;
			}
			QualityInspectMatItem qualityInspectMatItem = genQualityInspectMatItemCore(
					repairProdTargetMatItem,
					qualityInspectOrder, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			qualityInspectMatItemList.add(qualityInspectMatItem);
		}
		/*
		 * Update production targetmaterial item list into DB
		 */
		repairProdOrderManager.updateSENodeList(repairProdTargetMatItemList,
				logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		/*
		 * [Step3] Update quality inspect order into Persistence as well as
		 * in-bound determination
		 */
		if (newModelFlag) {
			QualityInspectOrderServiceModel qualityInspectOrderServiceModel = (QualityInspectOrderServiceModel) qualityInspectOrderManager
					.quickCreateServiceModel(qualityInspectOrder,
							qualityInspectMatItemList);
			qualityInspectOrderManager.updateServiceModuleWithPost(
					QualityInspectOrderServiceModel.class,
					qualityInspectOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		} else {
			qualityInspectOrderManager.updateSENode(qualityInspectOrder,
					logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
			qualityInspectOrderManager.updateSENodeList(
					qualityInspectMatItemList, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		}

	}

	/**
	 * Logic to get the Existed & Proper Quality Inspect item instance list for
	 * batch Quality inspect order creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: Quality Inspect Order already existed and binded to one of prod target
	 * material items. 2nd: Quality Inspect Order status should be 'Initial'.
	 * 
	 * @param repairProdOrder
	 * @param allRepairProdTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private List<ServiceEntityNode> getExistQualityInspectItemListForCreationBatch(
			RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> allRepairProdTargetMatItemList)
			throws ServiceEntityConfigureException {
		List<String> refQuaItemUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper
				.checkNullList(allRepairProdTargetMatItemList)) {
			// In case raw all production target material item list is provided
			// as input paras.
			for (ServiceEntityNode seNode : allRepairProdTargetMatItemList) {
				RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
				if (!ServiceEntityStringHelper
						.checkNullString(repairProdTargetMatItem
								.getNextDocMatItemUUID())
						&& repairProdTargetMatItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER) {
					refQuaItemUUIDList.add(repairProdTargetMatItem
							.getNextDocMatItemUUID());
				}
			}
		} else {
			// In case raw production target material item list not provided.
			allRepairProdTargetMatItemList = repairProdOrderManager
					.getEntityNodeListByKey(repairProdOrder.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							RepairProdTargetMatItem.NODENAME,
							repairProdOrder.getClient(), null);
			if (!ServiceCollectionsHelper
					.checkNullList(allRepairProdTargetMatItemList)) {
				// In case raw all production target material item list is
				// provided
				// as input paras.
				for (ServiceEntityNode seNode : allRepairProdTargetMatItemList) {
					RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
					if (!ServiceEntityStringHelper
							.checkNullString(repairProdTargetMatItem
									.getNextDocMatItemUUID())
							&& repairProdTargetMatItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER) {
						refQuaItemUUIDList.add(repairProdTargetMatItem
								.getNextDocMatItemUUID());
					}
				}
			}
		}
		/*
		 * [Step2]:Get the quality material item list
		 */
		List<ServiceEntityNode> qualityMatItemList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(refQuaItemUUIDList)) {
			qualityMatItemList = qualityInspectOrderManager
					.getEntityNodeListByMultipleKey(refQuaItemUUIDList,
							IServiceEntityNodeFieldConstant.UUID,
							QualityInspectMatItem.NODENAME,
							repairProdOrder.getClient(), null);
		}
		return qualityMatItemList;
	}

	/**
	 * Core Logic to generate in-bound item or quality inspect item for each
	 * repairProdTargetMatItem instance
	 * 
	 * @param repairProdTargetMatItem
	 * @throws ServiceEntityConfigureException
	 */
	private QualityInspectMatItem genQualityInspectMatItemCore(
			RepairProdTargetMatItem repairProdTargetMatItem,
			QualityInspectOrder qualityInspectOrder, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
		// Initial converting the production targetitem information to in-bound.
		QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) qualityInspectOrderManager
				.newEntityNode(qualityInspectOrder,
						QualityInspectMatItem.NODENAME);
		initConvertProdTargetItemToQualityInspectItem(
				repairProdTargetMatItem,
				qualityInspectMatItem, serialLogonInfo);
		qualityInspectMatItem.setRefWarehouseAreaUUID(qualityInspectOrder
				.getRefWarehouseAreaUUID());
//		repairProdTargetMatItem.setRefInWarehouseUUID(qualityInspectOrder
//				.getRefWarehouseUUID());
		return qualityInspectMatItem;
	}

	/**
	 * Logic to get the Existed & Proper Quality inspect order instance list for
	 * batch Quality inspect order creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: Quality order already existed and binded to one of production targetmaterial
	 * items. 2nd: Quality order status should be 'Initial'. 3rd: Quality order
	 * type should be in-bound check and warehouse matches
	 * 
	 * 
	 * @param repairProdOrder
	 * @param allRepairProdTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private List<ServiceEntityNode> getExistQualityInspectOrderForCreationBatch(
			RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> allRepairProdTargetMatItemList)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> qualityMatItemList = getExistQualityInspectItemListForCreationBatch(
				repairProdOrder, allRepairProdTargetMatItemList);
		List<ServiceEntityNode> qualityInspectOrderList = new ArrayList<>();
		List<String> rootUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(qualityMatItemList)) {
			qualityMatItemList.forEach(rawSENode -> {
				if (!rootUUIDList.contains(rawSENode.getRootNodeUUID())) {
					rootUUIDList.add(rawSENode.getRootNodeUUID());
				}
			});
			qualityInspectOrderList = qualityInspectOrderManager
					.getEntityNodeListByMultipleKey(rootUUIDList,
							IServiceEntityNodeFieldConstant.UUID,
							QualityInspectOrder.NODENAME,
							repairProdOrder.getClient(), null);
		}

		/**
		 * [Step3] Traverse and process each quality Inspect Order
		 */
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(qualityInspectOrderList)) {
			for (ServiceEntityNode rawSENode : qualityInspectOrderList) {
				QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) rawSENode;
				if (qualityInspectOrder.getStatus() != QualityInspectOrder.STATUS_INITIAL) {
					continue;
				}
				if (qualityInspectOrder.getCategory() != QualityInspectOrder.CATEGORY_INBOUND) {
					continue;
				}
				resultList.add(qualityInspectOrder);
			}
		}
		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			return resultList;
		}
		return resultList;
	}

	public void initCopyRepairProdOrderToQualityInspectOrder(
			RepairProdOrder repairProdOrder,
			QualityInspectOrder qualityInspectOrder) {
		if (qualityInspectOrder != null && repairProdOrder != null) {
			qualityInspectOrder
					.setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
			qualityInspectOrder.setPrevProfDocUUID(repairProdOrder.getUuid());
			qualityInspectOrder
					.setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
			qualityInspectOrder.setReservedDocUUID(repairProdOrder.getUuid());
			qualityInspectOrder.setProductionBatchNumber(repairProdOrder
					.getProductionBatchNumber());
		}
	}

	/**
	 * Logic to initial convert and copy information from production targetmaterial item
	 * to quality inspect list item.
	 * 
	 * @param repairProdTargetMatItem
	 * @param qualityInspectMatItem
	 */
	public void initConvertProdTargetItemToQualityInspectItem(
			RepairProdTargetMatItem repairProdTargetMatItem,
			QualityInspectMatItem qualityInspectMatItem, SerialLogonInfo serialLogonInfo) throws DocActionException {
		if (qualityInspectMatItem != null
				&& repairProdTargetMatItem != null) {
			docFlowProxy.buildItemPrevNextRelationship(
					repairProdTargetMatItem,
					qualityInspectMatItem, null, serialLogonInfo);
			qualityInspectMatItem.setSampleAmount(repairProdTargetMatItem
					.getAmount());
		}
	}

}
