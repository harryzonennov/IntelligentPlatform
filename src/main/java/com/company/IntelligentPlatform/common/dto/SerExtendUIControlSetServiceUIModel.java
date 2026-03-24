package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;

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
