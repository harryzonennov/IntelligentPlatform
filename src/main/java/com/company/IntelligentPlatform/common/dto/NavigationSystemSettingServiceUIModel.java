package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSettingActionNode;

public class NavigationSystemSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSetting.NODENAME, nodeInstId = NavigationSystemSetting.SENAME)
	protected NavigationSystemSettingUIModel navigationSystemSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = NavigationGroupSetting.NODENAME, nodeInstId = NavigationGroupSetting.NODENAME)
	protected List<NavigationGroupSettingServiceUIModel> navigationGroupSettingUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId = NavigationSystemSettingActionNode.NODEINST_ACTION_ACTIVE)
	protected NavigationSystemSettingActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_APPROVE)
	protected NavigationSystemSettingActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_SUBMIT)
	protected NavigationSystemSettingActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_REINIT)
	protected NavigationSystemSettingActionNodeUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = NavigationSystemSettingActionNode.NODENAME, nodeInstId =
			NavigationSystemSettingActionNode.NODEINST_ACTION_ARCHIVE)
	protected NavigationSystemSettingActionNodeUIModel archivedBy;

	public NavigationSystemSettingUIModel getNavigationSystemSettingUIModel() {
		return navigationSystemSettingUIModel;
	}

	public void setNavigationSystemSettingUIModel(NavigationSystemSettingUIModel navigationSystemSettingUIModel) {
		this.navigationSystemSettingUIModel = navigationSystemSettingUIModel;
	}

	public NavigationSystemSettingActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(NavigationSystemSettingActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public NavigationSystemSettingActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(NavigationSystemSettingActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public NavigationSystemSettingActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(NavigationSystemSettingActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public NavigationSystemSettingActionNodeUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(NavigationSystemSettingActionNodeUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public NavigationSystemSettingActionNodeUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(NavigationSystemSettingActionNodeUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public List<NavigationGroupSettingServiceUIModel> getNavigationGroupSettingUIModelList() {
		return this.navigationGroupSettingUIModelList;
	}

	public void setNavigationGroupSettingUIModelList(
			List<NavigationGroupSettingServiceUIModel> navigationGroupSettingUIModelList) {
		this.navigationGroupSettingUIModelList = navigationGroupSettingUIModelList;
	}

}
