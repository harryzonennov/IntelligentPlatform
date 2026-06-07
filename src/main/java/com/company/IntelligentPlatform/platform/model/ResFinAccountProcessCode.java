package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ResFinAccountProcessCode", catalog = "platform")
public class ResFinAccountProcessCode extends ServiceEntityNode{
	
	protected int processCode;
	
	public static final String NODENAME = IServiceModelConstants.ResFinAccountProcessCode;

	public static final String SENAME = SystemResource.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public ResFinAccountProcessCode() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;		
	}

	public int getProcessCode() {
		return processCode;
	}

	public void setProcessCode(int processCode) {
		this.processCode = processCode;
	}

}
