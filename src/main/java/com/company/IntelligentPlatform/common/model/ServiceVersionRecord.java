package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "ServiceVersionRecord", catalog = "platform")
public class ServiceVersionRecord extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceVersionRecord;

	public static final int DEF_START_VERSION = 100;

	public static final int DEF_STEP_VERSION = 10;

	public static final int DEF_START_SP = 0;

	public static final int DEF_STEP_SP = 1;

	protected int version;

	protected int startVersion;

	protected int versionStep;

	protected int sp;

	protected int spStep;

	protected int startSP;

	public ServiceVersionRecord() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.createdTime = java.time.LocalDateTime.now();
		this.lastUpdateTime = java.time.LocalDateTime.now();
		this.startVersion = DEF_START_VERSION;
		this.versionStep = DEF_STEP_VERSION;
		this.version = this.startVersion;
		this.startSP = DEF_START_SP;
		this.spStep = DEF_STEP_SP;
		this.sp = this.startSP;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getStartVersion() {
		return startVersion;
	}

	public void setStartVersion(int startVersion) {
		this.startVersion = startVersion;
	}

	public int getVersionStep() {
		return versionStep;
	}

	public void setVersionStep(int versionStep) {
		this.versionStep = versionStep;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int getSpStep() {
		return spStep;
	}

	public void setSpStep(int spStep) {
		this.spStep = spStep;
	}

	public int getStartSP() {
		return startSP;
	}

	public void setStartSP(int startSP) {
		this.startSP = startSP;
	}

}
