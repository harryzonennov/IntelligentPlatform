package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class HostCompanyServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = HostCompany.NODENAME, nodeInstId = HostCompany.SENAME)
	protected HostCompany hostCompany;

	public HostCompany getHostCompany() {
		return hostCompany;
	}

}
