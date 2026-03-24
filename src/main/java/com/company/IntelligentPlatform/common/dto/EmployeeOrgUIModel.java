package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.Organization;

public class EmployeeOrgUIModel extends SEUIComModel {

	protected String refUUID;

	protected String refSEName;

	protected String refNodeName;
	
	protected int employeeRole;

	protected String employeeRoleValue;

	protected String organizationId;

	protected String organizationName;

	protected int organizationFunction;

	protected String organizationFunctionValue;

	@ISEUIModelMapping(fieldName = "address", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String address;

	public int getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(int employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getEmployeeRoleValue() {
		return employeeRoleValue;
	}

	public void setEmployeeRoleValue(String employeeRoleValue) {
		this.employeeRoleValue = employeeRoleValue;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

	public String getOrganizationFunctionValue() {
		return organizationFunctionValue;
	}

	public void setOrganizationFunctionValue(String organizationFunctionValue) {
		this.organizationFunctionValue = organizationFunctionValue;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}


}
