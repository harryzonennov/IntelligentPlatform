package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;

/**
 * Compound model for Logon User and Logon info
 * @author Zhang,Hang
 *
 */
public class LoginComModel {
	
	protected LogonUser logonUser;
	
	protected LogonInfo logonInfo;

	public LoginComModel(LogonUser logonUser, LogonInfo logonInfo) {
		super();
		this.logonUser = logonUser;
		this.logonInfo = logonInfo;
	}

	public LogonUser getLogonUser() {
		return logonUser;
	}

	public void setLogonUser(LogonUser logonUser) {
		this.logonUser = logonUser;
	}

	public LogonInfo getLogonInfo() {
		return logonInfo;
	}

	public void setLogonInfo(LogonInfo logonInfo) {
		this.logonInfo = logonInfo;
	}

}
