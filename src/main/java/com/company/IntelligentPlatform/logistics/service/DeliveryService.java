package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinLogistics - InboundDeliveryManager, OutboundDeliveryManager,
 *           InventoryCheckOrderManager, InventoryTransferOrderManager
 */
@Service
@Transactional
public class DeliveryService extends ServiceEntityService {

	@Autowired
	protected InboundDeliveryRepository inboundDeliveryRepository;

	@Autowired
	protected OutboundDeliveryRepository outboundDeliveryRepository;

	@Autowired
	protected InventoryCheckOrderRepository inventoryCheckOrderRepository;

	@Autowired
	protected InventoryTransferOrderRepository inventoryTransferOrderRepository;

	// --- InboundDelivery ---

	public InboundDelivery createInbound(InboundDelivery delivery, String userUUID, String orgUUID) {
		delivery.setStatus(InboundDelivery.STATUS_INITIAL);
		return insertSENode(inboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public InboundDelivery getInboundByUuid(String uuid) {
		return getEntityNodeByUUID(inboundDeliveryRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<InboundDelivery> getInboundByClient(String client) {
		return inboundDeliveryRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<InboundDelivery> getInboundByClientAndStatus(String client, int status) {
		return inboundDeliveryRepository.findByClientAndStatus(client, status);
	}

	public InboundDelivery updateInbound(InboundDelivery delivery, String userUUID, String orgUUID) {
		return updateSENode(inboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	public void setInboundStatus(String uuid, int status, String userUUID, String orgUUID) {
		InboundDelivery delivery = inboundDeliveryRepository.findById(uuid).orElseThrow();
		delivery.setStatus(status);
		updateSENode(inboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	public void deleteInbound(String uuid) {
		deleteSENode(inboundDeliveryRepository, uuid);
	}

	// --- OutboundDelivery ---

	public OutboundDelivery createOutbound(OutboundDelivery delivery, String userUUID, String orgUUID) {
		delivery.setStatus(OutboundDelivery.STATUS_INITIAL);
		return insertSENode(outboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public OutboundDelivery getOutboundByUuid(String uuid) {
		return getEntityNodeByUUID(outboundDeliveryRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<OutboundDelivery> getOutboundByClient(String client) {
		return outboundDeliveryRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<OutboundDelivery> getOutboundByClientAndStatus(String client, int status) {
		return outboundDeliveryRepository.findByClientAndStatus(client, status);
	}

	public OutboundDelivery updateOutbound(OutboundDelivery delivery, String userUUID, String orgUUID) {
		return updateSENode(outboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	public void setOutboundStatus(String uuid, int status, String userUUID, String orgUUID) {
		OutboundDelivery delivery = outboundDeliveryRepository.findById(uuid).orElseThrow();
		delivery.setStatus(status);
		updateSENode(outboundDeliveryRepository, delivery, userUUID, orgUUID);
	}

	public void deleteOutbound(String uuid) {
		deleteSENode(outboundDeliveryRepository, uuid);
	}

	// --- InventoryCheckOrder ---

	public InventoryCheckOrder createInventoryCheck(InventoryCheckOrder order, String userUUID, String orgUUID) {
		order.setStatus(InventoryCheckOrder.STATUS_INITIAL);
		return insertSENode(inventoryCheckOrderRepository, order, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public InventoryCheckOrder getInventoryCheckByUuid(String uuid) {
		return getEntityNodeByUUID(inventoryCheckOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<InventoryCheckOrder> getInventoryCheckByClient(String client) {
		return inventoryCheckOrderRepository.findByClient(client);
	}

	public InventoryCheckOrder updateInventoryCheck(InventoryCheckOrder order, String userUUID, String orgUUID) {
		return updateSENode(inventoryCheckOrderRepository, order, userUUID, orgUUID);
	}

	public void setInventoryCheckStatus(String uuid, int status, String userUUID, String orgUUID) {
		InventoryCheckOrder order = inventoryCheckOrderRepository.findById(uuid).orElseThrow();
		order.setStatus(status);
		updateSENode(inventoryCheckOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteInventoryCheck(String uuid) {
		deleteSENode(inventoryCheckOrderRepository, uuid);
	}

	// --- InventoryTransferOrder ---

	public InventoryTransferOrder createInventoryTransfer(InventoryTransferOrder order, String userUUID, String orgUUID) {
		order.setStatus(InventoryTransferOrder.STATUS_INITIAL);
		return insertSENode(inventoryTransferOrderRepository, order, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public InventoryTransferOrder getInventoryTransferByUuid(String uuid) {
		return getEntityNodeByUUID(inventoryTransferOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<InventoryTransferOrder> getInventoryTransferByClient(String client) {
		return inventoryTransferOrderRepository.findByClient(client);
	}

	public InventoryTransferOrder updateInventoryTransfer(InventoryTransferOrder order, String userUUID, String orgUUID) {
		return updateSENode(inventoryTransferOrderRepository, order, userUUID, orgUUID);
	}

	public void setInventoryTransferStatus(String uuid, int status, String userUUID, String orgUUID) {
		InventoryTransferOrder order = inventoryTransferOrderRepository.findById(uuid).orElseThrow();
		order.setStatus(status);
		updateSENode(inventoryTransferOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteInventoryTransfer(String uuid) {
		deleteSENode(inventoryTransferOrderRepository, uuid);
	}

}
