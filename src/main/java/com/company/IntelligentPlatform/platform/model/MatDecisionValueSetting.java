package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "MatDecisionValueSetting", catalog = "platform")
public class MatDecisionValueSetting extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.MatDecisionValueSetting;

	public static final String SENAME = MaterialConfigureTemplate.SENAME;

	public MatDecisionValueSetting() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	protected int valueUsage;

	protected String rawValue;

	public int getValueUsage() {
		return valueUsage;
	}

	public void setValueUsage(int valueUsage) {
		this.valueUsage = valueUsage;
	}

	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

}
