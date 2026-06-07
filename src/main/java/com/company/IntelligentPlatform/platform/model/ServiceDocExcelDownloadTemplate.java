package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ServiceDocExcelDownloadTemplate", catalog = "platform")
public class ServiceDocExcelDownloadTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocExcelDownloadTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentReportTemplate;
	
	public ServiceDocExcelDownloadTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
}
