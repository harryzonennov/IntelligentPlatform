package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "LogonInfo", schema = "platform")
public class LogonInfo extends ServiceEntityNode implements Serializable {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.LogonInfo;

	public static final int LOGONTYPE_STANDARD = 1;

	public static final int STATUS_ONLINE = 1;

	public static final int STATUS_OFFLINE = 2;

	public static final String MODELID_LOGONINFO = LogonInfo.class.getSimpleName();

	public LogonInfo() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		status = STATUS_ONLINE;
		logonType = LOGONTYPE_STANDARD;
		this.languageCode = ServiceLanHelper.getDefault().toString();
	}

	public LogonInfo(String refUserUUID, String client) {
		status = STATUS_ONLINE;
		logonType = LOGONTYPE_STANDARD;
		this.languageCode = ServiceLanHelper.getDefault().toString();
		this.refUserUUID = refUserUUID;
		this.client = client;
	}

	protected String refUserUUID;
	
	protected String languageCode;

	protected int logonType;

	protected int status;

	protected Date startLogonTime;

	protected Date logOffTime;

	protected int logonTryTimes;
	
	@Transient
	protected ExtendedSettings extendedSettings;

	@Transient
	protected LogonUser logonUser;

	@Transient
	protected Organization homeOrganization;

	@Transient
	protected List<ServiceEntityNode> organizationList;

	@Transient
	protected Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap;

	@Transient
	protected List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList;

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

	public LogonUser getLogonUser() {
		return logonUser;
	}

	public void setLogonUser(LogonUser logonUser) {
		this.logonUser = logonUser;
	}

	public Organization getHomeOrganization() {
		return homeOrganization;
	}

	public void setHomeOrganization(Organization homeOrganization) {
		this.homeOrganization = homeOrganization;
	}

	public List<ServiceEntityNode> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<ServiceEntityNode> organizationList) {
		this.organizationList = organizationList;
	}

	public Map<AuthorizationObject, List<ActionCode>> getAuthorizationActionCodeMap() {
		return authorizationActionCodeMap;
	}

	public void setAuthorizationActionCodeMap(
			Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap) {
		this.authorizationActionCodeMap = authorizationActionCodeMap;
	}

	public List<AuthorizationManager.AuthorizationACUnion> getAuthorizationACUnionList() {
		return authorizationACUnionList;
	}

	public void setAuthorizationACUnionList(List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList) {
		this.authorizationACUnionList = authorizationACUnionList;
	}
}
