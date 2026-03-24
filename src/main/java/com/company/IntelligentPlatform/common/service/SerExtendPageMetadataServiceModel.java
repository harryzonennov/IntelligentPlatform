package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;

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
