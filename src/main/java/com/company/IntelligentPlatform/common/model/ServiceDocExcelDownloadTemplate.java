package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocExcelDownloadTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocExcelDownloadTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentReportTemplate;
	
	public ServiceDocExcelDownloadTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
}
