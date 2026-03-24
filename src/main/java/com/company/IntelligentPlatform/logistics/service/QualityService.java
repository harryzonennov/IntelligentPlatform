package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinLogistics - QualityInspectOrderManager, WasteProcessOrderManager
 */
@Service
@Transactional
public class QualityService extends ServiceEntityService {

	@Autowired
	protected QualityInspectOrderRepository qualityInspectOrderRepository;

	@Autowired
	protected WasteProcessOrderRepository wasteProcessOrderRepository;

	// --- QualityInspectOrder ---

	public QualityInspectOrder createInspect(QualityInspectOrder order, String userUUID, String orgUUID) {
		order.setStatus(QualityInspectOrder.STATUS_INITIAL);
		order.setCheckStatus(QualityInspectOrder.CHECKSTATUS_INITIAL);
		return insertSENode(qualityInspectOrderRepository, order, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public QualityInspectOrder getInspectByUuid(String uuid) {
		return getEntityNodeByUUID(qualityInspectOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<QualityInspectOrder> getInspectsByClient(String client) {
		return qualityInspectOrderRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<QualityInspectOrder> getInspectsByClientAndCategory(String client, int category) {
		return qualityInspectOrderRepository.findByClientAndCategory(client, category);
	}

	public QualityInspectOrder updateInspect(QualityInspectOrder order, String userUUID, String orgUUID) {
		return updateSENode(qualityInspectOrderRepository, order, userUUID, orgUUID);
	}

	public void setInspectStatus(String uuid, int status, String userUUID, String orgUUID) {
		QualityInspectOrder order = qualityInspectOrderRepository.findById(uuid).orElseThrow();
		order.setStatus(status);
		updateSENode(qualityInspectOrderRepository, order, userUUID, orgUUID);
	}

	public void setInspectCheckStatus(String uuid, int checkStatus, String userUUID, String orgUUID) {
		QualityInspectOrder order = qualityInspectOrderRepository.findById(uuid).orElseThrow();
		order.setCheckStatus(checkStatus);
		updateSENode(qualityInspectOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteInspect(String uuid) {
		deleteSENode(qualityInspectOrderRepository, uuid);
	}

	// --- WasteProcessOrder ---

	public WasteProcessOrder createWasteProcess(WasteProcessOrder order, String userUUID, String orgUUID) {
		order.setStatus(WasteProcessOrder.STATUS_INITIAL);
		return insertSENode(wasteProcessOrderRepository, order, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public WasteProcessOrder getWasteProcessByUuid(String uuid) {
		return getEntityNodeByUUID(wasteProcessOrderRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<WasteProcessOrder> getWasteProcessesByClient(String client) {
		return wasteProcessOrderRepository.findByClient(client);
	}

	public WasteProcessOrder updateWasteProcess(WasteProcessOrder order, String userUUID, String orgUUID) {
		return updateSENode(wasteProcessOrderRepository, order, userUUID, orgUUID);
	}

	public void setWasteProcessStatus(String uuid, int status, String userUUID, String orgUUID) {
		WasteProcessOrder order = wasteProcessOrderRepository.findById(uuid).orElseThrow();
		order.setStatus(status);
		updateSENode(wasteProcessOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteWasteProcess(String uuid) {
		deleteSENode(wasteProcessOrderRepository, uuid);
	}

}
