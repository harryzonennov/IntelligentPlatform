package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.*;

/**
 * Migrated from: ThorsteinPlatform - Role.hbm.xml
 * Old table: Role (db_tplatform)
 * New table: Role (schema: common)
 */
@Entity
@Table(name = "Role", schema = "platform")
public class Role extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.Role;

	@Column(name = "enableFlag")
	protected boolean enableFlag;

	@Column(name = "defaultPageUrl")
	protected String defaultPageUrl;

	public boolean isEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getDefaultPageUrl() {
		return defaultPageUrl;
	}

	public void setDefaultPageUrl(String defaultPageUrl) {
		this.defaultPageUrl = defaultPageUrl;
	}

}
