package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.sales.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinSalesDistribution - SalesContractManager, SalesAreaManager,
 *           SalesForcastManager, SalesReturnOrderManager, SettleOrderManager
 */
@Service
@Transactional
public class SalesService extends ServiceEntityService {

	@Autowired
	protected SalesContractRepository salesContractRepository;

	@Autowired
	protected SalesAreaRepository salesAreaRepository;

	@Autowired
	protected SalesForcastRepository salesForcastRepository;

	@Autowired
	protected SalesReturnOrderRepository salesReturnOrderRepository;

	@Autowired
	protected SettleOrderRepository settleOrderRepository;

	// --- SalesContract ---

	public SalesContract createContract(SalesContract contract, String userUUID, String orgUUID) {
		contract.setStatus(SalesContract.STATUS_INITIAL);
		return insertSENode(salesContractRepository, contract, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public SalesContract getContractByUuid(String uuid) {
		return getEntityNodeByUUID(salesContractRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<SalesContract> getContractsByClient(String client) {
		return salesContractRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<SalesContract> getContractsByClientAndStatus(String client, int status) {
		return salesContractRepository.findByClientAndStatus(client, status);
	}

	public SalesContract updateContract(SalesContract contract, String userUUID, String orgUUID) {
		return updateSENode(salesContractRepository, contract, userUUID, orgUUID);
	}

	public void setContractStatus(String uuid, int status, String userUUID, String orgUUID) {
		SalesContract contract = salesContractRepository.findById(uuid).orElseThrow();
		contract.setStatus(status);
		updateSENode(salesContractRepository, contract, userUUID, orgUUID);
	}

	public void deleteContract(String uuid) {
		deleteSENode(salesContractRepository, uuid);
	}

	// --- SalesArea ---

	public SalesArea createArea(SalesArea area, String userUUID, String orgUUID) {
		return insertSENode(salesAreaRepository, area, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public SalesArea getAreaByUuid(String uuid) {
		return getEntityNodeByUUID(salesAreaRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<SalesArea> getAreasByClient(String client) {
		return salesAreaRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<SalesArea> getAreaChildren(String parentAreaUUID) {
		return salesAreaRepository.findByParentAreaUUID(parentAreaUUID);
	}

	public SalesArea updateArea(SalesArea area, String userUUID, String orgUUID) {
		return updateSENode(salesAreaRepository, area, userUUID, orgUUID);
	}

	public void deleteArea(String uuid) {
		deleteSENode(salesAreaRepository, uuid);
	}

	// --- SalesForcast ---

	public SalesForcast createForcast(SalesForcast forcast, String userUUID, String orgUUID) {
		forcast.setStatus(SalesForcast.STATUS_INITIAL);
		return insertSENode(salesForcastRepository, forcast, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public SalesForcast getForcastByUuid(String uuid) {
		return getEntityNodeByUUID(salesForcastRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<SalesForcast> getForcastsByClient(String client) {
		return salesForcastRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<SalesForcast> getForcastsByClientAndStatus(String client, int status) {
		return salesForcastRepository.findByClientAndStatus(client, status);
	}

	public SalesForcast updateForcast(SalesForcast forcast, String userUUID, String orgUUID) {
		return updateSENode(salesForcastRepository, forcast, userUUID, orgUUID);
	}

	public void setForcastStatus(String uuid, int status, String userUUID, String orgUUID) {
		SalesForcast forcast = salesForcastRepository.findById(uuid).orElseThrow();
		forcast.setStatus(status);
		updateSENode(salesForcastRepository, forcast, userUUID, orgUUID);
	}

	public void deleteForcast(String uuid) {
		deleteSENode(salesForcastRepository, uuid);
	}

	// --- SalesReturnOrder ---

	public SalesReturnOrder createReturnOrder(SalesReturnOrder returnOrder, String userUUID, String orgUUID) {
		returnOrder.setStatus(SalesReturnOrder.STATUS_INITIAL);
		return insertSENode(salesReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public SalesReturnOrder getReturnOrderByUuid(String uuid) {
		return getEntityNodeByUUID(salesReturnOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<SalesReturnOrder> getReturnOrdersByClient(String client) {
		return salesReturnOrderRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<SalesReturnOrder> getReturnOrdersByClientAndStatus(String client, int status) {
		return salesReturnOrderRepository.findByClientAndStatus(client, status);
	}

	public SalesReturnOrder updateReturnOrder(SalesReturnOrder returnOrder, String userUUID, String orgUUID) {
		return updateSENode(salesReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	public void setReturnOrderStatus(String uuid, int status, String userUUID, String orgUUID) {
		SalesReturnOrder returnOrder = salesReturnOrderRepository.findById(uuid).orElseThrow();
		returnOrder.setStatus(status);
		updateSENode(salesReturnOrderRepository, returnOrder, userUUID, orgUUID);
	}

	public void deleteReturnOrder(String uuid) {
		deleteSENode(salesReturnOrderRepository, uuid);
	}

	// --- SettleOrder ---

	public SettleOrder createSettleOrder(SettleOrder settleOrder, String userUUID, String orgUUID) {
		settleOrder.setStatus(SettleOrder.STATUS_INIT);
		return insertSENode(settleOrderRepository, settleOrder, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public SettleOrder getSettleOrderByUuid(String uuid) {
		return getEntityNodeByUUID(settleOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<SettleOrder> getSettleOrdersByClient(String client) {
		return settleOrderRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<SettleOrder> getSettleOrdersByRefOrder(String refOrderUUID) {
		return settleOrderRepository.findByRefOrderUUID(refOrderUUID);
	}

	public SettleOrder updateSettleOrder(SettleOrder settleOrder, String userUUID, String orgUUID) {
		return updateSENode(settleOrderRepository, settleOrder, userUUID, orgUUID);
	}

	public void setSettleOrderStatus(String uuid, int status, String userUUID, String orgUUID) {
		SettleOrder settleOrder = settleOrderRepository.findById(uuid).orElseThrow();
		settleOrder.setStatus(status);
		updateSENode(settleOrderRepository, settleOrder, userUUID, orgUUID);
	}

	public void deleteSettleOrder(String uuid) {
		deleteSENode(settleOrderRepository, uuid);
	}

}
