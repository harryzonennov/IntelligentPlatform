package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.model.NavigationItemSetting;

@Component
public class NavigationItemSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = NavigationItemSetting.NODENAME, nodeInstId = NavigationItemSetting.NODENAME)
	protected NavigationItemSettingUIModel navigationItemSettingUIModel;

	public NavigationItemSettingUIModel getNavigationItemSettingUIModel() {
		return navigationItemSettingUIModel;
	}

	public void setNavigationItemSettingUIModel(NavigationItemSettingUIModel navigationItemSettingUIModel) {
		this.navigationItemSettingUIModel = navigationItemSettingUIModel;
	}
}
