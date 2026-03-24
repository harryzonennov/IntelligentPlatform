package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Migrated from: ThorsteinPlatform - LogonUser.hbm.xml
 * Old table: LogonUser (single shared DB)
 * New table: LogonUser (schema: platform)
 */
@Entity
@Table(name = "LogonUser", schema = "platform")
public class LogonUser extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.LogonUser;

	public static final int STATUS_INIT     = 1;
	public static final int STATUS_ACTIVE   = 2;
	public static final int STATUS_ARCHIVED = 400;

	@Column(name = "password")
	protected String password;         // NOTE: must be BCrypt — old code used plain/MD5

	@Column(name = "lockUserFlag")
	protected int lockUserFlag;

	@Column(name = "tryFailedTimes")
	protected int tryFailedTimes;

	@Column(name = "passwordInitFlag")
	protected int passwordInitFlag;

	@Column(name = "logonTime")
	protected LocalDateTime logonTime;

	@Column(name = "userType")
	protected int userType;

	@Column(name = "passwordNeedFlag")
	protected int passwordNeedFlag;

	@Column(name = "initPassword")
	protected String initPassword;

	@Column(name = "status")
	protected int status;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLockUserFlag() {
		return lockUserFlag;
	}

	public void setLockUserFlag(int lockUserFlag) {
		this.lockUserFlag = lockUserFlag;
	}

	public int getTryFailedTimes() {
		return tryFailedTimes;
	}

	public void setTryFailedTimes(int tryFailedTimes) {
		this.tryFailedTimes = tryFailedTimes;
	}

	public int getPasswordInitFlag() {
		return passwordInitFlag;
	}

	public void setPasswordInitFlag(int passwordInitFlag) {
		this.passwordInitFlag = passwordInitFlag;
	}

	public LocalDateTime getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(LocalDateTime logonTime) {
		this.logonTime = logonTime;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getPasswordNeedFlag() {
		return passwordNeedFlag;
	}

	public void setPasswordNeedFlag(int passwordNeedFlag) {
		this.passwordNeedFlag = passwordNeedFlag;
	}

	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}


	@Column(name = "checkSystemMessageFlag")
	protected boolean checkSystemMessageFlag;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public boolean isCheckSystemMessageFlag() {
		return checkSystemMessageFlag;
	}

	public void setCheckSystemMessageFlag(boolean checkSystemMessageFlag) {
		this.checkSystemMessageFlag = checkSystemMessageFlag;
	}

}
