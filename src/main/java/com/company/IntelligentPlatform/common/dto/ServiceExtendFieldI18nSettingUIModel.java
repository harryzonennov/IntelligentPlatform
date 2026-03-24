package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceExtendFieldI18nSettingUIModel extends SEUIComModel {

	protected String lanKey;
	
	protected String labelValue;
	
	protected boolean activeFlag;
	
	protected String parentNodeId;

	public String getLanKey() {
		return lanKey;
	}

	public void setLanKey(String lanKey) {
		this.lanKey = lanKey;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	
	
}
