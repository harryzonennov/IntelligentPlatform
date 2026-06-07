package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.SerExtendPageSection;

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
