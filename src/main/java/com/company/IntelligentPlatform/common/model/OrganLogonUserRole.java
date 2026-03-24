package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Record speci
 * 
 * @author ZhangHang
 * @date Nov 25, 2012
 * 
 */
public class OrganLogonUserRole extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.OrganLogonUserRole;

	public int USERROLE_ORG_MAN = 1;

	public int USERROLE_ORG_VICEMAN = 2;

	public int USERROLE_ORG_FINRES = 3;

	protected int logonUserRole;

	public OrganLogonUserRole() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getLogonUserRole() {
		return logonUserRole;
	}

	public void setLogonUserRole(int logonUserRole) {
		this.logonUserRole = logonUserRole;
	}
}
