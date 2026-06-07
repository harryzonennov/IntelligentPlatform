package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.NavigationItemSetting;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
