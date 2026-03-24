package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSettingActionNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class NavigationSystemSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSetting.NODENAME, nodeInstId = NavigationSystemSetting.SENAME)
	protected NavigationSystemSetting navigationSystemSetting;

	@IServiceModuleFieldConfig(nodeName = NavigationGroupSetting.NODENAME, nodeInstId = NavigationGroupSetting.NODENAME)
	protected List<NavigationGroupSettingServiceModel> navigationGroupSettingList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId = NavigationSystemSettingActionNode.NODEINST_ACTION_ACTIVE)
	protected NavigationSystemSettingActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId = NavigationSystemSettingActionNode.NODEINST_ACTION_APPROVE)
	protected NavigationSystemSettingActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId = NavigationSystemSettingActionNode.NODEINST_ACTION_SUBMIT)
	protected NavigationSystemSettingActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_REINIT)
	protected NavigationSystemSettingActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_ARCHIVE)
	protected NavigationSystemSettingActionNode archivedBy;

	public List<NavigationGroupSettingServiceModel> getNavigationGroupSettingList() {
		return this.navigationGroupSettingList;
	}

	public void setNavigationGroupSettingList(
			List<NavigationGroupSettingServiceModel> navigationGroupSettingList) {
		this.navigationGroupSettingList = navigationGroupSettingList;
	}

	public NavigationSystemSetting getNavigationSystemSetting() {
		return this.navigationSystemSetting;
	}

	public void setNavigationSystemSetting(
			NavigationSystemSetting navigationSystemSetting) {
		this.navigationSystemSetting = navigationSystemSetting;
	}

	public NavigationSystemSettingActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(NavigationSystemSettingActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public NavigationSystemSettingActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(NavigationSystemSettingActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public NavigationSystemSettingActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(NavigationSystemSettingActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public NavigationSystemSettingActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(NavigationSystemSettingActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public NavigationSystemSettingActionNode getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(NavigationSystemSettingActionNode archivedBy) {
		this.archivedBy = archivedBy;
	}
}
