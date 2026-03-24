package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocActionConfigure extends ServiceEntityNode {

	public static final String NODENAME =  IServiceModelConstants.ServiceDocActionConfigure;

	public static final String SENAME = ServiceDocumentSetting.SENAME;

	public ServiceDocActionConfigure() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.customSwitch = StandardSwitchProxy.SWITCH_OFF;
	}

	protected int customSwitch;
	
	protected String jsonContent;

	public String getJsonContent() {
		return jsonContent;
	}

	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

	public int getCustomSwitch() {
		return customSwitch;
	}

	public void setCustomSwitch(int customSwitch) {
		this.customSwitch = customSwitch;
	}
}
