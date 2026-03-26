package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "ActionCode", catalog = "platform")
public class ActionCode extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ActionCode;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public ActionCode() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

}
