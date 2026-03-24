package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class StandardMaterialUnitUIModel extends SEUIComModel {

	protected String languageCode;

	@ISEDropDownResourceMapping(resouceMapping = "StandardMaterialUnit_unitType", valueFieldName = "unitTypeValue")
	protected int unitType;
	
	protected String unitTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "StandardMaterialUnit_unitCategory", valueFieldName = "unitCategoryValue")
	protected int unitCategory;
	
	protected String unitCategoryValue;
	
    protected double toReferUnitFactor;
	
	protected String referUnitUUID;
	
	protected double toReferUnitOffset;
	
	@ISEDropDownResourceMapping(resouceMapping = "StandardMaterialUnit_systemCategory", valueFieldName = "systemCategoryValue")
	protected int systemCategory;
	
	protected String systemCategoryValue;
	
	protected String refMaterialUnitId;
	
	protected String refMaterialUnitName;

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public int getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(int unitCategory) {
		this.unitCategory = unitCategory;
	}

	public String getUnitTypeValue() {
		return unitTypeValue;
	}

	public void setUnitTypeValue(String unitTypeValue) {
		this.unitTypeValue = unitTypeValue;
	}

	public String getUnitCategoryValue() {
		return unitCategoryValue;
	}

	public void setUnitCategoryValue(String unitCategoryValue) {
		this.unitCategoryValue = unitCategoryValue;
	}

	public double getToReferUnitFactor() {
		return toReferUnitFactor;
	}

	public void setToReferUnitFactor(double toReferUnitFactor) {
		this.toReferUnitFactor = toReferUnitFactor;
	}

	public String getReferUnitUUID() {
		return referUnitUUID;
	}

	public void setReferUnitUUID(String referUnitUUID) {
		this.referUnitUUID = referUnitUUID;
	}

	public double getToReferUnitOffset() {
		return toReferUnitOffset;
	}

	public void setToReferUnitOffset(double toReferUnitOffset) {
		this.toReferUnitOffset = toReferUnitOffset;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public String getSystemCategoryValue() {
		return systemCategoryValue;
	}

	public void setSystemCategoryValue(String systemCategoryValue) {
		this.systemCategoryValue = systemCategoryValue;
	}

	public String getRefMaterialUnitId() {
		return refMaterialUnitId;
	}

	public void setRefMaterialUnitId(String refMaterialUnitId) {
		this.refMaterialUnitId = refMaterialUnitId;
	}

	public String getRefMaterialUnitName() {
		return refMaterialUnitName;
	}

	public void setRefMaterialUnitName(String refMaterialUnitName) {
		this.refMaterialUnitName = refMaterialUnitName;
	}

}
