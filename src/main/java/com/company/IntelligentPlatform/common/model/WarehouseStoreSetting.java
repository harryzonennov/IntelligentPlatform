package com.company.IntelligentPlatform.common.model;


import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;

public class WarehouseStoreSetting extends ReferenceNode {


	public final static String NODENAME = IServiceModelConstants.WarehouseStoreSetting;

	public final static String SENAME = Warehouse.SENAME;

	public static final int STORE_CATE_AMOUNT = 1;

	public static final int STORE_CATE_RATIO = 2;

	public static final int ERROR_TYPE_NONE = 1;

	public static final int ERROR_TYPE_SHORT = 2;

	public static final int ERROR_TYPE_EXCESS = 3;

	public static final int DATASOURCE_TYPE_MANUALSET = 1;

    public static final int DATASOURCE_TYPE_SERDOC = 2;

	protected String refMaterialSKUUUID;

	protected double maxSafeStoreAmount;

	protected String maxSafeStoreUnitUUID;

    protected double minSafeStoreAmount;

	protected String minSafeStoreUnitUUID;

	protected int safeStoreCalculateCategory;

	protected double maxStoreRatio;

	protected double minStoreRatio;

	protected double targetAverageStoreAmount;

	protected String targetAverageStoreUnitUUID;

	protected String refActiveSysWarnMsgUUID;

	protected int errorType;

	protected int dataSourceType;

	public WarehouseStoreSetting() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.safeStoreCalculateCategory = STORE_CATE_AMOUNT;
		this.errorType = ERROR_TYPE_NONE;
		this.dataSourceType = DATASOURCE_TYPE_MANUALSET;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getMaxSafeStoreAmount() {
		return maxSafeStoreAmount;
	}

	public void setMaxSafeStoreAmount(double maxSafeStoreAmount) {
		this.maxSafeStoreAmount = maxSafeStoreAmount;
	}

	public double getMinSafeStoreAmount() {
		return minSafeStoreAmount;
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

	public String getMaxSafeStoreUnitUUID() {
		return maxSafeStoreUnitUUID;
	}

	public void setMaxSafeStoreUnitUUID(String maxSafeStoreUnitUUID) {
		this.maxSafeStoreUnitUUID = maxSafeStoreUnitUUID;
	}

	public String getMinSafeStoreUnitUUID() {
		return minSafeStoreUnitUUID;
	}

	public void setMinSafeStoreUnitUUID(String minSafeStoreUnitUUID) {
		this.minSafeStoreUnitUUID = minSafeStoreUnitUUID;
	}

	public String getTargetAverageStoreUnitUUID() {
		return targetAverageStoreUnitUUID;
	}

	public void setTargetAverageStoreUnitUUID(String targetAverageStoreUnitUUID) {
		this.targetAverageStoreUnitUUID = targetAverageStoreUnitUUID;
	}

	public void setMinSafeStoreAmount(double minSafeStoreAmount) {
		this.minSafeStoreAmount = minSafeStoreAmount;
	}

	public String getRefActiveSysWarnMsgUUID() {
		return refActiveSysWarnMsgUUID;
	}

	public void setRefActiveSysWarnMsgUUID(String refActiveSysWarnMsgUUID) {
		this.refActiveSysWarnMsgUUID = refActiveSysWarnMsgUUID;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public int getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(int dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

}
