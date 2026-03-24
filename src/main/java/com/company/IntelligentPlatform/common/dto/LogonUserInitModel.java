package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class LogonUserInitModel extends SEUIComModel {
	
	protected String organizationUUID;

	protected String organizationId;	
	
	protected String roleId;
	
	protected String roleUUID;
	
	protected String client;

	public String getOrganizationUUID() {
		return organizationUUID;
	}

	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleUUID() {
		return roleUUID;
	}

	public void setRoleUUID(String roleUUID) {
		this.roleUUID = roleUUID;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

}
