package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class RoleAuthorization extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.RoleAuthorization;

	public static final String SENAME = Role.SENAME;

	/**
	 * It's value see Authorization Object constant:
	 */
	protected int authorizationObjectType;

	/**
	 * process Type for Authorization object :
	 * <p>
	 * default value:
	 * </p>
	 * <p>
	 * 1-"serial" means all the AOs with flag "serial" need to be considered
	 * with true result
	 * </P>
	 * <p>
	 * 2-"parallel" means if this AO matches requirement, do need to take other
	 * AOs into account with flag "parallel".
	 * </P>
	 */
	protected int processType;

	public static final int PROCESSTYPE_SERIAL = 1;

	public static final int PROCESSTYPE_PARALLEL = 2;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String actionCodeArray;

	public RoleAuthorization() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.nodeLevel = ServiceEntityNode.NODELEVEL_NODE;
	}

	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}

	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public String getActionCodeArray() {
		return actionCodeArray;
	}

	public void setActionCodeArray(String actionCodeArray) {
		this.actionCodeArray = actionCodeArray;
	}

}
