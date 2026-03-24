package com.company.IntelligentPlatform.common.model;


import java.io.Serializable;
import java.util.Date;

public class SerialLogonInfo implements Serializable {

	public SerialLogonInfo() {
	}

	public SerialLogonInfo(String refUserUUID, String client) {
		this.refUserUUID = refUserUUID;
		this.client = client;
	}

	protected String refUserUUID;
	
	protected String languageCode;

	protected String client;

	protected int logonType;

	protected int status;

	protected Date startLogonTime;

	protected Date logOffTime;

	protected int logonTryTimes;
	
	protected ExtendedSettings extendedSettings;

	protected String homeOrganizationUUID;

	protected String resOrgUUID;

	public String getRefUserUUID() {
		return refUserUUID;
	}

	public void setRefUserUUID(String refUserUUID) {
		this.refUserUUID = refUserUUID;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public int getLogonType() {
		return logonType;
	}

	public void setLogonType(int logonType) {
		this.logonType = logonType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStartLogonTime() {
		return startLogonTime;
	}

	public void setStartLogonTime(Date startLogonTime) {
		this.startLogonTime = startLogonTime;
	}

	public Date getLogOffTime() {
		return logOffTime;
	}

	public void setLogOffTime(Date logOffTime) {
		this.logOffTime = logOffTime;
	}

	public int getLogonTryTimes() {
		return logonTryTimes;
	}

	public void setLogonTryTimes(int logonTryTimes) {
		this.logonTryTimes = logonTryTimes;
	}

	public ExtendedSettings getExtendedSettings() {
		return extendedSettings;
	}

	public void setExtendedSettings(ExtendedSettings extendedSettings) {
		this.extendedSettings = extendedSettings;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getHomeOrganizationUUID() {
		return homeOrganizationUUID;
	}

	public void setHomeOrganizationUUID(String homeOrganizationUUID) {
		this.homeOrganizationUUID = homeOrganizationUUID;
	}

	public String getResOrgUUID() {
		return resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}
}
