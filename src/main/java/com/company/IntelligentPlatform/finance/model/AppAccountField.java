package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * General Service Entity class for Application-Account fee value field
 *
 * @author Zhang,Hang
 *
 */
public class AppAccountField extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "AppAccountField";

	public AppAccountField() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	/**
	 * field name to be shown on finance account UI.
	 */
	protected String fieldName;

	protected double value;

	protected int accountType;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

}
