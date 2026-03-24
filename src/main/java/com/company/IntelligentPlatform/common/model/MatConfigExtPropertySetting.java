package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class MatConfigExtPropertySetting extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.MatConfigExtPropertySetting;

	public static final String SENAME = MaterialConfigureTemplate.SENAME;

	public MatConfigExtPropertySetting() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.qualityInspectFlag = StandardSwitchProxy.SWITCH_ON;
		this.measureFlag = StandardSwitchProxy.SWITCH_ON;
	}

	protected String fieldType;

	protected int qualityInspectFlag;

	protected int measureFlag;

	protected String refUnitUUID;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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
