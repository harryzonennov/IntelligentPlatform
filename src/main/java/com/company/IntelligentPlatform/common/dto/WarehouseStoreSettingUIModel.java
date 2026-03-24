package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class WarehouseStoreSettingUIModel extends SEUIComModel {
	
	protected String baseUUID;

	protected String resOrgUUID;

	protected String resEmployeeUUID;
	
	protected String refMaterialSKUUUID;
	
	protected String refMaterialSKUId;
	
	protected String refMaterialSKUName;
	
	protected double maxSafeStoreAmount;
	
	protected String maxSafeStoreUnitUUID; 
	
	protected String maxSafeStoreUnitName;
	
	protected double minSafeStoreAmount;
	
	protected String minSafeStoreUnitUUID; 
	
	protected String minSafeStoreUnitName;
	
	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreSetting_storeCalculateCategory", valueFieldName = "safeStoreCalculateCategoryValue")
	protected int safeStoreCalculateCategory;
	
	protected String safeStoreCalculateCategoryValue;

	protected double maxStoreRatio;
	
	protected double minStoreRatio;
	
	protected double targetAverageStoreAmount;
	
	protected String targetAverageStoreUnitUUID;
	
	protected String targetAverageStoreUnitName;

	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreSetting_errorType", valueFieldName = "errorTypeValue")
	protected int errorType;
	
	protected String errorTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreSetting_dataSourceType", valueFieldName = "dataSourceTypeValue")
	protected int dataSourceType;
	
	protected String dataSourceTypeValue;
	
	protected String refUUID;
	
	protected String refWarehouseId;
	
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

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public double getMaxSafeStoreAmount() {
		return maxSafeStoreAmount;
	}

	public void setMaxSafeStoreAmount(double maxSafeStoreAmount) {
		this.maxSafeStoreAmount = maxSafeStoreAmount;
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

	public double getMinSafeStoreAmount() {
		return minSafeStoreAmount;
	}

	public void setMinSafeStoreAmount(double minSafeStoreAmount) {
		this.minSafeStoreAmount = minSafeStoreAmount;
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

	public String getSafeStoreCalculateCategoryValue() {
		return safeStoreCalculateCategoryValue;
	}

	public void setSafeStoreCalculateCategoryValue(
			String safeStoreCalculateCategoryValue) {
		this.safeStoreCalculateCategoryValue = safeStoreCalculateCategoryValue;
	}

	public int getSafeStoreCalculateCategory() {
		return safeStoreCalculateCategory;
	}

	public void setSafeStoreCalculateCategory(int safeStoreCalculateCategory) {
		this.safeStoreCalculateCategory = safeStoreCalculateCategory;
	}

	public double getMaxStoreRatio() {
		return maxStoreRatio;
	}

	public void setMaxStoreRatio(double maxStoreRatio) {
		this.maxStoreRatio = maxStoreRatio;
	}

	public double getMinStoreRatio() {
		return minStoreRatio;
	}

	public void setMinStoreRatio(double minStoreRatio) {
		this.minStoreRatio = minStoreRatio;
	}

	public double getTargetAverageStoreAmount() {
		return targetAverageStoreAmount;
	}

	public void setTargetAverageStoreAmount(double targetAverageStoreAmount) {
		this.targetAverageStoreAmount = targetAverageStoreAmount;
	}

	public String getTargetAverageStoreUnitUUID() {
		return targetAverageStoreUnitUUID;
	}

	public void setTargetAverageStoreUnitUUID(String targetAverageStoreUnitUUID) {
		this.targetAverageStoreUnitUUID = targetAverageStoreUnitUUID;
	}

	public String getTargetAverageStoreUnitName() {
		return targetAverageStoreUnitName;
	}

	public void setTargetAverageStoreUnitName(String targetAverageStoreUnitName) {
		this.targetAverageStoreUnitName = targetAverageStoreUnitName;
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

	public int getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(int dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getDataSourceTypeValue() {
		return dataSourceTypeValue;
	}

	public void setDataSourceTypeValue(String dataSourceTypeValue) {
		this.dataSourceTypeValue = dataSourceTypeValue;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getResOrgUUID() {
		return resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}

	public String getResEmployeeUUID() {
		return resEmployeeUUID;
	}

	public void setResEmployeeUUID(String resEmployeeUUID) {
		this.resEmployeeUUID = resEmployeeUUID;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

}
