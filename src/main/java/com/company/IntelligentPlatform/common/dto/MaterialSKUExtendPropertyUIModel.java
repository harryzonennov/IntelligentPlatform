package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MaterialSKUExtendPropertyUIModel extends SEUIComModel {

	protected String refValueSettingUUID;

	protected double doubleValue;

	protected String stringValue;

	protected int intValue;

	protected int qualityInspectFlag;

	protected int measureFlag;

	protected String qualityInspectFlagValue;

	protected String measureFlagValue;

	protected String refUnitUUID;

	protected String refUnitValue;

	public String getRefValueSettingUUID() {
		return this.refValueSettingUUID;
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

	public String getQualityInspectFlagValue() {
		return qualityInspectFlagValue;
	}

	public void setQualityInspectFlagValue(String qualityInspectFlagValue) {
		this.qualityInspectFlagValue = qualityInspectFlagValue;
	}

	public String getMeasureFlagValue() {
		return measureFlagValue;
	}

	public void setMeasureFlagValue(String measureFlagValue) {
		this.measureFlagValue = measureFlagValue;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitValue() {
		return refUnitValue;
	}

	public void setRefUnitValue(String refUnitValue) {
		this.refUnitValue = refUnitValue;
	}

}
