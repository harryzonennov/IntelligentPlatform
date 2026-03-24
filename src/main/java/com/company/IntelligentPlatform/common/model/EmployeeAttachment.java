package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class EmployeeAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.EmployeeAttachment;

	public static final String SENAME = IServiceModelConstants.Employee;
	
	public EmployeeAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
