package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocDeletionSetting extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.ServiceDocDeletionSetting;

	public static final String SENAME = ServiceDocumentSetting.SENAME;

	public static final int DELETESTG_ADM_DELETE = 1;

	public static final int DELETESTG_BUS_DELETE = 2;

	public static final int DELETESTG_CONFIG_DELETE = 3;

	public static final int DELETESTG_FORBD_DELETE = 4;

	protected String refServiceEntityName;

	protected String refNodeName;

	protected String nodeInstId;

	protected int deletionStrategy;

	protected String admDeleteStatus;

	public ServiceDocDeletionSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.deletionStrategy = DELETESTG_BUS_DELETE;
	}

	public String getRefServiceEntityName() {
		return refServiceEntityName;
	}

	public void setRefServiceEntityName(String refServiceEntityName) {
		this.refServiceEntityName = refServiceEntityName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public int getDeletionStrategy() {
		return deletionStrategy;
	}

	public void setDeletionStrategy(int deletionStrategy) {
		this.deletionStrategy = deletionStrategy;
	}

	public String getAdmDeleteStatus() {
		return admDeleteStatus;
	}

	public void setAdmDeleteStatus(String admDeleteStatus) {
		this.admDeleteStatus = admDeleteStatus;
	}
}
