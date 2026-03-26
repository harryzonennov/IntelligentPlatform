package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.SystemMandatoryModeProxy;
import com.company.IntelligentPlatform.common.service.SystemSerialParallelProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "AuthorizationGroup", catalog = "platform")
public class AuthorizationGroup extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.AuthorizationGroup;

	public static final int INPROCESS_SERIAL = SystemSerialParallelProxy.OP_SERIAL;

	public static final int INPROCESS_PARALLEL = SystemSerialParallelProxy.OP_PARALLEL;

	public static final int CROS_PROCESS_MANDATORY = SystemMandatoryModeProxy.MODE_MANDATORY;

	public static final int CROS_PROCESS_SELECTIVE = SystemMandatoryModeProxy.MODE_SELECTIVE;

	protected int innerProcessType;

	protected int crossGroupProcessType;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public AuthorizationGroup() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.innerProcessType = INPROCESS_PARALLEL;
		this.crossGroupProcessType = CROS_PROCESS_SELECTIVE;
	}

	public int getInnerProcessType() {
		return innerProcessType;
	}

	public void setInnerProcessType(int innerProcessType) {
		this.innerProcessType = innerProcessType;
	}

	public int getCrossGroupProcessType() {
		return crossGroupProcessType;
	}

	public void setCrossGroupProcessType(int crossGroupProcessType) {
		this.crossGroupProcessType = crossGroupProcessType;
	}

}
