package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocumentReportTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocumentReportTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentSetting;
	
	public ServiceDocumentReportTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
