package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceEntityRegisterEntityUIModel extends SEUIComModel {
	
	protected String seModuleType;
	
	protected String seModuleName;
	
	protected String seProxyType;
	
	protected String seManagerName;
	
	protected String seManagerType;

	protected String seDAOName;
	
	protected String packageName;
	
	protected String seDAOType;
	
	protected String seProxyName;

	public String getSeModuleType() {
		return this.seModuleType;
	}

	public void setSeModuleType(String seModuleType) {
		this.seModuleType = seModuleType;
	}

	public String getSeModuleName() {
		return this.seModuleName;
	}

	public void setSeModuleName(String seModuleName) {
		this.seModuleName = seModuleName;
	}

	public String getSeProxyType() {
		return this.seProxyType;
	}

	public void setSeProxyType(String seProxyType) {
		this.seProxyType = seProxyType;
	}

	public String getSeManagerName() {
		return this.seManagerName;
	}

	public void setSeManagerName(String seManagerName) {
		this.seManagerName = seManagerName;
	}
	public String getSeManagerType() {
		return this.seManagerType;
	}

	public void setSeManagerType(String seManagerType) {
		this.seManagerType = seManagerType;
	}

	public String getSeDAOName() {
		return this.seDAOName;
	}

	public void setSeDAOName(String seDAOName) {
		this.seDAOName = seDAOName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSeDAOType() {
		return this.seDAOType;
	}

	public void setSeDAOType(String seDAOType) {
		this.seDAOType = seDAOType;
	}

	public String getSeProxyName() {
		return this.seProxyName;
	}

	public void setSeProxyName(String seProxyName) {
		this.seProxyName = seProxyName;
	}

}
