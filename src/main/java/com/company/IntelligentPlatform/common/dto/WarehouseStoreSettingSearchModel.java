package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 *
 ** 
 * @author
 * @date Mon Sep 14 11:02:28 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class WarehouseStoreSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = WarehouseStoreSetting.NODENAME, seName = WarehouseStoreSetting.SENAME, nodeInstID = WarehouseStoreSetting.NODENAME, showOnUI = false)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = WarehouseStoreSetting.NODENAME, seName = WarehouseStoreSetting.SENAME, nodeInstID = WarehouseStoreSetting.NODENAME)
	protected String refMaterialSKUUUID;
	
	@BSearchFieldConfig(fieldName = IServiceEntityNodeFieldConstant.ROOTNODEUUID, nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseUUID;

	@BSearchFieldConfig(fieldName = "resOrgUUID", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String resOrgUUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String refWarehouseName;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;
	

	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	
	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getWarehouseUUID() {
		return warehouseUUID;
	}

	public void setWarehouseUUID(String warehouseUUID) {
		this.warehouseUUID = warehouseUUID;
	}

	public String getResOrgUUID() {
		return resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


}
