package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "EmployeeAttachment", catalog = "platform")
public class EmployeeAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.EmployeeAttachment;

	public static final String SENAME = IServiceModelConstants.Employee;
	
	public EmployeeAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
