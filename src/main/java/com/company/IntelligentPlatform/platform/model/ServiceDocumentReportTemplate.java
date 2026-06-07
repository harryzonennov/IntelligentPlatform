package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ServiceDocumentReportTemplate", catalog = "platform")
public class ServiceDocumentReportTemplate extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocumentReportTemplate;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentSetting;
	
	public ServiceDocumentReportTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
