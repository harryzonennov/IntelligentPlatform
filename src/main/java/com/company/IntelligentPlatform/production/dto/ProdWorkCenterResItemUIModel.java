package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdWorkCenterResItemUIModel extends SEUIComModel {

	protected String resourceId;

	protected String resourceName;
	
	protected int resourceType;
	
	protected String resourceTypeValue;
	
	protected int keyResourceFlag;
	
	protected String keyResourceValue;

	protected String refUUID;
	
	protected String refWorkCenterId;

	public int getKeyResourceFlag() {
		return this.keyResourceFlag;
	}

	public void setKeyResourceFlag(int keyResourceFlag) {
		this.keyResourceFlag = keyResourceFlag;
	}

	public String getRefUUID() {
		return this.refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeValue() {
		return resourceTypeValue;
	}

	public void setResourceTypeValue(String resourceTypeValue) {
		this.resourceTypeValue = resourceTypeValue;
	}

	public String getKeyResourceValue() {
		return keyResourceValue;
	}

	public void setKeyResourceValue(String keyResourceValue) {
		this.keyResourceValue = keyResourceValue;
	}

	public String getRefWorkCenterId() {
		return refWorkCenterId;
	}

	public void setRefWorkCenterId(String refWorkCenterId) {
		this.refWorkCenterId = refWorkCenterId;
	}

	
}
