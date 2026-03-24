package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;

public class SystemExecutorLogServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemExecutorLog.NODENAME, nodeInstId = SystemExecutorLog.NODENAME)
	protected SystemExecutorLog systemExecutorLog;

	public SystemExecutorLog getSystemExecutorLog() {
		return systemExecutorLog;
	}

	public void setSystemExecutorLog(SystemExecutorLog systemExecutorLog) {
		this.systemExecutorLog = systemExecutorLog;
	}
}
