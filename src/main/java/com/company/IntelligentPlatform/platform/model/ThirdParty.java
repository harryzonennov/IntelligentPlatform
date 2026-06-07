package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.CorporateAccount;

public class ThirdParty extends CorporateAccount{

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "ThirdParty";

	public ThirdParty(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}
