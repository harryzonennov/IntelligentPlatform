package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class EmployeeOrgReference extends ReferenceNode {


	public final static String NODENAME = "EmployeeOrgReference";

	public final static String SENAME = Employee.SENAME;

	protected int employeeRole;

	public EmployeeOrgReference() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.nodeLevel = ServiceEntityNode.NODELEVEL_NODE;
	}

	public int getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(int employeeRole) {
		this.employeeRole = employeeRole;
	}

}
