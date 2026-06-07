package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.SystemExecutorLog;

public class SystemExecutorLogServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemExecutorLog.NODENAME, nodeInstId =
			SystemExecutorLog.NODENAME)
	protected SystemExecutorLogUIModel systemExecutorLogUIModel;

	public SystemExecutorLogUIModel getSystemExecutorLogUIModel() {
		return systemExecutorLogUIModel;
	}

	public void setSystemExecutorLogUIModel(SystemExecutorLogUIModel systemExecutorLogUIModel) {
		this.systemExecutorLogUIModel = systemExecutorLogUIModel;
	}
}
