package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreCheckProxy;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemException;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


/**
 * Implementation class for <code>WarehouseStoreItemManager</code>
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class OutboundDeliveryWarehouseItemManager extends
		WarehouseStoreItemManager {

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected OutboundDeliveryActionExecutionProxy outboundDeliveryActionExecutionProxy;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected DocumentSpecifierFactory documentSpecifierFactory;

	@Autowired
	protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(OutboundDeliveryWarehouseItemManager.class);

	public StorageCoreUnit getSumAvailableSKUStorage(String refMaterialTemplateUUID,
													 List<ServiceEntityNode> rawWarehouseStoreItemList,
			List<String> warehouseUUIDList, String reservedMatItemUUID,
													 String homeOutboundItemUUID, boolean reservedDocConsidered,
													 String client)
			throws ServiceEntityConfigureException, MaterialException {
		if (ServiceCollectionsHelper.checkNullList(warehouseUUIDList)) {
			return null;
		}
		/*
		 * [Step1] Filter
		 */
		List<ServiceEntityNode> rawStoreItemList = null;
		if(ServiceCollectionsHelper.checkNullList(rawWarehouseStoreItemList)){
			// retrieve raw warehouse store list
			rawStoreItemList = warehouseStoreManager
					.getStoreItemList(refMaterialTemplateUUID, null, warehouseUUIDList, true);
		}else{
			// filter online
			rawStoreItemList = ServiceCollectionsHelper.filterListOnline(rawWarehouseStoreItemList,
					serviceEntityNode -> {
						WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) serviceEntityNode;
						if(!refMaterialTemplateUUID.equals(warehouseStoreItem.getRefMaterialTemplateUUID())){
							return false;
						}
						String resultUUID = ServiceCollectionsHelper.filterOnline(warehouseUUIDList, uuid->{
							return warehouseStoreItem.getRootNodeUUID().equals(uuid);
						});
						return resultUUID != null;
					}, false);
		}
		// In case no store item at all
		if (ServiceCollectionsHelper.checkNullList(rawStoreItemList)) {
			return new StorageCoreUnit(refMaterialTemplateUUID,
					materialStockKeepUnitManager.getMainUnitUUID(refMaterialTemplateUUID), 0);
		}
		StorageCoreUnit storageCoreUnit = getSumAvailableSKUStorage(refMaterialTemplateUUID, rawStoreItemList,
				warehouseUUIDList.get(0), reservedMatItemUUID, homeOutboundItemUUID, reservedDocConsidered, client);
		for (int i = 1; i < warehouseUUIDList.size(); i++) {
			StorageCoreUnit tempStorageCoreUnit = getSumAvailableSKUStorage(refMaterialTemplateUUID,rawStoreItemList,
					warehouseUUIDList.get(i), reservedMatItemUUID, homeOutboundItemUUID, reservedDocConsidered, client);
			storageCoreUnit = materialStockKeepUnitManager
					.mergeStorageUnitCore(storageCoreUnit, tempStorageCoreUnit,
							StorageCoreUnit.OPERATOR_ADD, client);
		}
		return storageCoreUnit;
	}

	public StorageCoreUnit getSumAvailableSKUStorage(String refMaterialTemplateUUID,
													 List<ServiceEntityNode> rawWarehouseStoreItemList,
			String warehouseUUID, String reservedMatItemUUID, String homeOutboundItemUUID,
													 boolean reservedDocConsidered, String client)
			throws ServiceEntityConfigureException, MaterialException {
		StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
		storageCoreUnit.setRefMaterialSKUUUID(refMaterialTemplateUUID);
		List<ServiceEntityNode> storageItemList = new ArrayList<>();
		if(ServiceCollectionsHelper.checkNullList(rawWarehouseStoreItemList)){
			storageItemList = warehouseStoreManager
					.getStoreItemList(refMaterialTemplateUUID, null, ServiceCollectionsHelper.asList(warehouseUUID), true);
		}else{
			storageItemList = ServiceCollectionsHelper.filterListOnline(rawWarehouseStoreItemList,
					serviceEntityNode -> {
						WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) serviceEntityNode;
						if(!refMaterialTemplateUUID.equals(warehouseStoreItem.getRefMaterialTemplateUUID())){
							return false;
						}
						return warehouseUUID.equals(warehouseStoreItem.getRootNodeUUID());
					}, false);
		}
		if (!ServiceCollectionsHelper.checkNullList(storageItemList)) {
			storageCoreUnit = getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
					(WarehouseStoreItem) storageItemList.get(0), homeOutboundItemUUID, true, reservedMatItemUUID));
			for (int i = 1; i < storageItemList.size(); i++) {
				StorageCoreUnit tempStorageCoreUnit = getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
						(WarehouseStoreItem) storageItemList.get(i), homeOutboundItemUUID, false));
				storageCoreUnit = materialStockKeepUnitManager
						.mergeStorageUnitCore(storageCoreUnit,
								tempStorageCoreUnit,
								StorageCoreUnit.OPERATOR_ADD, client);
			}
		}
		return storageCoreUnit;
	}

	/**
	 * API to get Available store item lists according to reserved document information.
	 *
	 * @param reservedUUID: reserved document item uuid
	 * @param reservedDocType: reserved document type
	 * @param logonInfo: session logonInfo
	 */
	public List<WarehouseStoreItemUIModel> getAvailableStoreItemList(String reservedUUID, int reservedDocType,
																	 LogonInfo logonInfo)
			throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException {
		DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(reservedDocType);
		DocMatItemNode reservedDocMatItem =
				(DocMatItemNode) documentContentSpecifier.getDocMatItem(reservedUUID, logonInfo.getClient());
		List<ServiceEntityNode> rawWarehouseStoreItemList =
				outboundDeliveryActionExecutionProxy.getRawSourceItemListFromReserved(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
						reservedDocMatItem,
						LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
		return warehouseStoreItemManager.getStoreModuleListCore(rawWarehouseStoreItemList, logonInfo,
				WarehouseStoreItemSearchModel.BATCH_MODE_DISPLAY, reservedUUID, reservedDocType);
	}

	public int checkStoreStatusService(DocMatItemNode docMatItemNode, List<ServiceEntityNode> rawWarehouseStoreItemList,
											 int refMaterialCategory,
											 List<String> warehouseUUIDList, String homeOutItemUUID,
											 boolean reservedDocConsidered, boolean convertTemplateNeed,
											 String client) throws SearchConfigureException, MaterialException, ServiceEntityConfigureException {
		StorageCoreUnit requestUnit = new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(),
				docMatItemNode.getRefUnitUUID(), docMatItemNode.getAmount());
		if(convertTemplateNeed){
			// Convert to refMaterialSKU
			MaterialStockKeepUnit templateSKU =
					materialStockKeepUnitManager.getRefTemplateMaterialSKU(docMatItemNode.getRefMaterialSKUUUID(),
							docMatItemNode.getClient());
			requestUnit.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(templateSKU));
			requestUnit.setRefMaterialSKUUUID(templateSKU.getUuid());
		}
		return checkStoreStatusService(requestUnit, rawWarehouseStoreItemList, refMaterialCategory, warehouseUUIDList,
				docMatItemNode.getUuid(), homeOutItemUUID, reservedDocConsidered, client);
	}

	/**
	 * Check if the request could be meet by current free storage
	 * @param requestUnit: Material SKU Request
	 * @param rawWarehouseStoreItemList all possible store item List
	 * @return check status see <link>WarehouseStoreCheckProxy</link>
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public int checkStoreStatusService(StorageCoreUnit requestUnit,
									   List<ServiceEntityNode> rawWarehouseStoreItemList, int refMaterialCategory,
									   List<String> warehouseUUIDList, String reservedMatItemUUID,
									   String homeOutItemUUID,
									   boolean reservedDocConsidered,
									   String client) throws MaterialException {
		if (ServiceCollectionsHelper.checkNullList(warehouseUUIDList)) {
			try {
				warehouseUUIDList = warehouseManager.getWarehouseUUIDList(refMaterialCategory, client);
			} catch (ServiceEntityConfigureException | NoSuchFieldException e) {
				throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_SYSTEM_WRONG, e.getMessage());
			}
		}
		try {
			StorageCoreUnit storageCoreUnit = getSumAvailableSKUStorage(requestUnit.getRefMaterialSKUUUID(),
					rawWarehouseStoreItemList, warehouseUUIDList, reservedMatItemUUID, homeOutItemUUID,
					reservedDocConsidered,client
					);
			// Compare storage and request from contract
			if (storageCoreUnit.getAmount() <= 0) {
				// In case empty store
				return WarehouseStoreCheckProxy.STATUS_EMPTY;
			}
			StorageCoreUnit resultUnit = materialStockKeepUnitManager
					.mergeStorageUnitCore(storageCoreUnit, requestUnit,
							StorageCoreUnit.OPERATOR_MINUS, client);
			if (resultUnit.getAmount() >= 0) {
				return WarehouseStoreCheckProxy.STATUS_SUFFICIENT;
			}else{
				return WarehouseStoreCheckProxy.STATUS_INSUFFICIENT;
			}
		} catch (ServiceEntityConfigureException e) {
			throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_SYSTEM_WRONG, e.getMessage());
		}
	}

	public static class OutboundReqToStoreResult{

		private WarehouseStoreItem warehouseStoreItem;

		/* gross Available amount for the store item */
		private StorageCoreUnit availableStoreUnit;

		/* left amount for the request not match */
		private StorageCoreUnit leftStoreUnit;

		public OutboundReqToStoreResult() {
		}

		public OutboundReqToStoreResult(WarehouseStoreItem warehouseStoreItem, StorageCoreUnit availableStoreUnit,
										StorageCoreUnit leftStoreUnit) {
			this.warehouseStoreItem = warehouseStoreItem;
			this.availableStoreUnit = availableStoreUnit;
			this.leftStoreUnit = leftStoreUnit;
		}

		public WarehouseStoreItem getWarehouseStoreItem() {
			return warehouseStoreItem;
		}

		public void setWarehouseStoreItem(WarehouseStoreItem warehouseStoreItem) {
			this.warehouseStoreItem = warehouseStoreItem;
		}

		public StorageCoreUnit getAvailableStoreUnit() {
			return availableStoreUnit;
		}

		public void setAvailableStoreUnit(StorageCoreUnit availableStoreUnit) {
			this.availableStoreUnit = availableStoreUnit;
		}

		public StorageCoreUnit getLeftStoreUnit() {
			return leftStoreUnit;
		}

		public void setLeftStoreUnit(StorageCoreUnit leftStoreUnit) {
			this.leftStoreUnit = leftStoreUnit;
		}
	}

	/**
	 * For one specific warehouse store item, calculate if the request amount of material could be meet
	 * and return result in the format of <code>OutboundReqToStoreResult</code>
	 *
	 * @param reservedDocUUID: request document item UUID, for checking if this store item already been reserved by other document
	 * @param warehouseStoreItem: the specific warehouse store item instance
	 * @param requestCoreUnit: request material amount
	 * @return result in the format of OutboundReqToStoreResult
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public OutboundReqToStoreResult genRequestToStoreItem(String reservedDocUUID,
															  WarehouseStoreItem warehouseStoreItem, StorageCoreUnit requestCoreUnit)
			throws MaterialException, ServiceEntityConfigureException {
		// For one specific warehouse store item, calculate the available store amount result
		WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreItem.clone();
		StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItemBack, null, true);
		storeAvailableStoreItemRequest.setReservedDocUUID(reservedDocUUID);
		StorageCoreUnit availableStoreCoreUnit = getAvailableStoreItemAmountUnion(
				storeAvailableStoreItemRequest);
		// In case this store item already been fully occupied with other documents, just skip
		if (availableStoreCoreUnit.getAmount() <= 0) {
			return null;
		}
		// Calculate if the request amount and current available amount from the current
		requestCoreUnit = checkWarehouseStoreItemAvailableCore(requestCoreUnit,
				storeAvailableStoreItemRequest.getWarehouseStoreItem(), availableStoreCoreUnit);
		if (requestCoreUnit.getAmount() == 0) {
			/*
			 * In case current warehouseStore meet requirement,
			 */
			// here request Core unit should be ZERO, and break traverse
            return new OutboundReqToStoreResult(warehouseStoreItem,
					requestCoreUnit, null);
		}else{
			// In case not meet requirement, continue to next
            return new OutboundReqToStoreResult(warehouseStoreItem,
					availableStoreCoreUnit, requestCoreUnit);
		}
	}


	/**
	 * Wrapper for checkout warehouse store item special for outbound delivery & purchase contact
	 * @param storeItemUUID
	 * @param amount
	 * @param refUnitUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public StorageCoreUnit checkWarehouseStoreItemAvailableWrapper(String homeOutUUID, String storeItemUUID,
																   double amount,
																 String refUnitUUID)
			throws ServiceEntityConfigureException, MaterialException {
		WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) warehouseStoreManager
				.getEntityNodeByKey(storeItemUUID,
						IServiceEntityNodeFieldConstant.UUID,
						WarehouseStoreItem.NODENAME, null);
		if (warehouseStoreItem == null) {
			throw new WarehouseStoreItemException(WarehouseStoreItemException.TYPE_SYSTEM_WRONG);
		}
		return checkWarehouseStoreItemAvailableWrapper(homeOutUUID, warehouseStoreItem, amount, refUnitUUID);
	}

	/**
	 * Check if the warehouse store item could meet the request amount from special outbound item (specified by homeOutUUID).
	 * * This method will check if the warehouse store item has been reserved by other document, and return the request amount object
	 * @param homeOutUUID
	 * @param warehouseStoreItem
	 * @param amount
	 * @param refUnitUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public StorageCoreUnit checkWarehouseStoreItemAvailableWrapper(String homeOutUUID, WarehouseStoreItem warehouseStoreItem,
																   double amount,
																   String refUnitUUID)
			throws ServiceEntityConfigureException, MaterialException {
		WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreItem.clone();
		StorageCoreUnit requestUpdateCoreUnit = new StorageCoreUnit();
		requestUpdateCoreUnit.setRefMaterialSKUUUID(warehouseStoreItemBack
				.getRefMaterialSKUUUID());
		requestUpdateCoreUnit.setRefUnitUUID(refUnitUUID);
		requestUpdateCoreUnit.setAmount(amount);
		StoreAvailableStoreItemRequest storeAvailableStoreItemRequest =
				new StoreAvailableStoreItemRequest(warehouseStoreItemBack, homeOutUUID, false, null);
		StorageCoreUnit availableStoreCoreUnit = getAvailableStoreItemAmountUnion(
				storeAvailableStoreItemRequest);
		return checkWarehouseStoreItemAvailableCore(requestUpdateCoreUnit,
				storeAvailableStoreItemRequest.getWarehouseStoreItem(), availableStoreCoreUnit);
	}


	/**
	 * Core Method to calculate the available warehouse storage item amount by
	 * deduce the pending out-bound delivery
	 * 
	 *
	 * @param storeAvailableStoreItemRequest
	 *            : Home out-bound delivery item UUID or transfer item UUID, in
	 *            case to exclude current out-bound delivery or inventory
	 *            transfer order,
	 *
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public StorageCoreUnit getAvailableStoreItemAmountUnion(
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest)
			throws ServiceEntityConfigureException, MaterialException {
		/*
		 * [Step1] Check if this warehouse storeItem has reserved doc, If so,
		 * then return 0 directly
		 */
		WarehouseStoreItem warehouseStoreItem = storeAvailableStoreItemRequest
				.getWarehouseStoreItem();
		String homeOutItemUUID = storeAvailableStoreItemRequest
				.getHomeOutItemUUID();
		if (storeAvailableStoreItemRequest.getReservedDocConsidered() && !ServiceEntityStringHelper.checkNullString(warehouseStoreItem
				.getReservedMatItemUUID())) {
			if (warehouseStoreItem.getReservedMatItemUUID()
							.equals(storeAvailableStoreItemRequest
									.getReservedDocUUID())) {
				// In case reserved doc UUID matches
				return new StorageCoreUnit(
						warehouseStoreItem.getRefMaterialSKUUUID(),
						warehouseStoreItem.getRefUnitUUID(), warehouseStoreItem.getAmount());
			}else{
				// In case reserved doc UUID doesn't match
				return new StorageCoreUnit(
						warehouseStoreItem.getRefMaterialSKUUUID(),
						warehouseStoreItem.getRefUnitUUID(), 0);
			}
		}

		/*
		 * [Step2] Get pending out-bound delivery item list
		 */
		List<OutboundItem> otherItemReferenceList = outboundDeliveryManager
				.getOtherPendingOutboundItem(warehouseStoreItem,
						homeOutItemUUID);
		StorageCoreUnit storageCoreUnit1 = convertWarehouseStoreToStorageUnit(warehouseStoreItem);
		boolean hitOutboundFlag = false;
		if (otherItemReferenceList != null && otherItemReferenceList.size() > 0) {
			for (OutboundItem outboundItem : otherItemReferenceList) {
				hitOutboundFlag = true;
				StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
				storageCoreUnit2.setRefMaterialSKUUUID(warehouseStoreItem
						.getRefMaterialTemplateUUID());
				storageCoreUnit2.setAmount(outboundItem.getAmount());
				storageCoreUnit2.setRefUnitUUID(outboundItem
						.getRefUnitUUID());
				storageCoreUnit1 = materialStockKeepUnitManager
						.mergeStorageUnitCore(storageCoreUnit1,
								storageCoreUnit2,
								StorageCoreUnit.OPERATOR_MINUS,
								warehouseStoreItem.getClient());
			}
		}
		if (hitOutboundFlag) {
			// In case hit as pending out-bound list, just return
			return storageCoreUnit1;
		}
		/*
		 * [Step3] Get pending transfer item list
		 */
		List<InventoryTransferItem> pendingTransferItemList = inventoryTransferOrderManager
				.getOtherPendingTransferItem(warehouseStoreItem,
						homeOutItemUUID);
		if (pendingTransferItemList != null
				&& pendingTransferItemList.size() > 0) {
			for (InventoryTransferItem transferItemReference : pendingTransferItemList) {
				StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
				storageCoreUnit2.setRefMaterialSKUUUID(warehouseStoreItem
						.getRefMaterialTemplateUUID());
				storageCoreUnit2.setAmount(transferItemReference.getAmount());
				storageCoreUnit2.setRefUnitUUID(transferItemReference
						.getRefUnitUUID());
				storageCoreUnit1 = materialStockKeepUnitManager
						.mergeStorageUnitCore(storageCoreUnit1,
								storageCoreUnit2,
								StorageCoreUnit.OPERATOR_MINUS,
								warehouseStoreItem.getClient());
			}
		}
		return storageCoreUnit1;
	}

	public static class WarehouseStoreItemWithReference{

		private WarehouseStoreItem warehouseStoreItem;

		private MaterialStockKeepUnit refTemplateSKU;

		private RegisteredProduct registeredProduct;

		private List<ServiceEntityNode> refPendingOutboundItemList;

		private List<ServiceEntityNode> refPendingTransferItemList;

		public WarehouseStoreItemWithReference() {
		}

		public WarehouseStoreItemWithReference(WarehouseStoreItem warehouseStoreItem,
											   List<ServiceEntityNode> refPendingOutboundItemList,
											   List<ServiceEntityNode> refPendingTransferItemList) {
			this.warehouseStoreItem = warehouseStoreItem;
			this.refPendingOutboundItemList = refPendingOutboundItemList;
			this.refPendingTransferItemList = refPendingTransferItemList;
		}

		public WarehouseStoreItem getWarehouseStoreItem() {
			return warehouseStoreItem;
		}

		public void setWarehouseStoreItem(WarehouseStoreItem warehouseStoreItem) {
			this.warehouseStoreItem = warehouseStoreItem;
		}

		public List<ServiceEntityNode> getRefPendingOutboundItemList() {
			return refPendingOutboundItemList;
		}

		public void setRefPendingOutboundItemList(List<ServiceEntityNode> refPendingOutboundItemList) {
			this.refPendingOutboundItemList = refPendingOutboundItemList;
		}

		public List<ServiceEntityNode> getRefPendingTransferItemList() {
			return refPendingTransferItemList;
		}

		public void setRefPendingTransferItemList(List<ServiceEntityNode> refPendingTransferItemList) {
			this.refPendingTransferItemList = refPendingTransferItemList;
		}

		public MaterialStockKeepUnit getRefTemplateSKU() {
			return refTemplateSKU;
		}

		public void setRefTemplateSKU(MaterialStockKeepUnit refTemplateSKU) {
			this.refTemplateSKU = refTemplateSKU;
		}

		public RegisteredProduct getRegisteredProduct() {
			return registeredProduct;
		}

		public void setRegisteredProduct(RegisteredProduct registeredProduct) {
			this.registeredProduct = registeredProduct;
		}
	}

	/**
	 * Category and package warehouse with pending other reference
	 * @param rawStoreItemList
	 */
	public List<WarehouseStoreItemWithReference> batchCategoryStoreItemWithReference(List<WarehouseStoreItem> rawStoreItemList) throws ServiceEntityConfigureException {
		if(ServiceCollectionsHelper.checkNullList(rawStoreItemList)){
			return null;
		}
		List<String> storeItemUUIDList = null;
		try {
			storeItemUUIDList = ServiceCollectionsHelper.pluckList(rawStoreItemList,
					IServiceEntityNodeFieldConstant.UUID);
		} catch (NoSuchFieldException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
		}
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setMultipleValueList(storeItemUUIDList);
		key1.setKeyName(DeliveryItem.FILED_REFSTOREITEMUUID);
		List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(key1);
		String client = rawStoreItemList.get(0).getClient();
		List<ServiceEntityNode> pendingOutboundItemList = outboundDeliveryManager.getEntityNodeListByKeyList(keyList, OutboundItem.NODENAME, client, null);
        /*
		 * [Step2] Process to traverse store item and category
		 */
		List<WarehouseStoreItemWithReference> resultList = new ArrayList<>();
		List<String> refMaterialSKUUUIDList = rawStoreItemList.stream().map(WarehouseStoreItem::getRefMaterialSKUUUID).collect(Collectors.toList());
		List<ServiceEntityNode> refMaterialSKUList =
				materialStockKeepUnitManager.getEntityNodeListByMultipleKey(refMaterialSKUUUIDList,
						IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME, client, null);
		for(WarehouseStoreItem warehouseStoreItem: rawStoreItemList){
			List<ServiceEntityNode> tmpPendingOutboundItemList = new ArrayList<>();
			if(!ServiceCollectionsHelper.checkNullList(pendingOutboundItemList)){
				tmpPendingOutboundItemList = ServiceCollectionsHelper.filterListOnline(pendingOutboundItemList,
						serviceEntityNode -> {
							OutboundItem outboundItem = (OutboundItem) serviceEntityNode;
							return warehouseStoreItem.getUuid().equals(outboundItem.getRefStoreItemUUID());
						}, false);
			}
			WarehouseStoreItemWithReference warehouseStoreItemWithReference = new WarehouseStoreItemWithReference();
			warehouseStoreItemWithReference.setWarehouseStoreItem(warehouseStoreItem);
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) ServiceCollectionsHelper.filterOnline(refMaterialSKUList,
					seNode->{return warehouseStoreItem.getRefMaterialSKUUUID().equals(seNode.getUuid());});
			if(materialStockKeepUnit != null){
				if(RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)){
					warehouseStoreItemWithReference.setRegisteredProduct((RegisteredProduct) materialStockKeepUnit);
				}
			}
			if(!ServiceCollectionsHelper.checkNullList(tmpPendingOutboundItemList)){
				warehouseStoreItemWithReference.setRefPendingOutboundItemList(tmpPendingOutboundItemList);
			}
			resultList.add(warehouseStoreItemWithReference);
		}
		return resultList;
	}

}
