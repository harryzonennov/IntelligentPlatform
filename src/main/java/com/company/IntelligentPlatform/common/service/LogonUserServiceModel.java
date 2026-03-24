package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserActionNode;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.UserRole;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class LogonUserServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = LogonUser.NODENAME, nodeInstId = LogonUser.SENAME)
	protected LogonUser logonUser;

	@IServiceModuleFieldConfig(nodeName = LogonUserOrgReference.NODENAME, nodeInstId = LogonUserOrgReference.NODENAME)
	protected List<LogonUserOrgServiceModel> logonUserOrgList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = UserRole.NODENAME, nodeInstId = UserRole.NODENAME)
	protected List<UserRoleServiceModel> userRoleList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId = LogonUserActionNode.NODEINST_ACTION_ACTIVE)
	protected LogonUserActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId = LogonUserActionNode.NODEINST_ACTION_APPROVE)
	protected LogonUserActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId = LogonUserActionNode.NODEINST_ACTION_SUBMIT)
	protected LogonUserActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_REINIT)
	protected LogonUserActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_ARCHIVE)
	protected LogonUserActionNode archivedBy;

	public LogonUser getLogonUser() {
		return this.logonUser;
	}

	public void setLogonUser(LogonUser logonUser) {
		this.logonUser = logonUser;
	}

	public List<LogonUserOrgServiceModel> getLogonUserOrgList() {
		return logonUserOrgList;
	}

	public void setLogonUserOrgList(List<LogonUserOrgServiceModel> logonUserOrgList) {
		this.logonUserOrgList = logonUserOrgList;
	}

	public List<UserRoleServiceModel> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRoleServiceModel> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public LogonUserActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(LogonUserActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public LogonUserActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(LogonUserActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public LogonUserActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(LogonUserActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public LogonUserActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(LogonUserActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public LogonUserActionNode getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(LogonUserActionNode archivedBy) {
		this.archivedBy = archivedBy;
	}
}
