package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "AuthorizationObject", schema = "platform")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AuthorizationObject extends ServiceEntityNode {

	public static final String DEFAULT_PREFIX = "Author";

	public static final String FIELD_AuthorizationObjectType = "authorizationObjectType";

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.AuthorizationObject;

	public static final int AUTH_TYPE_SYS = 1;

	public static final int AUTH_TYPE_RESOURCE = 2;

	public static final int AUTH_TYPE_ACCTITLE = 3;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	protected boolean enableFlag;

	protected int authorizationObjectType;

	protected String refAGUUID;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
	protected String simObjectArray;

	protected int systemAuthorCheck = StandardSwitchProxy.SWITCH_ON;

	protected int subSystemAuthorNeed = StandardSwitchProxy.SWITCH_OFF;

	public AuthorizationObject() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public boolean isEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}

	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}

	public String getRefAGUUID() {
		return refAGUUID;
	}

	public void setRefAGUUID(String refAGUUID) {
		this.refAGUUID = refAGUUID;
	}

	public String getSimObjectArray() {
		return simObjectArray;
	}

	public void setSimObjectArray(String simObjectArray) {
		this.simObjectArray = simObjectArray;
	}

	public static int getNodeCategory() {
		return nodeCategory;
	}

	public static void setNodeCategory(int nodeCategory) {
		AuthorizationObject.nodeCategory = nodeCategory;
	}

	public int getSystemAuthorCheck() {
		return systemAuthorCheck;
	}

	public void setSystemAuthorCheck(int systemAuthorCheck) {
		this.systemAuthorCheck = systemAuthorCheck;
	}

	public int getSubSystemAuthorNeed() {
		return subSystemAuthorNeed;
	}

	public void setSubSystemAuthorNeed(int subSystemAuthorNeed) {
		this.subSystemAuthorNeed = subSystemAuthorNeed;
	}
}
