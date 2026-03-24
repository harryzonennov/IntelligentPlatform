package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.HostCompany;

public class HostCompanyServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = HostCompany.NODENAME, nodeInstId =
			HostCompany.SENAME)
	protected HostCompanyUIModel hostCompanyUIModel;

	public HostCompanyUIModel getHostCompanyUIModel() {
		return hostCompanyUIModel;
	}

	public void setHostCompanyUIModel(HostCompanyUIModel hostCompanyUIModel) {
		this.hostCompanyUIModel = hostCompanyUIModel;
	}
}
