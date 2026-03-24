package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class LogonUserUIModel extends SEUIComModel {

	protected String password;
	
	@ISEDropDownResourceMapping(resouceMapping = "LogonUser_userType", valueFieldName = "userTypeStr")
	protected int userType;
	
	protected String userTypeStr;
	
	protected int lockUserFlag;

	protected String lockUserFlagValue;

	protected int passwordInitFlag;

	protected String passwordInitFlagValue;
	
	protected String organizationUUID;
	
	protected String organizationId;
	
	protected String organizationName;
	
	protected String organizationAddress;
	
	protected String organizationTypeValue;
	
	protected int organizationType;
	
	protected String roleUUID;
	
	protected String roleId;
	
	protected String roleName;
	
	protected String roleNote;
	
	@ISEDropDownResourceMapping(resouceMapping = "LogonUser_status", valueFieldName = "genderValue")
	protected int status;
	
	protected String statusValue;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserTypeStr() {
		return userTypeStr;
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}

	public int getLockUserFlag() {
		return lockUserFlag;
	}

	public void setLockUserFlag(int lockUserFlag) {
		this.lockUserFlag = lockUserFlag;
	}

	public int getPasswordInitFlag() {
		return passwordInitFlag;
	}

	public void setPasswordInitFlag(int passwordInitFlag) {
		this.passwordInitFlag = passwordInitFlag;
	}

	public String getLockUserFlagValue() {
		return lockUserFlagValue;
	}

	public void setLockUserFlagValue(String lockUserFlagValue) {
		this.lockUserFlagValue = lockUserFlagValue;
	}

	public String getPasswordInitFlagValue() {
		return passwordInitFlagValue;
	}

	public void setPasswordInitFlagValue(String passwordInitFlagValue) {
		this.passwordInitFlagValue = passwordInitFlagValue;
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

	public String getOrganizationUUID() {
		return organizationUUID;
	}

	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(int organizationType) {
		this.organizationType = organizationType;
	}

	public String getRoleUUID() {
		return roleUUID;
	}

	public void setRoleUUID(String roleUUID) {
		this.roleUUID = roleUUID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleNote() {
		return roleNote;
	}

	public void setRoleNote(String roleNote) {
		this.roleNote = roleNote;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getOrganizationTypeValue() {
		return organizationTypeValue;
	}

	public void setOrganizationTypeValue(String organizationTypeValue) {
		this.organizationTypeValue = organizationTypeValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}


}
