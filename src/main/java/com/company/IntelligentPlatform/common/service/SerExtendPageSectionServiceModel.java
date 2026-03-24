package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;

public class SerExtendPageSectionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerExtendPageSection.NODENAME, nodeInstId = SerExtendPageSection.NODENAME)
	protected SerExtendPageSection serExtendPageSection;

	public SerExtendPageSection getSerExtendPageSection() {
		return serExtendPageSection;
	}

	public void setSerExtendPageSection(SerExtendPageSection serExtendPageSection) {
		this.serExtendPageSection = serExtendPageSection;
	}


}
