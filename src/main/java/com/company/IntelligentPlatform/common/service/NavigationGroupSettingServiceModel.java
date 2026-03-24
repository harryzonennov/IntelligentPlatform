package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class NavigationGroupSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = NavigationGroupSetting.NODENAME, nodeInstId = NavigationGroupSetting.NODENAME)
	protected NavigationGroupSetting navigationGroupSetting;

	@IServiceModuleFieldConfig(nodeName = NavigationItemSetting.NODENAME, nodeInstId = NavigationItemSetting.NODENAME)
	protected List<NavigationItemSettingServiceModel> navigationItemSettingList = new ArrayList<>();

	public NavigationGroupSetting getNavigationGroupSetting() {
		return this.navigationGroupSetting;
	}

	public void setNavigationGroupSetting(
			NavigationGroupSetting navigationGroupSetting) {
		this.navigationGroupSetting = navigationGroupSetting;
	}

	public List<NavigationItemSettingServiceModel> getNavigationItemSettingList() {
		return this.navigationItemSettingList;
	}

	public void setNavigationItemSettingList(
			List<NavigationItemSettingServiceModel> navigationItemSettingList) {
		this.navigationItemSettingList = navigationItemSettingList;
	}

}
