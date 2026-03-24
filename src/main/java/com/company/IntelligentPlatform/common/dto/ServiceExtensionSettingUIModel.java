package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

public class ServiceExtensionSettingUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "refNodeName", seName = ServiceExtensionSetting.SENAME, nodeName = ServiceExtensionSetting.NODENAME, nodeInstID = ServiceExtensionSetting.SENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected String refNodeName;

	@ISEUIModelMapping(fieldName = "refSEName", seName = ServiceExtensionSetting.SENAME, nodeName = ServiceExtensionSetting.NODENAME, nodeInstID = ServiceExtensionSetting.SENAME, tabId = TABID_BASIC)
	protected String refSEName;

	@ISEUIModelMapping(fieldName = "switchFlag", seName = ServiceExtensionSetting.SENAME, nodeName = ServiceExtensionSetting.NODENAME, nodeInstID = ServiceExtensionSetting.SENAME, tabId = TABID_BASIC)
	protected int switchFlag;

	protected String modelId;

	public String getRefNodeName() {
		return this.refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getRefSEName() {
		return this.refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public int getSwitchFlag() {
		return this.switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
}
