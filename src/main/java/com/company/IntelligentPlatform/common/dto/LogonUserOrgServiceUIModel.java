package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;

public class LogonUserOrgServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = LogonUserOrgReference.NODENAME, nodeInstId =
			LogonUserOrgReference.NODENAME)
	protected LogonUserOrganizationUIModel logonUserOrganizationUIModel;

	public LogonUserOrganizationUIModel getLogonUserOrganizationUIModel() {
		return logonUserOrganizationUIModel;
	}

	public void setLogonUserOrganizationUIModel(LogonUserOrganizationUIModel logonUserOrganizationUIModel) {
		this.logonUserOrganizationUIModel = logonUserOrganizationUIModel;
	}
}
