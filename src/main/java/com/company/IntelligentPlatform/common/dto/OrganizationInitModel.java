package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class OrganizationInitModel extends SEUIComModel {
	
	protected String refOrganizationFunction;
	
	protected String baseUUID;
	
	protected String parentOrganizationId;
	
	protected String parentOrganizationName;
	
	protected String parentOrganizationFunction;
	
	protected String parentOrganizationFunctionValue;

	public String getRefOrganizationFunction() {
		return refOrganizationFunction;
	}

	public void setRefOrganizationFunction(String refOrganizationFunction) {
		this.refOrganizationFunction = refOrganizationFunction;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public String getParentOrganizationFunction() {
		return parentOrganizationFunction;
	}

	public void setParentOrganizationFunction(String parentOrganizationFunction) {
		this.parentOrganizationFunction = parentOrganizationFunction;
	}

	public String getParentOrganizationFunctionValue() {
		return parentOrganizationFunctionValue;
	}

	public void setParentOrganizationFunctionValue(
			String parentOrganizationFunctionValue) {
		this.parentOrganizationFunctionValue = parentOrganizationFunctionValue;
	}


}
