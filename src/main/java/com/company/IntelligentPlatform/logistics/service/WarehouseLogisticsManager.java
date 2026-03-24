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

// TODO-DAO: import platform.coreFunction.DAO.WarehouseDAO;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.WarehouseConfigureProxy;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

@Service
@Transactional
public class WarehouseLogisticsManager extends WarehouseManager {

	// TODO-DAO: @Autowired
	protected BsearchService bsearchService;

	// TODO-DAO: @Autowired
// TODO-DAO: WarehouseDAO warehouseDAO;

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
		// TODO-DAO: super.setServiceEntityDAO(warehouseDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(warehouseConfigureProxy);
	}






}
