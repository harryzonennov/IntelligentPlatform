package com.company.IntelligentPlatform.common.dto;


import com.company.IntelligentPlatform.common.model.SystemExecutorLog;

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
