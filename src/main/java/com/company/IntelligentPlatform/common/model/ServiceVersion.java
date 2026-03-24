package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

public class ServiceVersion extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "ServiceVersion";

	public static final int START_VERSION = 1000;

	public static final String ID_MOBILE_ERROR_CODE = "mobileErrorCode";

	protected int version;

	public ServiceVersion() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
