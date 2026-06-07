package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.CorporateContactPerson;

public class CorporateContactPersonServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME)
	protected CorporateContactPersonUIModel corporateContactPersonUIModel;

	public CorporateContactPersonUIModel getCorporateContactPersonUIModel() {
		return corporateContactPersonUIModel;
	}

	public void setCorporateContactPersonUIModel(CorporateContactPersonUIModel corporateContactPersonUIModel) {
		this.corporateContactPersonUIModel = corporateContactPersonUIModel;
	}
}
