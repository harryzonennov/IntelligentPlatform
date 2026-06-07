package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class LogonUserOrgServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = LogonUserOrgReference.NODENAME, nodeInstId = LogonUserOrgReference.NODENAME)
	protected LogonUserOrgReference logonUserOrgReference;

	public LogonUserOrgReference getLogonUserOrgReference() {
		return logonUserOrgReference;
	}

	public void setLogonUserOrgReference(LogonUserOrgReference logonUserOrgReference) {
		this.logonUserOrgReference = logonUserOrgReference;
	}
}
