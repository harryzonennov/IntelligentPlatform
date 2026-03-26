package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class NavigationSystemSettingUIModel extends SEUIComModel {

	protected int status;
	
	protected String statusValue;

	protected String resEmployeeUUID;
	
	protected String serviceEntityName;

	protected String resOrgUUID;
	
	@ISEDropDownResourceMapping(resouceMapping = "NavigationSystemSetting_applicationLevel", valueFieldName = "null")
	protected int applicationLevel;
	
	protected String applicationLevelValue;
	
	protected String createdTime;
	
	protected String createdBy;
	
	protected String lastUpdateBy;
	
	protected String lastUpdateTime;
	
	protected int systemCategory;
	
	protected String systemCategoryValue;

	public int getStatus() {
		return this.status;
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

	public String getResEmployeeUUID() {
		return this.resEmployeeUUID;
	}

	public void setResEmployeeUUID(String resEmployeeUUID) {
		this.resEmployeeUUID = resEmployeeUUID;
	}

	public String getServiceEntityName() {
		return this.serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public String getResOrgUUID() {
		return this.resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}

	public int getApplicationLevel() {
		return this.applicationLevel;
	}

	public void setApplicationLevel(int applicationLevel) {
		this.applicationLevel = applicationLevel;
	}

	public String getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getSystemCategory() {
		return this.systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public String getApplicationLevelValue() {
		return applicationLevelValue;
	}

	public void setApplicationLevelValue(String applicationLevelValue) {
		this.applicationLevelValue = applicationLevelValue;
	}

	public String getSystemCategoryValue() {
		return systemCategoryValue;
	}

	public void setSystemCategoryValue(String systemCategoryValue) {
		this.systemCategoryValue = systemCategoryValue;
	}

}
