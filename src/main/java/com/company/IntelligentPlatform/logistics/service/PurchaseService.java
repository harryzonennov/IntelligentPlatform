package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinLogistics - PurchaseContractManager, PurchaseRequestManager,
 *           PurchaseOrderManager, PurchaseReturnOrderManager, InquiryManager
 */
@Service
@Transactional
public class PurchaseService extends ServiceEntityService {

	@Autowired
	protected PurchaseContractRepository purchaseContractRepository;

	@Autowired
	protected PurchaseRequestRepository purchaseRequestRepository;

	@Autowired
	protected PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	protected PurchaseReturnOrderRepository purchaseReturnOrderRepository;

	@Autowired
	protected InquiryRepository inquiryRepository;

	// --- PurchaseContract ---

	public PurchaseContract createContract(PurchaseContract contract, String userUUID, String orgUUID) {
		contract.setStatus(PurchaseContract.STATUS_INITIAL);
		return insertSENode(purchaseContractRepository, contract, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public PurchaseContract getContractByUuid(String uuid) {
		return getEntityNodeByUUID(purchaseContractRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<PurchaseContract> getContractsByClient(String client) {
		return purchaseContractRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<PurchaseContract> getContractsByClientAndStatus(String client, int status) {
		return purchaseContractRepository.findByClientAndStatus(client, status);
	}

	public PurchaseContract updateContract(PurchaseContract contract, String userUUID, String orgUUID) {
		return updateSENode(purchaseContractRepository, contract, userUUID, orgUUID);
	}

	public void setContractStatus(String uuid, int status, String userUUID, String orgUUID) {
		PurchaseContract contract = purchaseContractRepository.findById(uuid).orElseThrow();
		contract.setStatus(status);
		updateSENode(purchaseContractRepository, contract, userUUID, orgUUID);
	}

	public void deleteContract(String uuid) {
		deleteSENode(purchaseContractRepository, uuid);
	}

	// --- PurchaseRequest ---

	public PurchaseRequest createRequest(PurchaseRequest request, String userUUID, String orgUUID) {
		request.setStatus(PurchaseRequest.STATUS_INITIAL);
		return insertSENode(purchaseRequestRepository, request, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public PurchaseRequest getRequestByUuid(String uuid) {
		return getEntityNodeByUUID(purchaseRequestRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<PurchaseRequest> getRequestsByClient(String client) {
		return purchaseRequestRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<PurchaseRequest> getRequestsByClientAndStatus(String client, int status) {
		return purchaseRequestRepository.findByClientAndStatus(client, status);
	}

	public PurchaseRequest updateRequest(PurchaseRequest request, String userUUID, String orgUUID) {
		return updateSENode(purchaseRequestRepository, request, userUUID, orgUUID);
	}

	public void setRequestStatus(String uuid, int status, String userUUID, String orgUUID) {
		PurchaseRequest request = purchaseRequestRepository.findById(uuid).orElseThrow();
		request.setStatus(status);
		updateSENode(purchaseRequestRepository, request, userUUID, orgUUID);
	}

	public void deleteRequest(String uuid) {
		deleteSENode(purchaseRequestRepository, uuid);
	}

	// --- PurchaseOrder ---

	public PurchaseOrder createOrder(PurchaseOrder order, String userUUID, String orgUUID) {
		order.setStatus(PurchaseOrder.STATUS_INITIAL);
		return insertSENode(purchaseOrderRepository, order, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public PurchaseOrder getOrderByUuid(String uuid) {
		return getEntityNodeByUUID(purchaseOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrder> getOrdersByClient(String client) {
		return purchaseOrderRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrder> getOrdersByClientAndStatus(String client, int status) {
		return purchaseOrderRepository.findByClientAndStatus(client, status);
	}

	public PurchaseOrder updateOrder(PurchaseOrder order, String userUUID, String orgUUID) {
		return updateSENode(purchaseOrderRepository, order, userUUID, orgUUID);
	}

	public void setOrderStatus(String uuid, int status, String userUUID, String orgUUID) {
		PurchaseOrder order = purchaseOrderRepository.findById(uuid).orElseThrow();
		order.setStatus(status);
		updateSENode(purchaseOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteOrder(String uuid) {
		deleteSENode(purchaseOrderRepository, uuid);
	}

	// --- PurchaseReturnOrder ---

	public PurchaseReturnOrder createReturnOrder(PurchaseReturnOrder returnOrder, String userUUID, String orgUUID) {
		returnOrder.setStatus(PurchaseReturnOrder.STATUS_INITIAL);
		return insertSENode(purchaseReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public PurchaseReturnOrder getReturnOrderByUuid(String uuid) {
		return getEntityNodeByUUID(purchaseReturnOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<PurchaseReturnOrder> getReturnOrdersByClient(String client) {
		return purchaseReturnOrderRepository.findByClient(client);
	}

	public PurchaseReturnOrder updateReturnOrder(PurchaseReturnOrder returnOrder, String userUUID, String orgUUID) {
		return updateSENode(purchaseReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	public void setReturnOrderStatus(String uuid, int status, String userUUID, String orgUUID) {
		PurchaseReturnOrder returnOrder = purchaseReturnOrderRepository.findById(uuid).orElseThrow();
		returnOrder.setStatus(status);
		updateSENode(purchaseReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	public void deleteReturnOrder(String uuid) {
		deleteSENode(purchaseReturnOrderRepository, uuid);
	}

	// --- Inquiry ---

	public Inquiry createInquiry(Inquiry inquiry, String userUUID, String orgUUID) {
		inquiry.setStatus(Inquiry.STATUS_INIT);
		return insertSENode(inquiryRepository, inquiry, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public Inquiry getInquiryByUuid(String uuid) {
		return getEntityNodeByUUID(inquiryRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<Inquiry> getInquiriesByClient(String client) {
		return inquiryRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<Inquiry> getInquiriesByClientAndStatus(String client, int status) {
		return inquiryRepository.findByClientAndStatus(client, status);
	}

	public Inquiry updateInquiry(Inquiry inquiry, String userUUID, String orgUUID) {
		return updateSENode(inquiryRepository, inquiry, userUUID, orgUUID);
	}

	public void setInquiryStatus(String uuid, int status, String userUUID, String orgUUID) {
		Inquiry inquiry = inquiryRepository.findById(uuid).orElseThrow();
		inquiry.setStatus(status);
		updateSENode(inquiryRepository, inquiry, userUUID, orgUUID);
	}

	public void deleteInquiry(String uuid) {
		deleteSENode(inquiryRepository, uuid);
	}

}
