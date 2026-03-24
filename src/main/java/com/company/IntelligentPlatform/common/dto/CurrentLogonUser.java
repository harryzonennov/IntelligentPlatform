package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;

public class CurrentLogonUser extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "name", seName = Organization.SENAME, nodeName = LogonUser.NODENAME)
	protected String orgName;

	@ISEUIModelMapping(fieldName = "name", seName = Organization.SENAME, nodeName = LogonUser.NODENAME)
	protected Date startLogonTime;
	
	@ISEUIModelMapping(fieldName = "name", seName = Role.SENAME, nodeName = Role.NODENAME)
	protected String roleName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getStartLogonTime() {
		return startLogonTime;
	}

	public void setStartLogonTime(Date startLogonTime) {
		this.startLogonTime = startLogonTime;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
