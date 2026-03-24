package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemUIModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class ProdPickingRefMaterialItemManager {


	public static final String METHOD_ConvProdPickingRefMaterialItemToUI = "convProdPickingRefMaterialItemToUI";

	public static final String METHOD_ConvUIToProdPickingRefMaterialItem = "convUIToProdPickingRefMaterialItem";

	public static final String METHOD_ConvPickingOrderToItemUI = "convPickingOrderToItemUI";

	public static final String METHOD_ConvProductionOrderToItemUI = "convProductionOrderToItemUI";

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingRefOrderltemManager prodPickingRefOrderltemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ProductionOutboundDeliveryManager productionOutboundDeliveryManager;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	protected Logger logger = LoggerFactory.getLogger(ProdPickingRefMaterialItemManager.class);

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<PageHeaderModel> getPageHeaderModelList(ProdPickingRefMaterialItem prodPickingRefMaterialItem, String client)
			throws ServiceEntityConfigureException {
		ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
				.getEntityNodeByKey(prodPickingRefMaterialItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProdPickingRefOrderItem.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
		if (prodPickingRefOrderItem != null) {
			List<PageHeaderModel> pageHeaderModelList = prodPickingRefOrderltemManager
					.getPageHeaderModelList(prodPickingRefOrderItem, client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
				index = pageHeaderModelList.size();
			}
			PageHeaderModel itemHeaderModel = getPageHeaderModel(prodPickingRefMaterialItem, index);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(ProdPickingRefMaterialItem prodPickingRefMaterialItem, int index)
			throws ServiceEntityConfigureException {
		if (prodPickingRefMaterialItem == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("prodPickingRefMaterialItemPageTitle");
		pageHeaderModel.setNodeInstId(ProdPickingRefMaterialItem.NODENAME);
		pageHeaderModel.setUuid(prodPickingRefMaterialItem.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(prodPickingRefMaterialItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, prodPickingRefMaterialItem.getClient(), null);
		if (materialStockKeepUnit != null) {
			pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	/**
	 * Core Logic to wrapper newEntityNode and return standard type picking material item
	 *
	 * @param prodPickingRefOrderItem
	 * @param itemProcessType
	 * @return
	 */
	public ProdPickingRefMaterialItem newPickingRefMaterialItem(ProdPickingRefOrderItem prodPickingRefOrderItem,
			int itemProcessType) throws ServiceEntityConfigureException {
		ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
				.newEntityNode(prodPickingRefOrderItem, ProdPickingRefMaterialItem.NODENAME);
		if (itemProcessType == ProdPickingOrder.PROCESSTYPE_INPLAN || itemProcessType == ProdPickingOrder.PROCESSTYPE_REPLENISH) {
			prodPickingRefMaterialItem.setHomeDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
			prodPickingRefMaterialItem.setItemProcessType(itemProcessType);
		}
		if (itemProcessType == ProdPickingOrder.PROCESSTYPE_RETURN) {
			prodPickingRefMaterialItem.setHomeDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER);
			prodPickingRefMaterialItem.setItemProcessType(itemProcessType);
		}
		return prodPickingRefMaterialItem;
	}



	/**
	 * Logic to get free storage according to the reference Material SKU
	 *
	 * @param refMaterialSKUUUID
	 * @param client
	 * @param warehouseUUIDList
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getFreeStoreAmount(String refMaterialSKUUUID, String client, List<String> warehouseUUIDList)
			throws MaterialException, ServiceEntityConfigureException {
		List<ServiceEntityNode> rawStoreItemList = warehouseStoreManager
				.getStoreItemList(refMaterialSKUUUID, null, warehouseUUIDList, true);
		if (ServiceCollectionsHelper.checkNullList(rawStoreItemList)) {
			return null;
		}
		List<StorageCoreUnit> storageCoreUnitList = new ArrayList<StorageCoreUnit>();
		for (ServiceEntityNode seNode : rawStoreItemList) {
			WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
			// Have to check if material in valid status
			if (!DocFlowProxy.checkAccountableMaterialStatus(warehouseStoreItem.getMaterialStatus())) {
				continue;
			}
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem,
					null, true);
			StorageCoreUnit availableStorageUnit = productionOutboundDeliveryManager
					.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
			storageCoreUnitList.add(availableStorageUnit);
		}
		return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList, client);
	}

	/**
	 * Logic to set direction for storage core unit, in order for merging together
	 *
	 * @param storageCoreUnit
	 * @param prodPickingRefMaterialItem
	 * @return
	 */
	public static StorageCoreUnit setDirectionStorageByProcessType(StorageCoreUnit storageCoreUnit,
			ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
		// Case1: return type: set amount to minus
		if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
			if (storageCoreUnit != null && storageCoreUnit.getAmount() > 0) {
				storageCoreUnit.setAmount(0 - storageCoreUnit.getAmount());
			}
		}
		return storageCoreUnit;
	}

	/**
	 * Core Logic to calculate in process amount
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getInProcessAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		List<ServiceEntityNode> inProcessDocMatItemList = getInProcessDocMatItemList(prodPickingRefMaterialItem);
		if (ServiceCollectionsHelper.checkNullList(inProcessDocMatItemList)) {
			return null;
		}
		List<StorageCoreUnit> storageCoreUnitList = new ArrayList<>();
		for (ServiceEntityNode seNode : inProcessDocMatItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			// [Step2] Have to check if material in valid status
			if (!DocFlowProxy.checkAccountableMaterialStatus(docMatItemNode.getMaterialStatus())) {
				continue;
			}
			// [Step3] Have to exclude if already has pending outbound or warehouse store
			if (docMatItemNode.getNextDocType() > 0) {
				if (docMatItemNode.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
					continue;
				}
				if (docMatItemNode.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM) {
					continue;
				}
			}
			StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(),
					docMatItemNode.getRefUnitUUID(), docMatItemNode.getAmount());
			requestStorageCoreUnit = setDirectionStorageByProcessType(requestStorageCoreUnit, prodPickingRefMaterialItem);
			storageCoreUnitList.add(requestStorageCoreUnit);
		}
		return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList, prodPickingRefMaterialItem.getClient());
	}

	/**
	 * Constants to decide document type factor when need to decide End document
	 *
	 * @param documentType
	 * @return
	 */
	public static int getEndDocTypeWeightFactor(int documentType) {
		if (documentType == 0) {
			return 0;
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM) {
			return 2;
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
			return 3;
		}
		return 0;
	}

	public class EndDocMatItemPair {

		DocMatItemNode targetDocMatItemNode;

		int docWeightFactor;

		public EndDocMatItemPair() {

		}

		public EndDocMatItemPair(final DocMatItemNode targetDocMatItemNode, final int docWeightFactor) {
			this.targetDocMatItemNode = targetDocMatItemNode;
			this.docWeightFactor = docWeightFactor;
		}

		public DocMatItemNode getTargetDocMatItemNode() {
			return targetDocMatItemNode;
		}

		public void setTargetDocMatItemNode(final DocMatItemNode targetDocMatItemNode) {
			this.targetDocMatItemNode = targetDocMatItemNode;
		}

		public int getDocWeightFactor() {
			return docWeightFactor;
		}

		public void setDocWeightFactor(final int docWeightFactor) {
			this.docWeightFactor = docWeightFactor;
		}
	}

	/**
	 * Logic to get the target end document from list
	 *
	 * @param docMatItemList
	 * @return
	 */
	public DocMatItemNode calculateTargetEndDocListByWeight(List<ServiceEntityNode> docMatItemList, boolean checkMaterialStatus) {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return null;
		}
		EndDocMatItemPair targetDocMatItemPair = null;
		for (ServiceEntityNode seNode : docMatItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			if (checkMaterialStatus && !docFlowProxy.checkAccountableMaterialStatus(docMatItemNode.getMaterialStatus())) {
				continue;
			}
			int homeDocumentType = docMatItemNode.getHomeDocumentType();
			if (homeDocumentType == 0) {
				homeDocumentType = serviceDocumentComProxy.getDocumentTypeByModelName(docMatItemNode.getNodeName());
			}
			int curDocWeightFactor = getEndDocTypeWeightFactor(homeDocumentType);
			if (targetDocMatItemPair == null || targetDocMatItemPair.getDocWeightFactor() < curDocWeightFactor) {
				targetDocMatItemPair = new EndDocMatItemPair(docMatItemNode, curDocWeightFactor);
			}
		}
		return targetDocMatItemPair.getTargetDocMatItemNode();
	}

	/**
	 * Core Logic to calculate in process amount
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getInProcessDocMatItemList(ProdPickingRefMaterialItem prodPickingRefMaterialItem) throws DocActionException {
		/*
		 * [Step1] Should filter out store item, out-bound
		 */
		if (IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM == prodPickingRefMaterialItem.getDocumentType()) {
			return null;
		}
		if (IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY == prodPickingRefMaterialItem.getDocumentType()) {
			return null;
		}
		if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
			return null;
		}
		List<ServiceEntityNode> inProcessDocMatItemList = getCurInprocessDocByPickingMatItem(prodPickingRefMaterialItem);
		if (ServiceCollectionsHelper.checkNullList(inProcessDocMatItemList)) {
			// Special process for none in process document
			if (prodPickingRefMaterialItem.getDocumentType() == 0) {
				// In case no next document has been assigned to this picking mat item
				inProcessDocMatItemList = new ArrayList<>();
				inProcessDocMatItemList.add(prodPickingRefMaterialItem);
			} else {
				return null;
			}
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : inProcessDocMatItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			// In case current node is already end node, and material status is
			// valid
			if (!DocFlowProxy.checkAccountableMaterialStatus(docMatItemNode.getMaterialStatus())) {
				continue;
			}
			if (docMatItemNode.getNextDocType() == 0) {
				resultList.add(docMatItemNode);
			} else {
				List<ServiceEntityNode> allNextMatItemList = docFlowProxy
						.findTargetDocMatItemListTillNext(docMatItemNode, ServiceCollectionsHelper.asList(IServiceModelConstants.WarehouseStoreItem), false,true);
				if (!ServiceCollectionsHelper.checkNullList(allNextMatItemList)) {
					resultList.addAll(allNextMatItemList);
				}
			}
		}
		return resultList;
	}

	/**
	 * [Internal method] Get Current picking material item's in process document list
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 */
	private List<ServiceEntityNode> getCurInprocessDocByPickingMatItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
		List<ServiceEntityNode> inProcessDocMatItemList = new ArrayList<>();
		if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
			/*
			 * [Case1] In case return type, SHOULD NOT use "reserved UUID", since return picking material item didn't reserve document
			 * using next doc mat item uuid to retrieved next document (should be inbound)
			 */
			if (ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItem.getNextDocMatItemUUID())){
				// process of empty next mat item
				return null;
			}
			inProcessDocMatItemList = docFlowProxy.getDefDocItemNodeList(prodPickingRefMaterialItem.getNextDocType(),
					prodPickingRefMaterialItem.getNextDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
					prodPickingRefMaterialItem.getClient());
		} else {
			/*
			 * [Case2] In case standard type, using "reserved UUID" to search in process document
			 */
			inProcessDocMatItemList = docFlowProxy
					.getDefDocItemNodeList(prodPickingRefMaterialItem.getDocumentType(), prodPickingRefMaterialItem.getUuid(),
							IServiceEntityCommonFieldConstant.RESEARVEDMATITEMUUID, prodPickingRefMaterialItem.getClient());
		}
		return inProcessDocMatItemList;
	}

	/**
	 * Core Logic to calculate in process amount
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllEndDocMatItemList(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
			boolean checkMaterialStatus) throws DocActionException {
		/*
		 * [Step1] Should filter out store item, out-bound
		 */
		List<ServiceEntityNode> inProcessDocMatItemList = getCurInprocessDocByPickingMatItem(prodPickingRefMaterialItem);
		if (ServiceCollectionsHelper.checkNullList(inProcessDocMatItemList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : inProcessDocMatItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			// In case current node is already end node, and material status is
			// valid
			if (!DocFlowProxy.checkAccountableMaterialStatus(docMatItemNode.getMaterialStatus())) {
				continue;
			}
			if (docMatItemNode.getNextDocType() == 0) {
				resultList.add(docMatItemNode);
			} else {
				List<ServiceEntityNode> allNextMatItemList = docFlowProxy.findEndDocMatItemListTillNext(docMatItemNode, true);
				if (!ServiceCollectionsHelper.checkNullList(allNextMatItemList)) {
					resultList.addAll(allNextMatItemList);
				}
			}
		}
		return resultList;
	}

	/**
	 * Get all the reserved and active stock list with [in-stock] status and not out-bound has been generated
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getReservedInStockStoreItemList(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(prodPickingRefMaterialItem.getUuid(),
				IServiceEntityCommonFieldConstant.RESEARVEDMATITEMUUID);
		keyList.add(key1);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(WarehouseStoreItem.STATUS_INSTOCK,
				IDocItemNodeFieldConstant.itemStatus);
		keyList.add(key2);
		List<ServiceEntityNode> warehouseStoreItemList = warehouseStoreManager
				.getEntityNodeListByKeyList(keyList, WarehouseStoreItem.NODENAME, prodPickingRefMaterialItem.getClient(), null);
		if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
			return null;
		}
		List<ServiceEntityNode> inStockStoreItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : warehouseStoreItemList) {
			WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
			// Have to check if material in valid status
			if (!DocFlowProxy.checkAccountableMaterialStatus(warehouseStoreItem.getMaterialStatus())) {
				continue;
			}
			// exclude if has pending out bound item already
			if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem.getNextDocMatItemUUID())
					&& warehouseStoreItem.getNextDocType() > 0) {
				continue;
			}
			inStockStoreItemList.add(warehouseStoreItem);
		}
		return inStockStoreItemList;
	}

	public List<ServiceEntityNode> getReturnToPickMaterialItemList(List<ServiceEntityNode> rawProdPickingRefMaterialItemList) throws ServiceEntityConfigureException, MaterialException {
		if(ServiceCollectionsHelper.checkNullList(rawProdPickingRefMaterialItemList)){
			return null;
		}
		return rawProdPickingRefMaterialItemList;
	}

	/**
	 * Filter list of prod picking material list could be picked
	 * @param rawProdPickingRefMaterialItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> getToPickMaterialItemList(List<ServiceEntityNode> rawProdPickingRefMaterialItemList) throws ServiceEntityConfigureException, MaterialException, DocActionException {
		if(ServiceCollectionsHelper.checkNullList(rawProdPickingRefMaterialItemList)){
			return null;
		}
		List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList =
				getPickingItemAmountListWrapper(rawProdPickingRefMaterialItemList);
		if(ServiceCollectionsHelper.checkNullList(rawProdPickingExtendAmountModelList)){
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for(ProdPickingExtendAmountModel prodPickingExtendAmountModel: rawProdPickingExtendAmountModelList){
			if(prodPickingExtendAmountModel.getToPickAmount() != null && prodPickingExtendAmountModel.getToPickAmount().getAmount() > 0){
				ProdPickingRefMaterialItem prodPickingRefMaterialItem =
						prodPickingExtendAmountModel.getProdPickingRefMaterialItem();
				prodPickingRefMaterialItem.setToPickAmount(prodPickingExtendAmountModel.getToPickAmount().getAmount());
				ServiceCollectionsHelper.mergeToList(resultList,
						prodPickingExtendAmountModel.getProdPickingRefMaterialItem());
			}
		}
		return resultList;
	}

	public void updateRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem) throws MaterialException, ServiceEntityConfigureException, DocActionException {
		ProdPickingExtendAmountModel prodPickingExtendAmountModel = getPickingItemAmountWrapper(prodPickingRefMaterialItem);
		refreshPickingRefMaterialItem(prodPickingRefMaterialItem,
				ServiceCollectionsHelper.asList(prodPickingExtendAmountModel));
	}

	/**
	 * Logic to refresh update material item
	 *
	 * @param prodPickingRefMaterialItem
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void refreshPickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
			List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
		/*
		 * [Step1] Update amount
		 */
		StorageCoreUnit suppliedStorageCoreUnit = null;
		ProdPickingExtendAmountModel prodPickingExtendAmountModel = null;
		if (!ServiceCollectionsHelper.checkNullList(rawProdPickingExtendAmountModelList)) {
			prodPickingExtendAmountModel = filterPickingExtendModelOnline(prodPickingRefMaterialItem.getUuid(),
					tempPickingMaterialItem -> {
						return tempPickingMaterialItem.getUuid();
					}, rawProdPickingExtendAmountModelList);
		}
		if (prodPickingExtendAmountModel != null) {
			suppliedStorageCoreUnit = prodPickingExtendAmountModel.getSuppliedAmount();
		} else {
			// should dreprecate this logic path
			suppliedStorageCoreUnit = getSuppliedStoreAmount(prodPickingRefMaterialItem);
		}
		if (suppliedStorageCoreUnit != null) {
			prodPickingRefMaterialItem.setAmount(suppliedStorageCoreUnit.getAmount());
			prodPickingRefMaterialItem.setRefUnitUUID(suppliedStorageCoreUnit.getRefUnitUUID());
		} else {
			prodPickingRefMaterialItem.setAmount(0);
			prodPickingRefMaterialItem.setRefUnitUUID(suppliedStorageCoreUnit.getRefUnitUUID());
		}
		if (prodPickingExtendAmountModel != null) {
			StorageCoreUnit inStockAmount = prodPickingExtendAmountModel.getInStockAmount();
			if(inStockAmount != null){
				prodPickingRefMaterialItem.setInStockAmount(inStockAmount.getAmount());
			}
			StorageCoreUnit inProcessAmount = prodPickingExtendAmountModel.getInProcessAmount();
			if(inProcessAmount != null){
				prodPickingRefMaterialItem.setInProcessAmount(inProcessAmount.getAmount());
			}
			StorageCoreUnit toPickAmount = prodPickingExtendAmountModel.getToPickAmount();
			if(toPickAmount != null){
				prodPickingRefMaterialItem.setToPickAmount(toPickAmount.getAmount());
			}
			StorageCoreUnit pickedAmount = prodPickingExtendAmountModel.getPickedAmount();
			if(pickedAmount != null){
				prodPickingRefMaterialItem.setPickedAmount(pickedAmount.getAmount());
			}
		}
	}

	/**
	 * Logic to Calculate all the provided amount by checking the next doc mat item status
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getSuppliedStoreAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		StorageCoreUnit inProcessAmount = this.getInProcessAmount(prodPickingRefMaterialItem);
		StorageCoreUnit inStoreAmount = this.getReservedStoreAmount(prodPickingRefMaterialItem);
		StorageCoreUnit outStorageUnit = this.getReservedOutboundAmount(prodPickingRefMaterialItem, true);
		StorageCoreUnit pickedAmount = this.getPickedAmount(prodPickingRefMaterialItem);
		return getSuppliedStoreAmount(prodPickingRefMaterialItem, inProcessAmount, inStoreAmount, outStorageUnit, pickedAmount);
	}

	public StorageCoreUnit getSuppliedStoreAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
			StorageCoreUnit inProcessAmount, StorageCoreUnit inStoreAmount, StorageCoreUnit toPickAmount,
			StorageCoreUnit pickedAmount) throws MaterialException, ServiceEntityConfigureException {
		setDirectionStorageByProcessType(inProcessAmount, prodPickingRefMaterialItem);
		setDirectionStorageByProcessType(inStoreAmount, prodPickingRefMaterialItem);
		setDirectionStorageByProcessType(toPickAmount, prodPickingRefMaterialItem);
		setDirectionStorageByProcessType(pickedAmount, prodPickingRefMaterialItem);
		List<StorageCoreUnit> allSuppliedStorageList = new ArrayList<>();
		if (inProcessAmount != null && inProcessAmount.getAmount() != 0) {
			allSuppliedStorageList.add(inProcessAmount);
		}
		if (inStoreAmount != null && inStoreAmount.getAmount() != 0) {
			allSuppliedStorageList.add(inStoreAmount);
		}
		if (toPickAmount != null && toPickAmount.getAmount() != 0) {
			allSuppliedStorageList.add(toPickAmount);
		}
		if (pickedAmount != null && pickedAmount.getAmount() != 0) {
			allSuppliedStorageList.add(pickedAmount);
		}
		return materialStockKeepUnitManager.mergeStorageUnitCore(allSuppliedStorageList, prodPickingRefMaterialItem.getClient());
	}

	/**
	 * Provided all the reserved store item flag
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getReservedStoreAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
			throws MaterialException, ServiceEntityConfigureException {
		List<ServiceEntityNode> inStockStoreItemList = this.getReservedInStockStoreItemList(prodPickingRefMaterialItem);
		if (ServiceCollectionsHelper.checkNullList(inStockStoreItemList)) {
			return null;
		}
		List<StorageCoreUnit> storageCoreUnitList = new ArrayList<StorageCoreUnit>();
		for (ServiceEntityNode seNode : inStockStoreItemList) {
			WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem,
					null, true, prodPickingRefMaterialItem.getUuid());
			StorageCoreUnit availableStorageUnit = productionOutboundDeliveryManager
					.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
			setDirectionStorageByProcessType(availableStorageUnit, prodPickingRefMaterialItem);
			storageCoreUnitList.add(availableStorageUnit);
		}
		return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList, prodPickingRefMaterialItem.getClient());
	}

	public List<ServiceEntityNode> getReservedStoredMatItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
			throws ServiceEntityConfigureException {
		if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
			return null;
		}
		List<ServiceEntityNode> warehouseStoreItemList = warehouseStoreManager
				.getEntityNodeListByKey(prodPickingRefMaterialItem.getUuid(), IServiceEntityCommonFieldConstant.RESEARVEDMATITEMUUID,
						WarehouseStoreItem.NODENAME, prodPickingRefMaterialItem.getClient(), null);
		return warehouseStoreItemList;
	}

	/**
	 * Core Logic to calculate reserved accountable outbound material item.
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getPickedAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
			throws MaterialException, ServiceEntityConfigureException {
		if (prodPickingRefMaterialItem.getItemStatus() != ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
			return null;
		}
		StorageCoreUnit toPickAmount = this.getReservedOutboundAmount(prodPickingRefMaterialItem, false);
//		StorageCoreUnit pickedStorageCoreUnit = new StorageCoreUnit(prodPickingRefMaterialItem.getRefMaterialSKUUUID(),
//				prodPickingRefMaterialItem.getRefUnitUUID(), prodPickingRefMaterialItem.getAmount());
		setDirectionStorageByProcessType(toPickAmount, prodPickingRefMaterialItem);
		return toPickAmount;
	}

	/**
	 * Core Logic to calculate reserved accountable outbound material item.
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit getReservedOutboundAmount(ProdPickingRefMaterialItem prodPickingRefMaterialItem, boolean notPicked)
			throws MaterialException, ServiceEntityConfigureException {
		List<ServiceEntityNode> outboundItemList = getReservedOutboundMatItem(prodPickingRefMaterialItem, notPicked);
		if (ServiceCollectionsHelper.checkNullList(outboundItemList)) {
			return null;
		}
		List<StorageCoreUnit> storageCoreUnitList = new ArrayList<StorageCoreUnit>();
		for (ServiceEntityNode seNode : outboundItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			// [Step2] Have to check if material in valid status
			if (!DocFlowProxy.checkAccountableMaterialStatus(docMatItemNode.getMaterialStatus())) {
				continue;
			}
			StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(),
					docMatItemNode.getRefUnitUUID(), docMatItemNode.getAmount());
			setDirectionStorageByProcessType(requestStorageCoreUnit, prodPickingRefMaterialItem);
			storageCoreUnitList.add(requestStorageCoreUnit);
		}
		return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList, prodPickingRefMaterialItem.getClient());
	}


	/**
	 * Logic to get all the accountable reserved outbound item list from current picking material item
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getReservedOutboundMatItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem, boolean notPicked)
			throws ServiceEntityConfigureException {
		if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED && notPicked) {
			return null;
		}
		List<ServiceEntityNode> rawOutboundItemList = outboundDeliveryManager
				.getEntityNodeListByKey(prodPickingRefMaterialItem.getUuid(), IServiceEntityCommonFieldConstant.RESEARVEDMATITEMUUID,
						OutboundItem.NODENAME, prodPickingRefMaterialItem.getClient(), null);
		if (ServiceCollectionsHelper.checkNullList(rawOutboundItemList)) {
			return null;
		}
		List<ServiceEntityNode> outboundItemList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawOutboundItemList) {
			OutboundItem outboundItem = (OutboundItem) seNode;
			if (!DocFlowProxy.checkAccountableMaterialStatus(outboundItem.getMaterialStatus())) {
				continue;
			}
			outboundItemList.add(outboundItem);
		}
		return outboundItemList;
	}

	/**
	 * Logic to get all the sub picking material item belongs to the production
	 * order item.
	 *
	 * @param prodOrderItemUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getSubRefMaterialItemListByOrderItem(String prodOrderItemUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingOrderManager
				.getEntityNodeListByKey(prodOrderItemUUID, "refProdOrderItemUUID", ProdPickingRefMaterialItem.NODENAME, client, null);
		return prodPickingRefMaterialItemList;
	}


	/**
	 * @param prodPickingRefMaterialItemList
	 * @return
	 */
	public List<ProdPickingExtendAmountModel> getPickingItemAmountListWrapper(
			List<ServiceEntityNode> prodPickingRefMaterialItemList) throws MaterialException, ServiceEntityConfigureException, DocActionException {
		if (ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
			return null;
		}
		List<ProdPickingExtendAmountModel> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : prodPickingRefMaterialItemList) {
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
			ProdPickingExtendAmountModel prodPickingExtendAmountModel = getPickingItemAmountWrapper(prodPickingRefMaterialItem);
			resultList.add(prodPickingExtendAmountModel);
		}
		return resultList;
	}

	public ProdPickingExtendAmountModel filterPickingExtendModelOnline(Object keyValue,
			Function<ProdPickingRefMaterialItem, Object> keyValueCallback,
			List<ProdPickingExtendAmountModel> prodPickingExtendAmountModelList) {
		List<ProdPickingExtendAmountModel> resultList = filterPickingExtendModelListOnline(keyValue, keyValueCallback,
				prodPickingExtendAmountModelList);
		if (ServiceCollectionsHelper.checkNullList(resultList)) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	public List<ProdPickingExtendAmountModel> filterPickingExtendModelListOnline(Object keyValue,
			Function<ProdPickingRefMaterialItem, Object> keyValueCallback,
			List<ProdPickingExtendAmountModel> prodPickingExtendAmountModelList) {
		if (ServiceCollectionsHelper.checkNullList(prodPickingExtendAmountModelList)) {
			return null;
		}
		if (keyValue == null) {
			return null;
		}
		List<ProdPickingExtendAmountModel> resultList = new ArrayList<>();
		for (ProdPickingExtendAmountModel prodPickingExtendAmountModel : prodPickingExtendAmountModelList) {
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingExtendAmountModel.getProdPickingRefMaterialItem();
			Object fieldValue = keyValueCallback.apply(prodPickingRefMaterialItem);
			if (fieldValue != null && fieldValue.equals(keyValue)) {
				resultList.add(prodPickingExtendAmountModel);
			}
		}
		return resultList;
	}

	/**
	 * Wrapper method to calculate all the relative amount information for picking item
	 *
	 * @param prodPickingRefMaterialItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public ProdPickingExtendAmountModel getPickingItemAmountWrapper(ProdPickingRefMaterialItem prodPickingRefMaterialItem)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
		StorageCoreUnit inProcessAmount = this.getInProcessAmount(prodPickingRefMaterialItem);
		StorageCoreUnit inStockAmount = this.getReservedStoreAmount(prodPickingRefMaterialItem);
		StorageCoreUnit toPickAmount = this.getReservedOutboundAmount(prodPickingRefMaterialItem, true);
		StorageCoreUnit pickedAmount = this.getPickedAmount(prodPickingRefMaterialItem);
		StorageCoreUnit suppliedAmount = null;
		if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
			suppliedAmount = new StorageCoreUnit(prodPickingRefMaterialItem.getRefMaterialSKUUUID(),
					prodPickingRefMaterialItem.getRefUnitUUID(), prodPickingRefMaterialItem.getAmount());
			suppliedAmount = setDirectionStorageByProcessType(suppliedAmount, prodPickingRefMaterialItem);
		}else{
			suppliedAmount = this
					.getSuppliedStoreAmount(prodPickingRefMaterialItem, inProcessAmount, inStockAmount, toPickAmount, pickedAmount);
		}
		List<ServiceEntityNode> endDocMatItemList = this.getAllEndDocMatItemList(prodPickingRefMaterialItem, true);
		// Special process for picked
		if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
			if (toPickAmount != null) {
				toPickAmount.setAmount(0);
			}
			if (inProcessAmount != null) {
				inProcessAmount.setAmount(0);
			}
			if (inStockAmount != null) {
				inStockAmount.setAmount(0);
			}
		}
		ProdPickingExtendAmountModel prodPickingExtendAmountModel = new ProdPickingExtendAmountModel(prodPickingRefMaterialItem,
				inProcessAmount, toPickAmount, inStockAmount, suppliedAmount, pickedAmount, endDocMatItemList);
		return prodPickingExtendAmountModel;
	}



	public void convProdPickingRefMaterialItemToUI(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
												   ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convProdPickingRefMaterialItemToUI(prodPickingRefMaterialItem, prodPickingRefMaterialItemUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convProdPickingRefMaterialItemToUI(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
												   ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		if (prodPickingRefMaterialItem != null) {
			docFlowProxy.convDocMatItemToUI(prodPickingRefMaterialItem, prodPickingRefMaterialItemUIModel, logonInfo);
			prodPickingRefMaterialItemUIModel.setAmount(prodPickingRefMaterialItem.getAmount());
			prodPickingRefMaterialItemUIModel.setRefUnitUUID(prodPickingRefMaterialItem.getRefUnitUUID());
			prodPickingRefMaterialItemUIModel.setRefInboundItemUUID(prodPickingRefMaterialItem.getRefInboundItemUUID());
			prodPickingRefMaterialItemUIModel.setRefOutboundItemUUID(prodPickingRefMaterialItem.getRefOutboundItemUUID());
			prodPickingRefMaterialItemUIModel.setRefUUID(prodPickingRefMaterialItem.getRefUUID());
			prodPickingRefMaterialItemUIModel.setInProcessAmount(prodPickingRefMaterialItem.getInProcessAmount());
			prodPickingRefMaterialItemUIModel.setInStockAmount(prodPickingRefMaterialItem.getInStockAmount());
			prodPickingRefMaterialItemUIModel.setAvailableAmount(prodPickingRefMaterialItem.getAvailableAmount());
			prodPickingRefMaterialItemUIModel.setSuppliedAmount(prodPickingRefMaterialItem.getSuppliedAmount());
			prodPickingRefMaterialItemUIModel.setToPickAmount(prodPickingRefMaterialItem.getToPickAmount());
			prodPickingRefMaterialItemUIModel.setPickedAmount(prodPickingRefMaterialItem.getPickedAmount());
			try {
				prodPickingRefMaterialItemUIModel.setAmountLabel(materialStockKeepUnitManager
						.getAmountLabel(prodPickingRefMaterialItem.getRefMaterialSKUUUID(), prodPickingRefMaterialItem.getRefUnitUUID(),
								prodPickingRefMaterialItem.getAmount(), prodPickingRefMaterialItem.getClient()));
				prodPickingRefMaterialItemUIModel.setInStockAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getInStockAmount()));
				prodPickingRefMaterialItemUIModel.setInProcessAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getInProcessAmount()));
				prodPickingRefMaterialItemUIModel.setAvailableAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getAvailableAmount()));
				prodPickingRefMaterialItemUIModel.setSuppliedAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getSuppliedAmount()));
				prodPickingRefMaterialItemUIModel.setToPickAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getToPickAmount()));
				prodPickingRefMaterialItemUIModel.setPickedAmountLabel(getAmountLabelUnion(prodPickingRefMaterialItem,
						prodPickingRefMaterialItem.getAvailableAmount()));
			} catch (MaterialException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
			prodPickingRefMaterialItemUIModel.setRefNextOrderUUID(prodPickingRefMaterialItem.getRefNextOrderUUID());
			prodPickingRefMaterialItemUIModel.setRefPrevOrderUUID(prodPickingRefMaterialItem.getRefPrevOrderUUID());
			prodPickingRefMaterialItemUIModel.setItemStatus(prodPickingRefMaterialItem.getItemStatus());
			prodPickingRefMaterialItemUIModel.setItemProcessType(prodPickingRefMaterialItem.getItemProcessType());
			prodPickingRefMaterialItemUIModel.setRefProdOrderItemUUID(prodPickingRefMaterialItem.getRefProdOrderItemUUID());
			prodPickingRefMaterialItemUIModel.setRefWarehouseUUID(prodPickingRefMaterialItem.getRefWarehouseUUID());
			prodPickingRefMaterialItemUIModel.setRefBillOfMaterialUUID(prodPickingRefMaterialItem.getRefBillOfMaterialUUID());
			prodPickingRefMaterialItemUIModel.setUnitPriceNoTax(prodPickingRefMaterialItem.getUnitPriceNoTax());
			prodPickingRefMaterialItemUIModel.setItemPriceNoTax(prodPickingRefMaterialItem.getItemPriceNoTax());
			prodPickingRefMaterialItemUIModel.setTaxRate(prodPickingRefMaterialItem.getTaxRate());
			prodPickingRefMaterialItemUIModel.setDocumentType(prodPickingRefMaterialItem.getDocumentType());
			if (prodPickingRefMaterialItem.getCreatedTime() != null) {
				prodPickingRefMaterialItemUIModel
						.setCreatedTime(DefaultDateFormatConstant.DATE_FORMAT.format(prodPickingRefMaterialItem.getCreatedTime()));
			}
			if (logonInfo != null) {
				Map<Integer, String> itemStatusMap = prodPickingOrderManager.initItemStatusMap(logonInfo.getLanguageCode());
				Map<Integer, String> processTypeMap = prodPickingOrderManager.initProcessTypeMap(logonInfo.getLanguageCode());
				prodPickingRefMaterialItemUIModel
						.setItemProcessTypeValue(processTypeMap.get(prodPickingRefMaterialItem.getItemProcessType()));
				prodPickingRefMaterialItemUIModel.setItemStatusValue(itemStatusMap.get(prodPickingRefMaterialItem.getItemStatus()));
			}
		}
	}


	private String getAmountLabelUnion(ProdPickingRefMaterialItem prodPickingRefMaterialItem, double amount) throws MaterialException,
			ServiceEntityConfigureException {
		String amountLabel =
				materialStockKeepUnitManager.getAmountLabel(prodPickingRefMaterialItem.getRefMaterialSKUUUID(),
						prodPickingRefMaterialItem.getRefUnitUUID(), amount, prodPickingRefMaterialItem.getClient());
		return amountLabel;
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:prodPickingRefMaterialItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProdPickingRefMaterialItem(ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel,
												   ProdPickingRefMaterialItem rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getUuid())) {
			rawEntity.setUuid(prodPickingRefMaterialItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodPickingRefMaterialItemUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodPickingRefMaterialItemUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getClient())) {
			rawEntity.setClient(prodPickingRefMaterialItemUIModel.getClient());
		}
		rawEntity.setAmount(prodPickingRefMaterialItemUIModel.getAmount());
		rawEntity.setRefUnitUUID(prodPickingRefMaterialItemUIModel.getRefUnitUUID());
		rawEntity.setRefInboundItemUUID(prodPickingRefMaterialItemUIModel.getRefInboundItemUUID());
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRefOutboundItemUUID())) {
			rawEntity.setRefOutboundItemUUID(prodPickingRefMaterialItemUIModel.getRefOutboundItemUUID());
		}
		rawEntity.setRefUUID(prodPickingRefMaterialItemUIModel.getRefUUID());
		rawEntity.setNextDocType(prodPickingRefMaterialItemUIModel.getNextDocType());
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getNextDocMatItemUUID())) {
			rawEntity.setNextDocMatItemUUID(prodPickingRefMaterialItemUIModel.getNextDocMatItemUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getPrevDocMatItemUUID())) {
			rawEntity.setPrevDocMatItemUUID(prodPickingRefMaterialItemUIModel.getPrevDocMatItemUUID());
		}
		rawEntity.setRefNextOrderUUID(prodPickingRefMaterialItemUIModel.getRefNextOrderUUID());
		rawEntity.setPrevDocType(prodPickingRefMaterialItemUIModel.getPrevDocType());
		rawEntity.setRefPrevOrderUUID(prodPickingRefMaterialItemUIModel.getRefPrevOrderUUID());
		rawEntity.setInProcessAmount(prodPickingRefMaterialItemUIModel.getInProcessAmount());
		rawEntity.setInStockAmount(prodPickingRefMaterialItemUIModel.getInStockAmount());
		rawEntity.setAvailableAmount(prodPickingRefMaterialItemUIModel.getAvailableAmount());
		rawEntity.setToPickAmount(prodPickingRefMaterialItemUIModel.getToPickAmount());
		rawEntity.setPickedAmount(prodPickingRefMaterialItemUIModel.getPickedAmount());
		rawEntity.setSuppliedAmount(prodPickingRefMaterialItemUIModel.getSuppliedAmount());
		if (prodPickingRefMaterialItemUIModel.getItemStatus() != 0) {
			rawEntity.setItemStatus(prodPickingRefMaterialItemUIModel.getItemStatus());
		}
		if (prodPickingRefMaterialItemUIModel.getItemProcessType() != 0) {
			rawEntity.setItemProcessType(prodPickingRefMaterialItemUIModel.getItemProcessType());
		}

		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRefMaterialSKUUUID())) {
			rawEntity.setRefMaterialSKUUUID(prodPickingRefMaterialItemUIModel.getRefMaterialSKUUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRefMaterialSKUUUID())) {
			rawEntity.setRefProdOrderItemUUID(prodPickingRefMaterialItemUIModel.getRefProdOrderItemUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRefWarehouseUUID())) {
			rawEntity.setRefWarehouseUUID(prodPickingRefMaterialItemUIModel.getRefWarehouseUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getRefBillOfMaterialUUID())) {
			rawEntity.setRefBillOfMaterialUUID(prodPickingRefMaterialItemUIModel.getRefBillOfMaterialUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getProductionBatchNumber())) {
			rawEntity.setProductionBatchNumber(prodPickingRefMaterialItemUIModel.getProductionBatchNumber());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPickingRefMaterialItemUIModel.getPurchaseBatchNumber())) {
			rawEntity.setPurchaseBatchNumber(prodPickingRefMaterialItemUIModel.getPurchaseBatchNumber());
		}
		rawEntity.setUnitPrice(prodPickingRefMaterialItemUIModel.getUnitPrice());
		rawEntity.setUnitPriceNoTax(prodPickingRefMaterialItemUIModel.getUnitPriceNoTax());
		rawEntity.setItemPrice(prodPickingRefMaterialItemUIModel.getItemPrice());
		rawEntity.setItemPriceNoTax(prodPickingRefMaterialItemUIModel.getItemPriceNoTax());
		rawEntity.setTaxRate(prodPickingRefMaterialItemUIModel.getTaxRate());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPickingOrderToItemUI(ProdPickingOrder prodPickingOrder,
										 ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
		if (prodPickingOrder != null) {
			prodPickingRefMaterialItemUIModel.setRefPickingOrderId(prodPickingOrder.getId());
			prodPickingRefMaterialItemUIModel.setRefPickingOrderName(prodPickingOrder.getName());
			prodPickingRefMaterialItemUIModel.setRootNodeUUID(prodPickingOrder.getUuid());
		}
	}


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProductionOrderToItemUI(ProductionOrder productionOrder,
											ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
		if (productionOrder != null) {
			prodPickingRefMaterialItemUIModel.setRefOrderId(productionOrder.getId());
			prodPickingRefMaterialItemUIModel.setRefOrderName(productionOrder.getName());
			prodPickingRefMaterialItemUIModel.setRefProdOrderUUID(productionOrder.getUuid());
		}
	}


}
