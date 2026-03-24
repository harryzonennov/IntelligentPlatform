package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliverySpecifier;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
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
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Deprecated
@Service
public class ProdOrderTargetItemToCrossInboundProxy {

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected ProductionInboundDeliveryManager productionInboundDeliveryManager;

	@Autowired
	protected InboundDeliverySpecifier inboundDeliverySpecifier;

	@Autowired
	protected WarehouseManager warehouseManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	/**
	 * Main entrance to create direct in-bound delivery & in-bound items based
	 * on production order and material item list
	 * 
	 * @param productionOrder
	 * @param allProdOrderTargetMatItemList
	 * @param prodOrderTargetMatItemList
	 * 
	 * @param refMaterialSKUList
	 *            : online reference material SKU list, should be quality check
	 *            [OFF] materials
	 * @param genRequest
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceModuleProxyException
	 */
	public void createDirectInboundBatch(ProductionOrder productionOrder,
										 List<ServiceEntityNode> allProdOrderTargetMatItemList,
										 List<ServiceEntityNode> prodOrderTargetMatItemList,
										 List<ServiceEntityNode> refMaterialSKUList,
										 DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo) throws ServiceEntityConfigureException,
            SearchConfigureException,
            ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException, DocActionException {
		/*
		 * [Step1] found the existed in-bound data:delivery and existed
		 * reference in-bound item
		 */
		if (ServiceCollectionsHelper.checkNullList(refMaterialSKUList)) {
			return;
		}
		List<ServiceEntityNode> inboundDeliveryList = getExistInboundDeliveryForCreationBatch(
				productionOrder, allProdOrderTargetMatItemList);
		/*
		 * [1.2] Logic to get in-bound item for reference.
		 */
		List<ServiceEntityNode> refInboundItemList = getExistInboundItemListForCreationBatch(
				productionOrder, allProdOrderTargetMatItemList);
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
					.newRootEntityNode(productionOrder.getClient());
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
			initConverProductionOrderToInboundDelivery(productionOrder,
					inboundDelivery);
		}
		/*
		 * [Step2] Batch creation of in-bound item list from production target item
		 * material item list
		 */
		List<ServiceEntityNode> inboundItemList = new ArrayList<>();
		// Get the existed in-bound item list
		
