package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class SerExtendPageSettingUIModel extends SEUIComModel{	

    protected String navigationId;
	
	protected String accessResourceId;
	
	protected String accessPageUrl;
	
	protected String settingId;
	
	protected String refSEName;
	
	protected String refNodeName;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerExtendPageSetting_pageCategory", valueFieldName = "pageCategoryValue")
	protected int pageCategory;
	
	protected String pageCategoryValue;
	
    protected int activeSwitch;
	
	protected String activeSwitchValue;
	
    protected int systemCategory;
	
	protected String systemCategoryValue;
	
	protected String tabArray;

	@ISEDropDownResourceMapping(resouceMapping = "SerExtendPageSetting_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	public String getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}

	public String getAccessResourceId() {
		return accessResourceId;
	}

	public void setAccessResourceId(String accessResourceId) {
		this.accessResourceId = accessResourceId;
	}

	public String getAccessPageUrl() {
		return accessPageUrl;
	}

	public void setAccessPageUrl(String accessPageUrl) {
		this.accessPageUrl = accessPageUrl;
	}

	public int getPageCategory() {
		return pageCategory;
	}

	public void setPageCategory(int pageCategory) {
		this.pageCategory = pageCategory;
	}

	public String getPageCategoryValue() {
		return pageCategoryValue;
	}

	public void setPageCategoryValue(String pageCategoryValue) {
		this.pageCategoryValue = pageCategoryValue;
	}

	public String getSettingId() {
		return settingId;
	}

	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public int getActiveSwitch() {
		return activeSwitch;
	}

	public void setActiveSwitch(int activeSwitch) {
		this.activeSwitch = activeSwitch;
	}

	public String getActiveSwitchValue() {
		return activeSwitchValue;
	}

	public void setActiveSwitchValue(String activeSwitchValue) {
		this.activeSwitchValue = activeSwitchValue;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public String getSystemCategoryValue() {
		return systemCategoryValue;
	}

	public void setSystemCategoryValue(String systemCategoryValue) {
		this.systemCategoryValue = systemCategoryValue;
	}

	public String getTabArray() {
		return tabArray;
	}

	public void setTabArray(String tabArray) {
		this.tabArray = tabArray;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
