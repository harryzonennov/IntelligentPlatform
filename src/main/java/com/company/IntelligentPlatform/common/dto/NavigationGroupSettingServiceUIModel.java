package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;

@Component
public class NavigationGroupSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = NavigationGroupSetting.NODENAME, nodeInstId = NavigationGroupSetting.NODENAME)
	protected NavigationGroupSettingUIModel navigationGroupSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = NavigationItemSetting.NODENAME, nodeInstId = NavigationItemSetting.NODENAME)
	protected List<NavigationItemSettingServiceUIModel> navigationItemSettingUIModelList = new ArrayList<>();

	public NavigationGroupSettingUIModel getNavigationGroupSettingUIModel() {
		return this.navigationGroupSettingUIModel;
	}

	public void setNavigationGroupSettingUIModel(
			NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		this.navigationGroupSettingUIModel = navigationGroupSettingUIModel;
	}

	public List<NavigationItemSettingServiceUIModel> getNavigationItemSettingUIModelList() {
		return this.navigationItemSettingUIModelList;
	}

	public void setNavigationItemSettingUIModelList(
			List<NavigationItemSettingServiceUIModel> navigationItemSettingUIModelList) {
		this.navigationItemSettingUIModelList = navigationItemSettingUIModelList;
	}

}
