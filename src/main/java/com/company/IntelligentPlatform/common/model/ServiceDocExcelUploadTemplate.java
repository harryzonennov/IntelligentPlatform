package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocExcelUploadTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocExcelUploadTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentReportTemplate;
	
	public ServiceDocExcelUploadTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
