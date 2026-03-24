package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.LogonInfo;

public class LogonInfoUIModel extends SEUIComModel{

	protected String logonUserName;

	protected String logonUserId;
	
	protected String logonUserUUID;
	
	@ISEDropDownResourceMapping(resouceMapping = "LogonInfo_status", valueFieldName = "statusValue")
	protected int status;
	
	protected String statusValue;

	protected String startLogonTime;

	protected String logOffTime;

	protected int logonTryTimes;
	
	protected String homeOrgId;
	
	protected String homeOrgName;

	public String getLogonUserUUID() {
		return logonUserUUID;
	}

	public void setLogonUserUUID(String logonUserUUID) {
		this.logonUserUUID = logonUserUUID;
	}

	public String getLogonUserName() {
		return logonUserName;
	}

	public void setLogonUserName(String logonUserName) {
		this.logonUserName = logonUserName;
	}

	public String getLogonUserId() {
		return logonUserId;
	}

	public void setLogonUserId(String logonUserId) {
		this.logonUserId = logonUserId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartLogonTime() {
		return startLogonTime;
	}

	public void setStartLogonTime(String startLogonTime) {
		this.startLogonTime = startLogonTime;
	}

	public String getLogOffTime() {
		return logOffTime;
	}

	public void setLogOffTime(String logOffTime) {
		this.logOffTime = logOffTime;
	}

	public int getLogonTryTimes() {
		return logonTryTimes;
	}

	public void setLogonTryTimes(int logonTryTimes) {
		this.logonTryTimes = logonTryTimes;
	}

	public String getHomeOrgId() {
		return homeOrgId;
	}

	public void setHomeOrgId(String homeOrgId) {
		this.homeOrgId = homeOrgId;
	}

	public String getHomeOrgName() {
		return homeOrgName;
	}

	public void setHomeOrgName(String homeOrgName) {
		this.homeOrgName = homeOrgName;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

}
