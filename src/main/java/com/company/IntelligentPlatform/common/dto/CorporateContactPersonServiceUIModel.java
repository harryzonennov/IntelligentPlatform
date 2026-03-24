package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;

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
