package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.CorporateContactPerson;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class CorporateContactPersonServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME)
	protected CorporateContactPerson corporateContactPerson;

	public CorporateContactPerson getCorporateContactPerson() {
		return corporateContactPerson;
	}

	public void setCorporateContactPerson(CorporateContactPerson corporateContactPerson) {
		this.corporateContactPerson = corporateContactPerson;
	}
}
