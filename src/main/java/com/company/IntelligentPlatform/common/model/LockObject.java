package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class LockObject extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.LockObject;

	protected Date lockTimeDate;

	public LockObject() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public Date getLockTimeDate() {
		return lockTimeDate;
	}

	public void setLockTimeDate(Date lockTimeDate) {
		this.lockTimeDate = lockTimeDate;
	}

}
