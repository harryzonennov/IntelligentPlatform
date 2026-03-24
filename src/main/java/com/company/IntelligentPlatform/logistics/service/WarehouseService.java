package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinLogistics - WarehouseStoreManager
 */
@Service
@Transactional
public class WarehouseService extends ServiceEntityService {

	@Autowired
	protected WarehouseStoreRepository warehouseStoreRepository;

	@Autowired
	protected WarehouseStoreItemRepository warehouseStoreItemRepository;

	// --- WarehouseStore ---

	public WarehouseStore createStore(WarehouseStore store, String userUUID, String orgUUID) {
		store.setStatus(WarehouseStore.STATUS_INITIAL);
		return insertSENode(warehouseStoreRepository, store, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public WarehouseStore getStoreByUuid(String uuid) {
		return getEntityNodeByUUID(warehouseStoreRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<WarehouseStore> getStoresByClient(String client) {
		return warehouseStoreRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<WarehouseStore> getStoresByWarehouse(String client, String refWarehouseUUID) {
		return warehouseStoreRepository.findByClientAndRefWarehouseUUID(client, refWarehouseUUID);
	}

	public WarehouseStore updateStore(WarehouseStore store, String userUUID, String orgUUID) {
		return updateSENode(warehouseStoreRepository, store, userUUID, orgUUID);
	}

	public void setStoreStatus(String uuid, int status, String userUUID, String orgUUID) {
		WarehouseStore store = warehouseStoreRepository.findById(uuid).orElseThrow();
		store.setStatus(status);
		updateSENode(warehouseStoreRepository, store, userUUID, orgUUID);
	}

	public void deleteStore(String uuid) {
		deleteSENode(warehouseStoreRepository, uuid);
	}

	// --- WarehouseStoreItem ---

	public WarehouseStoreItem createStoreItem(WarehouseStoreItem item, String userUUID, String orgUUID) {
		item.setItemStatus(WarehouseStoreItem.STATUS_INSTOCK);
		return insertSENode(warehouseStoreItemRepository, item, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public WarehouseStoreItem getStoreItemByUuid(String uuid) {
		return getEntityNodeByUUID(warehouseStoreItemRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<WarehouseStoreItem> getStoreItemsByDocument(String documentUUID) {
		return warehouseStoreItemRepository.findByDocumentUUID(documentUUID);
	}

	@Transactional(readOnly = true)
	public List<WarehouseStoreItem> getInstockItemsByWarehouse(String refWarehouseUUID) {
		return warehouseStoreItemRepository.findByRefWarehouseUUIDAndItemStatus(
				refWarehouseUUID, WarehouseStoreItem.STATUS_INSTOCK);
	}

	public WarehouseStoreItem updateStoreItem(WarehouseStoreItem item, String userUUID, String orgUUID) {
		return updateSENode(warehouseStoreItemRepository, item, userUUID, orgUUID);
	}

	public void archiveStoreItem(String uuid, String userUUID, String orgUUID) {
		WarehouseStoreItem item = warehouseStoreItemRepository.findById(uuid).orElseThrow();
		item.setItemStatus(WarehouseStoreItem.STATUS_ARCHIVE);
		updateSENode(warehouseStoreItemRepository, item, userUUID, orgUUID);
	}

	public void deleteStoreItem(String uuid) {
		deleteSENode(warehouseStoreItemRepository, uuid);
	}

}
