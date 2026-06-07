package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SerExtendUIControlSet;

@Component
public class SerExtendUIControlSetServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SerExtendUIControlSet.NODENAME, nodeInstId = SerExtendUIControlSet.NODENAME)
	protected SerExtendUIControlSetUIModel serExtendUIControlSetUIModel;

	public SerExtendUIControlSetUIModel getSerExtendUIControlSetUIModel() {
		return serExtendUIControlSetUIModel;
	}

	public void setSerExtendUIControlSetUIModel(SerExtendUIControlSetUIModel serExtendUIControlSetUIModel) {
		this.serExtendUIControlSetUIModel = serExtendUIControlSetUIModel;
	}

}
