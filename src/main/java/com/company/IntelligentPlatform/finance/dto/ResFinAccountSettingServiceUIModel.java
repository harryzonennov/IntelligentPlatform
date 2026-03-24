package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

@Component
public class ResFinAccountSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ResFinAccountSetting.NODENAME, nodeInstId = ResFinAccountSetting.NODENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResFinAccountSettingToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResFinAccountSetting)
	protected ResFinAccountSettingUIModel resFinAccountSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstId = ResFinAccountFieldSetting.NODENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResFinAccountFieldSettingToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResFinAccountFieldSetting)
	protected List<ResFinAccountFieldSettingUIModel> resFinAccountFieldSettingUIModelList = new ArrayList<ResFinAccountFieldSettingUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = ResFinAccountProcessCode.NODENAME, nodeInstId = ResFinAccountProcessCode.NODENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResFinAccountProcessCodeToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResFinAccountProcessCode)
	protected List<ResFinAccountProcessCodeUIModel> resFinAccountProcessCodeUIModelList = new ArrayList<ResFinAccountProcessCodeUIModel>();

	public ResFinAccountSettingUIModel getResFinAccountSettingUIModel() {
		return resFinAccountSettingUIModel;
	}

	public void setResFinAccountSettingUIModel(
			ResFinAccountSettingUIModel resFinAccountSettingUIModel) {
		this.resFinAccountSettingUIModel = resFinAccountSettingUIModel;
	}

	public List<ResFinAccountFieldSettingUIModel> getResFinAccountFieldSettingUIModelList() {
		return resFinAccountFieldSettingUIModelList;
	}

	public void setResFinAccountFieldSettingUIModelList(
			List<ResFinAccountFieldSettingUIModel> resFinAccountFieldSettingUIModelList) {
		this.resFinAccountFieldSettingUIModelList = resFinAccountFieldSettingUIModelList;
	}

	public List<ResFinAccountProcessCodeUIModel> getResFinAccountProcessCodeUIModelList() {
		return resFinAccountProcessCodeUIModelList;
	}

	public void setResFinAccountProcessCodeUIModelList(
			List<ResFinAccountProcessCodeUIModel> resFinAccountProcessCodeUIModelList) {
		this.resFinAccountProcessCodeUIModelList = resFinAccountProcessCodeUIModelList;
	}

}
