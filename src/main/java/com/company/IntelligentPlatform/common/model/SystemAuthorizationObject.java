package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SystemAuthorizationObject", schema = "platform")
public class SystemAuthorizationObject extends AuthorizationObject {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SystemAuthorizationObject;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	/**
	 * The short name of system AO determiner class
	 */
	protected String deterMineName;

	/**
	 * The long technique name of system AO determiner class
	 */
	protected String deterMineType;

	public SystemAuthorizationObject() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.authorizationObjectType = AUTH_TYPE_SYS;
	}

	public String getDeterMineName() {
		return deterMineName;
	}

	public void setDeterMineName(String deterMineName) {
		this.deterMineName = deterMineName;
	}

	public String getDeterMineType() {
		return deterMineType;
	}

	public void setDeterMineType(String deterMineType) {
		this.deterMineType = deterMineType;
	}

}
