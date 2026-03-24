package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.logistics.repository.InventoryTransferOrderRepository;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferOrderConfigureProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [InventoryTransferOrder]
 * 
 * @author Zhang, Harry
 * @date Fri Nov 27 11:18:45 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class InventoryTransferOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvInventoryTransferOrderToUI = "convInventoryTransferOrderToUI";

	public static final String METHOD_ConvUIToInventoryTransferOrder = "convUIToInventoryTransferOrder";

	public static final String METHOD_ConvInboundWarehouseAreaToUI = "convInboundWarehouseAreaToUI";

	public static final String METHOD_ConvOutboundWarehouseAreaToUI = "convOutboundWarehouseAreaToUI";

	public static final String METHOD_ConvOutboundDeliveryToUI = "convOutboundDeliveryToUI";

	public static final String METHOD_ConvOutboundWarehouseToUI = "convOutboundWarehouseToUI";

	public static final String METHOD_ConvInboundWarehouseToUI = "convInboundWarehouseToUI";

	public static final String METHOD_ConvInboundDeliveryToUI = "convInboundDeliveryToUI";

	@Autowired
	protected InventoryTransferOrderRepository inventoryTransferOrderDAO;

	@Autowired
	protected InventoryTransferOrderConfigureProxy inventoryTransferOrderConfigureProxy;

	@Autowired
	protected InventoryTransferOrderActionExecutionProxy inventoryTransferOrderActionExecutionProxy;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	@Autowired
	protected InventoryTransferOrderIdHelper inventoryTransferOrderIdHelper;

	@Autowired
	protected InventoryTransferOrderSearchProxy inventoryTransferOrderSearchProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected InventoryTransferItemServiceUIModelExtension inventoryTransferItemServiceUIModelExtension;

	@Autowired
	protected InventoryTransferOrderServiceUIModelExtension inventoryTransferOrderServiceUIModelExtension;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected PrevNextDocItemProxy prevNextDocItemProxy;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	@Autowired
	protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

	@Autowired
	protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

	public Logger logger = LoggerFactory.getLogger(InventoryTransferOrderManager.class);

	protected Map<String, Map<Integer, String>> planCategoryMapLan = new HashMap<>();

	protected Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();
    @Autowired
    private LogonInfoManager logonInfoManager;

	public InventoryTransferOrderManager() {
		super.seConfigureProxy = new InventoryTransferOrderConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new InventoryTransferOrderDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(inventoryTransferOrderDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(inventoryTransferOrderConfigureProxy);
	}


	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, InventoryTransferOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
	}

	public Map<Integer, String> initPriorityCode(String languageCode)
			throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
												SerialLogonInfo serialLogonInfo){
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, uuid, serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REJECT_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, uuid,serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REVOKE_SUBMIT){
			serviceFlowRuntimeEngine.clearInvolveProcessIns(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,uuid);
			return true;
		}
		return true;
	}


	/**
	 * Logic to get all document type might be suitable for inventory transfer
	 * order
	 *
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getDocumentTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		Map<Integer, String> rawDocumentMap = serviceDocumentComProxy
				.getDocumentTypeMap(false, languageCode);
		return rawDocumentMap;
	}

	public List<InventoryTransferItem> getOtherPendingTransferItem(
			WarehouseStoreItem warehouseStoreItem, String homeTransferItemUUID)
			throws ServiceEntityConfigureException {
		List<InventoryTransferItem> resultList = new ArrayList<>();
		List<ServiceEntityNode> rawTransferItemList = getEntityNodeListByKey(warehouseStoreItem.getUuid(), DeliveryItem.FILED_REFSTOREITEMUUID,
				InventoryTransferItem.NODENAME, warehouseStoreItem.getClient(), null);
		if (ServiceCollectionsHelper.checkNullList(rawTransferItemList)) {
			return null;
		}
		List<String> transferItemUUIDArray = ServiceEntityStringHelper.convStringToStrList(homeTransferItemUUID);
		for (ServiceEntityNode seNode : rawTransferItemList) {
			InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) seNode;
			InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) getEntityNodeByKey(
					inventoryTransferItem.getRootNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID,
					InventoryTransferOrder.NODENAME,
					warehouseStoreItem.getClient(), null);
			if (inventoryTransferOrder == null
					|| inventoryTransferOrder.getStatus() != InventoryTransferOrder.STATUS_INITIAL) {
				// Filter out [done] and [approved] inventoryTransferOrder
				continue;
			}
			if (!ServiceCollectionsHelper.checkNullList(transferItemUUIDArray)) {
				Object filterResult = ServiceCollectionsHelper.filterOnline(inventoryTransferItem.getUuid(), rawStr->{
					return rawStr;
				}, transferItemUUIDArray);
				if (filterResult == null) {
					resultList.add(inventoryTransferItem);
				}
			} else {
				// In case don't need to filter home outboundItemUUID
				resultList.add(inventoryTransferItem);
			}
		}
		return resultList;
	}

	public StorageCoreUnit checkOutboundRequest(OutboundItemRequestJSONModel request, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException, InventoryTransferOrderException, MaterialException {
		InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) getEntityNodeByUUID(request.getUuid(),
						InventoryTransferItem.NODENAME,
				serialLogonInfo.getClient());
		InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) getEntityNodeByUUID(inventoryTransferItem.getRootNodeUUID(),
						InventoryTransferOrder.NODENAME, serialLogonInfo.getClient());
		if(inventoryTransferOrder.getStatus() == InventoryTransferOrder.STATUS_PROCESSDONE){
			// skip already done outbound delivery
			return null;
		}
		return outboundDeliveryWarehouseItemManager.checkWarehouseStoreItemAvailableWrapper(request.getUuid(),
				inventoryTransferItem.getRefStoreItemUUID(), new Double(request.getAmount()),
				request.getRefUnitUUID());
	}

	public List<CrossDocBatchConvertProxy.DocContentCreateContext> generateDelivery(ServiceModule sourceServiceModule,
																					List<ServiceEntityNode> selectedSourceDocMatItemList,
																					DocumentMatItemBatchGenRequest genRequest,
																					LogonInfo logonInfo) throws DocActionException {
        try {
			// Step 1: Get relative warehouse store item list by selected transfer item list.
			List<String> uuidList = ServiceCollectionsHelper.pluckList(
                    selectedSourceDocMatItemList, "refStoreItemUUID");
			List<ServiceEntityNode> warehouseStoreMatItemList = docFlowProxy.getDefDocItemNodeList(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
					uuidList,logonInfo.getClient());
			InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel = (InventoryTransferOrderServiceModel) sourceServiceModule;
			InventoryTransferOrder inventoryTransferOrder = inventoryTransferOrderServiceModel.getInventoryTransferOrder();

			// Step 2: Generate outbound delivery based on the warehouse store item list
			DeliveryMatItemBatchGenRequest genOutboundRequest = new DeliveryMatItemBatchGenRequest();
			genOutboundRequest.setUuidList(uuidList);
			genOutboundRequest.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
			genOutboundRequest.setSourceDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
			genOutboundRequest.setRefWarehouseUUID(inventoryTransferOrder.getRefWarehouseUUID());
			DocActionExecutionProxy<?, ?, ?> warehouseStoreDocActionProxy =
					docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
			List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentCreateContextList =
					warehouseStoreDocActionProxy.crossCreateDocumentBatch(null,
							warehouseStoreMatItemList, genOutboundRequest, new CrossDocConvertRequest.InputOption(true), logonInfo);


			// Step3 Build the prev-next relationship from outbound delivery to current inventory
			ServiceCollectionsHelper.traverseListInterrupt(docContentCreateContextList.get(0).getDocMatItemCreateContextList(), docMatItemCreateContext -> {
				OutboundItem outboundItem = (OutboundItem) docMatItemCreateContext.getTargetDocMatItemNode();
				InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) ServiceCollectionsHelper
						.filterSENodeOnline(outboundItem.getRefStoreItemUUID(),
								"refStoreItemUUID",
								selectedSourceDocMatItemList);
				WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) ServiceCollectionsHelper
						.filterSENodeOnline(outboundItem.getRefStoreItemUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								warehouseStoreMatItemList);
				// Clear prev-next relationship from inventory to warehouse store
				if (warehouseStoreItem != null && inventoryTransferItem != null) {
					prevNextDocItemProxy.cleanPrevNext(warehouseStoreItem, inventoryTransferItem, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
				}
				// Build prev-next relationship from outboundItem to current inventory item.
				docFlowProxy.buildItemPrevNextRelationship(outboundItem, inventoryTransferItem, genRequest, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
				return true;
			});
			// Step4 Generate inbound delivery based on current inventory item list
			DeliveryMatItemBatchGenRequest genInboundRequest = new DeliveryMatItemBatchGenRequest();
			genInboundRequest.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
			genInboundRequest.setSourceDocType(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER);
			List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentCreateInboundContextList =
					inventoryTransferOrderActionExecutionProxy.crossCreateDocumentBatch(inventoryTransferOrderServiceModel,
					selectedSourceDocMatItemList, genInboundRequest, logonInfo);
			if (!ServiceCollectionsHelper.checkNullList(docContentCreateInboundContextList)) {
				docContentCreateContextList.addAll(docContentCreateInboundContextList);
			}
			return docContentCreateContextList;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (ServiceModuleProxyException | ServiceEntityInstallationException | SearchConfigureException |
                 ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInventoryTransferOrderToUI(
			InventoryTransferOrder inventoryTransferOrder,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel,
			LogonInfo logonInfo) {
		docFlowProxy.convDocumentToUI(inventoryTransferOrder,
				inventoryTransferOrderUIModel, logonInfo);
		try {
			Map<Integer, String> statusMap = this.initStatusMap(logonInfo
					.getLanguageCode());
			inventoryTransferOrderUIModel.setStatusValue(statusMap
					.get(inventoryTransferOrder.getStatus()));
		} catch (ServiceEntityInstallationException e) {
			// just continue
		}
		inventoryTransferOrderUIModel
				.setProductionBatchNumber(inventoryTransferOrder
						.getProductionBatchNumber());
		inventoryTransferOrderUIModel
				.setPurchaseBatchNumber(inventoryTransferOrder
						.getPurchaseBatchNumber());
		inventoryTransferOrderUIModel
				.setRefInboundDeliveryUUID(inventoryTransferOrder
						.getRefInboundDeliveryUUID());
		inventoryTransferOrderUIModel
				.setGrossOutboundFee(inventoryTransferOrder
						.getGrossOutboundFee());
		inventoryTransferOrderUIModel
				.setRefInboundWarehouseUUID(inventoryTransferOrder
						.getRefInboundWarehouseUUID());
		if (inventoryTransferOrder.getPlanExecuteDate() != null) {
			inventoryTransferOrderUIModel
					.setPlanExecuteDate(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(inventoryTransferOrder.getPlanExecuteDate()));
		}
		inventoryTransferOrderUIModel.setPriorityCode(inventoryTransferOrder
				.getPriorityCode());
		inventoryTransferOrderUIModel.setPlanCategory(inventoryTransferOrder
				.getPlanCategory());
		if (inventoryTransferOrder.getShippingTime() != null) {
			inventoryTransferOrderUIModel
					.setShippingTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(inventoryTransferOrder.getShippingTime()));
		}
		inventoryTransferOrderUIModel.setShippingPoint(inventoryTransferOrder
				.getShippingPoint());
		if (inventoryTransferOrder.getCreatedTime() != null) {
			inventoryTransferOrderUIModel
					.setCreatedDate(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(inventoryTransferOrder.getCreatedTime()));
		}
		inventoryTransferOrderUIModel.setGrossPrice(inventoryTransferOrder
				.getGrossPrice());
		inventoryTransferOrderUIModel
				.setRefOutboundWarehouseUUID(inventoryTransferOrder
						.getRefWarehouseUUID());
		inventoryTransferOrderUIModel
				.setRefOutboundWarehouseAreaUUID(inventoryTransferOrder
						.getRefWarehouseAreaUUID());
		inventoryTransferOrderUIModel
				.setRefOutboundDeliveryUUID(inventoryTransferOrder
						.getRefOutboundDeliveryUUID());
		inventoryTransferOrderUIModel.setGrossStorageFee(inventoryTransferOrder
				.getGrossStorageFee());
		inventoryTransferOrderUIModel.setId(inventoryTransferOrder.getId());
		inventoryTransferOrderUIModel
				.setRefInboundWarehouseAreaUUID(inventoryTransferOrder
						.getRefInboundWarehouseAreaUUID());
		inventoryTransferOrderUIModel.setPlanCategory(inventoryTransferOrder
				.getPlanCategory());
		if (inventoryTransferOrderUIModel.getPlanExecuteDate() != null) {
			inventoryTransferOrderUIModel
					.setPlanExecuteDate(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(inventoryTransferOrder.getPlanExecuteDate()));
		}
	}

	public void convOutboundWarehouseToUI(Warehouse outboundWarehouse,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (outboundWarehouse != null) {
			inventoryTransferOrderUIModel
					.setRefOutboundWarehouseUUID(outboundWarehouse.getUuid());
			inventoryTransferOrderUIModel
					.setRefOutboundWarehouseId(outboundWarehouse.getId());
			inventoryTransferOrderUIModel
					.setRefOutboundWarehouseName(outboundWarehouse.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInboundWarehouseAreaToUI(
			WarehouseArea inboundWarehouseArea,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (inboundWarehouseArea != null) {
			inventoryTransferOrderUIModel
					.setRefInboundWarehouseAreaName(inboundWarehouseArea
							.getName());
			inventoryTransferOrderUIModel
					.setRefInboundWarehouseAreaId(inboundWarehouseArea.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutboundWarehouseAreaToUI(
			WarehouseArea outboundWarehouseArea,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (outboundWarehouseArea != null) {
			inventoryTransferOrderUIModel
					.setRefOutboundWarehouseAreaId(outboundWarehouseArea
							.getId());
			inventoryTransferOrderUIModel
					.setRefOutboundWarehouseAreaName(outboundWarehouseArea
							.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:inventoryTransferOrder
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToInventoryTransferOrder(
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel,
			InventoryTransferOrder rawEntity) {
		docFlowProxy.convUIToDocument(inventoryTransferOrderUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefInboundDeliveryUUID())) {
			rawEntity.setRefInboundDeliveryUUID(inventoryTransferOrderUIModel
					.getRefInboundDeliveryUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefOutboundDeliveryUUID())) {
			rawEntity.setRefOutboundDeliveryUUID(inventoryTransferOrderUIModel
					.getRefInboundDeliveryUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefOutboundWarehouseUUID())) {
			rawEntity.setRefWarehouseUUID(inventoryTransferOrderUIModel
					.getRefOutboundWarehouseUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefOutboundWarehouseAreaUUID())) {
			rawEntity.setRefWarehouseAreaUUID(inventoryTransferOrderUIModel
					.getRefOutboundWarehouseAreaUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefOutboundWarehouseUUID())) {
			rawEntity.setRefWarehouseUUID(inventoryTransferOrderUIModel
					.getRefOutboundWarehouseUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefOutboundWarehouseAreaUUID())) {
			rawEntity.setRefWarehouseAreaUUID(inventoryTransferOrderUIModel
					.getRefOutboundWarehouseAreaUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefInboundWarehouseAreaUUID())) {
			rawEntity
					.setRefInboundWarehouseAreaUUID(inventoryTransferOrderUIModel
							.getRefInboundWarehouseAreaUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getRefInboundWarehouseUUID())) {
			rawEntity.setRefInboundWarehouseUUID(inventoryTransferOrderUIModel
					.getRefInboundWarehouseUUID());
		}
		rawEntity.setGrossOutboundFee(inventoryTransferOrderUIModel
				.getGrossOutboundFee());
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getProductionBatchNumber())) {
			rawEntity.setProductionBatchNumber(inventoryTransferOrderUIModel
					.getProductionBatchNumber());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getPurchaseBatchNumber())) {
			rawEntity.setPurchaseBatchNumber(inventoryTransferOrderUIModel
					.getPurchaseBatchNumber());
		}
		rawEntity.setClient(inventoryTransferOrderUIModel.getClient());
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getPlanExecuteDate())) {
			try {
				rawEntity
						.setPlanExecuteDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.parse(inventoryTransferOrderUIModel
										.getPlanExecuteDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setName(inventoryTransferOrderUIModel.getName());
		rawEntity.setPriorityCode(inventoryTransferOrderUIModel
				.getPriorityCode());
		rawEntity.setPlanCategory(inventoryTransferOrderUIModel
				.getPlanCategory());
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferOrderUIModel
						.getShippingTime())) {
			try {
				rawEntity
						.setShippingTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.parse(inventoryTransferOrderUIModel
										.getShippingTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setShippingPoint(inventoryTransferOrderUIModel
				.getShippingPoint());
		rawEntity.setUuid(inventoryTransferOrderUIModel.getUuid());
		rawEntity.setGrossPrice(inventoryTransferOrderUIModel.getGrossPrice());
		rawEntity.setGrossStorageFee(inventoryTransferOrderUIModel
				.getGrossStorageFee());
		rawEntity.setId(inventoryTransferOrderUIModel.getId());
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) super
				.newRootEntityNode(client);
		String InventoryTransferOrderId = inventoryTransferOrderIdHelper
				.genDefaultId(client);
		inventoryTransferOrder.setId(InventoryTransferOrderId);
		return inventoryTransferOrder;
	}

	public void convOutboundDeliveryToUI(OutboundDelivery outboundDelivery,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (outboundDelivery != null) {
			inventoryTransferOrderUIModel
					.setRefOutboundDeliveryId(outboundDelivery.getId());
			inventoryTransferOrderUIModel
					.setRefOutboundDeliveryStatus(outboundDelivery.getStatus());
		}
	}

	public void convInboundDeliveryToUI(InboundDelivery inboundDelivery,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (inboundDelivery != null) {
			inventoryTransferOrderUIModel
					.setRefInboundDeliveryId(inboundDelivery.getId());
			inventoryTransferOrderUIModel
					.setRefInboundDeliveryStatus(inboundDelivery.getStatus());
		}
	}

	public void convInboundWarehouseToUI(Warehouse warehouse,
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		if (warehouse != null) {
			inventoryTransferOrderUIModel.setRefInboundWarehouseId(warehouse
					.getId());
			inventoryTransferOrderUIModel.setRefInboundWarehouseName(warehouse
					.getName());
		}
	}

	public ServiceDocumentExtendUIModel convInventoryTransferOrderToDocExtUIModel(
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel
				.setRefUIModel(inventoryTransferOrderUIModel);
		serviceDocumentExtendUIModel.setUuid(inventoryTransferOrderUIModel
				.getUuid());
		serviceDocumentExtendUIModel
				.setParentNodeUUID(inventoryTransferOrderUIModel
						.getParentNodeUUID());
		serviceDocumentExtendUIModel
				.setRootNodeUUID(inventoryTransferOrderUIModel
						.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(inventoryTransferOrderUIModel
				.getId());
		serviceDocumentExtendUIModel.setStatus(inventoryTransferOrderUIModel
				.getStatus());
		serviceDocumentExtendUIModel
				.setStatusValue(inventoryTransferOrderUIModel.getStatusValue());
		serviceDocumentExtendUIModel
				.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER);
		if(logonInfo != null){
			serviceDocumentExtendUIModel
					.setDocumentTypeValue(serviceDocumentComProxy
							.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
									logonInfo.getLanguageCode()));
		}
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convInventoryTransferItemToDocExtUIModel(
			InventoryTransferItemUIModel inventoryTransferItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		docFlowProxy.convDocMatItemUIToDocExtUIModel(inventoryTransferItemUIModel,
				serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER);
		serviceDocumentExtendUIModel
				.setRefUIModel(inventoryTransferItemUIModel);		
		serviceDocumentExtendUIModel.setId(inventoryTransferItemUIModel
				.getParentDocId());
		serviceDocumentExtendUIModel.setStatus(inventoryTransferItemUIModel
				.getParentDocStatus());
		serviceDocumentExtendUIModel
				.setStatusValue(inventoryTransferItemUIModel
						.getParentDocStatusValue());
		// Logic of reference Date
		String referenceDate = inventoryTransferItemUIModel.getProcessDate();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = inventoryTransferItemUIModel.getApprovedDate();
		}
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = inventoryTransferItemUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	@Override
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
			ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
			InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) seNode;
			try {
				InventoryTransferOrderUIModel inventoryTransferOrderUIModel = (InventoryTransferOrderUIModel) genUIModelFromUIModelExtension(
						InventoryTransferOrderUIModel.class,
						inventoryTransferOrderServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						inventoryTransferOrder, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
						convInventoryTransferOrderToDocExtUIModel(inventoryTransferOrderUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, InventoryTransferOrder.SENAME));
			}
		}
		if (InventoryTransferItem.NODENAME
				.equals(seNode.getNodeName())) {
			InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) seNode;
			try {
				InventoryTransferItemUIModel inventoryTransferItemUIModel = (InventoryTransferItemUIModel) genUIModelFromUIModelExtension(
						InventoryTransferItemUIModel.class,
						inventoryTransferItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						inventoryTransferItem, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convInventoryTransferItemToDocExtUIModel(inventoryTransferItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, InventoryTransferItem.NODENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, InventoryTransferItem.NODENAME));
			}
		}
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.InventoryTransferOrder;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return inventoryTransferOrderSearchProxy;
	}

}
