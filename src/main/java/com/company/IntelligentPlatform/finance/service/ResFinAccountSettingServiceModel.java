package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

public class ResFinAccountSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ResFinAccountSetting.NODENAME, nodeInstId = ResFinAccountSetting.NODENAME)
	protected ResFinAccountSetting resFinAccountSetting;

	@IServiceModuleFieldConfig(nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstId = ResFinAccountFieldSetting.NODENAME)
	protected List<ServiceEntityNode> resFinAccountFieldSettingList = new ArrayList<ServiceEntityNode>();

	@IServiceModuleFieldConfig(nodeName = ResFinAccountProcessCode.NODENAME, nodeInstId = ResFinAccountProcessCode.NODENAME)
	protected List<ServiceEntityNode> resFinAccountProcessCodeList = new ArrayList<ServiceEntityNode>();

	public ResFinAccountSetting getResFinAccountSetting() {
		return resFinAccountSetting;
	}

	public void setResFinAccountSetting(
			ResFinAccountSetting resFinAccountSetting) {
		this.resFinAccountSetting = resFinAccountSetting;
	}

	public List<ServiceEntityNode> getResFinAccountFieldSettingList() {
		return resFinAccountFieldSettingList;
	}

	public void setResFinAccountFieldSettingList(
			List<ServiceEntityNode> resFinAccountFieldSettingList) {
		this.resFinAccountFieldSettingList = resFinAccountFieldSettingList;
	}

	public List<ServiceEntityNode> getResFinAccountProcessCodeList() {
		return resFinAccountProcessCodeList;
	}

	public void setResFinAccountProcessCodeList(
			List<ServiceEntityNode> resFinAccountProcessCodeList) {
		this.resFinAccountProcessCodeList = resFinAccountProcessCodeList;
	}

}
