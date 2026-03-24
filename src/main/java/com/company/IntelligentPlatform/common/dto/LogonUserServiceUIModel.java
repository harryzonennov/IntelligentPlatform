package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserActionNode;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.UserRole;

@Component
public class LogonUserServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = LogonUser.NODENAME, nodeInstId = LogonUser.SENAME)
	protected LogonUserUIModel logonUserUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = LogonUserOrgReference.NODENAME, nodeInstId = LogonUserOrgReference.NODENAME)
	protected List<LogonUserOrgServiceUIModel> logonUserOrganizationUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = UserRole.NODENAME, nodeInstId = UserRole.NODENAME)
	protected List<UserRoleServiceUIModel> userRoleUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId = LogonUserActionNode.NODEINST_ACTION_ACTIVE)
	protected LogonUserActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_APPROVE)
	protected LogonUserActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_SUBMIT)
	protected LogonUserActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_REINIT)
	protected LogonUserActionNodeUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = LogonUserActionNode.NODENAME, nodeInstId =
			LogonUserActionNode.NODEINST_ACTION_ARCHIVE)
	protected LogonUserActionNodeUIModel archivedBy;

	public LogonUserUIModel getLogonUserUIModel() {
		return this.logonUserUIModel;
	}

	public void setLogonUserUIModel(LogonUserUIModel logonUserUIModel) {
		this.logonUserUIModel = logonUserUIModel;
	}

	public List<LogonUserOrgServiceUIModel> getLogonUserOrganizationUIModelList() {
		return logonUserOrganizationUIModelList;
	}

	public void setLogonUserOrganizationUIModelList(List<LogonUserOrgServiceUIModel> logonUserOrganizationUIModelList) {
		this.logonUserOrganizationUIModelList = logonUserOrganizationUIModelList;
	}

	public List<UserRoleServiceUIModel> getUserRoleUIModelList() {
		return userRoleUIModelList;
	}

	public void setUserRoleUIModelList(List<UserRoleServiceUIModel> userRoleUIModelList) {
		this.userRoleUIModelList = userRoleUIModelList;
	}

	public LogonUserActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(LogonUserActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public LogonUserActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(LogonUserActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public LogonUserActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(LogonUserActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public LogonUserActionNodeUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(LogonUserActionNodeUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public LogonUserActionNodeUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(LogonUserActionNodeUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}
}
