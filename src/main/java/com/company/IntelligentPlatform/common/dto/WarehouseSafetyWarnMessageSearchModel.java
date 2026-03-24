package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * WarehouseSafetyWarnMessage UI Model
 ** 
 * @author
 * @date Thu Nov 19 11:09:43 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class WarehouseSafetyWarnMessageSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "refMaterialSKUID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "errorType", nodeName = WarehouseStoreSetting.NODENAME, seName = WarehouseStoreSetting.SENAME, nodeInstID = WarehouseStoreSetting.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreSettinSearch_errorType", valueFieldName = "")
	protected int errorType;

	@BSearchFieldConfig(fieldName = "id", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseID;

	@BSearchFieldConfig(fieldName = "name", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseName;

	@BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME)
	protected String resUserName;

	@BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME)
	protected String resUserID;

	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getRefMaterialSKUID() {
		return refMaterialSKUID;
	}

	public void setRefMaterialSKUID(String refMaterialSKUID) {
		this.refMaterialSKUID = refMaterialSKUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getResUserName() {
		return resUserName;
	}

	public void setResUserName(String resUserName) {
		this.resUserName = resUserName;
	}

	public String getResUserID() {
		return resUserID;
	}

	public void setResUserID(String resUserID) {
		this.resUserID = resUserID;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
