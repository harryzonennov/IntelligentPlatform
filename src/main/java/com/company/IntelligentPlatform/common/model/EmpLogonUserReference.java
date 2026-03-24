package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class EmpLogonUserReference extends ReferenceNode {


	public final static String NODENAME = "EmpLogonUserReference";

	public final static String SENAME = Employee.SENAME;

	protected int mainFlag;

	public EmpLogonUserReference() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.nodeLevel = ServiceEntityNode.NODELEVEL_NODE;
		this.mainFlag = StandardSwitchProxy.SWITCH_ON;
	}

	public int getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(int mainFlag) {
		this.mainFlag = mainFlag;
	}
}
