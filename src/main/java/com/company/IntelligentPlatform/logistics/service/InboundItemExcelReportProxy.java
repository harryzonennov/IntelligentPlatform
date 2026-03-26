package com.company.IntelligentPlatform.logistics.service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.company.IntelligentPlatform.logistics.dto.InboundItemUIModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.InboundItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class InboundItemExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = InboundItem.NODENAME;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected InboundItemManager inboundItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected InboundDeliverySpecifier inboundDeliverySpecifier;

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.documentResourceId = CONFIG_NAME;
		this.excelModelClass = InboundItemUIModel.class;
	}

	@Override
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {
		super.insertExcelBatchData(serviceExcelReportResponseModel, modelName);
	}

	/**
	 * Main entry to update excel upload content into persistence
	 *
	 * @param serviceExcelReportResponseModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws MaterialException
	 * @throws CorporateCustomerException
	 */
	public void updateExcelBatch(ServiceExcelReportResponseModel serviceExcelReportResponseModel, String logonUserUUID,
			String organizationUUID, String client, String languageCode)
			throws ServiceEntityConfigureException, ServiceEntityInstallationException, InboundDeliveryException,
			ServiceModuleProxyException {
		Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel.getDataMap();
		if (dataMap == null || dataMap.isEmpty()) {
			return;
		}
		/*
		 * [Step1] retrieve raw data list
		 */
		Set<Integer> keySet = dataMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		List<String> warehouseIdList = new ArrayList<>();
		List<String> materialSKUIdList = new ArrayList<>();
		Map<String, List<InboundItemUIModel>> inboundItemMap = new HashMap<>();
		while (it.hasNext()) {
			int key = it.next();
			InboundItemUIModel inboundItemUIModel = (InboundItemUIModel) dataMap.get(key);
			String warehouseId = inboundItemUIModel.getRefWarehouseId();
			if (ServiceEntityStringHelper.checkNullString(warehouseId)) {
				continue;
			}
			if (ServiceEntityStringHelper.checkNullString(inboundItemUIModel.getRefMaterialSKUId())) {
				continue;
			}
			ServiceCollectionsHelper.mergeToList(warehouseIdList, warehouseId);
			ServiceCollectionsHelper.mergeToList(materialSKUIdList, inboundItemUIModel.getRefMaterialSKUId());
			List<InboundItemUIModel> tempInboundItemList = inboundItemMap.get(warehouseId);
			if (tempInboundItemList == null) {
				tempInboundItemList = new ArrayList<>();
				tempInboundItemList.add(inboundItemUIModel);
			} else {
				tempInboundItemList.add(inboundItemUIModel);
			}
			inboundItemMap.put(warehouseId, tempInboundItemList);
		}
		/*
		 * [Step2] Batch load warehouse and material information
		 */
		List<ServiceEntityNode> rawWarehouseList = warehouseManager
				.getEntityNodeListByMultipleKey(warehouseIdList, IServiceEntityNodeFieldConstant.ID, Warehouse.NODENAME, client,
						null);
		if (ServiceCollectionsHelper.checkNullList(rawWarehouseList)) {
			throw new InboundDeliveryException(InboundDeliveryException.PARA_NO_WAREHOUSE_SET, "");
		}
		List<ServiceEntityNode> rawMaterialSKUList = materialStockKeepUnitManager
				.getEntityNodeListByMultipleKey(materialSKUIdList, IServiceEntityNodeFieldConstant.ID, MaterialStockKeepUnit.NODENAME,
						client, null);
		if (ServiceCollectionsHelper.checkNullList(rawMaterialSKUList)) {
			throw new InboundDeliveryException(InboundDeliveryException.PARA_NO_SKU_FOUND, "");
		}
		/*
		 * [Step3] Process batch of inbound in every warehouse
		 */
		Set<String> inboundKeySet = inboundItemMap.keySet();
		for (String warehouseId : inboundKeySet) {
			List<InboundItemUIModel> tempInboundItemList = inboundItemMap.get(warehouseId);
			if (ServiceCollectionsHelper.checkNullList(tempInboundItemList)) {
				continue;
			}
			createInboundDeliveryBatch(warehouseId, tempInboundItemList, rawWarehouseList, rawMaterialSKUList, client,
					logonUserUUID, organizationUUID);
		}
	}

	private void createInboundDeliveryBatch(String warehouseId, List<InboundItemUIModel> inboundItemUIModelList,
			List<ServiceEntityNode> rawWarehouseList, List<ServiceEntityNode> rawMaterialSKUList, String client,
			String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, ServiceModuleProxyException {
		/*
		 * [Step1] Create new manually inbound Delivery
		 */
		Warehouse warehouse = (Warehouse) ServiceCollectionsHelper.filterSENodeById(warehouseId, rawWarehouseList);
		if (warehouse == null) {
			return;
		}
		if (ServiceCollectionsHelper.checkNullList(inboundItemUIModelList)) {
			return;
		}
		InboundDelivery inboundDelivery = (InboundDelivery) inboundDeliveryManager.newRootEntityNode(client);
		inboundDelivery.setRefWarehouseUUID(warehouse.getUuid());
		List<ServiceEntityNode> inboundItemList = new ArrayList<>();
		for (InboundItemUIModel inboundItemUIModel : inboundItemUIModelList) {
			InboundItem inboundItem = (InboundItem) inboundDeliveryManager.newEntityNode(inboundDelivery,
					InboundItem.NODENAME);
			inboundItemManager.convUIToInboundItem(inboundItemUIModel, inboundItem);
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) ServiceCollectionsHelper
					.filterSENodeById(inboundItemUIModel.getRefMaterialSKUId(), rawMaterialSKUList);
			if (materialStockKeepUnit == null) {
				continue;
			}
			inboundItem.setRefMaterialSKUUUID(materialStockKeepUnit.getUuid());
			// By default set main ref Unit
			inboundItem.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(materialStockKeepUnit));
			inboundItemList.add(inboundItem);
		}
		if (ServiceCollectionsHelper.checkNullList(inboundItemList)) {
			return;
		}
		InboundDeliveryServiceModel inboundDeliveryServiceModel = inboundDeliverySpecifier.quickCreateServiceModel(inboundDelivery, inboundItemList);
		inboundDeliveryManager
				.updateServiceModuleWithDelete(InboundDeliveryServiceModel.class, inboundDeliveryServiceModel, logonUserUUID,
						organizationUUID);
	}

}
