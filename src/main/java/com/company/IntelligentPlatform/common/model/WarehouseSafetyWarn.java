package com.company.IntelligentPlatform.common.model;

/**
 * Dummy Service Entity node for store warehouse safety warn message
 * @author I043125
 *
 */
public class WarehouseSafetyWarn extends WarehouseStoreSetting{

	protected double gapAmount;

	protected String gapUnitUUID;

	protected String storeUnitUUID;

	protected double storeAmount;

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

	public String getStoreUnitUUID() {
		return storeUnitUUID;
	}

	public void setStoreUnitUUID(String storeUnitUUID) {
		this.storeUnitUUID = storeUnitUUID;
	}

	public double getStoreAmount() {
		return storeAmount;
	}

	public void setStoreAmount(double storeAmount) {
		this.storeAmount = storeAmount;
	}

}
