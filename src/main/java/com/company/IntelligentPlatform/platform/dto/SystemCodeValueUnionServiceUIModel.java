package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueUnion;

@Component
public class SystemCodeValueUnionServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemCodeValueUnion.NODENAME, nodeInstId = SystemCodeValueUnion.NODENAME)
	protected SystemCodeValueUnionUIModel systemCodeValueUnionUIModel;

	public SystemCodeValueUnionUIModel getSystemCodeValueUnionUIModel() {
		return systemCodeValueUnionUIModel;
	}

	public void setSystemCodeValueUnionUIModel(SystemCodeValueUnionUIModel systemCodeValueUnionUIModel) {
		this.systemCodeValueUnionUIModel = systemCodeValueUnionUIModel;
	}
}
