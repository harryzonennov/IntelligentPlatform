package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SystemConfigureExtensionUnionUIModel extends SEUIComModel {

    protected String configureValueName;
    
    protected String configureValueId;
	
	protected String configureValue;
	
	protected String refCodeValueUUID;
	
	protected String configureSwitchProxy;
	
	protected String refCodeValueId;
	
	protected String refCodeValueName;
	
	protected String parentNodeId;

	public String getConfigureValueName() {
		return configureValueName;
	}

	public void setConfigureValueName(String configureValueName) {
		this.configureValueName = configureValueName;
	}

	public String getConfigureValueId() {
		return configureValueId;
	}

	public void setConfigureValueId(String configureValueId) {
		this.configureValueId = configureValueId;
	}

	public String getConfigureValue() {
		return configureValue;
	}

	public void setConfigureValue(String configureValue) {
		this.configureValue = configureValue;
	}

	public String getRefCodeValueUUID() {
		return refCodeValueUUID;
	}

	public void setRefCodeValueUUID(String refCodeValueUUID) {
		this.refCodeValueUUID = refCodeValueUUID;
	}

	public String getConfigureSwitchProxy() {
		return configureSwitchProxy;
	}

	public void setConfigureSwitchProxy(String configureSwitchProxy) {
		this.configureSwitchProxy = configureSwitchProxy;
	}

	public String getRefCodeValueId() {
		return refCodeValueId;
	}

	public void setRefCodeValueId(String refCodeValueId) {
		this.refCodeValueId = refCodeValueId;
	}

	public String getRefCodeValueName() {
		return refCodeValueName;
	}

	public void setRefCodeValueName(String refCodeValueName) {
		this.refCodeValueName = refCodeValueName;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

}
