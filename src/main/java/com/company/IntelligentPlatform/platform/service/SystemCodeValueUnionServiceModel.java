package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.SystemCodeValueUnion;

public class SystemCodeValueUnionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemCodeValueUnion.NODENAME, nodeInstId = SystemCodeValueUnion.NODENAME)
	protected SystemCodeValueUnion systemCodeValueUnion;

	public SystemCodeValueUnion getSystemCodeValueUnion() {
		return systemCodeValueUnion;
	}

	public void setSystemCodeValueUnion(SystemCodeValueUnion systemCodeValueUnion) {
		this.systemCodeValueUnion = systemCodeValueUnion;
	}
}
