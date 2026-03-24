package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

public class IndividualAccount extends Account {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "IndividualAccount";

	protected String mobile;

	public IndividualAccount() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.accountType = Account.ACCOUNTTYPE_IND_CUSTOMER;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
