package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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
