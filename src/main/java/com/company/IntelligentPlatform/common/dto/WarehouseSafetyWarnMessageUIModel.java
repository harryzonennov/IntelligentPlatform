package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class WarehouseSafetyWarnMessageUIModel extends SEUIComModel {

	protected String baseUUID;
	
	protected String refMaterialSKUUUID;

	protected String refMaterialSKUID;
	
	protected String refMaterialSKUName;
	
	protected String maxSafeStoreAmount;

	protected String maxSafeStoreUnitUUID; 
	
	protected String maxSafeStoreUnitName;
	
	protected String minSafeStoreAmount;

	protected String minSafeStoreUnitUUID; 
	
	protected String minSafeStoreUnitName;
	
	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreSetting_errorType", valueFieldName = "errorTypeValue")
	protected int errorType;
	
	protected String errorTypeValue;

	protected String warehouseId;
	
	protected String warehouseName;
	
	protected String resUserName;
	
	protected String resUserID;
	
	protected String actualStoreAmount;
	
    protected double gapAmount;
	
	protected String gapUnitUUID;
	
	protected String gapUnitName;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

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
	
	public String getMaxSafeStoreAmount() {
		return maxSafeStoreAmount;
	}

	public void setMaxSafeStoreAmount(String maxSafeStoreAmount) {
		this.maxSafeStoreAmount = maxSafeStoreAmount;
	}

	public void setMinSafeStoreAmount(String minSafeStoreAmount) {
		this.minSafeStoreAmount = minSafeStoreAmount;
	}

	public String getMaxSafeStoreUnitUUID() {
		return maxSafeStoreUnitUUID;
	}

	public void setMaxSafeStoreUnitUUID(String maxSafeStoreUnitUUID) {
		this.maxSafeStoreUnitUUID = maxSafeStoreUnitUUID;
	}

	public String getMaxSafeStoreUnitName() {
		return maxSafeStoreUnitName;
	}

	public void setMaxSafeStoreUnitName(String maxSafeStoreUnitName) {
		this.maxSafeStoreUnitName = maxSafeStoreUnitName;
	}

	public String getMinSafeStoreUnitUUID() {
		return minSafeStoreUnitUUID;
	}

	public void setMinSafeStoreUnitUUID(String minSafeStoreUnitUUID) {
		this.minSafeStoreUnitUUID = minSafeStoreUnitUUID;
	}

	public String getMinSafeStoreUnitName() {
		return minSafeStoreUnitName;
	}

	public void setMinSafeStoreUnitName(String minSafeStoreUnitName) {
		this.minSafeStoreUnitName = minSafeStoreUnitName;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorTypeValue() {
		return errorTypeValue;
	}

	public void setErrorTypeValue(String errorTypeValue) {
		this.errorTypeValue = errorTypeValue;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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

	public String getActualStoreAmount() {
		return actualStoreAmount;
	}

	public void setActualStoreAmount(String actualStoreAmount) {
		this.actualStoreAmount = actualStoreAmount;
	}

	public String getMinSafeStoreAmount() {
		return minSafeStoreAmount;
	}

	public double getGapAmount() {
		return gapAmount;
	}

	public void setGapAmount(double gapAmount) {
		this.gapAmount = gapAmount;
	}

	public String getGapUnitUUID() {
		return gapUnitUUID;
	}

	public void setGapUnitUUID(String gapUnitUUID) {
		this.gapUnitUUID = gapUnitUUID;
	}

	public String getGapUnitName() {
		return gapUnitName;
	}

	public void setGapUnitName(String gapUnitName) {
		this.gapUnitName = gapUnitName;
	}

}
