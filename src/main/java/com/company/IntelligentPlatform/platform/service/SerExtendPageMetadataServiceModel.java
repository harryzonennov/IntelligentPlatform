package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.SerExtendPageMetadata;

public class SerExtendPageMetadataServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerExtendPageMetadata.NODENAME, nodeInstId = SerExtendPageMetadata.NODENAME)
	protected SerExtendPageMetadata serExtendPageMetadata;

	public SerExtendPageMetadata getSerExtendPageMetadata() {
		return serExtendPageMetadata;
	}

	public void setSerExtendPageMetadata(SerExtendPageMetadata serExtendPageMetadata) {
		this.serExtendPageMetadata = serExtendPageMetadata;
	}
}
