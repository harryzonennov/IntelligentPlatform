package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceExtendFieldI18nSetting extends ServiceEntityNode{
	
	public static final String NODENAME = IServiceModelConstants.ServiceExtendFieldI18nSetting;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public ServiceExtendFieldI18nSetting() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
		this.activeFlag = true;
	}
	
	protected String lanKey;
	
	protected String labelValue;
	
	protected boolean activeFlag;

	public String getLanKey() {
		return lanKey;
	}

	public void setLanKey(String lanKey) {
		this.lanKey = lanKey;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	
}
