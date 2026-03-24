package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SystemConfigureTreeModel extends SEUIComModel{
	
	protected String uuid;
	
	protected String parentNodeUUID;
	
	protected String rootNodeUUID;
	
	protected String id;
	
	protected String name;
	
	protected String configureTypeValue;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfigureTypeValue() {
		return configureTypeValue;
	}

	public void setConfigureTypeValue(String configureTypeValue) {
		this.configureTypeValue = configureTypeValue;
	}

}
