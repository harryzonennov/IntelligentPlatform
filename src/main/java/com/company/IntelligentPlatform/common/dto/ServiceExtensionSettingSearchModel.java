package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Component
public class ServiceExtensionSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "client", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refNodeName;

	@BSearchFieldConfig(fieldName = "note", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "refSEName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refSEName;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected int switchFlag;

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getRefNodeName() {
		return this.refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRefSEName() {
		return this.refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getSwitchFlag() {
		return this.switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

}
