package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SerExtendPageMetadata;

@Component
public class SerExtendPageMetadataServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageMetadata.NODENAME, nodeInstId = SerExtendPageMetadata.NODENAME)
	protected SerExtendPageMetadataUIModel serExtendPageMetadataUIModel;

	public SerExtendPageMetadataUIModel getSerExtendPageMetadataUIModel() {
		return serExtendPageMetadataUIModel;
	}

	public void setSerExtendPageMetadataUIModel(SerExtendPageMetadataUIModel serExtendPageMetadataUIModel) {
		this.serExtendPageMetadataUIModel = serExtendPageMetadataUIModel;
	}
}
