package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceCrossDocConfigure extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.ServiceCrossDocConfigure;

	public static final String SENAME = ServiceDocumentSetting.SENAME;

	public ServiceCrossDocConfigure() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}

	protected String targetDocType;
	
	protected int crossDocRelationType;

	public String getTargetDocType() {
		return targetDocType;
	}

	public void setTargetDocType(String targetDocType) {
		this.targetDocType = targetDocType;
	}

	public int getCrossDocRelationType() {
		return crossDocRelationType;
	}

	public void setCrossDocRelationType(int crossDocRelationType) {
		this.crossDocRelationType = crossDocRelationType;
	}
}
