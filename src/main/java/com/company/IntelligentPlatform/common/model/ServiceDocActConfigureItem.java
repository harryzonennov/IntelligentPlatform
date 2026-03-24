package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocActConfigureItem extends ServiceEntityNode {

	public static final String NODENAME =  IServiceModelConstants.ServiceDocActConfigureItem;

	public static final String SENAME = ServiceDocumentSetting.SENAME;

	public ServiceDocActConfigureItem() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}
	
	protected String preStatus;

	protected int targetStatus;

	protected String authorAction;

	protected int actionCode;

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	public int getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(int targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getAuthorAction() {
		return authorAction;
	}

	public void setAuthorAction(String authorAction) {
		this.authorAction = authorAction;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
}
