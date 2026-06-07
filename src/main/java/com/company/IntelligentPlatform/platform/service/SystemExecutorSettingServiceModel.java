package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.SystemExecutorLog;
import com.company.IntelligentPlatform.platform.model.SystemExecutorSetting;

public class SystemExecutorSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemExecutorSetting.NODENAME, nodeInstId = SystemExecutorSetting.SENAME)
	protected SystemExecutorSetting systemExecutorSetting;

	@IServiceModuleFieldConfig(nodeName = SystemExecutorLog.NODENAME, nodeInstId = SystemExecutorLog.NODENAME, blockUpdate = true)
	protected List<ServiceEntityNode> systemExecutorLogList = new ArrayList<>();

	public SystemExecutorSetting getSystemExecutorSetting() {
		return this.systemExecutorSetting;
	}

	public void setSystemExecutorSetting(
			SystemExecutorSetting systemExecutorSetting) {
		this.systemExecutorSetting = systemExecutorSetting;
	}

	public List<ServiceEntityNode> getSystemExecutorLogList() {
		return this.systemExecutorLogList;
	}

	public void setSystemExecutorLogList(
			List<ServiceEntityNode> systemExecutorLogList) {
		this.systemExecutorLogList = systemExecutorLogList;
	}

}
