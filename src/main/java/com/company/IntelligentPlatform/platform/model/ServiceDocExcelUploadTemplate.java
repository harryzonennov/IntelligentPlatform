package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ServiceDocExcelUploadTemplate", catalog = "platform")
public class ServiceDocExcelUploadTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocExcelUploadTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentReportTemplate;
	
	public ServiceDocExcelUploadTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
