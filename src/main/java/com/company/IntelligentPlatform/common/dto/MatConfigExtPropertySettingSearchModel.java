package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;


@Component
public class MatConfigExtPropertySettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "client", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "name", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String name;
	
	@BSearchFieldConfig(fieldName = "fieldType", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String fieldType;
	
	@BSearchFieldConfig(fieldName = "qualityInspectFlag", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected int qualityInspectFlag;
	
	@BSearchFieldConfig(fieldName = "measureFlag", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected int measureFlag;
	
	@BSearchFieldConfig(fieldName = "refUnitUUID", nodeName = MatConfigExtPropertySetting.NODENAME, seName = MatConfigExtPropertySetting.SENAME, 
			nodeInstID = MatConfigExtPropertySetting.NODENAME)
	protected String refUnitUUID;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
