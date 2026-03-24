package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocConfigureParaGroup extends ServiceEntityNode{
	
	public static final String GROUPID_DEFAULT = "default";
	
	protected int logicOperator;
	
	protected int layer;
	
	protected String refParentGroupUUID;
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocConfigureParaGroup;

	public static final String SENAME = ServiceDocConfigure.SENAME;

	public ServiceDocConfigureParaGroup() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.layer = 1;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getRefParentGroupUUID() {
		return refParentGroupUUID;
	}

	public void setRefParentGroupUUID(String refParentGroupUUID) {
		this.refParentGroupUUID = refParentGroupUUID;
	}

}
