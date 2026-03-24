package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocConsumerFieldUnion extends ServiceEntityNode{	
	
	protected String fieldLabel;
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocConsumerFieldUnion;

	public static final String SENAME = ServiceDocConsumerUnion.SENAME;

	public ServiceDocConsumerFieldUnion() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}	


}
