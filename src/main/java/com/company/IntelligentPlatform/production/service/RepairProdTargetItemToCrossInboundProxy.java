package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliverySpecifier;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
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
public class RepairProdTargetItemToCrossInboundProxy {

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected ProductionInboundDeliveryManager productionInboundDeliveryManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected InboundDeliverySpecifier inboundDeliverySpecifier;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	/**
	 * Main entrance to create direct in-bound delivery & in-bound items based
	 * on production order and material item list
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
	public void createDirectInboundBatch(RepairProdOrder repairProdOrder,
										 List<ServiceEntityNode> allRepairProdTargetMatItemList,
										 List<ServiceEntityNode> repairProdTargetMatItemList,
										 List<ServiceEntityNode> refMaterialSKUList,
										 DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo) throws ServiceEntityConfigureException,
            SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException, DocActionException {
		/*
		 * [Step1] found the existed in-bound data:delivery and existed
		 * reference in-bound item
		 */
		if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
			return;
		}
		List<ServiceEntityNode> inboundDeliveryList = getExistInboundDeliveryForCreationBatch(
				repairProdOrder, allRepairProdTargetMatItemList);
		/*
		 * [1.2] Logic to get in-bound item for reference.
		 */
		List<ServiceEntityNode> refInboundItemList = getExistInboundItemListForCreationBatch(
				repairProdOrder, allRepairProdTargetMatItemList);
		InboundItem refInboundItem = null;
		if (!ServiceCollectionsHelper.checkNullList(refInboundItemList)) {
			refInboundItem = (InboundItem) refInboundItemList.get(0);
		}
		/*
		 * [1.5] Logic build in-bound delivery
		 */
		InboundDelivery inboundDelivery;
		boolean newModelFlag = false;
		if (!ServiceCollectionsHelper.checkNullList(inboundDeliveryList)) {
			inboundDelivery = (InboundDelivery) inboundDeliveryList.get(0);
		} else {
			newModelFlag = true;
			// If can not find, then create new in-bound delivery
			inboundDelivery = (InboundDelivery) inboundDeliveryManager
					.newRootEntityNode(repairProdOrder.getClient());
			// and initial convert production target item information to this new
			// in-bound delivery
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
				inboundDelivery.setRefWarehouseUUID(genRequest
						.getRefWarehouseUUID());
				inboundDelivery.setRefWarehouseAreaUUID(genRequest
						.getRefWarehouseAreaUUID());
			}
			initConverRepairProdOrderToInboundDelivery(repairProdOrder,
					inboundDelivery);
		}
		/*
		 * [Step2] Batch creation of in-bound item list from production target item
		 * material item list
		 */
		List<ServiceEntityNode> inboundItemList = new ArrayList<>();
		// Get the existed in-bound item list
		
		for (ServiceEntityNode seNode : repairProdTargetMatItemList) {
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
			MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper
					.filterSENodeOnline(repairProdTargetMatItem
							.getRefMaterialSKUUUID(), refMaterialSKUList);
			MaterialStockKeepUnit templateMaterial = materialStockKeepUnitManager
					.getRefTemplateMaterialSKU(refMaterialSKU);
			// Quality inspect flag only for template material
			if (templateMaterial.getQualityInspectFlag() == StandardSwitchProxy.SWITCH_ON) {
				continue;
			}
			if(!ServiceEntityStringHelper.checkNullString(repairProdTargetMatItem.getNextDocMatItemUUID())){
				continue;
			}
			InboundItem inboundItem = genInboundItemCore(
					refMaterialSKU, repairProdTargetMatItem,
					inboundDelivery, refInboundItem, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			inboundItemList.add(inboundItem);			
		}
		/*
		 * Update product target material item list into DB
		 */
		repairProdOrderManager.updateSENodeList(repairProdTargetMatItemList,
				logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		/*
		 * [Step4] Update in-bound delivery into Persistence as well as in-bound
		 * determination
		 */
		if(newModelFlag){
			InboundDeliveryServiceModel inboundDeliveryServiceModel = inboundDeliverySpecifier
					.quickCreateServiceModel(inboundDelivery,
							inboundItemList);
			inboundDeliveryManager.updateServiceModuleWithPost(
					InboundDeliveryServiceModel.class, inboundDeliveryServiceModel,
					logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		}else{
			inboundDeliveryManager.updateSENode(inboundDelivery,
					logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
			inboundDeliveryManager.updateSENodeList(
					inboundItemList, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		}
		
	}

	/**
	 * Logic to get the Existed & Proper InboundDelivery Item instance list for
	 * batch in-bound delivery creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: InboundDelivery already existed and binded to one of production
	 * material items. 2nd: InboundDelivery status should be 'Initial'. 3rd:
	 * InboundDelivery should be production order generation type
	 * 
	 * @param repairProdOrder
	 * @param rawRepairProdTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private List<ServiceEntityNode> getExistInboundItemListForCreationBatch(
			RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> rawRepairProdTargetMatItemList)
			throws ServiceEntityConfigureException {
		List<String> refInboundItemUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper
				.checkNullList(rawRepairProdTargetMatItemList)) {
			// In case raw all production order material item list is provided
			// as input paras.
			for (ServiceEntityNode seNode : rawRepairProdTargetMatItemList) {
				RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
				if (!ServiceEntityStringHelper
						.checkNullString(repairProdTargetMatItem
								.getNextDocMatItemUUID())
						&& repairProdTargetMatItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
					refInboundItemUUIDList.add(repairProdTargetMatItem
							.getNextDocMatItemUUID());
				}
			}
		} else {
			// In case raw production order material item list not provided.
			rawRepairProdTargetMatItemList = repairProdOrderManager
					.getEntityNodeListByKey(repairProdOrder.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							RepairProdTargetMatItem.NODENAME,
							repairProdOrder.getClient(), null);
			if (!ServiceCollectionsHelper
					.checkNullList(rawRepairProdTargetMatItemList)) {
				// In case raw all production order material item list is
				// provided
				// as input paras.
				for (ServiceEntityNode seNode : rawRepairProdTargetMatItemList) {
					RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
					if (!ServiceEntityStringHelper
							.checkNullString(repairProdTargetMatItem
									.getNextDocMatItemUUID())) {
						refInboundItemUUIDList.add(repairProdTargetMatItem
								.getNextDocMatItemUUID());
					}
				}
			}
		}

		/*
		 * [Step2]:Get the in-bound delivery list
		 */
		List<ServiceEntityNode> inboundItemList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(refInboundItemUUIDList)) {
			inboundItemList = inboundDeliveryManager
					.getEntityNodeListByMultipleKey(refInboundItemUUIDList,
							IServiceEntityNodeFieldConstant.UUID,
							InboundItem.NODENAME,
							repairProdOrder.getClient(), null);

		}
		return inboundItemList;
	}

	/**
	 * Core Logic to generate in-bound item for each
	 * repairProdTargetMatItem instance
	 * 
	 * @param repairProdTargetMatItem
	 * @throws ServiceEntityConfigureException
	 */
	private InboundItem genInboundItemCore(
			MaterialStockKeepUnit materialStockKeepUnit,
			RepairProdTargetMatItem repairProdTargetMatItem,
			InboundDelivery inboundDelivery, InboundItem refInboundItem, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
		// Initial converting the production target item information to in-bound.
		InboundItem inboundItem = (InboundItem) inboundDeliveryManager
				.newEntityNode(inboundDelivery, InboundItem.NODENAME);
		initConvertProdTargetItemToQualityInspectItem(repairProdTargetMatItem,
				materialStockKeepUnit, inboundItem, refInboundItem, serialLogonInfo);
		return inboundItem;
	}

	/**
	 * Logic to get the Existed & Proper InboundDelivery instance list for batch
	 * inbound delivery creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: InboundDelivery already existed and binded to one of product target
	 * material items. 2nd: InboundDelivery status should be 'Initial'. 3rd:
	 * InboundDelivery should be production order generation type
	 * 
	 * 
	 * @param repairProdOrder
	 * @param rawRepairProdTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getExistInboundDeliveryForCreationBatch(
			RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> rawRepairProdTargetMatItemList)
			throws ServiceEntityConfigureException {

		List<ServiceEntityNode> inboundItemList = getExistInboundItemListForCreationBatch(
				repairProdOrder, rawRepairProdTargetMatItemList);
		List<String> rootUUIDList = new ArrayList<>();
		List<ServiceEntityNode> inboundDeliveryList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(inboundItemList)) {
			inboundItemList.forEach(rawSENode -> {
				if (!rootUUIDList.contains(rawSENode.getRootNodeUUID())) {
					rootUUIDList.add(rawSENode.getRootNodeUUID());
				}
			});
			inboundDeliveryList = inboundDeliveryManager
					.getEntityNodeListByMultipleKey(rootUUIDList,
							IServiceEntityNodeFieldConstant.UUID,
							InboundDelivery.NODENAME,
							repairProdOrder.getClient(), null);
		}

		/*
		 * [Step3] Traverse and process each in-bound delivery
		 */
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(inboundDeliveryList)) {
			for (ServiceEntityNode rawSENode : inboundDeliveryList) {
				InboundDelivery inboundDelivery = (InboundDelivery) rawSENode;
				if (inboundDelivery.getStatus() != InboundDelivery.STATUS_INITIAL) {
					continue;
				}
				resultList.add(inboundDelivery);
			}
		}
		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			return resultList;
		}
		return resultList;
	}

	public void initConverRepairProdOrderToInboundDelivery(
			RepairProdOrder repairProdOrder, InboundDelivery inboundDelivery) {
		if (inboundDelivery != null && repairProdOrder != null) {
			inboundDelivery
					.setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
			inboundDelivery.setPrevProfDocUUID(repairProdOrder.getUuid());
			inboundDelivery.setProductionBatchNumber(repairProdOrder.getProductionBatchNumber());

		}
	}

	/**
	 * Logic to initial convert and copy information from prod target material item
	 * to inbound list item.
	 * 
	 * @param repairProdTargetMatItem
	 * @param inboundItem
	 * @param refInboundItem
	 */
	public void initConvertProdTargetItemToQualityInspectItem(
			RepairProdTargetMatItem repairProdTargetMatItem,
			MaterialStockKeepUnit materialStockKeepUnit,
			InboundItem inboundItem,
			InboundItem refInboundItem, SerialLogonInfo serialLogonInfo) throws DocActionException {
		if (inboundItem != null
				&& repairProdTargetMatItem != null) {
			repairProdTargetMatItem.setRefMaterialSKUUUID(repairProdTargetMatItem.getRefMaterialSKUUUID());
			productionInboundDeliveryManager.copyPrevDocMatItemToInboundItem(
					repairProdTargetMatItem,
					IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER,
					inboundItem, materialStockKeepUnit, serialLogonInfo);
			inboundItem
					.setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
			inboundItem.setPrevProfDocMatItemUUID(repairProdTargetMatItem
					.getUuid());
			if (refInboundItem != null) {
				inboundItem
						.setRefWarehouseAreaUUID(refInboundItem
								.getRefWarehouseAreaUUID());
				inboundItem
						.setRefShelfNumberID(refInboundItem
								.getRefShelfNumberID());
			}
		}
	}

}
