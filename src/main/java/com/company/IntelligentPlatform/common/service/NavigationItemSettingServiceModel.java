package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class NavigationItemSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = NavigationItemSetting.NODENAME, nodeInstId = NavigationItemSetting.NODENAME)
	protected NavigationItemSetting navigationItemSetting;

	public NavigationItemSetting getNavigationItemSetting() {
		return navigationItemSetting;
	}

	public void setNavigationItemSetting(NavigationItemSetting navigationItemSetting) {
		this.navigationItemSetting = navigationItemSetting;
	}
}
