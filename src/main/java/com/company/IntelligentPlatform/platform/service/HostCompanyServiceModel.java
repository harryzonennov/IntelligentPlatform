package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.HostCompany;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class HostCompanyServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = HostCompany.NODENAME, nodeInstId = HostCompany.SENAME)
	protected HostCompany hostCompany;

	public HostCompany getHostCompany() {
		return hostCompany;
	}

}
