package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemExecutorLog extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.SystemExecutorLog;

	public static final String SENAME = IServiceModelConstants.SystemExecutorSetting;
	

	
	public SystemExecutorLog() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}
	
	protected int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
}