		for (ServiceEntityNode seNode : prodOrderTargetMatItemList) {
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
			MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper
					.filterSENodeOnline(prodOrderTargetMatItem
							.getRefMaterialSKUUUID(), refMaterialSKUList);
			MaterialStockKeepUnit templateMaterial = materialStockKeepUnitManager
					.getRefTemplateMaterialSKU(refMaterialSKU);
			// Quality inspect flag only for template material
			if (templateMaterial.getQualityInspectFlag() == StandardSwitchProxy.SWITCH_ON) {
				continue;
			}
			if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItem.getNextDocMatItemUUID())){
				continue;
			}
			InboundItem inboundItem = genInboundItemCore(
					refMaterialSKU, prodOrderTargetMatItem,
					inboundDelivery, refInboundItem, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			inboundItemList.add(inboundItem);			
		}
		/*
		 * Update product target material item list into DB
		 */
		productionOrderManager.updateSENodeList(prodOrderTargetMatItemList,
				logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		/*
		 * [Step4] Update in-bound delivery into Persistence as well as in-bound
		 * determination
		 */
		if(newModelFlag){
			InboundDeliveryServiceModel inboundDeliveryServiceModel = inboundDeliverySpecifier.quickCreateServiceModel(inboundDelivery,
							inboundItemList);
			inboundDeliveryManager.updateServiceModuleWithPost(
					InboundDeliveryServiceModel.class, inboundDeliveryServiceModel,
					logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		}else{
			inboundDeliveryManager.updateSENode(inboundDelivery,
					logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
			inboundDeliveryManager.updateSENodeList(
					inboundItemList,  logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
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
	 * @param productionOrder
	 * @param rawProdOrderTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private List<ServiceEntityNode> getExistInboundItemListForCreationBatch(
			ProductionOrder productionOrder,
			List<ServiceEntityNode> rawProdOrderTargetMatItemList)
			throws ServiceEntityConfigureException {
		List<String> refInboundItemUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper
				.checkNullList(rawProdOrderTargetMatItemList)) {
			// In case raw all production order material item list is provided
			// as input paras.
			for (ServiceEntityNode seNode : rawProdOrderTargetMatItemList) {
				ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
				if (!ServiceEntityStringHelper
						.checkNullString(prodOrderTargetMatItem
								.getNextDocMatItemUUID())
						&& prodOrderTargetMatItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
					refInboundItemUUIDList.add(prodOrderTargetMatItem
							.getNextDocMatItemUUID());
				}
			}
		} else {
			// In case raw production order material item list not provided.
			rawProdOrderTargetMatItemList = productionOrderManager
					.getEntityNodeListByKey(productionOrder.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdOrderTargetMatItem.NODENAME,
							productionOrder.getClient(), null);
			if (!ServiceCollectionsHelper
					.checkNullList(rawProdOrderTargetMatItemList)) {
				// In case raw all production order material item list is
				// provided
				// as input paras.
				for (ServiceEntityNode seNode : rawProdOrderTargetMatItemList) {
					ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
					if (!ServiceEntityStringHelper
							.checkNullString(prodOrderTargetMatItem
									.getNextDocMatItemUUID())) {
						refInboundItemUUIDList.add(prodOrderTargetMatItem
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
							productionOrder.getClient(), null);

		}
		return inboundItemList;
	}

	/**
	 * Core Logic to generate in-bound item for each
	 * prodOrderTargetMatItem instance
	 * 
	 * @param prodOrderTargetMatItem
	 * @throws ServiceEntityConfigureException
	 */
	private InboundItem genInboundItemCore(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProdOrderTargetMatItem prodOrderTargetMatItem,
			InboundDelivery inboundDelivery, InboundItem refInboundItem, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
		// Initial converting the production target item information to in-bound.
		InboundItem inboundItem = (InboundItem) inboundDeliveryManager
				.newEntityNode(inboundDelivery, InboundItem.NODENAME);
		initConvertProdTargetItemToQualityInspectItem(prodOrderTargetMatItem,
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
	 * @param productionOrder
	 * @param rawProdOrderTargetMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getExistInboundDeliveryForCreationBatch(
			ProductionOrder productionOrder,
			List<ServiceEntityNode> rawProdOrderTargetMatItemList)
			throws ServiceEntityConfigureException {

		List<ServiceEntityNode> inboundItemList = getExistInboundItemListForCreationBatch(
				productionOrder, rawProdOrderTargetMatItemList);
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
							productionOrder.getClient(), null);
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

	public void initConverProductionOrderToInboundDelivery(
			ProductionOrder productionOrder, InboundDelivery inboundDelivery) {
		if (inboundDelivery != null && productionOrder != null) {
			// inboundDelivery.setDeliveryCategory(deliveryCategory);
			// inboundDelivery.setDocumentCategoryType(documentCategoryType);
			inboundDelivery
					.setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
			inboundDelivery.setPrevProfDocUUID(productionOrder.getUuid());
			inboundDelivery.setProductionBatchNumber(productionOrder.getProductionBatchNumber());

		}
	}

	/**
	 * Logic to initial convert and copy information from prod target material item
	 * to inbound list item.
	 * 
	 * @param prodOrderTargetMatItem
	 * @param inboundItem
	 * @param refInboundItem
	 */
	public void initConvertProdTargetItemToQualityInspectItem(
			ProdOrderTargetMatItem prodOrderTargetMatItem,
			MaterialStockKeepUnit materialStockKeepUnit,
			InboundItem inboundItem,
			InboundItem refInboundItem, SerialLogonInfo serialLogonInfo) throws DocActionException {
		if (inboundItem != null
				&& prodOrderTargetMatItem != null) {
			prodOrderTargetMatItem.setRefMaterialSKUUUID(prodOrderTargetMatItem.getRefMaterialSKUUUID());
			productionInboundDeliveryManager.copyPrevDocMatItemToInboundItem(
					prodOrderTargetMatItem,
					IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
					inboundItem, materialStockKeepUnit, serialLogonInfo);
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
