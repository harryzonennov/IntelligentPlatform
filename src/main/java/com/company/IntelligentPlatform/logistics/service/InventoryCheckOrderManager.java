package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.logistics.repository.InventoryCheckOrderRepository;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrderConfigureProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [InventoryCheckOrder]
 * 
 * @author
 * @date Thu Sep 17 16:24:18 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class InventoryCheckOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvInventoryCheckOrderToUI = "convInventoryCheckOrderToUI";

	public static final String METHOD_ConvUIToInventoryCheckOrder = "convUIToInventoryCheckOrder";

	public static final String METHOD_ConvWarehouseAreaToUI = "convWarehouseAreaToUI";

	public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> inventoryCheckResultMapLan = new HashMap<>();
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected InventoryCheckOrderRepository inventoryCheckOrderDAO;

	@Autowired
	protected InventoryCheckOrderConfigureProxy inventoryCheckOrderConfigureProxy;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	@Autowired
	protected InventoryCheckOrderSearchProxy inventoryCheckOrderSearchProxy;

	@Autowired
	protected InventoryCheckOrderIdHelper inventoryCheckOrderIdHelper;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected InventoryCheckOrderServiceUIModelExtension inventoryCheckOrderServiceUIModelExtension;

	@Autowired
	protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public InventoryCheckOrderManager() {
		super.seConfigureProxy = new InventoryCheckOrderConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, inventoryCheckOrderDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(inventoryCheckOrderConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		InventoryCheckOrder InventoryCheckOrder = (InventoryCheckOrder) super
				.newRootEntityNode(client);
		String InventoryCheckOrderID = inventoryCheckOrderIdHelper
				.genDefaultId(client);
		InventoryCheckOrder.setId(InventoryCheckOrderID);
		return InventoryCheckOrder;
	}

	public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
												SerialLogonInfo serialLogonInfo){
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER, uuid, serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REJECT_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER, uuid,serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REVOKE_SUBMIT){
			serviceFlowRuntimeEngine.clearInvolveProcessIns(
					IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER,uuid);
			return true;
		}
		return true;
	}

	public InventoryCheckItemServiceModel newInventoryCheckItem(
			InventoryCheckOrder inventoryCheckOrder,
			WarehouseStoreItem warehouseStoreItem)
			throws ServiceEntityConfigureException {
		InventoryCheckItem inventoryCheckItem = (InventoryCheckItem) newEntityNode(
				inventoryCheckOrder, InventoryCheckItem.NODENAME);
		initConvWarehouseStoreToCheckItem(warehouseStoreItem,
				inventoryCheckItem);
		InventoryCheckItemServiceModel inventoryCheckItemServiceModel = new InventoryCheckItemServiceModel();
		inventoryCheckItemServiceModel.setInventoryCheckItem(inventoryCheckItem);
		return inventoryCheckItemServiceModel;
	}

	public Map<Integer, String> initInventoryCheckResultMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.inventoryCheckResultMapLan,
				InventoryCheckItemUIModel.class, "inventCheckResult");
	}

	public Map<Integer, String> initPriorityCodeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan,
				InventoryCheckOrderUIModel.class, "status");		
	}

	/**
	 * Get all possible warehouse store item by condition set in init model
	 * 
	 * @param initInventoryCheckModel
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws InventoryCheckException
	 * @throws SearchConfigureException
	 * @throws MaterialException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public List<ServiceEntityNode> getAllPossibleWarehouseStoreList(
			InitInventoryCheckModel initInventoryCheckModel, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, InventoryCheckException,
            SearchConfigureException, MaterialException, NodeNotFoundException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
		Warehouse warehouse = (Warehouse) warehouseManager.getEntityNodeByUUID(
				initInventoryCheckModel.getRefWarehouseUUID(),
				 Warehouse.NODENAME, initInventoryCheckModel.getClient());
		if (warehouse == null) {
			throw new InventoryCheckException(
					InventoryCheckException.PARA_NO_WAREHOUSE,
					initInventoryCheckModel.getRefWarehouseUUID());
		}
		WarehouseStoreItemSearchModel warehouseStoreItemSearchModel = new WarehouseStoreItemSearchModel();
		warehouseStoreItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
		warehouseStoreItemSearchModel.getRefWarehouse()
				.setRefWarehouseAreaUUID(initInventoryCheckModel
						.getRefWarehouseAreaUUID());
		warehouseStoreItemSearchModel.getRefWarehouse()
				.setRefWarehouseUUID(initInventoryCheckModel
						.getRefWarehouseUUID());
		warehouseStoreItemSearchModel
				.setItemStatus(WarehouseStoreItem.STATUS_INSTOCK);
        return warehouseStoreManager
				.searchStoreItemInternal(warehouseStoreItemSearchModel, logonInfo);
	}

	/**
	 * Core Logic to Generate
	 * 
	 * @param initInventoryCheckModel
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws InventoryCheckException
	 * @throws SearchConfigureException
	 * @throws MaterialException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceModuleProxyException
	 */
	@Transactional
	public InventoryCheckOrderServiceModel initInventoryCheckOrder(
			InitInventoryCheckModel initInventoryCheckModel, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, InventoryCheckException,
            SearchConfigureException, MaterialException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException {
		checkCreateInventoryCheckRequirement(initInventoryCheckModel, logonInfo);
		InventoryCheckOrder inventoryCheckOrder = (InventoryCheckOrder) newRootEntityNode(logonInfo.getClient());
		inventoryCheckOrder.setRefWarehouseAreaUUID(initInventoryCheckModel
				.getRefWarehouseAreaUUID());
		inventoryCheckOrder.setRefWarehouseUUID(initInventoryCheckModel
				.getRefWarehouseUUID());
		InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel = new InventoryCheckOrderServiceModel();
		inventoryCheckOrderServiceModel
				.setInventoryCheckOrder(inventoryCheckOrder);
		inventoryCheckOrderServiceModel
				.setInventoryCheckItemList(new ArrayList<>());
		List<ServiceEntityNode> warehouseStoreItemList = getAllPossibleWarehouseStoreList(
				initInventoryCheckModel, logonInfo);
		if (!ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
			for (ServiceEntityNode seNode : warehouseStoreItemList) {
				WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
				InventoryCheckItemServiceModel inventoryCheckItemServiceModel = this
						.newInventoryCheckItem(inventoryCheckOrder,
								warehouseStoreItem);
				inventoryCheckOrderServiceModel.getInventoryCheckItemList()
						.add(inventoryCheckItemServiceModel);
			}
		} else {
			// Raise exception when no items to be checked
			throw new InventoryCheckException(
					InventoryCheckException.TYPE_NO_WAREHOUSEITEM);
		}
		updateServiceModule(InventoryCheckOrderServiceModel.class,
				inventoryCheckOrderServiceModel, logonInfo.getRefUserUUID(),
				logonInfo.getResOrgUUID());
		return inventoryCheckOrderServiceModel;
	}

	/**
	 * Core logic to check creating new inventory check from warehouse
	 * information
	 * 
	 * @param initInventoryCheckModel
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws InventoryCheckException
	 * @throws MaterialException
	 */
	public void checkCreateInventoryCheckRequirement(
			InitInventoryCheckModel initInventoryCheckModel, LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityConfigureException,
            NodeNotFoundException, ServiceEntityInstallationException,
            InventoryCheckException, MaterialException, AuthorizationException, LogonInfoException {
		/**
		 * [Step1] Check weather the pending in-bound delivery exist for this
		 * warehouse
		 */
		String warehouseUUID = initInventoryCheckModel.getRefWarehouseUUID();
		String warehouseAreaUUID = initInventoryCheckModel
				.getRefWarehouseAreaUUID();
		List<ServiceEntityNode> inboundDeliveryListInProcess = getInProcessInboundDelivery(warehouseUUID, warehouseAreaUUID,
				logonInfo.getClient());
		if (inboundDeliveryListInProcess != null
				&& inboundDeliveryListInProcess.size() > 0) {
			String errorMessage = ServiceCollectionsHelper
					.joinServiceEntityList(inboundDeliveryListInProcess,
							IServiceEntityNodeFieldConstant.ID, ",");
			if (errorMessage != null) {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_INBOUND,
						errorMessage);
			} else {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_INBOUND,
						ServiceEntityStringHelper.EMPTYSTRING);
			}
		}
		/**
		 * [Step2] Check weather the pending out-bound delivery exist for this
		 * warehouse
		 */
		List<ServiceEntityNode> outboundDeliveryListInProcess = getInProcessOutboundDelivery(warehouseUUID,
				warehouseAreaUUID,
						logonInfo.getClient());
		if (outboundDeliveryListInProcess != null
				&& outboundDeliveryListInProcess.size() > 0) {
			String errorMessage = ServiceCollectionsHelper
					.joinServiceEntityList(outboundDeliveryListInProcess,
							IServiceEntityNodeFieldConstant.ID, ",");
			if (errorMessage != null) {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_OUTBOUND,
						errorMessage);
			} else {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_OUTBOUND,
						ServiceEntityStringHelper.EMPTYSTRING);
			}
		}
		/**
		 * [Step3] Check weather there is already other inventory check orders
		 */
		List<ServiceEntityNode> inventoryCheckOrderListInProcess = getInProcessInventoryCheckOrder(
				warehouseUUID, warehouseAreaUUID);
		if (inventoryCheckOrderListInProcess != null
				&& inventoryCheckOrderListInProcess.size() > 0) {
			String errorMessage = ServiceCollectionsHelper
					.joinServiceEntityList(inventoryCheckOrderListInProcess,
							IServiceEntityNodeFieldConstant.ID, ",");
			if (errorMessage != null) {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_OUTBOUND,
						errorMessage);
			} else {
				throw new InventoryCheckException(
						InventoryCheckException.PARA_INPROCESS_OUTBOUND,
						ServiceEntityStringHelper.EMPTYSTRING);
			}
		}
		/*
		 * [Step4] warehouse store item list
		 */
		List<ServiceEntityNode> warehouseStoreItemList = getAllPossibleWarehouseStoreList(
				initInventoryCheckModel, logonInfo);
		if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
			// Raise exception when no items to be checked
			throw new InventoryCheckException(
					InventoryCheckException.TYPE_NO_WAREHOUSEITEM);
		}
	}

	/**
	 * Get the list of pending in-bound delivery list for this warehouse and
	 * warehouseArea which might block inventory checking order creation
	 *
	 * @param warehouseUUID     :mandatory
	 * @param warehouseAreaUUID : could be null value
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> getInProcessInboundDelivery(
			String warehouseUUID, String warehouseAreaUUID, String client)
			throws ServiceEntityConfigureException {
		//TODO read this from system configuration
		List<Integer> inProcessStatus = ServiceCollectionsHelper.asList(InboundDelivery.STATUS_INITIAL,
				InboundDelivery.STATUS_REJECTED,
				InboundDelivery.STATUS_SUBMITTED, InboundDelivery.STATUS_APPROVED, InboundDelivery.STATUS_REVOKE_SUBMIT);
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(inProcessStatus, IDocumentNodeFieldConstant.STATUS,
				ServiceBasicKeyStructure.OPERATOR_OR);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure("refWarehouseUUID", warehouseUUID);
		ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure("refWarehouseAreaUUID", warehouseAreaUUID);
		List<ServiceEntityNode> resultList =
				inboundDeliveryManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2, key3),
						InboundDelivery.NODENAME, client, null);
		return resultList;
	}

	/**
	 * Get the list of pending outbound delivery list for this warehouse and
	 * warehouseArea
	 *
	 * @param warehouseUUID     :mandatory
	 * @param warehouseAreaUUID : could be null value
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> getInProcessOutboundDelivery(String warehouseUUID, String warehouseAreaUUID,
																String client)
			throws ServiceEntityConfigureException{
		//TODO read this from system configuration
		List<Integer> inProcessStatus = ServiceCollectionsHelper.asList(OutboundDelivery.STATUS_INITIAL,
				OutboundDelivery.STATUS_REJECTED,
				OutboundDelivery.STATUS_SUBMITTED, OutboundDelivery.STATUS_APPROVED, OutboundDelivery.STATUS_REVOKE_SUBMIT);
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(inProcessStatus, IDocumentNodeFieldConstant.STATUS,
				ServiceBasicKeyStructure.OPERATOR_OR);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure("refWarehouseUUID", warehouseUUID);
		ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure("refWarehouseAreaUUID", warehouseAreaUUID);
		List<ServiceEntityNode> resultList =
				outboundDeliveryManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2, key3),
						OutboundDelivery.NODENAME, client, null);
		return resultList;
	}

	/**
	 * Get the list of pending inventory check order list for this warehouse and
	 * warehouseArea
	 * 
	 * @param warehouseUUID
	 *            :mandatory
	 * @param warehouseAreaUUID
	 *            : could be null value
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> getInProcessInventoryCheckOrder(
			String warehouseUUID, String warehouseAreaUUID)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(
				InventoryCheckOrder.STATUS_INITIAL,
				IDocumentNodeFieldConstant.STATUS);
		keyList.add(key1);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(
				warehouseUUID, "refWarehouseUUID");
		keyList.add(key2);
		ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure(
				warehouseAreaUUID, "refWarehouseAreaUUID");
		keyList.add(key3);
		return getEntityNodeListByKeyList(keyList,
				InventoryCheckOrder.NODENAME, null);
	}

	/**
	 * Core Logic of adjust each check item check status
	 *
	 * @param inventoryCheckItem
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void adjustInventoryUpdateValue(InventoryCheckItem inventoryCheckItem)
			throws ServiceEntityConfigureException, MaterialException {
		StorageCoreUnit resultStorageUnit = new StorageCoreUnit();
		resultStorageUnit.setAmount(inventoryCheckItem.getResultAmount());
		resultStorageUnit
				.setRefUnitUUID(inventoryCheckItem.getResultUnitUUID());
		resultStorageUnit.setRefMaterialSKUUUID(inventoryCheckItem
				.getRefMaterialSKUUUID());
		StorageCoreUnit rawStorageUnit = new StorageCoreUnit();
		rawStorageUnit.setAmount(inventoryCheckItem.getAmount());
		rawStorageUnit.setRefUnitUUID(inventoryCheckItem.getRefUnitUUID());
		rawStorageUnit.setRefMaterialSKUUUID(inventoryCheckItem
				.getRefMaterialSKUUUID());
		StorageCoreUnit updateStorageUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(resultStorageUnit, rawStorageUnit,
						StorageCoreUnit.OPERATOR_MINUS,
						inventoryCheckItem.getClient());
		inventoryCheckItem.setUpdateAmount(updateStorageUnit.getAmount());
		inventoryCheckItem
				.setUpdateUnitUUID(updateStorageUnit.getRefUnitUUID());
		inventoryCheckItem.setUpdateDeclaredValue(inventoryCheckItem
				.getResultDeclaredValue()
				- inventoryCheckItem.getDeclaredValue());
	}

	/**
	 * Logic to adjust item update value then calculate the item check status
	 * 
	 * @param inventoryCheckItem
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void adjustComItemCheckStatus(InventoryCheckItem inventoryCheckItem)
			throws ServiceEntityConfigureException, MaterialException {
		adjustInventoryUpdateValue(inventoryCheckItem);
		/*
		 * [Step1] Calculate status firstly by declared value
		 */
		if (inventoryCheckItem.getUpdateDeclaredValue() == 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_BALANCE);
		}
		if (inventoryCheckItem.getUpdateDeclaredValue() > 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_PROFIT);
			return;
		}
		if (inventoryCheckItem.getUpdateDeclaredValue() < 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_LOSS);
			return;
		}
		if (inventoryCheckItem.getUpdateAmount() == 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_BALANCE);
		}
		/*
		 * [Step2] Calculate status secondly by declared value
		 */
		if (inventoryCheckItem.getUpdateAmount() == 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_BALANCE);
		}
		if (inventoryCheckItem.getUpdateAmount() > 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_PROFIT);
		}
		if (inventoryCheckItem.getUpdateAmount() < 0) {
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_LOSS);
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:inventoryCheckOrder
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToInventoryCheckOrder(
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel,
			InventoryCheckOrder rawEntity) {
		docFlowProxy.convUIToDocument(inventoryCheckOrderUIModel, rawEntity);
		rawEntity.setNote(inventoryCheckOrderUIModel.getNote());
		rawEntity.setRefWarehouseAreaUUID(inventoryCheckOrderUIModel
				.getRefWarehouseAreaUUID());
		rawEntity.setId(inventoryCheckOrderUIModel.getId());
		rawEntity.setGrossCheckResult(inventoryCheckOrderUIModel
				.getGrossCheckResult());
		rawEntity.setClient(inventoryCheckOrderUIModel.getClient());
		rawEntity.setGrossUpdateValue(inventoryCheckOrderUIModel
				.getGrossUpdateValue());
		rawEntity.setPriorityCode(inventoryCheckOrderUIModel.getPriorityCode());
		rawEntity.setRefWarehouseUUID(inventoryCheckOrderUIModel
				.getRefWarehouseUUID());
	}

	public void recordCheckItem(InventoryCheckItem inventoryCheckItem, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, MaterialException {
		WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) warehouseStoreManager
				.getEntityNodeByUUID(inventoryCheckItem
								.getRefWarehouseStoreItemUUID(),
						WarehouseStoreItem.NODENAME, inventoryCheckItem
								.getClient());
		if (warehouseStoreItem != null) {
			warehouseStoreItem.setAmount(inventoryCheckItem
					.getResultAmount());
			warehouseStoreItem.setDeclaredValue(inventoryCheckItem
					.getResultDeclaredValue());
			warehouseStoreItem.setRefUnitUUID(inventoryCheckItem
					.getResultUnitUUID());
			warehouseStoreManager.updateSENode(warehouseStoreItem,
					logonUserUUID, organizationUUID);
			if (warehouseStoreItem.getAmount() <= 0) {
				warehouseStoreItem
						.setItemStatus(WarehouseStoreItem.STATUS_ARCHIVE);
			}
		}
		/*
		 * [Step3] update warehouse store item log
		 */
		updateWarehouseStoreItemLog(inventoryCheckItem,
				warehouseStoreItem, logonUserUUID, organizationUUID);
	}

	@Deprecated
	public void updateWarehouseStoreItemLog(
			InventoryCheckItem inventoryCheckItem,
			WarehouseStoreItem warehouseStoreItem, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException, MaterialException {
		WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreItem
				.clone();
		WarehouseStoreItemLog warehouseStoreItemLog = warehouseStoreManager.updateStoreItemLogFromDocItem(
				inventoryCheckItem, warehouseStoreItem,
				warehouseStoreItemBack);
		warehouseStoreManager.updateSENode(warehouseStoreItemLog, logonUserUUID,
				organizationUUID);
	}

	public void initConvWarehouseStoreToCheckItem(
			WarehouseStoreItem warehouseStoreItem,
			InventoryCheckItem inventoryCheckItem) {
		if (warehouseStoreItem != null && inventoryCheckItem != null) {
			inventoryCheckItem.setRefWarehouseStoreItemUUID(warehouseStoreItem
					.getUuid());
			inventoryCheckItem.setRefMaterialSKUUUID(warehouseStoreItem
					.getRefMaterialSKUUUID());
			inventoryCheckItem.setRefUnitUUID(warehouseStoreItem
					.getRefUnitUUID());
			inventoryCheckItem.setAmount(warehouseStoreItem.getAmount());
			inventoryCheckItem.setDeclaredValue(warehouseStoreItem
					.getDeclaredValue());
			inventoryCheckItem.setResultAmount(warehouseStoreItem.getAmount());
			inventoryCheckItem.setResultDeclaredValue(warehouseStoreItem
					.getDeclaredValue());
			inventoryCheckItem.setResultUnitUUID(warehouseStoreItem
					.getRefUnitUUID());
			inventoryCheckItem
					.setInventCheckResult(InventoryCheckItem.CHECK_RESULT_BALANCE);
		}

	}

	public void convWarehouseToUI(Warehouse warehouse,
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel) {
		if (warehouse != null) {
			inventoryCheckOrderUIModel.setRefWarehouseId(warehouse.getId());
			inventoryCheckOrderUIModel.setRefWarehouseName(warehouse.getName());
		}
	}

	public void convWarehouseAreaToUI(WarehouseArea warehouseArea,
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel) {
		if (warehouseArea != null) {
			inventoryCheckOrderUIModel
					.setRefWarehouseAreaId(warehouseArea.getId());
		}
	}

	public void convInventoryCheckOrderToUI(
			InventoryCheckOrder inventoryCheckOrder,
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel)
			throws ServiceEntityInstallationException {
		convInventoryCheckOrderToUI(inventoryCheckOrder,
				inventoryCheckOrderUIModel, null);
	}

	public void convInventoryCheckOrderToUI(
			InventoryCheckOrder inventoryCheckOrder,
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (inventoryCheckOrder != null) {
			docFlowProxy.convDocumentToUI(inventoryCheckOrder,
					inventoryCheckOrderUIModel, logonInfo);
			inventoryCheckOrderUIModel.setRefWarehouseUUID(inventoryCheckOrder
					.getRefWarehouseUUID());
			inventoryCheckOrderUIModel
					.setRefWarehouseAreaUUID(inventoryCheckOrder
							.getRefWarehouseAreaUUID());
			inventoryCheckOrderUIModel.setGrossUpdateValue(inventoryCheckOrder
					.getGrossUpdateValue());
			inventoryCheckOrderUIModel.setStatus(inventoryCheckOrder
					.getStatus());
			if (logonInfo != null) {
				Map<Integer, String> priorityCodeMap = this
						.initPriorityCodeMap(logonInfo.getLanguageCode());
				inventoryCheckOrderUIModel.setPriorityCodeValue(priorityCodeMap
						.get(inventoryCheckOrder.getPriorityCode()));
			}
			inventoryCheckOrderUIModel.setPriorityCode(inventoryCheckOrder
					.getPriorityCode());
			inventoryCheckOrderUIModel.setNote(inventoryCheckOrder.getNote());
			if(logonInfo != null){
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
				inventoryCheckOrderUIModel.setStatusValue(statusMap
						.get(inventoryCheckOrder.getStatus()));
				Map<Integer, String> inventoryCheckResultMap = initInventoryCheckResultMap(logonInfo.getLanguageCode());
				inventoryCheckOrderUIModel
						.setGrossCheckResultValue(inventoryCheckResultMap
								.get(inventoryCheckOrder.getGrossCheckResult()));
				
			}
			inventoryCheckOrderUIModel.setGrossCheckResult(inventoryCheckOrder
					.getGrossCheckResult());
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return inventoryCheckOrderSearchProxy;
	}
}
