package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.SystemExecutorLogUIModel;
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingUIModel;
import com.company.IntelligentPlatform.common.service.SystemExecutorSettingManager;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;

@Component
public class SystemExecutorSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemExecutorSetting.NODENAME, nodeInstId = SystemExecutorSetting.SENAME, convToUIMethod = SystemExecutorSettingManager.METHOD_ConvSystemExecutorSettingToUI, convUIToMethod = SystemExecutorSettingManager.METHOD_ConvUIToSystemExecutorSetting)
	protected SystemExecutorSettingUIModel systemExecutorSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SystemExecutorLog.NODENAME, nodeInstId = SystemExecutorLog.NODENAME, convToUIMethod = SystemExecutorSettingManager.METHOD_ConvSystemExecutorLogToUI)
	protected List<SystemExecutorLogUIModel> systemExecutorLogUIModelList = new ArrayList<SystemExecutorLogUIModel>();

	public SystemExecutorSettingUIModel getSystemExecutorSettingUIModel() {
		return this.systemExecutorSettingUIModel;
	}

	public void setSystemExecutorSettingUIModel(
			SystemExecutorSettingUIModel systemExecutorSettingUIModel) {
		this.systemExecutorSettingUIModel = systemExecutorSettingUIModel;
	}

	public List<SystemExecutorLogUIModel> getSystemExecutorLogUIModelList() {
		return this.systemExecutorLogUIModelList;
	}

	public void setSystemExecutorLogUIModelList(
			List<SystemExecutorLogUIModel> systemExecutorLogUIModelList) {
		this.systemExecutorLogUIModelList = systemExecutorLogUIModelList;
	}

}
