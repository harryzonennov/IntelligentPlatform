package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class MaterialSKUExtendProperty extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.MaterialSKUExtendProperty;

	public static final String SENAME = MaterialStockKeepUnit.SENAME;

	public MaterialSKUExtendProperty() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	protected String refValueSettingUUID;

	protected double doubleValue;

	protected String stringValue;

	protected int intValue;

	protected int qualityInspectFlag;

	protected int measureFlag;

	protected String refUnitUUID;

	public String getRefValueSettingUUID() {
		return refValueSettingUUID;
	}

	public void setRefValueSettingUUID(String refValueSettingUUID) {
		this.refValueSettingUUID = refValueSettingUUID;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

	public int getMeasureFlag() {
		return measureFlag;
	}

	public void setMeasureFlag(int measureFlag) {
		this.measureFlag = measureFlag;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

}
