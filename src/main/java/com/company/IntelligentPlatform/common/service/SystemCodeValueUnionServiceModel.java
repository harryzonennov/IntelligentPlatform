package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;


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
