package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ServiceExtendFieldI18nSetting", catalog = "platform")
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
