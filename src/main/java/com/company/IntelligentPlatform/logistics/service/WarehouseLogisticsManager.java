package com.company.IntelligentPlatform.logistics.service;

import java.util.List;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.Delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.WarehouseRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.platform.service.WarehouseManager;
import com.company.IntelligentPlatform.platform.model.WarehouseConfigureProxy;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;

@Service
@Transactional
public class WarehouseLogisticsManager extends WarehouseManager {

	// TODO-DAO: @Autowired
	protected BsearchService bsearchService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected WarehouseRepository warehouseDAO;
	// TODO-DAO: @Autowired
	protected WarehouseConfigureProxy warehouseConfigureProxy;

	// TODO-DAO: @Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	// TODO-DAO: @Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	// TODO-DAO: @Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	protected Logger logger = LoggerFactory.getLogger(WarehouseLogisticsManager.class);
	
	/**
	 * Core Logic to initial copy warehouse store attributes to delivery
	 */
	public static void initCopyWarehouseStoreToDelivery(List<ServiceEntityNode> warehouseStoreItemList, Delivery delivery){
		if(ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)){
			return;
		}
		if(delivery == null){
			return;
		}
		WarehouseStoreItem sampleStoreItem = (WarehouseStoreItem) warehouseStoreItemList.get(0);
		initCopyWarehouseStoreToDelivery(sampleStoreItem, delivery);
	}
	
	/**
	 * Core Logic to initial copy warehouse store attributes to delivery
	 */
	public static void initCopyWarehouseStoreToDelivery(WarehouseStoreItem warehouseStoreItem, Delivery delivery){
		if(delivery != null && warehouseStoreItem != null){
			delivery.setProductionBatchNumber(warehouseStoreItem.getProductionBatchNumber());
			delivery.setPurchaseBatchNumber(warehouseStoreItem.getPurchaseBatchNumber());
		}
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setSeConfigureProxy(warehouseConfigureProxy);
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, warehouseDAO, this.getSeConfigureProxy()));
	}

}
