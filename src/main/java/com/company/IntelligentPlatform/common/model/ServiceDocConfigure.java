package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocConfigure extends ServiceEntityNode{	
	
	protected String consumerUnionUUID;
	
	protected int resourceDocType;
	
	protected int switchFlag;
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceDocConfigure;
	
	protected String resourceID;
	
	protected String inputUnionUUID;
	
    protected String refSearchProxyUUID;

	public ServiceDocConfigure() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;	
		this.switchFlag = StandardSwitchProxy.SWITCH_OFF;
	}

	public String getConsumerUnionUUID() {
		return consumerUnionUUID;
	}

	public void setConsumerUnionUUID(String consumerUnionUUID) {
		this.consumerUnionUUID = consumerUnionUUID;
	}

	public int getResourceDocType() {
		return resourceDocType;
	}

	public void setResourceDocType(int resourceDocType) {
		this.resourceDocType = resourceDocType;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public String getInputUnionUUID() {
		return inputUnionUUID;
	}

	public void setInputUnionUUID(String inputUnionUUID) {
		this.inputUnionUUID = inputUnionUUID;
	}

	public String getRefSearchProxyUUID() {
		return refSearchProxyUUID;
	}

	public void setRefSearchProxyUUID(String refSearchProxyUUID) {
		this.refSearchProxyUUID = refSearchProxyUUID;
	}

}
